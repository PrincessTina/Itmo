#ifndef DICTIONARY_DICTIONARY_H
#define DICTIONARY_DICTIONARY_H

#include <stack>

enum Color {
    BLACK,
    RED
};

/**
 * Дерево, где элементы балансируются по ключам
 * @tparam K - тип ключа
 * @tparam V - тип значения
 */
template<typename K, typename V>
class Dictionary final {
    /**
     * Элемент дерева
     */
    struct Node {
        K key;
        V value;
        Color edgeToParentColor;
        Node *left;
        Node *right;
    };

    /**
     * Итератор дерева
     */
    class Iterator {
        Node **nodes = nullptr; // указатель на массив указателей на элементы дерева
        int index = 0; // индекс в массиве указателей для определения, на что указывает итератор в текущий момент времени
        int nodesCount; // размер массива / количество элементов дерева на момент инициализации итератора

    private:
        /**
         * Заполняет массив nodes указателями на элементы дерева
         * @param rootNode - корень дерева
         */
        void fillNodes(Node *rootNode);

    public:
        /**
         * Конструктор, инициализирует итератор
         * @param dictionary - указатель на дерево
         */
        Iterator(Dictionary *dictionary);

        /**
         * @return ключ текущего элемента
         */
        const K &key() const;

        /**
         * @return значение текущего элемента
         */
        const V &get() const;

        /**
         * Устанавливает новое значение текущему элементу
         * @param value - новое значение
         */
        void set(const V &value);

        /**
         * Инкрементирует index
         */
        void next();

        /**
         * Декрементирует index
         */
        void prev();

        /**
         * Проверяет index
         * @return true, если можно перейти к следующему элементу (next), иначе false
         */
        bool hasNext() const;

        /**
         * Проверяет index
         * @return true, если можно перейти к предыдущему элементу (prev), иначе false
         */
        bool hasPrev() const;
    };

    Node *root = nullptr; // указатель на корень дерева
    int nodesCount = 0; // количество элементов дерева

private:
    /**
     * Выделяет память под новый элемент дерева. Заполняет его поля значениями по умолчанию
     * @return указатель на участок выделенной памяти
     */
    Node *allocateNode();

    /**
     * Освобождает память, выделенную под элемент
     * @param node - указатель на элемент дерева, занимаемую память которым необходимо освободить
     */
    void freeNode(Node *node);

    /**
     * Осуществляет поиск элемента в дереве по ключу
     * @param key - ключ искомого элемента
     * @return указатель на искомый элемент, либо nullptr
     */
    Node *find(const K &key);

    /**
     * Находит подходящее для элемента место в дереве и осуществляет его вставку в дерево без балансировки
     * @param key - ключ нового элемента
     * @param value - значение нового элемента
     * @param path - указатель на стек, в котором сохранится иерархия элементов для нового элемента
     */
    void insertNode(const K &key, const V &value, std::stack<Node *> *path);

    /**
     * Находит самый левый элемент в поддереве node
     * @param node - указатель на поддерево, в котором необходимо произвести поиск
     * @return указатель на самый левый элемент поддерева
     */
    Node *findLeftmostNode(Node *node);

    /**
     * Осуществляет "поворот элемента влево" / перемещает элемент влево по дереву
     * @param parentNode - элемент, который необходимо повернуть
     */
    void rotateLeft(Node *parentNode);

    /**
     * Осуществляет "поворот элемента вправо" / перемещает элемент вправо по дереву
     * @param grandParentNode - элемент, который необходимо повернуть
     */
    void rotateRight(Node *grandParentNode);

    /**
     * Если цвета edgeToParentColor дочерних элементов parentNode равны RED, заменяет их на BLACK, а цвет parentNode
     * на RED (если parentNode не корень дерева).
     * Если цвета edgeToParentColor дочерних элементов parentNode равны BLACK, заменяет их на RED, а цвет parentNode на BLACK
     * @param parentNode - элемент, цвет edgeToParentColor которого необходимо поменять с цветом его дочерних элементов
     */
    void flipColor(Node *parentNode);

    /**
     * Осуществляет перемещение красной ссылки влево по дереву
     * @param node - элемент, красную ссылку с которого необходимо переместить
     */
    void moveRedLeft(Node *node);

    /**
     * Осуществляет перемещение красной ссылки вправо по дереву
     * @param node - элемент, красную ссылку с которого необходимо переместить
     */
    void moveRedRight(Node *node);

    /**
     * Сохраняет иерархию элементов в path для самого левого элемента поддерева node
     * @param node - указатель на поддерево, в котором необходимо совершить поиск самого левого элемента
     * @param path - указатель на стек, в котором сохранится иерархия элементов для искомого элемента
     * @return указатель на самый левый элемент поддерева node
     */
    Node *reachLeftmostNode(Node *node, std::stack<Node *> *path);

    /**
     * Исправляет балансировку дерева, нарушенную после вставки или удаления элемента
     * @param path - указатель на стек, в котором хранится информация о пути, на котором могла быть нарушена балансировка
     * @param needFullTraversal - если true, осуществляет проверку для всех элементов из path; иначе только до тех пор,
     * пока не попадется элемент с ненарушенной балансировкой
     */
    void fixNodesOrder(std::stack<Node *> *path, bool needFullTraversal);

public:
    /**
     * Конструктор, инициализирует дерево, выделяя память, необходимую для хранения корня дерева
     */
    Dictionary();

    /**
     * Деструктор, освобождает память, выделенную под хранение элементов
     */
    ~Dictionary();

    /**
     * Запрет конструктора копирования
     */
    Dictionary(const Dictionary &) = delete;

    /**
     * Запрет оператора присваивания
     * @return
     */
    Dictionary &operator=(const Dictionary &) = delete;

    /**
     * Вставляет элемент в дерево
     * Если элемент с таким ключом уже существует, перезаписывает его значение на новое
     * @param key - ключ элемента
     * @param value - значение элемента
     */
    void put(const K &key, const V &value);

    /**
     * Алгоритм удаления описан здесь: https://www.cs.princeton.edu/~rs/talks/LLRB/RedBlack.pdf
     * Удаляет элемент из дерева
     * Если nodesCount равен 0, либо элемента с таким ключом в дереве нет, ничего не делает
     * Так же не очищает память из-под корня дерева
     * @param key - ключ элемента, который необходимо удалить
     */
    void remove(const K &key);

    /**
     * Проверяет, есть ли в дереве элемент с таким ключом
     * @param key - ключ искомого элемента
     * @return true, если в дереве есть такой элемент, иначе false
     */
    bool contains(const K &key);

    /**
     * Переопределение константного оператора []
     * Возвращает значение элемента по ключу. Если элемента с таким ключом нет, возвращает значение по умолчанию
     * @param key - ключ искомого элемента
     * @return значение элемента
     */
    const V &operator[](const K &key) const;

    /**
     * Переопределение неконстантного оператора []
     * Возвращает значение элемента по ключу. Если элемента с таким ключом нет, вставляет его в дерево со значением
     * по умолчанию и возвращает это значение
     * @param key - ключ искомого элемента
     * @return значение элемента
     */
    V &operator[](const K &key);

    /**
     * @return количество элементов дерева
     */
    int size() const;

    /**
     * @return итератор, указывающий на корень дерева
     */
    Iterator iterator();

    /**
     * @return итератор, указывающий на корень дерева
     */
    const Iterator iterator() const;
};

#include "dictionary_impl.h"

#endif //DICTIONARY_DICTIONARY_H
