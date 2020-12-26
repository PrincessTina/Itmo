#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H

#include "list.h"

template<typename T>
void List<T>::throwException() {
    throw std::invalid_argument("Accessing list out of bounds");
}

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
void List<T>::insertInChunk(Chunk *chunk, int index, const T &value) {
    for (int i = chunk->length; i > index; i--) {
        chunk->nodes[i] = std::move(chunk->nodes[i - 1]);
    }

    chunk->nodes[index] = value;
    chunk->length++;
    nodesCount++;
}

template<typename T>
void List<T>::removeFromChunk(Chunk *chunk, int index) {
    for (int i = index; i < chunk->length - 1; i++) {
        chunk->nodes[i] = std::move(chunk->nodes[i + 1]);
    }

    chunk->length--;
    nodesCount--;
}

template<typename T>
int List<T>::relocateChunkPartToRight(Chunk *chunkFrom, Chunk *chunkTo) {
    int length = (chunkFrom->length > chunkSize / 2) ? chunkSize / 2 : chunkFrom->length;

    for (int i = 0; i < length; i++) {
        insertInChunk(chunkTo, 0, chunkFrom->nodes[chunkFrom->length - 1]);
        removeFromChunk(chunkFrom, chunkFrom->length - 1);
    }

    return length;
}

template<typename T>
void List<T>::relocateChunkPartToLeft(List::Chunk *chunkFrom, List::Chunk *chunkTo) {
    int length = (chunkFrom->length > chunkSize / 2) ? chunkSize / 2 : chunkFrom->length;

    for (int i = 0; i < length; i++) {
        insertInChunk(chunkTo, chunkTo->length, chunkFrom->nodes[0]);
        removeFromChunk(chunkFrom, 0);
    }
}

template<typename T>
int List<T>::insert(Chunk *chunkPointer, int index, const T &value) {
    if (chunkPointer->length == chunkSize) {
        Chunk *chunk = allocateChunk();
        chunk->next = chunkPointer->next;
        chunk->prev = chunkPointer;
        chunkPointer->next = chunk;

        tailChunk = (chunkPointer == tailChunk) ? chunk : tailChunk;

        relocateChunkPartToRight(chunkPointer, chunk);

        if (index > chunkSize / 2) {
            chunkPointer = chunkPointer->next;
            index = (index < chunkSize) ? index % (chunkSize / 2) : chunkSize / 2;
        }
    }

    insertInChunk(chunkPointer, index, value);
    return index;
}

template<typename T>
int List<T>::remove(Chunk *chunkPointer, int index) {
    if (nodesCount == 0) {
        throwException();
    }

    removeFromChunk(chunkPointer, index);

    if (chunkPointer->length <= chunkSize / 2) {
        if (chunkPointer == headChunk && chunkPointer == tailChunk) {
            return index;
        }

        if (chunkPointer != tailChunk) {
            relocateChunkPartToLeft(chunkPointer->next, chunkPointer);

            if (chunkPointer->next->length == 0) {
                Chunk *chunkToFree = chunkPointer->next;

                chunkPointer->next = chunkToFree->next;
                (chunkToFree == tailChunk) ? tailChunk = chunkPointer : chunkPointer->next->prev = chunkPointer;
                freeChunk(chunkToFree);
            }
        } else {
            index = index + relocateChunkPartToRight(chunkPointer->prev, chunkPointer);

            if (chunkPointer->prev->length == 0) {
                Chunk *chunkToFree = chunkPointer->prev;

                chunkPointer->prev = chunkToFree->prev;
                (chunkToFree == headChunk) ? headChunk = chunkPointer : chunkPointer->prev->next = chunkPointer;
                freeChunk(chunkToFree);
            }
        }
    }

    return index;
}

template<typename T>
List<T>::List() {
    headChunk = tailChunk = allocateChunk();
}

template<typename T>
List<T>::~List() {
    if (nodesCount == 0) {
        freeChunk(headChunk);
    }
}

template<typename T>
void List<T>::insertHead(const T &value) {
    insert(headChunk, 0, value);
}

template<typename T>
void List<T>::insertTail(const T &value) {
    insert(tailChunk, tailChunk->length, value);
}

template<typename T>
void List<T>::removeHead() {
    remove(headChunk, 0);
}

template<typename T>
void List<T>::removeTail() {
    remove(tailChunk, tailChunk->length - 1);
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
    if (readableList) {
        throwException();
    }

    nodeIndex = list->insert(chunkPointer, nodeIndex, value);
}

template<typename T>
void List<T>::Iterator::remove() {
    if (readableList) {
        throwException();
    }

    nodeIndex = list->remove(chunkPointer, nodeIndex);
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
