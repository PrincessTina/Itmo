#include <windows.h>
#include <iostream>

#include "helper_library.h"
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
            //qsort(array, array + size - 1);
        }

        QueryPerformanceCounter(&finishTime);
        averageTime.QuadPart += (finishTime.QuadPart - startTime.QuadPart) * 1000000 / frequency.QuadPart;
    }

    averageTime.QuadPart /= checkCount;
    std::cout << std::endl << averageTime.QuadPart;
}

int main() {
    int size = 8;
    int array[] = {3, 8, 9, 12, 4, 1, 0, 48};

    sort(array, array + size - 1, commonCompare);
    printArray(array, size);
    return 0;
}