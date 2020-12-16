#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H

void Array::throwException() {
    throw std::invalid_argument("Accessing array out of bounds");
}

int *Array::allocateMemory(int memorySize) {
    maxSize = memorySize;
    return (int *) malloc(memorySize * sizeof(int));
}

Array::Array() {
    firstElementPointer = allocateMemory(maxSize);
}

Array::Array(int capacity) {
    firstElementPointer = allocateMemory(capacity);
}

Array::~Array() {
    free(firstElementPointer);
}

void Array::insert(const int &value) {
    insert(currentSize, value);
}

void Array::insert(int index, const int &value) {
    if (index < 0 || index > currentSize) {
        throwException();
    }

    currentSize++;

    if (currentSize > maxSize) {
        int *newFirstElementPointer = allocateMemory(maxSize * 2);
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

void Array::remove(int index) {
    if (index < 0 || index >= currentSize) {
        throwException();
    }

    for (int i = index; i < currentSize - 1; i++) {
        (*this)[i] = std::move(*this)[i + 1];
    }

    currentSize--;
}

const int &Array::operator[](int index) const {
    if (index < 0 || index >= currentSize) {
        throwException();
    }

    return *(firstElementPointer + index);
}

int &Array::operator[](int index) {
    if (index < 0 || index >= currentSize) {
        throwException();
    }

    return *(firstElementPointer + index);
}

int Array::size() const {
    return currentSize;
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H
