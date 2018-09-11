var fs = require("fs");
var alphabet = {};
var realLength = 0;

main();

function main() {
    readFile("document.txt");
    var symbolsChance = countSymbolsChance(alphabet);
}

/**
 * Считывает содержимое файла и формирует по нему два массива: просто алфавит символов и алфавит пар символов
 *
 * @param fileName
 */
function readFile(fileName) {
    const fileContent = fs.readFileSync(fileName, "utf8");
    const textLength = fileContent.length;

    for (var i = 0; i < textLength; i++) {
        var symbol = fileContent[i].toLowerCase();

        if (/[^\t\v\r\n\f]/.test(symbol)) {
            if (!/([a-z]|[0-9]| )/.test(symbol)) {
                symbol = ':';
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


function countSymbolsChance(symbols) {
    var symbolsChance = {};

    for (var key in symbols) {
        symbolsChance[key] = parseFloat((symbols[key] / realLength).toFixed(4));
    }

    return symbolsChance;
}