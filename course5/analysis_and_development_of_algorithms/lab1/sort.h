#ifndef LAB1_SORT_H
#define LAB1_SORT_H

#include <cmath>

/**
 * Экспериментально полученная длина интервала, до которой будет выполняться сортировка вставками
 */
const int transitionIntervalLength = 12;

/**
 * Сравнивает элементы
 * @param first - первый элемент
 * @param second - второй элемент
 * @return 1, если first > second; -1, если first < second; 0, если first == second
 */
template<typename T>
int compare(T first, T second);

/**
 * Переставляет элементы местами
 * @param first - указатель на первый элемент
 * @param second - указатель на второй элемент
 */
template<typename T>
void swap(T *first, T *second);

/**
 * Возвращает длину интервала
 * @param firstIntervalElement - указатель на первый элемент интервала
 * @param lastIntervalElement - указатель на последний элемент интервала
 * @return длину интервала
 */
template<typename T>
int getIntervalLength(T *firstIntervalElement, T *lastIntervalElement);

/**
 * Сортировка вставками
 * https://www.youtube.com/watch?v=ROalU379l3U&feature=emb_title
 * @param firstIntervalElement - указатель на первый элемент сортируемого интервала
 * @param lastIntervalElement - указатель на последний элемент сортируемого интервала
 */
template<typename T>
void insertionSort(T *firstIntervalElement, const T *lastIntervalElement);

 /**
  * Проверяет размер интервала и вызывает соответствующую для него функцию сортировки
  * @param firstIntervalElement - указатель на первый элемент интервала
  * @param lastIntervalElement - указатель на последний элемент интервала
  * @param canDoRecursion - можно ли делать рекурсивный вызов функции sort
  * @return возвращает true в случае, если сортировка интервала завершена, false, если интервал еще нуждается в сортировке
  */
 template<typename T>
 bool intervalSort(T *firstIntervalElement, T *lastIntervalElement, bool canDoRecursion);

/**
 * Элементы, больше опорного, отправляет в правый интервал, меньше опорного - в левый
 * @param leftIntervalPointer - указатель на самый левый (первый) элемент интервала
 * @param rightIntervalPointer - указатель на самый правый (последний) элемент интервала
 * @param pivotElement - опорный элемент
 * @return возвращает элемент, относительно которого произошло перераспределение
 */
template<typename T>
T * redistributeElements(T *leftIntervalPointer, T *rightIntervalPointer, T pivotElement);

/**
 * Возвращает опорный элемент интервала, который является медианой из первого, последнего и среднего элементов интервала
 * @param firstIntervalElement - указатель на первый элемент интервала
 * @param lastIntervalElement - указатель на последний элемент интервала
 * @return опорный элемент
 */
template<typename T>
T getPivotElement(const T *firstIntervalElement, const T *lastIntervalElement);

/**
 * Быстрая сортировка
 * https://www.youtube.com/watch?v=oS5bZdtEhHY&feature=emb_title
 * @param firstIntervalElement - указатель на первый элемент сортируемого интервала
 * @param lastIntervalElement - указатель на второй элемент сортируемого интервала
 */
template<typename T>
void sort(T *firstIntervalElement, T *lastIntervalElement, int (*)(const T, const T));

#include "sort_impl.h"
#endif //LAB1_SORT_H
