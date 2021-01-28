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
    map.printTree();
}

/**
 * Проверка операции contains, включающей операцию find
 */
TEST(map, Test2) {
    Dictionary<int, int> map;
    map.put(42, 10);

    EXPECT_EQ(map.contains(42), true);
    EXPECT_EQ(map.contains(13), false);
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
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
    map.printTree();
}

/**
 * Проверка дерева элементов со значениями типа string
 */
TEST(map, Test15) {
    Dictionary<int, std::string> topFriends;
    int keys[] = {0, 5, 2, 6, 10, 9, 7, 4, 1, 3, 8};
    std::string values[] = {"Alyson", "Shannon", "Mercy", "Jasper", "Jayson", "Clare", "Daniela", "Gary", "Miranda",
                            "Lesley", "Brent"};

    for (int i = 0; i < 11; i++) {
        topFriends.put(keys[i], values[i]);
    }

    for (int i = 0; i < 11; i++) {
        EXPECT_EQ(topFriends[keys[i]], values[i]);
    }

    topFriends.printTree();
}

/**
 * Проверка дерева элементов со значениями типа Friend
 */
TEST(map, Test16) {
    Dictionary<int, Friend> topFriends;
    int keys[] = {0, 5, 2, 6, 10, 9, 7, 4, 1, 3, 8};
    Friend values[] = {
            Friend("Alyson", "Rose"),
            Friend("Shannon", "Chambers"),
            Friend("Mercy", "Wilcox"),
            Friend("Jasper", "Fowler"),
            Friend("Jayson", "Cook"),
            Friend("Clare", "Harris"),
            Friend("Daniela", "Kelly"),
            Friend("Gary", "Barker"),
            Friend("Miranda", "Golden"),
            Friend("Lesley", "Berry"),
            Friend("Brent", "Benson")
    };

    for (int i = 0; i < 11; i++) {
        topFriends.put(keys[i], values[i]);
    }

    for (int i = 0; i < 11; i++) {
        EXPECT_EQ(topFriends[keys[i]], values[i]);
    }

    topFriends.printTree();
}

/**
 * Проверка дерева элементов с ключами типа string
 */
TEST(map, Test17) {
    Dictionary<std::string, int> topFriends;
    std::string keys[] = {"Alyson", "Shannon", "Mercy", "Jasper", "Jayson", "Clare", "Daniela", "Gary", "Miranda",
                          "Lesley", "Brent"};
    int values[] = {0, 5, 2, 6, 10, 9, 7, 4, 1, 3, 8};

    for (int i = 0; i < 11; i++) {
        topFriends.put(keys[i], values[i]);
    }

    for (int i = 0; i < 11; i++) {
        EXPECT_EQ(topFriends[keys[i]], values[i]);
    }

    topFriends.printTree();
}

/**
 * Проверка дерева элементов с ключами типа Friend
 */
TEST(map, Test18) {
    Dictionary<Friend, int> topFriends;
    Friend keys[] = {
            Friend("Alyson", "Rose"),
            Friend("Shannon", "Chambers"),
            Friend("Mercy", "Wilcox"),
            Friend("Jasper", "Fowler"),
            Friend("Jayson", "Cook"),
            Friend("Clare", "Harris"),
            Friend("Daniela", "Kelly"),
            Friend("Gary", "Barker"),
            Friend("Miranda", "Golden"),
            Friend("Lesley", "Berry"),
            Friend("Brent", "Benson")
    };
    int values[] = {0, 5, 2, 6, 10, 9, 7, 4, 1, 3, 8};

    for (int i = 0; i < 11; i++) {
        topFriends.put(keys[i], values[i]);
    }

    for (int i = 0; i < 11; i++) {
        EXPECT_EQ(topFriends[keys[i]], values[i]);
    }

    topFriends.printTree();
}

/**
 * Проверка вставки 100 случайных элементов и вывода дерева на экран
 */
TEST(map, Test19) {
    Dictionary<int, Friend> topFriends;
    int variantsCount = 26;
    int friendsCount = 10;
    std::string names[] = {"Alyson", "Shannon", "Mercy", "Jasper", "Jayson", "Clare", "Daniela", "Gary", "Miranda",
                           "Lesley", "Brent", "Gladys", "Mabel", "Berenice", "Debra", "Candice", "Barbara", "Austen",
                           "Jeremy", "Edgar", "Noel", "Clarence", "Bruno", "Bryce", "Colin", "Philip"};
    std::string surnames[] = {"Rose", "Chambers", "Wilcox", "Fowler", "Cook", "Harris", "Kelly", "Barker", "Golden",
                              "Berry", "Benson", "Davidson", "Short", "Cummings", "Welch", "Douglas", "Poole",
                              "Ferguson", "Stafford", "Miller", "Flynn", "Freeman", "Norris", "Hodge", "Bryan", "Wilkins"};

    srand(time(0));

    for (int i = 0; i < friendsCount; i++) {
        int key;

        do {
            key = random(0, friendsCount - 1);
        } while (topFriends.contains(key));

        topFriends.put(key, Friend(names[random(0, variantsCount - 1)],
                                   surnames[random(0, variantsCount - 1)]));
    }

    topFriends.printTree();
}