#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H
//ToDo: переписать на шаблоны
class List final {
    struct Node {
        int value; // значение элемента
        int index; // индекс элемента в текущем чанке
        Node *nextChunk = nullptr; // указатель на элемент следующего (слева или справа) чанка
    };

    class Iterator {
        const List *readableList = nullptr; // указатель на список только для чтения
        List *list = nullptr; // указатель на список для чтения и записи
        Node *currentNodePointer; // указатель на текущий элемент списка
        int chunkSize;

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

    Node *headNode = nullptr; // указатель на начало списка
    Node *tailNode = nullptr; // указатель на конец списка
    int chunkSize = 64 / sizeof(Node); // размер чанка
    int nodesCount = 0; // количество элементов списка

private:
    Node *allocateChunk();

    void freeChunk(Node *chunk);

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
