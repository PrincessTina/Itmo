#include <gtest/gtest.h>
#include <gmock/gmock.h>

#include "../main/helper_library.h"

/**
 * Проверяет, что сформированный динамический массив и стандартный статический массив состоят из одних и тех же элементов
 * @tparam T - тип элементов массивов
 * @param array - сформированный динамический массив
 * @param expectedArray - стандартный статический массив
 * @param size - количество элементов в масивах
 * @return true, если массивы одинаковы, иначе false
 */
template<typename T>
bool elementsAreEqual(Array<T> &array, T *expectedArray, int size) {
    for (int i = 0; i < size; i++) {
        if (array[i] != expectedArray[i]) {
            return false;
        }
    }

    return true;
}

/**
 * Базовая проверка на то, что работает операция вставки в конец и операция get
 */
TEST(array, Test1) {
    Array<int> array;

    array.insert(18);
    EXPECT_EQ(array[0], 18);
    printCustomArray(array);
}

/**
 * Проверка, что работает операция присвоения
 */
TEST(array, Test2) {
    Array<int> array;

    array.insert(18);
    EXPECT_EQ(array[0], 18);

    array[0] = 9;
    EXPECT_EQ(array[0], 9);

    printCustomArray(array);
}

/**
 * Проверка, что нельзя выйти за границы массива по операции get
 */
TEST(array, Test3) {
    Array<int> array;

    EXPECT_THROW(array[0], std::invalid_argument);

    array.insert(12);

    EXPECT_NO_THROW(array[0]);
    EXPECT_THROW(array[-1], std::invalid_argument);
    EXPECT_THROW(array[1], std::invalid_argument);

    printCustomArray(array);
}

/**
 * Проверка размера массива
 */
TEST(array, Test4) {
    Array<int> array;

    EXPECT_EQ(array.size(), 0);

    array.insert(12);
    array.insert(39);
    array.insert(-4);
    array.insert(0);

    EXPECT_EQ(array.size(), 4);
    printCustomArray(array);
}

/**
 * Проверка инициализации массива с изначальным capacity 3
 */
TEST(array, Test5) {
    Array<int> array(3);
    int size = 3;
    int expectedArray[] = {12, 39, -4};

    array.insert(12);
    array.insert(39);
    array.insert(-4);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка возможности массива увеличивать свой изначальный capacity с 3 до 6, а затем с 6 до 12, с 12 до 24
 */
TEST(array, Test6) {
    Array<int> array(3);
    int size = 13;
    int expectedArray[] = {43, 36, 72, 44, 35, 99, 23, 35, 38, 17, 32, 40, 64};

    array.insert(43);
    array.insert(36);
    array.insert(72);
    array.insert(44);
    array.insert(35);
    array.insert(99);
    array.insert(23);
    array.insert(35);
    array.insert(38);
    array.insert(17);
    array.insert(32);
    array.insert(40);
    array.insert(64);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка возможности массива увеличивать свой изначальный capacity с 16 (значение по умолчанию) до 32, с 32 до 64
 */
TEST(array, Test7) {
    Array<int> array;
    int size = 36;
    int expectedArray[size];

    for (int i = 0; i < size; i++) {
        int random = rand() / 100;

        array.insert(random);
        expectedArray[i] = random;
    }

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка операции вставки по индексу в начало массива
 */
TEST(array, Test8) {
    Array<int> array;
    int size = 5;
    int expectedArray[] = {5, -12, 10, 81, 0};

    array.insert(-12);
    array.insert(10);
    array.insert(81);
    array.insert(0);
    array.insert(0, 5);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка операции вставки по индексу в конец массива
 */
TEST(array, Test9) {
    Array<int> array;
    int size = 5;
    int expectedArray[] = {-12, 10, 81, 0, 5};

    array.insert(-12);
    array.insert(10);
    array.insert(81);
    array.insert(0);
    array.insert(4, 5);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка операции вставки по индексу
 */
TEST(array, Test10) {
    Array<int> array;
    int size = 5;
    int expectedArray[] = {-12, 10, 5, 81, 0};

    array.insert(-12);
    array.insert(10);
    array.insert(81);
    array.insert(0);
    array.insert(2, 5);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка, что нельзя выйти за границы массива по операции insert(index, value)
 */
TEST(array, Test11) {
    Array<int> array;
    int size = 6;
    int expectedArray[] = {-12, 10, 81, 0, 5, 33};

    array.insert(-12);
    array.insert(10);
    array.insert(81);
    array.insert(0);

    EXPECT_THROW(array.insert(-1, 33), std::invalid_argument);
    EXPECT_THROW(array.insert(5, 33), std::invalid_argument);

    array.insert(4, 5);

    EXPECT_NO_THROW(array.insert(5, 33));
    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка операции удаления по индексу из начала массива
 */
TEST(array, Test12) {
    Array<int> array;
    int size = 3;
    int expectedArray[] = {10, 49, 2};

    array.insert(18);
    array.insert(10);
    array.insert(49);
    array.insert(2);

    array.remove(0);
    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка операции удаления по индексу из конца массива
 */
TEST(array, Test13) {
    Array<int> array;
    int size = 3;
    int expectedArray[] = {18, 10, 49};

    array.insert(18);
    array.insert(10);
    array.insert(49);
    array.insert(2);

    array.remove(3);
    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка операции удаления по индексу
 */
TEST(array, Test14) {
    Array<int> array;
    int size = 3;
    int expectedArray[] = {18, 10, 2};

    array.insert(18);
    array.insert(10);
    array.insert(49);
    array.insert(2);

    array.remove(2);
    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка, что нельзя выйти за границы массива по операции remove
 */
TEST(array, Test15) {
    Array<int> array;
    int size = 3;
    int expectedArray[] = {18, 49, 2};

    array.insert(18);
    array.insert(10);
    array.insert(49);
    array.insert(2);

    EXPECT_THROW(array.remove(-1), std::invalid_argument);
    EXPECT_THROW(array.remove(4), std::invalid_argument);
    EXPECT_NO_THROW(array.remove(1));

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка, что работают операции insert и get итератора
 */
TEST(array, Test16) {
    Array<int> array;
    auto iterator = array.iterator();

    iterator.insert(102);
    EXPECT_EQ(iterator.get(), 102);
    printCustomArray(array);
}

/**
 * Проверка, что работает операция set итератора
 */
TEST(array, Test17) {
    Array<int> array;
    auto iterator = array.iterator();

    iterator.insert(108);
    EXPECT_EQ(iterator.get(), 108);

    iterator.set(90);
    EXPECT_EQ(iterator.get(), 90);

    printCustomArray(array);
}

/**
 * Проверка доступа к элементу по индексу у итератора
 */
TEST(array, Test18) {
    Array<int> array;
    auto iterator = array.iterator();

    array.insert(123);
    array.insert(78);
    array.insert(-1);
    array.insert(35);

    iterator.toIndex(2);
    EXPECT_EQ(iterator.get(), -1);

    iterator.set(41);
    EXPECT_EQ(array[2], 41);

    printCustomArray(array);
}

/**
 * Проверка взаимодействия с массивом с помощью итератора
 */
TEST(array, Test19) {
    Array<int> array;
    int exp1[] = {11, 39, 82};
    int exp2[] = {11, 39, 8, 13, 82};
    int exp3[] = {11, 13};
    auto iterator = array.iterator();

    iterator.insert(82); // вставка в 0, так как iterator указывает на 0: {82}
    iterator.insert(39); // вставка в 0, так как iterator указывает на 0: {39, 82}
    iterator.insert(11); // вставка в 0, так как iterator указывает на 0: {11, 39, 82}
    EXPECT_TRUE(elementsAreEqual(array, exp1, 3));

    iterator.toIndex(2); // указывает на 82 (2ой)
    EXPECT_FALSE(iterator.hasNext());

    iterator.insert(13); // вставка в 2, так как iterator указывает на 2: {11, 39, 13, 82}
    iterator.insert(8); // вставка в 2, так как iterator указывает на 2: {11, 39, 8, 13, 82}
    EXPECT_TRUE(elementsAreEqual(array, exp2, 5));

    while (iterator.hasNext()) {
        iterator.next();
    }

    EXPECT_EQ(iterator.get(), 82); // указывает на 82 (4ый)

    iterator.remove(); // удаление с 4, так как iterator указывал на 4: {11, 39, 8, 13}
    EXPECT_EQ(iterator.get(), 13); // указывает на 13 (3ий)

    iterator.toIndex(2);
    iterator.remove(); // удаление с 2, так как iterator указывает на 2: {11, 39, 13}
    EXPECT_EQ(iterator.get(), 13); // указывает на 13 (2ой)

    while (iterator.hasPrev()) {
        iterator.prev();
    }

    EXPECT_EQ(iterator.get(), 11); // указывает на 11 (0ой)

    iterator.next(); // указывает на 39 (1ый)
    iterator.remove(); // удаление с 2, так как iterator указывает на 2: {11, 13}
    EXPECT_TRUE(elementsAreEqual(array, exp3, 2));
    printCustomArray(array);
}

/**
 * Проверка на массиве из элементов типа double
 */
TEST(array, Test20) {
    Array<double> array;
    int size = 3;
    double expectedArray[] = {1.08, 4.94, 2.13};

    array.insert(1.08);
    array.insert(0.102);
    array.insert(4.94);
    array.insert(2.13);

    array.remove(1);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка на массиве из элементов типа bool
 */
TEST(array, Test21) {
    Array<bool> array;
    int size = 5;
    bool expectedArray[] = {true, true, false, false, true};

    array.insert(true);
    array.insert(true);
    array.insert(true);
    array.insert(false);
    array.insert(false);
    array.insert(true);

    array.remove(0);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка на массиве из элементов типа char
 */
TEST(array, Test22) {
    Array<char> array;
    int size = 6;
    char expectedArray[] = {'d', 'e', 's', 'e', 'r', 't'};

    array.insert('d');
    array.insert('e');
    array.insert('s');
    array.insert('s');
    array.insert('e');
    array.insert('r');
    array.insert('t');

    array.remove(3);

    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

/**
 * Проверка на массиве из элементов пользовательского типа данных Flower
 */
TEST(array, Test23) {
    Array<Flower> array;
    int size = 6;
    Flower expectedArray[] = {
            Flower(Rose, White, 0.6, 50),
            Flower(Rose, Pink, 0.7, 58),
            Flower(Tulip, Purple, 0.72, 34),
            Flower(Daisy, White, 0.3, 18),
            Flower(Narcissus, Yellow, 0.68, 37),
            Flower(Lily, White, 1, 61)
    };

    array.insert(Flower(Rose, White, 0.6, 50));
    array.insert(Flower(Rose, Pink, 0.7, 58));
    array.insert(Flower(Tulip, Orange, 0.79, 32));
    array.insert(Flower(Daisy, White, 0.3, 18));
    array.insert(Flower(Narcissus, Yellow, 0.68, 37));
    array.insert(Flower(Lily, White, 1, 61));

    array[2] = Flower(Tulip, Purple, 0.72, 34);
    EXPECT_TRUE(elementsAreEqual(array, expectedArray, size));
    printCustomArray(array);
}

