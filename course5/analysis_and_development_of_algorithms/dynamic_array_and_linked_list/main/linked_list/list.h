#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H

/**
 * Двусвязный список на чанках
 * @tparam T - тип элементов списка
 */
template<typename T>
class List final {
    struct Node {
        T value; // значение элемента
        int index; // индекс элемента в текущем чанке
        Node *nextChunk = nullptr; // указатель на элемент следующего (слева или справа) чанка
    };

    class Iterator {
        const List *readableList = nullptr; // указатель на список только для чтения
        List *list = nullptr; // указатель на список для чтения и записи
        Node *currentNodePointer; // указатель на текущий элемент списка
        int chunkSize; // размер чанка списка

    public:
        Iterator(List *list, Node *currentNodePointer, int chunkSize);

        Iterator(const List *list, Node *currentNodePointer, int chunkSize);

        const T &get() const;

        void set(const T &value);

        void insert(const T &value);

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

    //List(const List&) = delete; // запрет копирования

    /**
     * Запрет оператора присваивания
     * @return
     */
    List& operator=(const List&) = delete;

    ~List();

    void insertHead(const T &value);

    void insertTail(const T &value);

    void removeHead();

    void removeTail();

    const T &head() const;

    const T &tail() const;

    int size() const;

    Iterator iterator();

    Iterator iterator() const;
};


#include "list_impl.h"

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_H
