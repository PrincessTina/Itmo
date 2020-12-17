#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_H

class Array final {
    class Iterator {
        Array *array;
        int currentElementIndex = 0;

    public:
        Iterator(Array *array);

        const int &get() const;

        void set(const int &value);

        void insert(const int &value);

        void remove();

        void next();

        void prev();

        void toIndex(int index);

        bool hasNext() const;

        bool hasPrev() const;
    };

    int *firstElementPointer;
    int maxSize = 16;
    int currentSize = 0;
    Iterator *thisIterator = new Iterator(this);

private:
    static void throwException();

    int *allocateMemory(int memorySize);

public:
    Array();

    Array(int capacity);

    ~Array();

    void insert(const int &value);

    void insert(int index, const int &value);

    void remove(int index);

    const int &operator[](int index) const;

    int &operator[](int index);

    int size() const;

    Iterator iterator();

    Iterator iterator() const;
};

#include "array_impl.h"

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_H
