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

TEST(map, Test5) {
    Dictionary<int, int> map;
    map.put(1, 110);
    map.put(3, 87);
    map.put(5, 22);
    map.put(6, 10);
    map.put(7, 45);
    map.put(0, -17);

    //EXPECT_EQ(map[1], 110);
    //EXPECT_EQ(map[6], 87);
}

