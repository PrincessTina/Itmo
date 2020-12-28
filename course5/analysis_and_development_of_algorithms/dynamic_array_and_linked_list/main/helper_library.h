#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_HELPER_LIBRARY_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_HELPER_LIBRARY_H

#include "dynamic_array/array.h"
#include "unrolled_list/list.h"

enum Flower_Name {
    Tulip,
    Rose,
    Daisy,
    Narcissus,
    Lily
};

std::string flowerNames[] = {"Tulip", "Rose", "Daisy", "Narcissus", "Lily"};

enum Color {
    Yellow,
    Purple,
    White,
    Pink,
    Orange
};

std::string colors[] = {"Yellow", "Purple", "White", "Pink", "Orange"};

enum Locale {
    EN,
    FR,
    IT
};

std::string locales[] = {"EN", "FR", "IT"};

/**
 * Пользовательский тип данных Цветок
 */
struct Flower {
    Flower_Name name; // название
    Color mainColor; // основной цвет
    double smellStrength; // сила запаха, от 0 до 1
    int height; // высота цветка в см

    Flower(Flower_Name name, Color mainColor, double smellStrength, int height) {
        this->name = name;
        this->mainColor = mainColor;
        this->smellStrength = smellStrength;
        this->height = height;
    }
};

/**
 * Переопределение оператора != (неравенства) для типа данных Flower
 * @param flower1 - первый сравниваемый объект
 * @param flower2 - второй сравниваемый объект
 * @return true, если объекты различны, false, если объекты эквивалентны
 */
bool operator!=(const Flower flower1, const Flower flower2) {
    return flower1.name != flower2.name || flower1.mainColor != flower2.mainColor ||
           flower1.smellStrength != flower2.smellStrength || flower1.height != flower2.height;
}

/**
 * Переопределение оператора << (вывода) для типа данных Flower
 * @param out
 * @param flower - объект, информацию о котором нужно вывести на экран
 * @return
 */
std::ostream &operator<<(std::ostream &out, const Flower flower) {
    out << "Name: " << flowerNames[flower.name] << ", Main Color: " << colors[flower.mainColor] <<
        ", Strength of smell: " << flower.smellStrength << ", Height: " << flower.height;
    return out;
}

/**
 * Пользовательский тип данных Alphabet
 */
struct Alphabet {
    char letter; // символ
    Locale locale; // локаль

    Alphabet() {}

    Alphabet(char letter, Locale locale) {
        this->letter = letter;
        this->locale = locale;
    }
};

/**
 * Переопределение оператора != (неравенства) для типа данных Alphabet
 * @param letter1 - первый сравниваемый объект
 * @param letter2 - второй сравниваемый объект
 * @return true, если объекты различны, false, если объекты эквивалентны
 */
bool operator!=(const Alphabet letter1, const Alphabet letter2) {
    return letter1.letter != letter2.letter || letter1.locale != letter2.locale;
}

/**
 * Переопределение оператора << (вывода) для типа данных Alphabet
 * @param out
 * @param letter - объект, информацию о котором нужно вывести на экран
 * @return
 */
std::ostream &operator<<(std::ostream &out, const Alphabet letter) {
    out << "Letter: " << letter.letter << ", Locale: " << locales[letter.locale];
    return out;
}

/**
 * Выводит элементы массива на экран через пробел с запятой
 * @tparam T - тип элементов массива
 * @param array - сформированный динамический массив
 */
template<typename T>
void printCustomArray(const Array<T> &array) {
    for (int i = 0; i < array.size(); i++) {
        if (i == array.size() - 1) {
            std::cout << array[i];
        } else {
            std::cout << array[i] << ", ";
        }
    }

    std::cout << std::endl;
}

/**
 * Формирует массив из элементов списка
 * @tparam T - тип элементов
 * @param list - исходный список
 * @param arrayList - массив, который нужно заполнить элементами списка
 */
template<typename T>
void formArrayFromList(List<T> list, T *arrayList) {
    int i = 0;

    for (auto it = list.iterator(); it.hasNext(); it.next()) {
        arrayList[i] = it.get();
        i++;
    }

    arrayList[i] = list.tail();
}

/**
 * Выводит элементы массива на экран через пробел с запятой
 * @tparam T - тип элементов массива
 * @param array - массив, элементы которого нужно вывести на экран
 * @param size - предполагаемый размер массива
 */
template<typename T>
void printArray(T *array, int size) {
    for (int i = 0; i < size; i++) {
        if (i == size - 1) {
            std::cout << array[i];
        } else {
            std::cout << array[i] << ", ";
        }
    }

    std::cout << std::endl;
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_HELPER_LIBRARY_H
