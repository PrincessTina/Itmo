#include <windows.h>
#include <iostream>
#include "sort.h"

LARGE_INTEGER startTime, finishTime, averageTime, frequency;

void countTime() {
    int size = 5;
    int array[size];
    int checkCount = 10000;
    averageTime.QuadPart = 0;

    for (int k = 0; k < checkCount; k++) {
        QueryPerformanceFrequency(&frequency);
        QueryPerformanceCounter(&startTime);

        for (int i = 0; i < 10000; i++) {
            for (int j = size - 1; j >= 0; j--) {
                array[size - 1 - j] = j;
            }

            insertionSort(array, array + size - 1);
            //sort(array, array + size - 1);
        }

        QueryPerformanceCounter(&finishTime);
        averageTime.QuadPart += (finishTime.QuadPart - startTime.QuadPart) * 1000000 / frequency.QuadPart;
    }

    averageTime.QuadPart /= checkCount;
    std::cout << std::endl << averageTime.QuadPart;
}

void printArray(int *array, int size) {
    for (int i = 0; i < size; i++) {
        std::cout << array[i] << " ";
    }
}

int main() {
    int size = 10;
    //int array[] = {3, 8, 9, 12, 4, 1, 0, 48};
    //int array[] = {7, 49, 73, 58, 30, 72, 44, 78, 23, 9, 40, 65, 92, 42, 87, 3, 27, 29, 40, 12};
    //int array[] = {3, 12, 9, 12, 4, 1, 0, 12};
    int array[] = {3, 0, 1, 8, 7, 2, 5, 4, 9, 6};

    sort(array, array + size - 1);
    printArray(array, size);
    return 0;
}