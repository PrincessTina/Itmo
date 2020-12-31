#include <gtest/gtest.h>
#include <gmock/gmock.h>

#include "../main/helper_library.h"

/**
 * Базовая операция инициализации и вставки первого элемента, проверка операции size
 */
TEST(map, Test1) {
    Dictionary<int, int> map;
    map.put(12, 4);
    EXPECT_EQ(map.size(), 1);
}

/**
 * Проверка операции contains, включающей операцию find
 */
TEST(map, Test2) {
    Dictionary<int, int> map;
    map.put(42, 10);
    EXPECT_EQ(map.contains(42), true);
    EXPECT_EQ(map.contains(13), false);
}

/**
 * Проверка оператора индексирования на чтение и запись
 */
TEST(map, Test3) {
    Dictionary<int, int> map;
    map[39];
    EXPECT_EQ(map[39], 0);
    map[39] = 92;
    EXPECT_EQ(map[39], 92);
    map[39] = 74;
    EXPECT_EQ(map[39], 74);
}

/**
 * Проверка операции вставки второго элемента
 */
TEST(map, Test4) {
    Dictionary<int, int> map;
    map.put(1, 110);
    map.put(6, 87);
    map[6] = 33;

    EXPECT_EQ(map[1], 110);
    EXPECT_EQ(map[6], 33);
    EXPECT_EQ(map.size(), 2);
}

/**
 * Проверка операций вставки 20 элементов
 */
TEST(map, Test5) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 20; i++) {
        map.put(i, i);
    }

    for (int i = 1; i <= 20; i++) {
        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 20);
}

/**
 * Проверка операции remove из самого левого листа
 */
TEST(map, Test6) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 20; i++) {
        map.put(i, i);
    }

    map.put(0, 0);
    map.remove(0);

    for (int i = 1; i <= 20; i++) {
        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 20);
}

/**
 * Проверка операции remove из самого правого листа
 */
TEST(map, Test7) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 19; i++) {
        map.put(i, i);
    }

    map.remove(19);

    for (int i = 1; i <= 18; i++) {
        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 18);
}

/**
 * Проверка операции remove из правого листа самой левой ноды
 */
TEST(map, Test8) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 20; i++) {
        map.put(i, i);
    }

    map.put(0, 0);
    map.remove(3);

    for (int i = 0; i <= 20; i++) {
        if (i == 3) {
            continue;
        }

        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 20);
}

/**
 * Проверка операции remove из левого листа самой правой ноды
 */
TEST(map, Test9) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 20; i++) {
        map.put(i, i);
    }

    map.put(0, 0);
    map.remove(19);

    for (int i = 0; i <= 20; i++) {
        if (i == 19) {
            continue;
        }

        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 20);
}

/**
 * Проверка операции remove не из листа
 */
TEST(map, Test10) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 20; i++) {
        map.put(i, i);
    }

    map.put(0, 0);
    map.remove(4);

    for (int i = 0; i <= 20; i++) {
        if (i == 4) {
            continue;
        }

        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 20);
}

/**
 * Проверка операции remove из корня
 */
TEST(map, Test11) {
    Dictionary<int, int> map;

    for (int i = 1; i <= 20; i++) {
        map.put(i, i);
    }

    map.put(0, 0);
    map.remove(8);

    for (int i = 0; i <= 20; i++) {
        if (i == 8) {
            continue;
        }

        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 20);
}

/**
 * Проверка нескольких операций remove и put
 */
TEST(map, Test12) {

}

