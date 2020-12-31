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

    Node *root = nullptr;
    int nodesCount = 0;

private:
    Node *allocateNode();

    void freeNode(Node *node);

    Node *find(const K &key);

    void find(const K &key, const V &value, std::stack<Node *> *path);

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

    void put(const K &key, const V &value);

    void remove(const K &key);

    bool contains(const K &key);

    const V &operator[](const K &key) const;

    V &operator[](const K &key);

    int size() const;
};

#include "dictionary_impl.h"

#endif //DICTIONARY_DICTIONARY_H
