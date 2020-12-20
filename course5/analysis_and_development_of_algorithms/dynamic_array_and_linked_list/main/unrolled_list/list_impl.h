#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H

#include "list.h"

template<typename T>
typename List<T>::Chunk *List<T>::allocateChunk() {
    Chunk *chunk = (Chunk *) malloc(sizeof(Chunk));
    chunk->nodes = (T *) malloc(sizeof(T) * chunkSize);
    chunk->length = 0;
    chunk->prev = chunk->next = nullptr;
    return chunk;
}

template<typename T>
void List<T>::freeChunk(List::Chunk *chunk) {
    free(chunk->nodes);
    free(chunk);
}

template<typename T>
void List<T>::throwException() {
    throw std::invalid_argument("Accessing list out of bounds");
}

template<typename T>
void List<T>::insertInChunk(Chunk *chunk, int index, const T &value) {
    for (int i = chunk->length; i > index; i--) {
        chunk->nodes[i] = std::move(chunk->nodes[i - 1]);
    }

    chunk->nodes[index] = value;
    chunk->length++;
}

template<typename T>
void List<T>::removeFromChunk(Chunk *chunk, int index) {
    for (int i = index; i < chunk->length - 1; i++) {
        chunk->nodes[i] = std::move(chunk->nodes[i + 1]);
    }

    chunk->length--;
}

template<typename T>
List<T>::List() {
    headChunk = tailChunk = allocateChunk();
}

template<typename T>
List<T>::~List() {

}

template<typename T>
void List<T>::insertHead(const T &value) {
    if (headChunk->length == chunkSize) {
        Chunk *temporary = allocateChunk();

        headChunk->prev = temporary;
        temporary->next = headChunk;
        headChunk = temporary;
    }

    insertInChunk(headChunk, 0, value);
    nodesCount++;
}

template<typename T>
void List<T>::insertTail(const T &value) {
    if (tailChunk->length == chunkSize) {
        Chunk *temporary = allocateChunk();

        tailChunk->next = temporary;
        temporary->prev = tailChunk;
        tailChunk = temporary;
    }

    insertInChunk(tailChunk, tailChunk->length, value);
    nodesCount++;
}

template<typename T>
void List<T>::removeHead() {
    if (nodesCount == 0) {
        throwException();
    }

    removeFromChunk(headChunk, 0);
    nodesCount--;

    if (headChunk->length == 0 && nodesCount != 0) {
        headChunk = headChunk->next;
        freeChunk(headChunk->prev);
        headChunk->prev = nullptr;
    }
}

template<typename T>
void List<T>::removeTail() {
    if (nodesCount == 0) {
        throwException();
    }

    removeFromChunk(tailChunk, tailChunk->length - 1);
    nodesCount--;

    if (tailChunk->length == 0 && nodesCount != 0) {
        tailChunk = tailChunk->prev;
        freeChunk(tailChunk->next);
        tailChunk->next = nullptr;
    }
}

template<typename T>
const T &List<T>::head() const {
    if (nodesCount == 0) {
        throwException();
    }

    return headChunk->nodes[0];
}

template<typename T>
const T &List<T>::tail() const {
    if (nodesCount == 0) {
        throwException();
    }

    return tailChunk->nodes[tailChunk->length - 1];
}

template<typename T>
int List<T>::size() const {
    return nodesCount;
}

template<typename T>
typename List<T>::Iterator List<T>::iterator() {
    return Iterator(this, headChunk);
}

template<typename T>
typename List<T>::Iterator List<T>::iterator() const {
    return Iterator(this, headChunk);
}

template<typename T>
List<T>::Iterator::Iterator(List *list, Chunk *chunkPointer) {
    this->list = list;
    this->chunkPointer = chunkPointer;
}

template<typename T>
List<T>::Iterator::Iterator(const List *readableList, Chunk *chunkPointer) {
    this->readableList = readableList;
    this->chunkPointer = chunkPointer;
}

template<typename T>
const T &List<T>::Iterator::get() const {
    if (readableList && readableList->size() == 0 || list && list->size() == 0) {
        throwException();
    }

    return chunkPointer->nodes[nodeIndex];
}

template<typename T>
void List<T>::Iterator::set(const T &value) {
    if (readableList && readableList->size() == 0 || list && list->size() == 0) {
        throwException();
    }

    chunkPointer->nodes[nodeIndex] = value;
}


template<typename T>
void List<T>::Iterator::insert(const T &value) {

}

template<typename T>
void List<T>::Iterator::remove() {

}

template<typename T>
void List<T>::Iterator::next() {
    if (!hasNext()) {
        throwException();
    }

    if (nodeIndex < chunkPointer->length - 1) {
        nodeIndex++;
    } else {
        chunkPointer = chunkPointer->next;
        nodeIndex = 0;
    }
}

template<typename T>
void List<T>::Iterator::prev() {
    if (!hasPrev()) {
        throwException();
    }

    if (nodeIndex > 0) {
        nodeIndex--;
    } else {
        chunkPointer = chunkPointer->prev;
        nodeIndex = chunkPointer->length - 1;
    }
}

template<typename T>
bool List<T>::Iterator::hasNext() const {
    if (list) {
        return (chunkPointer != list->tailChunk) || (nodeIndex != list->tailChunk->length - 1);
    } else {
        return (chunkPointer != readableList->tailChunk) || (nodeIndex != readableList->tailChunk->length - 1);
    }
}

template<typename T>
bool List<T>::Iterator::hasPrev() const {
    if (list) {
        return (chunkPointer != list->headChunk) || (nodeIndex != 0);
    } else {
        return (chunkPointer != readableList->headChunk) || (nodeIndex != 0);
    }
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
