#include "sort.h"

bool compare(const int first, const int second) {
    return first < second;
}

void swap(int *first, int *second) {
    int temporary = *first;
    *first = *second;
    *second = temporary;
}

int getSupportElement(const int *firstIntervalElement, const int *lastIntervalElement) {
    int middleIntervalElement = *(firstIntervalElement + (lastIntervalElement - firstIntervalElement) / 2);

    if (compare(*firstIntervalElement, middleIntervalElement) + compare(*firstIntervalElement, *lastIntervalElement)
        == true) {
        return *firstIntervalElement;
    }

    if (compare(middleIntervalElement, *firstIntervalElement) + compare(middleIntervalElement, *lastIntervalElement)
        == true) {
        return middleIntervalElement;
    }

    if (compare(*lastIntervalElement, *firstIntervalElement) + compare(*lastIntervalElement, middleIntervalElement)
        == true) {
        return *lastIntervalElement;
    }

    return -1;
}

void intervalSort(int *firstIntervalElement, int *lastIntervalElement) {
    int intervalLength = lastIntervalElement - firstIntervalElement;

    if (intervalLength == 1) {
        if (*firstIntervalElement > *lastIntervalElement) {
            swap(firstIntervalElement, lastIntervalElement);
        }
    } else if (intervalLength > 1) {
        sort(firstIntervalElement, lastIntervalElement);
    }
}

void sort(int *firstIntervalElement, int *lastIntervalElement) {
    int supportElement = getSupportElement(firstIntervalElement, lastIntervalElement);
    int *leftIntervalPointer = firstIntervalElement;
    int *rightIntervalPointer = lastIntervalElement;

    while (true) {
        while (*leftIntervalPointer < supportElement) {
            leftIntervalPointer++;
        }

        while (*rightIntervalPointer > supportElement) {
            rightIntervalPointer--;
        }

        if (leftIntervalPointer == rightIntervalPointer) {
            break;
        } else {
            swap(leftIntervalPointer, rightIntervalPointer);
        }
    }

    intervalSort(firstIntervalElement, leftIntervalPointer - 1);
    intervalSort(rightIntervalPointer + 1, lastIntervalElement);
}