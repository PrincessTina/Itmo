#include <iostream>
#include "sort.h"

void printArray(int *array, int size) {
    for (int i = 0; i < size; i++) {
        std::cout << array[i] << " ";
    }
}

int main() {
    //int array[] = {3, 8, 9, 12, 4, 1, 0, 48};
    int array[] = {7, 49, 73, 58, 30, 72, 44, 78, 23, 9, 40, 65, 92, 42, 87, 3, 27, 29, 40, 12};
    //int array[] = {3, 12, 9, 12, 4, 1, 0, 12};
    //int array[] = {3, 0, 1, 8, 7, 2, 5, 4, 9, 6};
    int size = 20;

    sort(array, array + size - 1);
    printArray(array, size);
    return 0;
}