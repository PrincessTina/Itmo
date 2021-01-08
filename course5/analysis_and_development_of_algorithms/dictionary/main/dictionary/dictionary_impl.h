#ifndef DICTIONARY_DICTIONARY_IMPL_H
#define DICTIONARY_DICTIONARY_IMPL_H

#include "dictionary.h"

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::allocateNode() {
    Node *node = new Node;
    K key;
    V value;

    node->key = key;
    node->value = value;
    node->left = node->right = nullptr;
    node->edgeToParentColor = RED;

    return node;
}

template<typename K, typename V>
void Dictionary<K, V>::freeNode(Node *node) {
    delete(node);
}

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::find(const K &key) {
    Node *currentNode = root;

    while (currentNode && key != currentNode->key) {
        if (key < currentNode->key) {
            currentNode = currentNode->left;
        } else {
            currentNode = currentNode->right;
        }
    }

    return currentNode;
}

template<typename K, typename V>
void Dictionary<K, V>::insertNode(const K &key, const V &value, std::stack<Node *> *path) {
    Node *currentNode = root;

    while (currentNode) {
        path->push(currentNode);

        if (key < currentNode->key) {
            currentNode = currentNode->left;
        } else {
            currentNode = currentNode->right;
        }
    }

    currentNode = allocateNode();
    currentNode->key = key;
    currentNode->value = value;

    Node *parent = path->top();
    (key < parent->key) ? parent->left = currentNode : parent->right = currentNode;
}

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::findLeftmostNode(Node *node) {
    Node *currentNode = node;

    while (currentNode->left) {
        currentNode = currentNode->left;
    }

    return currentNode;
}

template<typename K, typename V>
void Dictionary<K, V>::rotateLeft(Dictionary::Node *parentNode) {
    Node parent = *parentNode;
    Node child = *parentNode->right;

    parentNode->key = child.key;
    parentNode->value = child.value;

    parentNode->left = parentNode->right;
    parentNode->right = child.right;

    parentNode->left->left = parent.left;
    parentNode->left->right = child.left;

    parentNode->left->key = parent.key;
    parentNode->left->value = parent.value;
}

template<typename K, typename V>
void Dictionary<K, V>::rotateRight(Dictionary::Node *grandParentNode) {
    Node grandParent = *grandParentNode;
    Node parent = *grandParentNode->left;

    grandParentNode->key = parent.key;
    grandParentNode->value = parent.value;

    grandParentNode->right = grandParentNode->left;
    grandParentNode->left = parent.left;

    grandParentNode->right->left = parent.right;
    grandParentNode->right->right = grandParent.right;

    grandParentNode->right->key = grandParent.key;
    grandParentNode->right->value = grandParent.value;
}

template<typename K, typename V>
void Dictionary<K, V>::flipColor(Dictionary::Node *parentNode) {
    if (parentNode->left->edgeToParentColor == RED) {
        parentNode->left->edgeToParentColor = parentNode->right->edgeToParentColor = BLACK;
        (parentNode != root) ? parentNode->edgeToParentColor = RED : parentNode->edgeToParentColor = BLACK;
    } else {
        parentNode->left->edgeToParentColor = parentNode->right->edgeToParentColor = RED;
        parentNode->edgeToParentColor = BLACK;
    }
}

template<typename K, typename V>
void Dictionary<K, V>::moveRedLeft(Dictionary::Node *node) {
    flipColor(node);

    if (node->right->left && node->right->left->edgeToParentColor == RED) {
        rotateRight(node->right);
        rotateLeft(node);
        flipColor(node);
    }
}

template<typename K, typename V>
void Dictionary<K, V>::moveRedRight(Dictionary::Node *node) {
    flipColor(node);

    if (node->left->left && node->left->left->edgeToParentColor == RED) {
        rotateRight(node);
        flipColor(node);
    }
}

template<typename K, typename V>
typename Dictionary<K, V>::Node *Dictionary<K, V>::removeLeftmostNode(Node *node, std::stack<Node *> *path) {
    Node *currentNode = node;

    while (currentNode->left) {
        path->push(currentNode);

        if ((currentNode->left->left && currentNode->left->left->edgeToParentColor == BLACK &&
             currentNode->left->edgeToParentColor == BLACK) ||
            (!currentNode->left->left && currentNode->left->edgeToParentColor == BLACK)) {
            moveRedLeft(currentNode);
        }

        currentNode = currentNode->left;
    }

    if (!currentNode->left) {
        path->push(currentNode);
    }

    return currentNode;
}

template<typename K, typename V>
void Dictionary<K, V>::fixNodesOrder(std::stack<Node *> *path, bool needFullTraversal) {
    bool fixed = false;

    while (path->size() != 0 && fixed != true) {
        Node *parentNode = path->top();
        path->pop();

        if ((parentNode->left && parentNode->left->edgeToParentColor == RED) &&
            (parentNode->right && parentNode->right->edgeToParentColor == RED)) {
            flipColor(parentNode);
        } else if (parentNode->edgeToParentColor == RED) {
            if (parentNode->right && parentNode->right->edgeToParentColor == RED) {
                rotateLeft(parentNode);
            }

            if (parentNode->left && parentNode->left->edgeToParentColor == RED) {
                rotateRight(path->top());
            }
        } else if (parentNode->right && parentNode->right->edgeToParentColor == RED) {
            rotateLeft(parentNode);
        } else if (!needFullTraversal) {
            fixed = true;
        }
    }
}

template<typename K, typename V>
Dictionary<K, V>::Dictionary() {
    root = allocateNode();
}

template<typename K, typename V>
Dictionary<K, V>::~Dictionary() {
    while (nodesCount != 0) {
        K key = root->key;
        remove(key);
    }

    freeNode(root);
}

template<typename K, typename V>
void Dictionary<K, V>::put(const K &key, const V &value) {
    if (nodesCount == 0) {
        root->key = key;
        root->value = value;
        root->edgeToParentColor = BLACK;
    } else {
        Node *existingNode = find(key);
        if (existingNode) {
            existingNode->value = value;
            return;
        }

        std::stack<Node *> path;
        insertNode(key, value, &path);
        fixNodesOrder(&path, false);
    }

    nodesCount++;
}

template<typename K, typename V>
void Dictionary<K, V>::remove(const K &key) {
    if (!contains(key) || nodesCount == 0) {
        return;
    }

    if (nodesCount == 1) {
        nodesCount--;
        return;
    }

    std::stack<Node *> path;
    Node *currentNode = root;

    while (true) {
        if (key < currentNode->key) {
            path.push(currentNode);

            if (!currentNode->left) {
                break;
            }

            if ((currentNode->left->left && currentNode->left->left->edgeToParentColor == BLACK &&
                 currentNode->left->edgeToParentColor == BLACK) ||
                (!currentNode->left->left && currentNode->left->edgeToParentColor == BLACK)) {
                moveRedLeft(currentNode);
            }

            currentNode = currentNode->left;
        } else {
            path.push(currentNode);

            if (currentNode->left && currentNode->left->edgeToParentColor == RED) {
                rotateRight(currentNode);
            }

            if ((key == currentNode->key && !currentNode->right) || !currentNode->right) {
                break;
            }

            if ((currentNode->right->left && currentNode->right->left->edgeToParentColor == BLACK &&
                 currentNode->right->edgeToParentColor == BLACK) ||
                (!currentNode->right->left && currentNode->right->edgeToParentColor == BLACK)) {
                moveRedRight(currentNode);
            }

            if (key == currentNode->key) {
                Node *leftmost = findLeftmostNode(currentNode->right);

                currentNode->key = leftmost->key;
                currentNode->value = leftmost->value;

                currentNode = removeLeftmostNode(currentNode->right, &path);
                break;
            } else {
                currentNode = currentNode->right;
            }
        }
    }

    path.pop();
    Node *parent = path.top();

    (parent->left && parent->left->key == currentNode->key) ? parent->left = nullptr : parent->right = nullptr;
    freeNode(currentNode);
    nodesCount--;

    fixNodesOrder(&path, true);
}

template<typename K, typename V>
bool Dictionary<K, V>::contains(const K &key) {
    return find(key);
}

template<typename K, typename V>
const V &Dictionary<K, V>::operator[](const K &key) const {
    Node *desired = find(key);

    if (!desired) {
        return new V;
    } else {
        return desired->value;
    }
}

template<typename K, typename V>
V &Dictionary<K, V>::operator[](const K &key) {
    Node *desired = find(key);

    if (!desired) {
        V value;

        put(key, value);
        desired = find(key);
    }

    return desired->value;
}

template<typename K, typename V>
int Dictionary<K, V>::size() const {
    return nodesCount;
}

template<typename K, typename V>
typename Dictionary<K, V>::Iterator Dictionary<K, V>::iterator() {
    return Iterator(this);
}

template<typename K, typename V>
const typename Dictionary<K, V>::Iterator Dictionary<K, V>::iterator() const {
    return Iterator(this);
}

template<typename K, typename V>
Dictionary<K, V>::Iterator::Iterator(Dictionary *dictionary) {
    nodesCount = dictionary->nodesCount;
    fillNodes(dictionary->root);
}

template<typename K, typename V>
void Dictionary<K, V>::Iterator::fillNodes(Node *rootNode) {
    std::stack<Node *> path;
    nodes = new Node*[nodesCount];

    path.push(rootNode);
    nodes[0] = path.top();

    for (int i = 1; i < nodesCount; i++) {
        Node *node = path.top();
        Node *prev = node;

        while (true) {
            if (node->left && node->left != prev && node->right != prev) {
                path.push(node->left);
                break;
            } else if (node->right && node->right != prev) {
                path.push(node->right);
                break;
            } else {
                prev = path.top();
                path.pop();
                node = path.top();
            }
        }

        nodes[i] = path.top();
    }
}

template<typename K, typename V>
const K &Dictionary<K, V>::Iterator::key() const {
    Node *node = nodes[index];
    return node->key;
}

template<typename K, typename V>
const V &Dictionary<K, V>::Iterator::get() const {
    Node *node = nodes[index];
    return node->value;
}

template<typename K, typename V>
void Dictionary<K, V>::Iterator::set(const V &value) {
    Node *node = nodes[index];
    node->value = value;
}

template<typename K, typename V>
void Dictionary<K, V>::Iterator::next() {
    if (hasNext()) {
        index++;
    }
}

template<typename K, typename V>
void Dictionary<K, V>::Iterator::prev() {
    if (hasPrev()) {
        index--;
    }
}

template<typename K, typename V>
bool Dictionary<K, V>::Iterator::hasNext() const {
    return index + 1 < nodesCount;
}

template<typename K, typename V>
bool Dictionary<K, V>::Iterator::hasPrev() const {
    return index - 1 >= 0;
}

#endif //DICTIONARY_DICTIONARY_IMPL_H
