#ifndef DICTIONARY_DICTIONARY_H
#define DICTIONARY_DICTIONARY_H

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

    Node *find(K key, Node *currentNode);

    Node *find(K key, Node *currentNode, Node *parentNode);

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
