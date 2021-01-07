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
    printTree(&map);
}

/**
 * Проверка операции contains, включающей операцию find
 */
TEST(map, Test2) {
    Dictionary<int, int> map;
    map.put(42, 10);

    EXPECT_EQ(map.contains(42), true);
    EXPECT_EQ(map.contains(13), false);
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
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
    printTree(&map);
}

/**
 * Проверка нескольких операций remove
 */
TEST(map, Test12) {
    int pasteOrder[] = {0, 5, 2, 13, 10, 19, 20, 4, 1, 3, 8, 7, 6, 18, 17, 15, 16, 12, 9, 11, 14};
    int removeOrder[] = {18, 13, 12, 16, 20, 19, 10, 11, 15, 17, 14};
    Dictionary<int, int> map;

    for (int i: pasteOrder) {
        map.put(i, i);
    }

    for (int i: removeOrder) {
        map.remove(i);
    }

    map.put(10, 10);

    for (int i = 0; i <= 10; i++) {
        EXPECT_EQ(map[i], i);
    }

    EXPECT_EQ(map.size(), 11);
    printTree(&map);
}

/**
 * Проверка операций key, get, next, hasNext, prev, hasPrev итератора
 */
TEST(map, Test13) {
    int pasteOrder[] = {0, 5, 2, 13, 10, 19, 20, 4, 1, 3, 8, 7, 6, 18, 17, 15, 16, 12, 9, 11, 14};
    int removeOrder[] = {18, 13, 12, 16, 20, 19, 10, 11, 15, 17, 14};
    Dictionary<int, int> map;

    for (int i: pasteOrder) {
        map.put(i, i);
    }

    for (int i: removeOrder) {
        map.remove(i);
    }

    map.put(10, 10);

    std::cout << "От корня к последнему:" << std::endl;

    auto it = map.iterator();
    for (it = map.iterator(); it.hasNext(); it.next()) {
        EXPECT_EQ(it.key(), it.get());
        std::cout << "(" << it.key() << ", " << it.get() << ")" << std::endl;
    }

    EXPECT_EQ(it.key(), it.get());
    std::cout << "(" << it.key() << ", " << it.get() << ")" << std::endl << std::endl << "От последнего к корню:" <<
              std::endl;

    for (it = it; it.hasPrev(); it.prev()) {
        EXPECT_EQ(it.key(), it.get());
        std::cout << "(" << it.key() << ", " << it.get() << ")" << std::endl;
    }

    EXPECT_EQ(it.key(), it.get());
    std::cout << "(" << it.key() << ", " << it.get() << ")";
}

/**
 * Проверка операции set итератора
 */
TEST(map, Test14) {
    int pasteOrder[] = {0, 5, 2, 13, 10, 19, 20, 4, 1, 3, 8, 7, 6, 18, 17, 15, 16, 12, 9, 11, 14};
    int removeOrder[] = {18, 13, 12, 16, 20, 19, 10, 11, 15, 17, 14};
    Dictionary<int, int> map;

    for (int i: pasteOrder) {
        map.put(i, i);
    }

    for (int i: removeOrder) {
        map.remove(i);
    }

    map.put(10, 10);

    auto it = map.iterator();
    for (int i = 0; i < 3; i++) {
        it.next();
    }

    EXPECT_EQ(map[0], 0);
    it.set(100);
    EXPECT_EQ(map[0], 100);
    printTree(&map);
}

