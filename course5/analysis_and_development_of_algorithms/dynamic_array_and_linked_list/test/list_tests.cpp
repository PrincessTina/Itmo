#include <gtest/gtest.h>
#include <gmock/gmock.h>

#include "../main/helper_library.h"

bool elementsAreEqual(int *arrayList, int *expectedArrayList, int size) {
    for (int i = 0; i < size; i++) {
        if (arrayList[i] != expectedArrayList[i]) {
            return false;
        }
    }

    return true;
}

/**
 * Проверка на списке из 1 элемента операций insertHead, size, head, tail
 */
TEST(list, Test1) {
    List list;
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
    List list;
    EXPECT_EQ(list.size(), 0);

    list.insertHead(8);
    list.insertHead(2);
    list.insertHead(5);

    EXPECT_EQ(list.head(), 5);
    EXPECT_EQ(list.tail(), 8);
    EXPECT_EQ(list.size(), 3);
}

/**
 * Проверка на списке из 6 элементов (> chunkSize), что при insertHead происходит расширение списка в левую сторону
 * (выделение чанка)
 * Вывод списка на экран обходом слева направо с помощью итератора, операций hasNext, next, get
 */
TEST(list, Test3) {
    List list;
    int i = 0;
    int size = 6;
    int arrayList[size];
    int expectedArrayList[] = {6, 4, 0, 5, 2, 8};

    list.insertHead(8);
    list.insertHead(2);
    list.insertHead(5);
    list.insertHead(0);
    list.insertHead(4);
    list.insertHead(6);

    for (auto it = list.iterator(); it.hasNext(); it.next()) {
        arrayList[i] = it.get();
        i++;
    }

    arrayList[i] = list.tail();

    EXPECT_EQ(list.head(), 6);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Вывод списка на экран обходом справа налево с помощью итератора, операций hasPrev, prev, get
 */
TEST(list, Test4) {
    List list;
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
    List list;
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
    List list;
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
 * Проверка на списке из 6 элементов (> chunkSize), что при insertTail происходит расширение списка в правую сторону
 * (выделение чанка)
 */
TEST(list, Test7) {
    List list;
    int size = 6;
    int arrayList[size];
    int expectedArrayList[] = {15, 22, 51, 12, 68, 72};

    list.insertTail(15);
    list.insertTail(22);
    list.insertTail(51);
    list.insertTail(12);
    list.insertTail(68);
    list.insertTail(72);

    formArrayFromList(list, arrayList);
    EXPECT_EQ(list.tail(), 72);
    EXPECT_EQ(list.head(), 15);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке из 10 элементов (> chunkSize), что при removeTail происходит сужение списка в левую сторону
 * (очищение чанка)
 */
TEST(list, Test8) {
    List list;
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
    List list;
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
    List list;
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

/**
 * Проверка операции insert итератора
 */
TEST(list, Test11) {
    List list;
    int size = 9;
    int arrayList[size];
    int expectedArrayList[] = {2, 3, 4, 5, 101, 7, 6, 3, 9};

    list.insertHead(5);
    list.insertHead(4);
    list.insertHead(3);
    list.insertHead(2);
    list.insertTail(7);
    list.insertTail(6);
    list.insertTail(3);
    list.insertTail(9);

    auto iterator = list.iterator();

    for (int i = 0; i < 4; i++) {
        iterator.next();
    }

    iterator.insert(101);
    iterator.next();
    EXPECT_EQ(iterator.get(), 7);

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка нескольких операций insert итератора
 */
TEST(list, Test12) {
    List list;
    auto iterator = list.iterator();
    int size = 4;
    int arrayList[size];
    int expectedArrayList[] = {0, 4, 22, 12};

    iterator.insert(12); // 12
    iterator.insert(4); // 4 12

    iterator.next();
    EXPECT_EQ(iterator.get(), 12);

    iterator.insert(22); // 4 22 12

    iterator.prev();
    EXPECT_EQ(iterator.get(), 4);

    iterator.insert(0); // 0 4 22 12

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка операции remove итератора
 */
TEST(list, Test13) {
    List list;
    int size = 8;
    int arrayList[size];
    int expectedArrayList[] = {34, 52, 20, 95, 77, 44, 82, 46};

    list.insertHead(53);
    list.insertHead(95);
    list.insertHead(20);
    list.insertHead(52);
    list.insertHead(34);
    list.insertTail(77);
    list.insertTail(44);
    list.insertTail(82);
    list.insertTail(46);

    auto iterator = list.iterator();

    for (int i = 0; i < 4; i++) {
        iterator.next();
    }

    iterator.remove();
    EXPECT_EQ(iterator.get(), 95);

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка нескольких операций remove итератора
 */
TEST(list, Test14) {
    List list;
    auto iterator = list.iterator();
    int size = 2;
    int arrayList[size];
    int expectedArrayList[] = {92, 44};

    iterator.insert(19); // 19
    iterator.remove();

    iterator.insert(44); // 44
    iterator.insert(92); // 92 44
    iterator.insert(13); // 13 92 44
    iterator.insert(6); // 6 13 92 44
    iterator.insert(-81); // -81 6 13 92 44
    iterator.insert(29); // 29 -81 6 13 92 44

    iterator.next();
    iterator.next();

    iterator.remove(); // 29 -81 13 92 44
    EXPECT_EQ(iterator.get(), -81);

    iterator.next();
    iterator.remove(); // 29 -81 92 44
    iterator.remove(); // 29 92 44
    iterator.remove(); // 92 44

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

TEST(list, Test15) {
    List list;
    auto iterator = list.iterator();

    //iterator.get();
}

