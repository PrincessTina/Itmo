#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_H

/**
 * Динамический массив
 * @tparam T - тип элементов массива
 */
template<typename T>
class Array final {
    class Iterator {
        Array *array; // указатель на массив итератора
        int currentElementIndex = 0; // индекс элемента, на который указывает итератор

    public:
        /**
         * Конструктор, инициализирует итератор
         * @param array - массив, связанный с итератором
         */
        explicit Iterator(Array *array);

        /**
         * Возвращает элемент на текущей позиции итератора, если это возможно. Иначе выбрасывает ошибку
         * @return элемент на текущей позиции итератора
         */
        const T &get() const;

        /**
         * Перезаписывает элемент в текущей позиции итератора, если это возможно. Иначе выбрасывает ошибку
         * @param value - новый элемент
         */
        void set(const T &value);

        /**
         * Вставляет элемент в текущую позицию итератора, если это возможно. Иначе выбрасывает ошибку
         * @param value - элемент, который необходимо вставить
         */
        void insert(const T &value);

        /**
         * Удаляет элемент на текущей позиции итератора, если это возможно. Иначе выбрасывает ошибку
         * Если после удаления элемента итератор указывает на элемент, находящийся за пределами массива, делается вызов
         * prev()
         */
        void remove();

        /**
         * Инкрементирует currentElementIndex, если это возможно. Иначе выбрасывает ошибку
         */
        void next();

        /**
         * Декрементирует currentElementIndex, если это возможно. Иначе выбрасывает ошибку
         */
        void prev();

        /**
         * Присваивает currentElementIndex значение index, если это возможно. Иначе выбрасывает ошибку
         * @param index - индекс элемента, на который будет указывать итератор
         */
        void toIndex(int index);

        /**
         * Проверяет, есть ли следующий элемент в массиве
         * @return true, если есть, иначе false
         */
        bool hasNext() const;

        /**
         * Проверяет, есть ли предыдущий элемент в массиве
         * @return true, если есть, иначе false
         */
        bool hasPrev() const;
    };

    T *firstElementPointer; // указатель на первый элемент массива
    int maxSize = 16; // количество элементов, на которое рассчитан массив
    int currentSize = 0; // размер массива

private:
    /**
     * Выбрасывает ошибку выхода за пределы массива
     */
    static void throwException();

    /**
     * Выделяет память, необходимую для того, чтобы поместилось memorySize количество элементов
     * Присваивает maxSize значение memorySize
     * @param memorySize - количество элементов, на которое рассчитан массив
     * @return указатель на выделенный участок памяти
     */
    T *allocateMemory(int memorySize);

public:
    /**
     * Конструктор
     * Выделяет память, необходимую для того, чтобы поместилось maxSize количество элементов
     */
    Array();

    /**
     * Конструктор
     * Выделяет память, необходимую для того, чтобы поместилось capacity количество элементов
     * @param capacity - количество элементов, на которое рассчитан массив
     */
    explicit Array(int capacity);

    /**
     * Запрет констуктора копирования
     */
    Array(const Array&) = delete;

    /**
     * Запрет оператора присваивания
     * @return
     */
    Array& operator=(const Array&) = delete;

    /**
     * Деструктор
     * Освобождает память
     */
    ~Array();

    /**
     * Осуществляет вставку элемента в конец массива, инкрементируя currentSize
     * Если выделенной под массив памяти (maxSize) недостаточно, выделяет новый участок в памяти, копируя в него
     * массив, и переопределяет firstElementPointer. Освобождает память, которую ранее занимал массив
     * @param value - элемент, который необходимо вставить
     */
    void insert(const T &value);

    /**
     * Осуществляет вставку элемента по индексу, инкрементируя currentSize
     * Осуществляет проверку индекса. В случае если index < 0 или index > currentSize, выбрасывает ошибку
     * Если выделенной под массив памяти (maxSize) недостаточно, выделяет новый участок в памяти, копируя в него
     * массив, и переопределяет firstElementPointer. Освобождает память, которую ранее занимал массив
     * @param index - индекс, по которому необходимо вставить элемент
     * @param value - элемент, который необходимо вставить
     */
    void insert(int index, const T &value);

    /**
     * Удаляет элемент по индексу, декрементируя currentSize
     * Осуществляет проверку индекса. В случае если index < 0 или index >= currentSize, выбрасывает ошибку
     * @param index - индекс, по которому необходимо удалить элемент
     */
    void remove(int index);

    /**
     * Переопределение оператора [] (get)
     * Осуществляет проверку индекса. В случае если index < 0 или index >= currentSize, выбрасывает ошибку
     * @param index - индекс, по которому необходимо получить элемент
     * @return элемент по индексу (чтение)
     */
    const T &operator[](int index) const;

    /**
     * Переопределение оператора [] (get)
     * Осуществляет проверку индекса. В случае если index < 0 или index >= currentSize, выбрасывает ошибку
     * @param index - индекс, по которому необходимо получить элемент
     * @return элемент по индексу (чтение и запись)
     */
    T &operator[](int index);

    /**
     * Возвращает количество элементов массива
     * @return количество элементов массива
     */
    int size() const;

    /**
     * Возвращает итератор (по умолчанию указывающий на первый элемент массива)
     * @return итератор (чтение и запись)
     */
    Iterator iterator();

    /**
     * Возвращает итератор (по умолчанию указывающий на первый элемент массива)
     * @return итератор (чтение)
     */
    Iterator iterator() const;
};

#include "array_impl.h"

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_ARRAY_H
