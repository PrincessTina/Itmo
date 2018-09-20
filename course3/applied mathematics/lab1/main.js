const fs = require("fs");
var fileContent;
var alphabet = {}; // объект, поля которого - символы, а их значения - объект с полями в частоту, вероятность и энтропию
var realLength = 0;

main();

function main() {
    readFile("document.txt");
    fillAdditionalInfo();
    printAlphabet();
    printEntropy();
}

/**
 * Считывает содержимое файла и формирует по нему алфавит символов
 *
 * @param fileName
 */
function readFile(fileName) {
    fileContent = fs.readFileSync(fileName, "utf8");
    const textLength = fileContent.length;

    for (var i = 0; i < textLength; i++) {
        var symbol = fileContent[i].toLowerCase();

        if (symbol.charCodeAt(0) >= 32 && symbol.charCodeAt(0) <= 126) {
            
        }

        if (/[^\t\v\r\n\f]/.test(symbol)) {
            if (!/([a-z]|[0-9]| )/.test(symbol)) {
                symbol = ',';
            }

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
    for (var key in alphabet) {
        alphabet[key] = {"frequency": alphabet[key], "probability": 0, "entropy": 0};
        alphabet[key].probability = parseFloat((alphabet[key].frequency / realLength).toFixed(4));
        alphabet[key].entropy = -parseFloat((Math.log(alphabet[key].probability)).toFixed(4));
    }
}

/**
 * Выписывает символ, его вероятность и энтропию
 */
function printAlphabet() {
    console.log("s  probability     entropy");
    for (var key in alphabet) {
        console.log(key + ":    " + alphabet[key].probability + "        " + alphabet[key].entropy);
    }
}

/**
 * Расчитывает и выписывает энтропию файла
 */
function printEntropy() {
    var entropy = 0;

    for (var key in alphabet) {
        entropy += alphabet[key].probability * alphabet[key].entropy;
    }

    console.log("\nЭнтропия файла равна " + entropy.toFixed(4));
}

/**
 * Расчитывает и выписывает энтропию файла при наличии статистических связей между двумя символами
 */
function printLinkedEntropy() {
    var entropy = 0;

    for (var j in alphabet) {
        for (var i in alphabet) {
            const substituteJ = punctuationMarks.test(j) ? ':' : j;
            const substituteI = punctuationMarks.test(i) ? ':' : i;
            const allPairs = fileContent.match(new RegExp(j + '.', 'ig'));
            var searchedPairs;

            if (punctuationMarks.test(j)) {
                allPairs = fileContent.match(new RegExp(j + '.', 'ig'));
            }
            const searchedPairs = fileContent.match(new RegExp(j + i, 'ig'));
        }
    }

    for (var i = 0; i < textLength; i++) {
        var symbol = fileContent[i].toLowerCase();

        if (/[^\t\v\r\n\f]/.test(symbol)) {
            if (!/([a-z]|[0-9]| )/.test(symbol)) {
                symbol = ',';
            }

            if (alphabet[symbol] === undefined) {
                alphabet[symbol] = 1;
            } else {
                alphabet[symbol]++;
            }

            realLength++;
        }
    }

    for (var key in alphabet) {
        entropy += alphabet[key].probability * alphabet[key].entropy;
    }

    console.log("\nЭнтропия файла равна " + entropy.toFixed(4));
}