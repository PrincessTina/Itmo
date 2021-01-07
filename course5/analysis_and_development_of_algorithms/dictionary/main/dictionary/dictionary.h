#ifndef DICTIONARY_DICTIONARY_H
#define DICTIONARY_DICTIONARY_H

#include <stack>

enum Color {
    BLACK,
    RED
};

template<typename K, typename V>
class Dictionary final {
    struct Node {
        K key;
        V value;
        Color edgeToParentColor;
        Node *left;
        Node *right;
    };

    class Iterator {
        Node **nodes = nullptr;
        int index = 0;
        int nodesCount = 0;

    private:
        void fillNodes(Node *rootNode);

    public:
        Iterator(Dictionary *dictionary);

        const K &key() const;

        const V &get() const;

        void set(const V &value);

        void next();

        void prev();

        bool hasNext() const;

        bool hasPrev() const;
    };

    Node *root = nullptr;
    int nodesCount = 0;

private:
    Node *allocateNode();

    void freeNode(Node *node);

    Node *find(const K &key);

    void insertNode(const K &key, const V &value, std::stack<Node *> *path);

    Node *findLeftmostNode(Node *node);

    void rotateLeft(Node *parentNode);

    void rotateRight(Node *grandParentNode);

    void flipColor(Node *parentNode);

    void moveRedLeft(Node *node);

    void moveRedRight(Node *node);

    Node *removeLeftmostNode(Node *node, std::stack<Node *> *path);

    void fixNodesOrder(std::stack<Node *> *path, bool needFullTraversal);

public:
    Dictionary();

    ~Dictionary();

    /**
     * Запрет конструктора копирования
     */
    Dictionary(const Dictionary &) = delete;

    /**
     * Запрет оператора присваивания
     * @return
     */
    Dictionary &operator=(const Dictionary &) = delete;

    void put(const K &key, const V &value);

    void remove(const K &key);

    bool contains(const K &key);

    const V &operator[](const K &key) const;

    V &operator[](const K &key);

    int size() const;

    Iterator iterator();

    const Iterator iterator() const;
};

#include "dictionary_impl.h"

#endif //DICTIONARY_DICTIONARY_H
