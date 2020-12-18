#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_HELPER_LIBRARY_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_HELPER_LIBRARY_H

#include "dynamic_array/array.h"
#include "../main/linked_list/list.h"

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
 * Выводит элементы массива на экран через пробел с запятой
 * @tparam T - тип элементов массивов
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

void printArray(int *array, int size) {
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
