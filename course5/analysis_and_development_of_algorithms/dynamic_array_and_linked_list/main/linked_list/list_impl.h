#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H

template<typename T>
typename List<T>::Node *List<T>::allocateChunk() {
    return (Node *) malloc(sizeof(Node) * chunkSize);
}

template<typename T>
void List<T>::freeChunk(Node *chunk) {
    free(chunk);
}

template<typename T>
void List<T>::throwException() {
    throw std::invalid_argument("Accessing list out of bounds");
}

template<typename T>
List<T>::List() {
    headNode = allocateChunk();
    headNode->index = -1;
}

template<typename T>
List<T>::~List() {
    if (nodesCount == 0) {
        free(headNode);
    }
}

template<typename T>
void List<T>::insertHead(const T &value) {
    int index = chunkSize - 1;

    if (nodesCount == 0) {
        headNode = headNode->index == -1 ? headNode + index : headNode;
        tailNode = headNode;
    } else if (headNode->index == 0) {
        Node *temporary = allocateChunk();
        temporary += index;

        headNode->nextChunk = temporary;
        temporary->nextChunk = headNode;
        headNode = temporary;
    } else {
        index = headNode->index - 1;
        headNode--;
    }

    headNode->value = value;
    headNode->index = index;

    nodesCount++;
}

template<typename T>
void List<T>::insertTail(const T &value) {
    int index = 0;

    if (nodesCount == 0) {
        tailNode = headNode;
    } else if (tailNode->index == chunkSize - 1) {
        Node *temporary = allocateChunk();

        tailNode->nextChunk = temporary;
        temporary->nextChunk = tailNode;
        tailNode = temporary;
    } else {
        index = tailNode->index + 1;
        tailNode++;
    }

    tailNode->value = value;
    tailNode->index = index;

    nodesCount++;
}

template<typename T>
void List<T>::removeHead() {
    if (nodesCount == 0) {
        throwException();
    }

    if (nodesCount != 1) {
        if (headNode->index == chunkSize - 1) {
            headNode = headNode->nextChunk;
            freeChunk(headNode->nextChunk - chunkSize + 1);
            headNode->nextChunk = nullptr;
        } else {
            headNode++;
        }
    }

    nodesCount--;
}

template<typename T>
void List<T>::removeTail() {
    if (nodesCount == 0) {
        throwException();
    }

    if (nodesCount != 1) {
        if (tailNode->index == 0) {
            tailNode = tailNode->nextChunk;
            freeChunk(tailNode->nextChunk);
            tailNode->nextChunk = nullptr;
        } else {
            tailNode--;
        }
    }

    nodesCount--;
}

template<typename T>
const T &List<T>::head() const {
    if (nodesCount == 0) {
        throwException();
    }

    return headNode->value;
}

template<typename T>
const T &List<T>::tail() const {
    if (nodesCount == 0) {
        throwException();
    }

    return tailNode->value;
}

template<typename T>
int List<T>::size() const {
    return nodesCount;
}

template<typename T>
typename List<T>::Iterator List<T>::iterator() {
    return Iterator(this, headNode, chunkSize);
}

template<typename T>
typename List<T>::Iterator List<T>::iterator() const {
    return Iterator(this, headNode, chunkSize);
}

template<typename T>
List<T>::Iterator::Iterator(List *list, Node *currentNodePointer, int chunkSize) {
    this->list = list;
    this->currentNodePointer = currentNodePointer;
    this->chunkSize = chunkSize;
}

template<typename T>
List<T>::Iterator::Iterator(const List *list, Node *currentNodePointer, int chunkSize) {
    this->readableList = list;
    this->currentNodePointer = currentNodePointer;
    this->chunkSize = chunkSize;
}

template<typename T>
const T &List<T>::Iterator::get() const {
    if (readableList && readableList->size() == 0 || list && list->size() == 0) {
        throwException();
    }

    return currentNodePointer->value;
}

template<typename T>
void List<T>::Iterator::set(const T &value) {
    if (readableList && readableList->size() == 0 || list && list->size() == 0) {
        throwException();
    }

    currentNodePointer->value = value;
}

template<typename T>
void List<T>::Iterator::insert(const T &value) {
    if (readableList) {
        throwException();
    }

    if (list->size() == 0) {
        list->insertHead(value);
        currentNodePointer = list->headNode;
        return;
    }

    Node *brokenNodePointer = currentNodePointer;
    currentNodePointer = list->tailNode;
    list->insertTail(list->tailNode->value);

    while (currentNodePointer != brokenNodePointer) {
        if (currentNodePointer->index > 0) {
            set((currentNodePointer - 1)->value);
            currentNodePointer--;
        } else {
            set(currentNodePointer->nextChunk->value);
            currentNodePointer = currentNodePointer->nextChunk;
        }
    }

    currentNodePointer->value = value;
}

template<typename T>
void List<T>::Iterator::remove() {
    if (readableList || list->size() == 0) {
        throwException();
    } else if (list->size() == 1) {
        list->removeHead();
        currentNodePointer = list->headNode;
        return;
    }

    Node *brokenNodePointer = currentNodePointer;

    while (currentNodePointer != list->headNode) {
        if (currentNodePointer->index > 0) {
            set((currentNodePointer - 1)->value);
            currentNodePointer--;
        } else {
            set(currentNodePointer->nextChunk->value);
            currentNodePointer = currentNodePointer->nextChunk;
        }
    }

    list->removeHead();
    currentNodePointer = brokenNodePointer;
}

template<typename T>
void List<T>::Iterator::next() {
    if (!hasNext()) {
        throwException();
    }

    if (currentNodePointer->index < chunkSize - 1) {
        currentNodePointer++;
    } else {
        currentNodePointer = currentNodePointer->nextChunk;
    }
}

template<typename T>
void List<T>::Iterator::prev() {
    if (!hasPrev()) {
        throwException();
    }

    if (currentNodePointer->index > 0) {
        currentNodePointer--;
    } else {
        currentNodePointer = currentNodePointer->nextChunk;
    }
}

template<typename T>
bool List<T>::Iterator::hasNext() const {
    return list ? currentNodePointer != list->tailNode : currentNodePointer != readableList->tailNode;
}

template<typename T>
bool List<T>::Iterator::hasPrev() const {
    return list ? currentNodePointer != list->headNode : currentNodePointer != readableList->headNode;
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
