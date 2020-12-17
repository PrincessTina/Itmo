#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H

#include "array.h"

template<typename T>
void Array<T>::throwException() {
    throw std::invalid_argument("Accessing array out of bounds");
}

template<typename T>
T *Array<T>::allocateMemory(int memorySize) {
    maxSize = memorySize;
    return (T *) malloc(memorySize * sizeof(T));
}

template<typename T>
Array<T>::Array() {
    firstElementPointer = allocateMemory(maxSize);
}

template<typename T>
Array<T>::Array(int capacity) {
    firstElementPointer = allocateMemory(capacity);
}

template<typename T>
Array<T>::~Array() {
    free(firstElementPointer);
}

template<typename T>
void Array<T>::insert(const T &value) {
    insert(currentSize, value);
}

template<typename T>
void Array<T>::insert(int index, const T &value) {
    if (index < 0 || index > currentSize) {
        throwException();
    }

    currentSize++;

    if (currentSize > maxSize) {
        T *newFirstElementPointer = allocateMemory(maxSize * 2);
        int j = 0;

        for (int i = 0; i < currentSize; i++) {
            if (i == index) {
                *(newFirstElementPointer + i) = value;
            } else {
                *(newFirstElementPointer + i) = std::move(*this)[j];
                j++;
            }
        }

        free(firstElementPointer);
        firstElementPointer = newFirstElementPointer;
    } else {
        for (int i = currentSize - 1; i > index; i--) {
            (*this)[i] = std::move(*this)[i - 1];
        }

        (*this)[index] = value;
    }
}

template<typename T>
void Array<T>::remove(int index) {
    if (index < 0 || index >= currentSize) {
        throwException();
    }

    for (int i = index; i < currentSize - 1; i++) {
        (*this)[i] = std::move(*this)[i + 1];
    }

    currentSize--;
}

template<typename T>
const T &Array<T>::operator[](int index) const {
    if (index < 0 || index >= currentSize) {
        throwException();
    }

    return *(firstElementPointer + index);
}

template<typename T>
T &Array<T>::operator[](int index) {
    if (index < 0 || index >= currentSize) {
        throwException();
    }

    return *(firstElementPointer + index);
}

template<typename T>
int Array<T>::size() const {
    return currentSize;
}

template<typename T>
typename Array<T>::Iterator Array<T>::iterator() {
    return Iterator(this);
}

template<typename T>
typename Array<T>::Iterator Array<T>::iterator() const {
    return Iterator(this);
}

template<typename T>
Array<T>::Iterator::Iterator(Array *array) {
    this->array = array;
}

template<typename T>
const T &Array<T>::Iterator::get() const {
    return (*array)[currentElementIndex];
}

template<typename T>
void Array<T>::Iterator::set(const T &value) {
    (*array)[currentElementIndex] = value;
}

template<typename T>
void Array<T>::Iterator::insert(const T &value) {
    array->insert(currentElementIndex, value);
}

template<typename T>
void Array<T>::Iterator::remove() {
    array->remove(currentElementIndex);

    if (currentElementIndex == array->size() && currentElementIndex != 0) {
        prev();
    }
}

template<typename T>
void Array<T>::Iterator::next() {
    if (currentElementIndex == array->size() - 1) {
        throwException();
    }

    currentElementIndex++;
}

template<typename T>
void Array<T>::Iterator::prev() {
    if (currentElementIndex == 0) {
        throwException();
    }

    currentElementIndex--;
}

template<typename T>
void Array<T>::Iterator::toIndex(int index) {
    if (index < 0 || index >= array->size()) {
        throwException();
    }

    currentElementIndex = index;
}

template<typename T>
bool Array<T>::Iterator::hasNext() const {
    return currentElementIndex != array->size() - 1;
}

template<typename T>
bool Array<T>::Iterator::hasPrev() const {
    return currentElementIndex != 0;
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H
