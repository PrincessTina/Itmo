#include <iostream>
#include "array.h"

void printArray(const Array &array) {
    for (int i = 0; i < array.size(); i++) {
        std::cout << array[i] << " ";
    }

    std::cout << std::endl;
}

int main() {
    Array array(3);

    array.insert(5);
    printArray(array);
    array.insert(12);
    printArray(array);
    array.insert(10);
    printArray(array);
    array.insert(21);
    printArray(array);
    array.insert(1, 8);
    printArray(array);

    return 0;
}
