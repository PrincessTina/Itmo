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
    int size = 6;
    //int array[] = {3, 8, 9, 12, 4, 1, 0, 48};
    //int array[] = {7, 49, 73, 58, 30, 72, 44, 78, 23, 9, 40, 65, 92, 42, 87, 3, 27, 29, 40, 12};
    //int array[] = {3, 12, 9, 12, 4, 1, 0, 12};
    //int array[] = {3, 0, 1, 8, 7, 2, 5, 4, 9, 6};
    //double array[] = {3.7, 8.1, 3.77, 12.3, 3.71, 3.7, 0, 48.2};
    Gnome array[] = {
            Gnome("Vasilevsk", 40, 0, 100),
            Gnome("Doorin", 45, 0, 120),
            Gnome("Vasilisa", 38, 0, 83.1),
            Gnome("Lakrya", 42, 2, 102),
            Gnome("Innokey", 61, 21, 115),
            Gnome("Armando", 73, 33, 139)};

    //sort(array, array + size - 1, commonCompare);
    sort(array, array + size - 1, gnomeCompare);
    printArray(array, size);
    return 0;
}