#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H

class List final {
    struct Node {
        int value;
        int index;
        Node *nextChunk = nullptr; // указатель на 0ой элемент следующего (слева или справа) чанка
    };

    class Iterator {
        const List *readableList = nullptr;
        List *list = nullptr;
        int chunkSize;
        Node *currentNodePointer;

    public:
        Iterator(List *list, Node *currentNodePointer, int chunkSize);

        Iterator(const List *list, Node *currentNodePointer, int chunkSize);

        const int &get() const;

        void set(const int &value);

        void insert(const int &value);

        void remove();

        void next();

        void prev();

        bool hasNext() const;

        bool hasPrev() const;
    };

    Node *headNode = nullptr;
    Node *tailNode = nullptr;
    int chunkSize = 5;
    int nodesCount = 0;

private:
    Node *allocateChunk();

    static void throwException();

public:
    List();

    ~List();

    void insertHead(const int &value);

    void insertTail(const int &value);

    void removeHead();

    void removeTail();

    const int &head() const;

    const int &tail() const;

    int size() const;

    Iterator iterator();

    Iterator iterator() const;
};


#include "list_impl.h"

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H
