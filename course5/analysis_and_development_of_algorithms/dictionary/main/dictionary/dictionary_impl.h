#ifndef DICTIONARY_DICTIONARY_IMPL_H
#define DICTIONARY_DICTIONARY_IMPL_H

#include "dictionary.h"

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::allocateNode() {
    Node *node = (Node *) malloc(sizeof(Node));
    node->left = node->right = nullptr;
    node->edgeToParentColor = RED;
    return node;
}

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::find(K key, Node *currentNode) {
    if (currentNode == nullptr || key == currentNode->key) {
        return currentNode;
    }

    if (key < currentNode->key) {
        return find(key, currentNode->left);
    }

    if (key > currentNode->key) {
        return find(key, currentNode->right);
    }
}

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::find(K key, Node *currentNode, Node *parentNode) {
    if (currentNode == nullptr) {
        currentNode = allocateNode();
        currentNode->key = key;
        (key < parentNode->key) ? parentNode->left = currentNode : parentNode->right = currentNode;
        return parentNode;
    }

    if (key < currentNode->key) {
        return find(key, currentNode->left, currentNode);
    }

    if (key > currentNode->key) {
        return find(key, currentNode->right, currentNode);
    }
}

template<typename K, typename V>
Dictionary<K, V>::Dictionary() {
    root = allocateNode();
}

template<typename K, typename V>
Dictionary<K, V>::~Dictionary() {

}

template<typename K, typename V>
void Dictionary<K, V>::put(const K &key, const V &value) {
    if (nodesCount == 0) {
        root->key = key;
        root->value = value;
        root->edgeToParentColor = BLACK;
    } else {
        Node *existingNode = find(key, root);
        if (existingNode != nullptr) {
            existingNode->value = value;
            return;
        }

        Node *parentNode = find(key, root, nullptr);
        Node *newNode = (parentNode->left && parentNode->left->key == key) ? parentNode->left : parentNode->right;
        newNode->value = value;
        // ToDo: повороты для балансировки
    }

    nodesCount++;
}

template<typename K, typename V>
void Dictionary<K, V>::remove(const K &key) {

}

template<typename K, typename V>
bool Dictionary<K, V>::contains(const K &key) {
    return find(key, root) != nullptr;
}

template<typename K, typename V>
const V &Dictionary<K, V>::operator[](const K &key) const {
    Node *desired = find(key, root);

    if (desired == nullptr) {
        V value;
        V *pointer = &value;
        return *pointer;
    } else {
        return desired->value;
    }
}

template<typename K, typename V>
V &Dictionary<K, V>::operator[](const K &key) {
    Node *desired = find(key, root);

    if (desired == nullptr) {
        V value;
        V *pointer = &value;
        put(key, value);
        return *pointer;
    } else {
        return desired->value;
    }
}

template<typename K, typename V>
int Dictionary<K, V>::size() const {
    return nodesCount;
}

#endif //DICTIONARY_DICTIONARY_IMPL_H
