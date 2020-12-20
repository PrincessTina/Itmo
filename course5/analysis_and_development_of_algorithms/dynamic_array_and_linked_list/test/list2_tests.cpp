#include <gtest/gtest.h>
#include <gmock/gmock.h>

#include "../main/unrolled_list/list.h"

/**
 * Сравнивает элементы массивов
 * @tparam T - тип элементов массивов
 * @param arrayList - сформированный массив из элементов списка
 * @param expectedArrayList - массив с ожидаемыми элементами
 * @param size - количество элементов в массивах
 * @return true, если массивы одинаковы, иначе false
 */
template<typename T>
bool elementsAreEqual(T *arrayList, T *expectedArrayList, int size) {
    for (int i = 0; i < size; i++) {
        if (arrayList[i] != expectedArrayList[i]) {
            return false;
        }
    }

    return true;
}

/**
 * Выводит элементы массива на экран через пробел с запятой
 * @tparam T - тип элементов массива
 * @param array - массив, элементы которого нужно вывести на экран
 * @param size - предполагаемый размер массива
 */
template<typename T>
void printArray(T *array, int size) {
    for (int i = 0; i < size; i++) {
        if (i == size - 1) {
            std::cout << array[i];
        } else {
            std::cout << array[i] << ", ";
        }
    }

    std::cout << std::endl;
}

/**
 * Формирует массив из элементов списка
 * @tparam T - тип элементов
 * @param list - исходный список
 * @param arrayList - массив, который нужно заполнить элементами списка
 */
template<typename T>
void formArrayFromList(List<T> list, T *arrayList) {
    int i = 0;

    for (auto it = list.iterator(); it.hasNext(); it.next()) {
        arrayList[i] = it.get();
        i++;
    }

    arrayList[i] = list.tail();
}

/**
 * Проверка на списке из 1 элемента операций insertHead, size, head, tail
 */
TEST(list, Test1) {
    List<int> list;
    EXPECT_EQ(list.size(), 0);

    list.insertHead(6);
    EXPECT_EQ(list.head(), 6);
    EXPECT_EQ(list.tail(), 6);
    EXPECT_EQ(list.size(), 1);
}

/**
 * Проверка на списке из 3 элементов операций insertHead, size, head, tail
 */
TEST(list, Test2) {
    List<int> list;
    EXPECT_EQ(list.size(), 0);

    list.insertHead(8);
    list.insertHead(2);
    list.insertHead(5);

    EXPECT_EQ(list.head(), 5);
    EXPECT_EQ(list.tail(), 8);
    EXPECT_EQ(list.size(), 3);
}

/**
 * Проверка на списке из 10 элементов (> chunkSize), что при insertHead происходит расширение списка в левую сторону
 * (выделение чанка)
 * Вывод списка на экран обходом слева направо с помощью итератора, операций hasNext, next, get
 */
TEST(list, Test3) {
    List<int> list;
    int i = 0;
    int size = 10;
    int arrayList[size];
    int expectedArrayList[] = {52, 21, 78, 98, 55, 86, 76, 88, 20, 25};

    list.insertHead(25);
    list.insertHead(20);
    list.insertHead(88);
    list.insertHead(76);
    list.insertHead(86);
    list.insertHead(55);
    list.insertHead(98);
    list.insertHead(78);
    list.insertHead(21);
    list.insertHead(52);

    for (auto it = list.iterator(); it.hasNext(); it.next()) {
        arrayList[i] = it.get();
        i++;
    }

    arrayList[i] = list.tail();

    EXPECT_EQ(list.head(), 52);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Вывод списка на экран обходом справа налево с помощью итератора, операций hasPrev, prev, get
 */
TEST(list, Test4) {
    List<int> list;
    int i = 5;
    int size = 6;
    int arrayList[size];
    int expectedArrayList[] = {6, 4, 0, 5, 2, 8};

    list.insertHead(8);
    list.insertHead(2);
    list.insertHead(5);
    list.insertHead(0);
    list.insertHead(4);
    list.insertHead(6);

    auto iterator = list.iterator();

    while (iterator.hasNext()) {
        iterator.next();
    }

    for (auto it = iterator; it.hasPrev(); it.prev()) {
        arrayList[i] = it.get();
        i--;
    }

    arrayList[i] = list.head();

    EXPECT_EQ(list.head(), 6);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке из 4 элементов, что работает операция removeHead
 */
TEST(list, Test5) {
    List<int> list;
    int size = 3;
    int arrayList[size];
    int expectedArrayList[] = {9, 21, 13};

    list.insertHead(13);
    list.insertHead(21);
    list.insertHead(9);
    list.insertHead(0);

    list.removeHead();

    formArrayFromList(list, arrayList);
    EXPECT_EQ(list.head(), 9);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке из 10 элементов (> chunkSize), что при removeHead происходит сужение списка в правую сторону
 * (очищение чанка)
 */
TEST(list, Test6) {
    List<int> list;
    int size = 4;
    int arrayList[size];
    int expectedArrayList[] = {0, 11, 2, 37};

    list.insertHead(37);
    list.insertHead(2);
    list.insertHead(11);
    list.insertHead(1);
    list.insertHead(9);
    list.insertHead(48);
    list.insertHead(4);
    list.insertHead(13);
    list.insertHead(26);
    list.insertHead(50);

    for (int i = 0; i < 7; i++) {
        list.removeHead();
    }

    list.insertHead(0);

    formArrayFromList(list, arrayList);
    EXPECT_EQ(list.head(), 0);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке из 10 элементов (> chunkSize), что при insertTail происходит расширение списка в правую сторону
 * (выделение чанка)
 */
TEST(list, Test7) {
    List<int> list;
    int size = 10;
    int arrayList[size];
    int expectedArrayList[] = {8, 25, 15, 21, 17, 3, 37, 28, 11, 12};

    list.insertTail(8);
    list.insertTail(25);
    list.insertTail(15);
    list.insertTail(21);
    list.insertTail(17);
    list.insertTail(3);
    list.insertTail(37);
    list.insertTail(28);
    list.insertTail(11);
    list.insertTail(12);

    formArrayFromList(list, arrayList);
    EXPECT_EQ(list.tail(), 12);
    EXPECT_EQ(list.head(), 8);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке из 10 элементов (> chunkSize), что при removeTail происходит сужение списка в левую сторону
 * (очищение чанка)
 */
TEST(list, Test8) {
    List<int> list;
    int size = 4;
    int arrayList[size];
    int expectedArrayList[] = {7, 50, 24, 9};

    list.insertTail(7);
    list.insertTail(50);
    list.insertTail(24);
    list.insertTail(61);
    list.insertTail(73);
    list.insertTail(48);
    list.insertTail(57);
    list.insertTail(32);
    list.insertTail(54);
    list.insertTail(42);

    for (int i = 0; i < 7; i++) {
        list.removeTail();
    }

    list.insertTail(9);

    formArrayFromList(list, arrayList);
    EXPECT_EQ(list.tail(), 9);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка списка при воздействии на него операциями insertHead, insertTail, removeHead, removeTail
 */
TEST(list, Test9) {
    List<int> list;
    int size = 6;
    int arrayList[size];
    int expectedArrayList[] = {24, 50, 7, 61, 73, 57};

    list.insertTail(7); // 7
    list.insertHead(50); // 50 7
    list.insertHead(24); // 24 50 7
    list.insertTail(61); // 24 50 7 61
    list.insertTail(73); // 24 50 7 61 73
    list.insertHead(48); // 48 24 50 7 61 73
    list.insertTail(57); // 48 24 50 7 61 73 57
    list.insertHead(32); // 32 48 24 50 7 61 73 57
    list.insertTail(54); // 32 48 24 50 7 61 73 57 54
    list.insertTail(42); // 32 48 24 50 7 61 73 57 54 42

    list.removeHead(); // 48 24 50 7 61 73 57 54 42
    list.removeTail(); // 48 24 50 7 61 73 57 54
    list.removeHead(); // 24 50 7 61 73 57 54
    list.removeTail(); // 24 50 7 61 73 57

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка операции set итератора
 */
TEST(list, Test10) {
    List<int> list;
    int size = 6;
    int arrayList[size];
    int expectedArrayList[] = {94, 57, 10, 92, 5, 63};

    list.insertTail(92);
    list.insertTail(5);
    list.insertTail(63);
    list.insertHead(48);
    list.insertHead(57);
    list.insertHead(94);

    auto iterator = list.iterator();
    iterator.next();
    iterator.next();
    iterator.set(10);

    formArrayFromList(list, arrayList);
    EXPECT_EQ(iterator.get(), 10);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}
