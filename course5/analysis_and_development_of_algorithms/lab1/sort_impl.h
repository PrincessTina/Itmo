#ifndef LAB1_SORT_IMPL_H
#define LAB1_SORT_IMPL_H

#include <functional>

template<typename T>
void swap(T *first, T *second) {
    T temporary = *first;
    *first = *second;
    *second = temporary;
}

template<typename T>
int getIntervalLength(T *firstIntervalElement, T *lastIntervalElement) {
    return (int) (lastIntervalElement - firstIntervalElement + 1);
}

template<typename T>
void insertionSort(T *firstIntervalElement, const T *lastIntervalElement) {
    T brokenElementMean;
    T *brokenElementPointer;
    T *currentElementPointer = firstIntervalElement + 1;

    while (currentElementPointer <= lastIntervalElement) {
        brokenElementPointer = currentElementPointer;
        brokenElementMean = *brokenElementPointer;

        while (currentElementPointer >= firstIntervalElement) {
            if (currentElementPointer == firstIntervalElement ||
                compare<T>(brokenElementMean, *(currentElementPointer - 1)) != -1) {
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

template<typename T>
bool intervalSort(T *firstIntervalElement, T *lastIntervalElement, bool canDoRecursion) {
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
        qsort(firstIntervalElement, lastIntervalElement);
        return true;
    }

    return false;
}

template<typename T>
T *redistributeElements(T *leftIntervalPointer, T *rightIntervalPointer, T pivotElement) {
    while (true) {
        while (compare<T>(*leftIntervalPointer, pivotElement) == -1) {
            leftIntervalPointer++;
        }

        while (compare<T>(*rightIntervalPointer, pivotElement) == 1) {
            rightIntervalPointer--;
        }

        if (leftIntervalPointer == rightIntervalPointer) {
            return leftIntervalPointer;
        } else if (compare<T>(*leftIntervalPointer, *rightIntervalPointer) == 0) {
            rightIntervalPointer--;
        } else {
            swap(leftIntervalPointer, rightIntervalPointer);
        }
    }
}

template<typename T>
T getPivotElement(const T *firstIntervalElement, const T *lastIntervalElement) {
    T middleIntervalElement = *(firstIntervalElement + (lastIntervalElement - firstIntervalElement) / 2);

    if (abs(compare<T>(*firstIntervalElement, middleIntervalElement) +
            compare<T>(*firstIntervalElement, *lastIntervalElement)) !=
        2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return *firstIntervalElement;
    }

    if (abs(compare<T>(middleIntervalElement, *firstIntervalElement) +
            compare<T>(middleIntervalElement, *lastIntervalElement)) !=
        2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return middleIntervalElement;
    }

    if ((compare<T>(*lastIntervalElement, *firstIntervalElement) +
         compare<T>(*lastIntervalElement, middleIntervalElement)) !=
        2) { // любые значения, кроме -1, -1 (<. <) и 1, 1 (>, >)
        return *lastIntervalElement;
    }

    return -1;
}

template<typename T>
void qsort(T *firstIntervalElement, T *lastIntervalElement) {
    T pivotElement;
    T *separationElement;

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

template<typename T>
void sort(T *firstIntervalElement, T *lastIntervalElement, int (*compareFunc)(const T, const T)) {
    compare<T> = compareFunc;
    qsort(firstIntervalElement, lastIntervalElement);
}

#endif //LAB1_SORT_IMPL_H
