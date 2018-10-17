const fs = require("fs");
let fileContent;
let alphabet = {}; // объект, поля которого - символы, а их значения - объект с полями в частоту, вероятность и энтропию
let realLength = 0;

main();

function main() {
    //let stdIn = process.openStdin();

    console.log('Input the filename (ex: doc.txt)\nDefault directory: text_files');

    let filename = "text_files//crimsonSails III.txt";

    try {
        readFile(filename);

        console.log('Result:');

        fillAdditionalInfo();
        printAlphabet();
        printEntropy();
        printLinkedEntropy();
    } catch (err) {
        console.log('Error: no such txt file ' + err)
    }

    /*stdIn.on('data', function (filename) {
        filename = "text_files//" + filename.toString().substr(0, filename.length - 1);

        console.log(filename);

        try {
            readFile(filename);

            console.log('Result:');

            fillAdditionalInfo();
            printAlphabet();
            printEntropy();
            printLinkedEntropy();
        } catch (err) {
            console.log('Error: no such txt file')
        }

        process.exit();
    });*/
}

/**
 * Считывает содержимое файла и формирует по нему алфавит символов
 *
 * @param fileName
 */
function readFile(fileName) {
    fileContent = fs.readFileSync(fileName, "utf8");
    const textLength = fileContent.length;

    for (let i = 0; i < textLength; i++) {
        let symbol = fileContent[i].toLowerCase();

        if (symbol.charCodeAt(0) >= 32 && symbol.charCodeAt(0) <= 126) {
            if (alphabet[symbol] === undefined) {
                alphabet[symbol] = 1;
            } else {
                alphabet[symbol]++;
            }

            realLength++;
        }
    }
}

/**
 * Добавляет в alphabet информацию о вероятности и энтропии для каждого символа
 */
function fillAdditionalInfo() {
    for (let key in alphabet) {
        alphabet[key] = {"frequency": alphabet[key], "probability": 0, "entropy": 0};
        alphabet[key].probability = alphabet[key].frequency / realLength;
        alphabet[key].entropy = -Math.log(alphabet[key].probability);
    }
}

/**
 * Выписывает символ, его вероятность и энтропию
 */
function printAlphabet() {
    let punctuationMarksProbability = 0;
    let keysArray = [];

    for (let key in alphabet) {
        keysArray.push(key);
    }

    keysArray.sort();

    console.log("s  probability     entropy");

    for (let i = 0; i < keysArray.length; i++) {
        let key = keysArray[i];

        if ([33, 34, 39, 40, 41, 44, 45, 46, 58, 59, 63, 96].indexOf(key.charCodeAt(0)) !== -1) {
            punctuationMarksProbability += alphabet[key].probability;
        } else {
            console.log(key + ":    " + alphabet[key].probability.toFixed(4) + "        " + alphabet[key].entropy.toFixed(4));
        }
    }

    if (punctuationMarksProbability !== 0) {
        console.log(",:    " + punctuationMarksProbability.toFixed(4) + "        " +
            -Math.log(punctuationMarksProbability).toFixed(4));
    }
}

/**
 * Расчитывает и выписывает энтропию файла
 */
function printEntropy() {
    let entropy = 0;

    for (let key in alphabet) {
        entropy += alphabet[key].probability * alphabet[key].entropy;
    }

    console.log("\nЭнтропия файла равна " + entropy.toFixed(4) + " нит");
}

/**
 * Экранирует спецсимволы в строке
 *
 * @param str
 * @return {string}
 */
function escapeExpressions(str) {
    return str.replace(/(?=[-(){}./?|"])/g, '\\');
}

/**
 * Расчитывает и выписывает энтропию файла при наличии статистических связей между двумя символами
 */
function printLinkedEntropy() {
    let entropy = 0;
    let alphabetSymbols = '[';

    for (let key in alphabet) {
        alphabetSymbols += key;
    }

    alphabetSymbols += "]";
    alphabetSymbols = escapeExpressions(alphabetSymbols);

    for (let j in alphabet) {
        const allPairsCount = fileContent.match(new RegExp('(?=' + escapeExpressions(j) + '(' + alphabetSymbols + '))', 'gi')) === null ? 0 :
            fileContent.match(new RegExp('(?=' + escapeExpressions(j) + '(' + alphabetSymbols + '))', 'gi')).length;

        for (let i in alphabet) {
            const searchedPairsCount = fileContent.match(new RegExp(escapeExpressions(j) + escapeExpressions(i), 'gi')) === null ? 0 :
                fileContent.match(new RegExp(escapeExpressions(j) + escapeExpressions(i), 'gi')).length;
            const probability = searchedPairsCount / allPairsCount;

            if (probability !== 0 && allPairsCount !== 0) {
                entropy += probability * alphabet[j].probability * -Math.log(probability);
            }
        }
    }

    console.log("Энтропия файла при наличии односвязной цепи равна " + entropy.toFixed(4) + " нит");
}