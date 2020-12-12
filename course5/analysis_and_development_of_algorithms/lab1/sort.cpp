#include "sort.h"

int compare(const int first, const int second) {
    if (first > second) {
        return 1;
    }

    if (first < second) {
        return -1;
    }

    return 0; // if first == second
}

void swap(int *first, int *second) {
    int temporary = *first;
    *first = *second;
    *second = temporary;
}

int getIntervalLength(int *firstIntervalElement, int *lastIntervalElement) {
    return lastIntervalElement - firstIntervalElement + 1;
}

void intervalSort(int *firstIntervalElement, int *lastIntervalElement) {
    int intervalLength = getIntervalLength(firstIntervalElement, lastIntervalElement);

    if (intervalLength == 1) {
        return;
    }

    if (intervalLength == 2) {
        if (*firstIntervalElement > *lastIntervalElement) {
            swap(firstIntervalElement, lastIntervalElement);
        }
    }

    if (intervalLength > 2) {
        sort(firstIntervalElement, lastIntervalElement);
    }
}

int * redistributeElements(int *leftIntervalPointer, int *rightIntervalPointer, int supportElement) {
    while (true) {
        while (compare(*leftIntervalPointer, supportElement) == -1) {
            leftIntervalPointer++;
        }

        while (compare(*rightIntervalPointer, supportElement) == 1) {
            rightIntervalPointer--;
        }

        if (leftIntervalPointer == rightIntervalPointer) {
            return leftIntervalPointer;
        } else if (compare(*leftIntervalPointer, *rightIntervalPointer) == 0) {
            rightIntervalPointer--;
        } else {
            swap(leftIntervalPointer, rightIntervalPointer);
        }
    }
}

int getSupportElement(const int *firstIntervalElement, const int *lastIntervalElement) {
    int middleIntervalElement = *(firstIntervalElement + (lastIntervalElement - firstIntervalElement) / 2);

    if (abs(compare(*firstIntervalElement, middleIntervalElement) +
            compare(*firstIntervalElement, *lastIntervalElement)) != 2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return *firstIntervalElement;
    }

    if (abs(compare(middleIntervalElement, *firstIntervalElement) +
            compare(middleIntervalElement, *lastIntervalElement)) != 2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return middleIntervalElement;
    }

    if ((compare(*lastIntervalElement, *firstIntervalElement) +
         compare(*lastIntervalElement, middleIntervalElement)) != 2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return *lastIntervalElement;
    }

    return -1;
}

void sort(int *firstIntervalElement, int *lastIntervalElement) {
    while (firstIntervalElement != lastIntervalElement) {
        int supportElement = getSupportElement(firstIntervalElement, lastIntervalElement);
        int *separationElement = redistributeElements(firstIntervalElement, lastIntervalElement, supportElement);

        if (getIntervalLength(firstIntervalElement, separationElement - 1) <
            getIntervalLength(separationElement + 1, lastIntervalElement)) {
            intervalSort(firstIntervalElement, separationElement - 1);
            firstIntervalElement = separationElement + 1;
        } else {
            intervalSort(separationElement + 1, lastIntervalElement);
            lastIntervalElement = separationElement - 1;
        }
    }
}