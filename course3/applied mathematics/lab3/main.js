const fs = require("fs");
let fileContent;
let alphabet = {}; // объект, поля которого - символы, а их значение - вероятность
let codeArray = []; // массив кодов блоков символов
let searchedContent = ''; // сообщение после декодирования
let cumulative = []; // массив кумулативных вероятностей
let realLength = 0;
let blockLength = 2; // длина блока считываемых символов

debug();

function main() {
    let stdIn = process.openStdin();

    console.log('Input the filename (ex: doc.txt)\nDefault directory: text_files');

    stdIn.on('data', function (filename) {
        filename = "text_files//" + filename.toString().substr(0, filename.length - 1);

        try {
            readFile(filename);

            console.log('Result:');

            setProbability();
            huffmanAlgorithm();
            fanoAlgorithm();
            printAlphabet();
            printEntropy();
        } catch (err) {
            console.log('Error: no such txt file')
        }

        process.exit();
    });
}

function debug() {
    console.log('Input the filename (ex: doc.txt)\nDefault directory: text_files');

    let filename = "text_files//test.txt";

    try {
        readFile(filename);

        console.log('Result:');
        console.log('Length of block:', blockLength);

        setProbability();
        countCumulativeProbability();
        coding();
        printCompressionCoefficient();
        decoding();
        checkContentOnIdentity();
    } catch (err) {
        console.log('Error: no such txt file')
    }
}

/**
 * Считывает содержимое файла и формирует по нему алфавит символов
 *
 * @param fileName
 */
function readFile(fileName) {
    fileContent = fs.readFileSync(fileName, 'utf8');
    const textLength = fileContent.length;

    for (let i = 0; i < textLength; i++) {
        let symbol = fileContent[i];

        /*if (symbol.charCodeAt(0) >= 32 && symbol.charCodeAt(0) <= 126) {
            if (alphabet[symbol] === undefined) {
                alphabet[symbol] = 1;
            } else {
                alphabet[symbol]++;
            }

            realLength++;
        }*/

        if (alphabet[symbol] === undefined) {
            alphabet[symbol] = 1;
        } else {
            alphabet[symbol]++;
        }

        realLength++;
    }

    /*alphabet = {"c": 1, "b": 4, "a": 2, "d": 3};
    realLength = 10;*/
}

/**
 * Меняет в alphabet значение с частоты на вероятность
 */
function setProbability() {
    for (let key in alphabet) {
        alphabet[key] = alphabet[key] / realLength;
    }
}

/**
 * Считает кумулативные вероятности
 */
function countCumulativeProbability() {
    cumulative.push(0);

    for (let key in alphabet) {
        if (cumulative.length === 1) {
            cumulative.push(alphabet[key]);
        } else {
            cumulative.push(cumulative[Object.keys(alphabet).indexOf(key)] + alphabet[key]);
        }
    }
}

/**
 * Переводит дробное десятичное с целой частью 0 в двоичное
 *
 * @param digit
 * @param ranks
 * @return {Number}
 */
function translateIntoBinary(digit, ranks) {
    let result = '';

    for (let i = 0; i < ranks; i++) {
        digit *= 2;

        if (digit > 1) {
            digit -= 1;
            result += 1;
        } else {
            result += 0;
        }
    }

    return parseInt(result);
}

/**
 * Арифметическое кодирование
 */
function coding() {
    for (let blockStartingSymbolIndex = 0; blockStartingSymbolIndex < realLength; blockStartingSymbolIndex += blockLength) {
        let leftLimit = 0;
        let rightLimit = 1;
        let intervalLength = 1;
        let blockEndingSymbolIndex = 0;

        if (blockStartingSymbolIndex + blockLength > realLength - 1) {
            if (blockStartingSymbolIndex === 0) {
                blockEndingSymbolIndex = realLength;
            } else {
                blockEndingSymbolIndex = blockStartingSymbolIndex + (realLength - 1) % blockStartingSymbolIndex + 1;
            }
        } else {
            blockEndingSymbolIndex = blockStartingSymbolIndex + blockLength;
        }

        for (let blockSymbolIndex = blockStartingSymbolIndex; blockSymbolIndex < blockEndingSymbolIndex; blockSymbolIndex++) {
            const id = Object.keys(alphabet).indexOf(fileContent[blockSymbolIndex]);
            const newLeftLimit = leftLimit + intervalLength * cumulative[id];

            rightLimit = leftLimit + intervalLength * cumulative[id + 1];
            leftLimit = newLeftLimit;
            intervalLength = rightLimit - leftLimit;
        }

        codeArray.push({'decimal': leftLimit, 'binary':
            translateIntoBinary(leftLimit, Math.ceil(Math.abs(Math.log(intervalLength) / Math.log(2))))});
    }
}

/**
 * Считает и печатает коэффициент сжатия
 */
function printCompressionCoefficient() {
    let codeSize = 0;

    codeArray.forEach((object) => {
        codeSize += object.binary.toString().length;
    });

    console.log('Compression coefficient:', Math.round((codeSize / fileContent.length) * 100) + '%',
        (codeSize > fileContent.length) ? '(not efficient)' : '');
}

/**
 * Выдает предполагаемый индекс символа в алфавите
 *
 * @param digit
 * @return {number}
 */
function getSupposedSymbolIndex(digit) {
    let supposedSymbolIndex = 0;

    cumulative.forEach((probability) => {
        if (digit >= probability.toFixed(15)) {
            supposedSymbolIndex = cumulative.indexOf(probability);
        } else {
            return supposedSymbolIndex;
        }
    });

    return supposedSymbolIndex;
}

/**
 * Декодирование
 */
function decoding() {
    codeArray.forEach((object) => {
        let digitCount = blockLength;
        let digit = object.decimal;

        if (codeArray.length === 1) {
            digitCount = realLength;
        } else if (codeArray.indexOf(object) === codeArray.length - 1) {
            digitCount = realLength - (codeArray.length - 1) * blockLength;
        }

        for (let i = 0; i < digitCount; i++) {
            if (digit.toString().match(/9{2,}/) !== null) {
                digit = parseFloat(digit.toFixed(digit.toString().match(/9{2,}/).index - 2));
            }

            const supposedSymbolIndex = getSupposedSymbolIndex(digit);

            searchedContent += Object.keys(alphabet)[supposedSymbolIndex];

            digit = (digit - cumulative[supposedSymbolIndex]) /
                (cumulative[supposedSymbolIndex + 1] - cumulative[supposedSymbolIndex]);
        }
    });
}

/**
 * Проверяет соответствие полученного текста после декодирования исходному и печатает результат
 */
function checkContentOnIdentity() {
    console.log('The content obtained after decoding', (fileContent === searchedContent) ? 'is' : 'isn\'t',
        'identical to the original');
}
