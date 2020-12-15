#ifndef LAB1_HELPER_LIBRARY_H
#define LAB1_HELPER_LIBRARY_H

#include <iostream>
#include <string>
#include <utility>

/**
 * Пользовательский тип данных Гном
 */
struct Gnome {
    std::string name; // имя
    int age; // возраст
    int workExperience; // опыт работы
    double beardLength; // длина бороды в см
    double respect; // уровень уважения

    Gnome() {}

    Gnome(std::string name, int age, int workExperience, double beardLength) {
        this->name = name;
        this->age = age;
        this->workExperience = workExperience;
        this->beardLength = beardLength;
        this->respect = 0.4 * this->beardLength + 0.3 * this->age + 0.3 * this->workExperience;
    }
};

/**
 * Переопределение оператора << (вывода) для типа данных Gnome
 * @param out
 * @param gnome - объект, информацию о котором нужно вывести на экран
 * @return
 */
std::ostream &operator<<(std::ostream &out, const Gnome gnome) {
    out << gnome.name << " (" << gnome.respect << ")";
    return out;
}

/**
 * Переопределение оператора == (эквивалентности) для типа данных Gnome
 * @param gnome1 - первый сравниваемый объект
 * @param gnome2 - второй сравниваемый объект
 * @return true, если объекты эквивалентны, false, если объекты различны
 */
bool operator==(const Gnome gnome1, const Gnome gnome2) {
    return gnome1.name == gnome2.name && gnome1.age == gnome2.age && gnome1.workExperience == gnome2.workExperience &&
           gnome1.beardLength == gnome2.beardLength;
}

/**
 * Выводит на экран элементы массива через пробел с запятой
 * @tparam T - тип данных
 * @param array - массив, который нужно вывести на экран
 * @param size - размер массива
 */
template<typename T>
void printArray(T *array, int size);

/**
 * Сравнивает элементы простых типов данных
 * @tparam T - тип данных
 * @param first - первый элемент
 * @param second - второй элемент
 * @return 1, если first > second; -1, если first < second; 0, если first == second
 */
template<typename T>
int commonCompare(T first, T second);

/**
 * Сравнивает элементы простых типов данных в обратном порядке
 * @tparam T - тип данных
 * @param first - первый элемент
 * @param second - второй элемент
 * @return -1, если first > second; 1, если first < second; 0, если first == second
 */
template<typename T>
int reverseCompare(T first, T second);

/**
 * Сравнивает объекты типа данных Gnome
 * @param first - первый объект
 * @param second - второй объект
 * @return 1, если first > second; -1, если first < second; 0, если first == second
 */
int gnomeCompare(Gnome first, Gnome second);

#include "helper_library_impl.h"

#endif //LAB1_HELPER_LIBRARY_H
