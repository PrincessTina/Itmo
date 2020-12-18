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
 * Проверить it.set
 */
TEST(list, Test5) {
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

