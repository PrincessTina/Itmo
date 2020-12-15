#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include <algorithm>

#include "../main/helper_library.h"
#include "../main/sort.h"

/**
 * Выводит на экран отсортированный массив в формате:
 * Test{testNum} / result:
 * {array}
 * @tparam T - тип данных
 * @param array - массив, который нужно вывести на экран
 * @param size - размер массива
 * @param testNum - номер теста
 */
template<typename T>
void printResultArray(T *array, int size, int testNum) {
    std::cout << "Test" << testNum << " / result:" << std::endl;
    printArray(array, size);
    std::cout << std::endl;
}

/**
 * Выводит на экран опорный элемент массива в формате:
 * Test{testNum} / result:
 * {pivotElement}
 * @tparam T - тип данных
 * @param pivotElement - опорный элемент
 * @param testNum - номер теста
 */
template<typename T>
void printResultPivot(T pivotElement, int testNum) {
    std::cout << "Test" << testNum << " / result:" << std::endl << pivotElement << std::endl;
}

/**
 * Массив из 1 элемента (== 1: не нуждается в сортировке)
 */
TEST(lab1, Test1) {
    int size = 1;
    int array[] = {101};
    int expectedArray[] = {101};

    sort(array, array + size - 1, commonCompare);
    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 1);
}

/**
 * Массив из 2 равных элементов (<= 12: сортируется только insertion sort)
 */
TEST(lab1, Test2) {
    int size = 2;
    int array[] = {-10, -10};
    int expectedArray[] = {-10, -10};

    sort(array, array + size - 1, commonCompare);
    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 2);
}

/**
 * Массив из 8 элементов (<= 12: сортируется только insertion sort)
 */
TEST(lab1, Test3) {
    int size = 8;
    int array[] = {3, 8, 9, 12, 4, 1, 0, 48};
    int expectedArray[] = {3, 8, 9, 12, 4, 1, 0, 48};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 3);
}

/**
 * Массив из 20 элементов
 */
TEST(lab1, Test4) {
    int size = 20;
    int array[] = {46, 100, 64, 28, 100, 31, 87, 59, 15, 52, 35, 3, 30, 63, 42, 38, 4, 4, 58, 62};
    int expectedArray[] = {46, 100, 64, 28, 100, 31, 87, 59, 15, 52, 35, 3, 30, 63, 42, 38, 4, 4, 58, 62};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 4);
}

/**
 * Массив из 60 случайных элементов
 */
TEST(lab1, Test5) {
    int size = 60;
    int array[60];
    int expectedArray[60];

    for (int i = 0; i < size; i++) {
        array[i] = expectedArray[i] = rand() / 100;
    }

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 5);
}

/**
 * Массив изначально отсортирован по убыванию
 */
TEST(lab1, Test6) {
    int size = 20;
    int array[] = {40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21};
    int expectedArray[] = {40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 6);
}

/**
 * Массив изначально отсортирован по возрастанию
 */
TEST(lab1, Test7) {
    int size = 20;
    int array[] = {-5, -4, -3, -2, -1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    int expectedArray[] = {-5, -4, -3, -2, -1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    sort(array, array + size - 1, commonCompare);
    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 7);
}

/**
 * Несколько элементов равны первому опорному
 */
TEST(lab1, Test8) {
    int size = 16;
    int array[] = {-30, 29, -29, 85, 64, -96, 74, -29, 3, 72, 29, -70, 98, -29, 61, 64};
    int expectedArray[] = {-30, 29, -29, 85, 64, -96, 74, -29, 3, 72, 29, -70, 98, -29, 61, 64};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 8);
}

/**
 * Первый, средний, последний элементы массива равны
 */
TEST(lab1, Test9) {
    int size = 16;
    int array[] = {82, 35, 38, 66, 21, 19, 64, 82, 88, 97, 41, 55, 91, 45, 15, 82};
    int expectedArray[] = {82, 35, 38, 66, 21, 19, 64, 82, 88, 97, 41, 55, 91, 45, 15, 82};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 9);
}

/**
 * Большая часть элементов массива состоит из равных элементов
 */
TEST(lab1, Test10) {
    int size = 16;
    int array[] = {11, 11, -5, 10, 11, 11, 21, 11, 0, 11, -108, 15, 11, 11, 11, 2};
    int expectedArray[] = {11, 11, -5, 10, 11, 11, 21, 11, 0, 11, -108, 15, 11, 11, 11, 2};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size, [](int a, int b) { return commonCompare<int>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 10);
}

/**
 * Массив состоит из равных элементов
 */
TEST(lab1, Test11) {
    int size = 16;
    int array[] = {31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31};
    int expectedArray[] = {31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31};

    sort(array, array + size - 1, commonCompare);
    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 11);
}

/**
 * Массив из элементов типа данных double
 */
TEST(lab1, Test12) {
    int size = 13;
    double array[] = {21.2389, 45.021, -18.99, 18.98, -121.008, 5.01, 21.2388, 0, -121.007, -7, 12.12, -0.21, 191.5};
    double expectedArray[] = {21.2389, 45.021, -18.99, 18.98, -121.008, 5.01, 21.2388, 0, -121.007, -7, 12.12, -0.21,
                              191.5};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size,
              [](double a, double b) { return commonCompare<double>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 12);
}

/**
 * Массив из элементов типа данных bool
 */
TEST(lab1, Test13) {
    int size = 13;
    bool array[] = {false, true, false, false, true, true, true, false, true, false, true, true, false};
    bool expectedArray[] = {false, true, false, false, true, true, true, false, true, false, true, true, false};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size,
              [](bool a, bool b) { return commonCompare<bool>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 13);
}

/**
 * Массив из элементов типа данных char
 */
TEST(lab1, Test14) {
    int size = 11;
    char array[] = {'s', 'p', 'e', 'c', 't', 'a', 'c', 'u', 'l', 'a', 'r'};
    char expectedArray[] = {'s', 'p', 'e', 'c', 't', 'a', 'c', 'u', 'l', 'a', 'r'};

    sort(array, array + size - 1, commonCompare);
    std::sort(expectedArray, expectedArray + size,
              [](char a, char b) { return commonCompare<char>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 14);
}

/**
 * Массив из объектов пользовательского типа данных Gnome
 */
TEST(lab1, Test15) {
    int size = 6;
    Gnome array[] = {
            Gnome("Warren", 40, 0, 150),
            Gnome("Dimple", 42, 0, 120),
            Gnome("Brina", 38, 0, 83.1),
            Gnome("Frog", 42, 2, 102),
            Gnome("Zooko", 61, 21, 145),
            Gnome("Alvin", 73, 27, 139)
    };
    Gnome expectedArray[] = {
            Gnome("Warren", 40, 0, 150),
            Gnome("Dimple", 42, 0, 120),
            Gnome("Brina", 38, 0, 83.1),
            Gnome("Frog", 42, 2, 102),
            Gnome("Zooko", 61, 21, 145),
            Gnome("Alvin", 73, 27, 139)
    };

    sort(array, array + size - 1, gnomeCompare);
    std::sort(expectedArray, expectedArray + size, [](Gnome a, Gnome b) { return gnomeCompare(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 15);
}

/**
 * Сортировка по убыванию
 */
TEST(lab1, Test16) {
    int size = 14;
    char array[] = {'c', 'o', 'u', 'n', 't', 'e', 'r', 'c', 'u', 'l', 't', 'u', 'r', 'e'};
    char expectedArray[] = {'c', 'o', 'u', 'n', 't', 'e', 'r', 'c', 'u', 'l', 't', 'u', 'r', 'e'};

    sort(array, array + size - 1, reverseCompare);
    std::sort(expectedArray, expectedArray + size,
              [](char a, char b) { return reverseCompare<char>(a, b) < 0; });

    EXPECT_THAT(array, ::testing::ElementsAreArray(expectedArray));
    printResultArray(array, size, 16);
}

/**
 * Проверка опорного элемента массива из 1 элемента
 */
TEST(lab1, Test17) {
    int size = 1;
    int array[] = {21};
    int pivotElement;

    compare<int> = commonCompare;
    pivotElement = getPivotElement(array, array + size - 1);

    EXPECT_EQ(pivotElement, 21);
    printResultPivot(pivotElement, 17);
}

/**
 * Проверка опорного элемента массива из 2 элементов
 */
TEST(lab1, Test18) {
    int size = 2;
    int array[] = {19, 48};
    int pivotElement;

    compare<int> = commonCompare;
    pivotElement = getPivotElement(array, array + size - 1);

    EXPECT_EQ(pivotElement, 19);
    printResultPivot(pivotElement, 18);
}

/**
 * Проверка опорного элемента массива из 3 элементов
 */
TEST(lab1, Test19) {
    int size = 3;
    int array[] = {3, 48, 9};
    int pivotElement;

    compare<int> = commonCompare;
    pivotElement = getPivotElement(array, array + size - 1);

    EXPECT_EQ(pivotElement, 9);
    printResultPivot(pivotElement, 19);
}

/**
 * Проверка опорного элемента массива из 4 элементов
 */
TEST(lab1, Test20) {
    int size = 4;
    int array[] = {3, 48, 9, 59};
    int pivotElement;

    compare<int> = commonCompare;
    pivotElement = getPivotElement(array, array + size - 1);

    EXPECT_EQ(pivotElement, 48);
    printResultPivot(pivotElement, 20);
}
