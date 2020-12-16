#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H

Array::Array() {
    firstElementPointer = (int *) malloc(maxSize * sizeof(int));
}

Array::Array(int capacity) {
    maxSize = capacity;
    firstElementPointer = (int *) malloc(maxSize * sizeof(int));
}

Array::~Array() {
    free(firstElementPointer);
}

void Array::insert(const int &value) {
    insert(currentSize, value);
}

// ToDo: добавлять память, если currentSize > maxSize
// this->operator[](currentSize - 1)
// (*this)[currentSize - 1]
void Array::insert(int index, const int &value) {
    if (index >= 0 && index <= currentSize) {
        currentSize++;

        if (currentSize <= maxSize) {
            for (int i = currentSize - 1; i > index; i--) {
                (*this)[i] = std::move(*this)[i - 1];
            }

            (*this)[index] = value;
        }
    } else {
        throw std::invalid_argument("Accessing array out of bounds");
    }
}

void Array::remove(int index) {
    if (index >= 0 && index < currentSize) {
        for (int i = index; i < currentSize - 1; i++) {
            (*this)[i] = std::move(*this)[i + 1];
        }

        currentSize--;
    } else {
        throw std::invalid_argument("Accessing array out of bounds");
    }
}

const int &Array::operator[](int index) const {
    if (index >= 0 && index < currentSize) {
        return *(firstElementPointer + index * sizeof(int));
    }

    throw std::invalid_argument("Accessing array out of bounds");
}

int &Array::operator[](int index) {
    if (index >= 0 && index < currentSize) {
        return *(firstElementPointer + index * sizeof(int));
    }

    throw std::invalid_argument("Accessing array out of bounds");
}

int Array::size() const {
    return currentSize;
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_IMPL_H
