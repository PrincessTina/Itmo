#ifndef LAB1_SORT_H
#define LAB1_SORT_H

/**
 * Сравнивает элементы
 * @param first - первый элемент
 * @param second - второй элемент
 * @return true, если first < second, иначе false
 */
bool compare(int first, int second);

/**
 * Переставляет элементы местами
 * @param first - указатель на первый элемент
 * @param second - указатель на второй элемент
 */
void swap(int *first, int *second);

/**
 * Возвращает опорный элемент массива, который является медианой из первого, последнего и среднего элементов массива
 * @param firstIntervalElement - указатель на первый элемент массива
 * @param lastIntervalElement - указатель на последний элемент массива
 * @return опорный элемент
 */
int getSupportElement(const int *firstIntervalElement, const int *lastIntervalElement);

void intervalSort(int *firstIntervalElement, int *lastIntervalElement);

/**
 * Быстрая сортировка
 * @param firstIntervalElement - указатель на первый элемент сортируемого интервала
 * @param lastIntervalElement - указател на второй элемент сортируемого интервала
 */
void sort(int *firstIntervalElement, int *lastIntervalElement);

#endif //LAB1_SORT_H
