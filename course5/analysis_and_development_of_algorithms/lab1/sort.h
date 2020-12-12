#ifndef LAB1_SORT_H
#define LAB1_SORT_H

#include <cmath>

/**
 * Сравнивает элементы
 * @param first - первый элемент
 * @param second - второй элемент
 * @return 1, если first > second; -1, если first < second; 0, если first == second
 */
int compare(int first, int second);

/**
 * Переставляет элементы местами
 * @param first - указатель на первый элемент
 * @param second - указатель на второй элемент
 */
void swap(int *first, int *second);

/**
 * Возвращает длину интервала
 * @param firstIntervalElement - указатель на первый элемент интервала
 * @param lastIntervalElement - указатель на последний элемент интервала
 * @return длину интервала
 */
int getIntervalLength(int *firstIntervalElement, int *lastIntervalElement);

/**
 * Сортировка вставками
 * @param firstIntervalElement - указатель на первый элемент сортируемого интервала
 * @param lastIntervalElement - указатель на последний элемент сортируемого интервала
 */
void insertionSort(int *firstIntervalElement, const int *lastIntervalElement);

 /**
  * Проверяет размер интервала и вызывает соответствующую для него функцию сортировки
  * @param firstIntervalElement - указатель на первый элемент интервала
  * @param lastIntervalElement - указатель на последний элемент интервала
  * @param canDoRecursion - можно ли делать рекурсивный вызов функции sort
  * @return возвращает true в случае, если сортировка интервала завершена, false, если интервал еще нуждается в сортировке
  */
bool intervalSort(int *firstIntervalElement, int *lastIntervalElement, bool canDoRecursion);

/**
 * Элементы, больше опорного, отправляет в правый интервал, меньше опорного - в левый
 * @param leftIntervalPointer - указатель на самый левый (первый) элемент интервала
 * @param rightIntervalPointer - указатель на самый правый (последний) элемент интервала
 * @param supportElement - опорный элемент
 * @return возвращает элемент, относительно которого произошло перераспределение
 */
int * redistributeElements(int *leftIntervalPointer, int *rightIntervalPointer, int supportElement);

/**
 * Возвращает опорный элемент интервала, который является медианой из первого, последнего и среднего элементов интервала
 * @param firstIntervalElement - указатель на первый элемент интервала
 * @param lastIntervalElement - указатель на последний элемент интервала
 * @return опорный элемент
 */
int getSupportElement(const int *firstIntervalElement, const int *lastIntervalElement);

/**
 * Быстрая сортировка
 * @param firstIntervalElement - указатель на первый элемент сортируемого интервала
 * @param lastIntervalElement - указатель на второй элемент сортируемого интервала
 */
void sort(int *firstIntervalElement, int *lastIntervalElement);

#endif //LAB1_SORT_H
