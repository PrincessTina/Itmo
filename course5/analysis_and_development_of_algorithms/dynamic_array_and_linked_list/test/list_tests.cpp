#include <gtest/gtest.h>
#include <gmock/gmock.h>

#include "../main/helper_library.h"

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
 * Проверка на списке из 10 элементов (> chunkSize), что при insertHead выделяется новый чанк справа
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
 * Проверка на списке из 10 элементов (> chunkSize), что при removeHead очищается чанк справа
 * (удаляем элементы из первого чанка)
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
 * Проверка на списке из 10 элементов (> chunkSize), что при insertTail выделяется новый чанк справа
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
 * Проверка на списке из 10 элементов (> chunkSize), что при removeTail очищается чанк справа
 * (удаляем элементы из первого чанка)
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

/**
 * Проверка операции insert итератора
 */
TEST(list, Test11) {
    List<int> list;
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
    List<int> list;
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
    List<int> list;
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
    EXPECT_EQ(iterator.get(), 77);

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка нескольких операций remove итератора
 */
TEST(list, Test14) {
    List<int> list;
    auto iterator = list.iterator();
    int size = 2;
    int arrayList[size];
    int expectedArrayList[] = {29, -81};

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
    EXPECT_EQ(iterator.get(), 13);

    iterator.next();
    iterator.remove(); // 29 -81 13 44
    iterator.remove(); // 29 -81 13
    iterator.remove(); // 29 -81

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке элементов типа double
 */
TEST(list, Test15) {
    List<double> list;
    int size = 6;
    double arrayList[size];
    double expectedArrayList[] = {2.4, 50.201, 7.32, 6.15, 7.3, 5.7};

    list.insertTail(7.32); // 7.32
    list.insertHead(50.201); // 50.201 7.32
    list.insertHead(2.4); // 2.4 50.201 7.32
    list.insertTail(6.15); // 2.4 50.201 7.32 6.15
    list.insertTail(7.3); // 2.4 50.201 7.32 6.15 7.3
    list.insertHead(4.81); // 4.81 2.4 50.201 7.32 6.15 7.3
    list.insertTail(5.7); // 4.81 2.4 50.201 7.32 6.15 7.3 5.7
    list.insertHead(3.29); // 3.29 4.81 2.4 50.201 7.32 6.15 7.3 5.7
    list.insertTail(54.701); // 3.29 4.81 2.4 50.201 7.32 6.15 7.3 5.7 54.701
    list.insertTail(4.2); // 3.29 4.81 2.4 50.201 7.32 6.15 7.3 5.7 54.701 4.2

    list.removeHead(); // 4.81 2.4 50.201 7.32 6.15 7.3 5.7 54.701 4.2
    list.removeTail(); // 4.81 2.4 50.201 7.32 6.15 7.3 5.7 54.701
    list.removeHead(); // 2.4 50.201 7.32 6.15 7.3 5.7 54.701
    list.removeTail(); // 2.4 50.201 7.32 6.15 7.3 5.7

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке элементов типа bool
 */
TEST(list, Test16) {
    List<bool> list;
    int size = 6;
    bool arrayList[size];
    bool expectedArrayList[] = {true, false, true, true, false, false};

    list.insertTail(true); // true
    list.insertHead(false); // false true
    list.insertHead(true); // true false true
    list.insertTail(true); // true false true true
    list.insertTail(false); // true false true true false
    list.insertHead(false); // false true false true true false
    list.insertTail(false); // false true false true true false false
    list.insertHead(true); // true false true false true true false false
    list.insertTail(true); // true false true false true true false false true
    list.insertTail(false); // true false true false true true false false true false

    list.removeHead(); // false true false true true false false true false
    list.removeTail(); // false true false true true false false true
    list.removeHead(); // true false true true false false true
    list.removeTail(); // true false true true false false

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке элементов типа char
 */
TEST(list, Test17) {
    List<char> list;
    int size = 3;
    char arrayList[size];
    char expectedArrayList[] = {'m', 'a', 'g'};

    list.insertHead('n'); // n
    list.insertHead('i'); // i n
    list.insertHead('g'); // g i n
    list.insertHead('a'); // a g i n
    list.insertHead('m'); // m a g i n
    list.insertHead('i'); // i m a g i n
    list.insertTail('a'); // i m a g i n a
    list.insertTail('t'); // i m a g i n a t
    list.insertTail('i'); // i m a g i n a t i
    list.insertTail('o'); // i m a g i n a t i o
    list.insertTail('n'); // i m a g i n a t i o n

    list.removeHead(); // m a g i n a t i o n

    for (int i = 0; i < 7; i++) {
        list.removeTail();
    }

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}

/**
 * Проверка на списке элементов пользовательского типа данных Alphabet
 */
TEST(list, Test18) {
    List<Alphabet> list;
    int size = 5;
    Alphabet arrayList[size];
    Alphabet expectedArrayList[] = {
            Alphabet('m', EN),
            Alphabet('a', FR),
            Alphabet('g', IT),
            Alphabet('i', EN),
            Alphabet('c', FR)
    };

    list.insertHead(Alphabet('g', IT));
    list.insertHead(Alphabet('a', FR));
    list.insertHead(Alphabet('m', EN));
    list.insertTail(Alphabet('j', EN));
    list.insertTail(Alphabet('s', FR));
    list.insertTail(Alphabet('c', FR));

    auto it = list.iterator();

    for (int i = 0; i < 4; i++) {
        it.next();
    }

    it.remove();
    it.prev();
    it.set(Alphabet('i', EN));

    formArrayFromList(list, arrayList);
    EXPECT_TRUE(elementsAreEqual(arrayList, expectedArrayList, size));
    printArray(arrayList, size);
}
