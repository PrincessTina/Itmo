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
    return (int) (lastIntervalElement - firstIntervalElement + 1);
}

void insertionSort(int *firstIntervalElement, const int *lastIntervalElement) {
    int brokenElementMean;
    int *brokenElementPointer;
    int *currentElementPointer = firstIntervalElement + 1;

    while (currentElementPointer <= lastIntervalElement) {
        brokenElementPointer = currentElementPointer;
        brokenElementMean = *brokenElementPointer;

        while (currentElementPointer >= firstIntervalElement) {
            if (currentElementPointer == firstIntervalElement ||
                compare(brokenElementMean, *(currentElementPointer - 1)) != -1) {
                *currentElementPointer = brokenElementMean;
                break;
            } else {
                *currentElementPointer = *(currentElementPointer - 1);
                currentElementPointer--;
            }
        }

        currentElementPointer = brokenElementPointer + 1;
    }
}

bool intervalSort(int *firstIntervalElement, int *lastIntervalElement, bool canDoRecursion) {
    int intervalLength = getIntervalLength(firstIntervalElement, lastIntervalElement);

    if (intervalLength == 1) {
        return true;
    }

    if (intervalLength >= 2 && intervalLength <= transitionIntervalLength) {
        insertionSort(firstIntervalElement, lastIntervalElement);
        return true;
    }

    if (!canDoRecursion) {
        return false;
    }

    if (intervalLength > transitionIntervalLength) {
        sort(firstIntervalElement, lastIntervalElement);
        return true;
    }

    return false;
}

int *redistributeElements(int *leftIntervalPointer, int *rightIntervalPointer, int pivotElement) {
    while (true) {
        while (compare(*leftIntervalPointer, pivotElement) == -1) {
            leftIntervalPointer++;
        }

        while (compare(*rightIntervalPointer, pivotElement) == 1) {
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

int getPivotElement(const int *firstIntervalElement, const int *lastIntervalElement) {
    int middleIntervalElement = *(firstIntervalElement + (lastIntervalElement - firstIntervalElement) / 2);

    if (abs(compare(*firstIntervalElement, middleIntervalElement) +
            compare(*firstIntervalElement, *lastIntervalElement)) !=
        2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return *firstIntervalElement;
    }

    if (abs(compare(middleIntervalElement, *firstIntervalElement) +
            compare(middleIntervalElement, *lastIntervalElement)) !=
        2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return middleIntervalElement;
    }

    if ((compare(*lastIntervalElement, *firstIntervalElement) +
         compare(*lastIntervalElement, middleIntervalElement)) !=
        2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return *lastIntervalElement;
    }

    return -1;
}

void sort(int *firstIntervalElement, int *lastIntervalElement) {
    int pivotElement;
    int *separationElement;

    while (firstIntervalElement != lastIntervalElement) {
        pivotElement = getPivotElement(firstIntervalElement, lastIntervalElement);
        separationElement = redistributeElements(firstIntervalElement, lastIntervalElement, pivotElement);

        if (getIntervalLength(firstIntervalElement, separationElement - 1) <
            getIntervalLength(separationElement + 1, lastIntervalElement)) {
            intervalSort(firstIntervalElement, separationElement - 1, true);
            firstIntervalElement = separationElement + 1;

            if (intervalSort(firstIntervalElement, lastIntervalElement, false)) {
                break;
            }
        } else {
            intervalSort(separationElement + 1, lastIntervalElement, true);
            lastIntervalElement = separationElement - 1;

            if (intervalSort(firstIntervalElement, lastIntervalElement, false)) {
                break;
            }
        }
    }
}