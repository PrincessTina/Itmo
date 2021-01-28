#ifndef DICTIONARY_HELPER_LIBRARY_H
#define DICTIONARY_HELPER_LIBRARY_H

#include "dictionary/dictionary.h"

class Friend {
public:
    std::string name;
    std::string surname;

    Friend(std::string name, std::string surname) {
        this->name = name;
        this->surname = surname;
    }

    Friend() {
        name = "John";
        surname = "Doe";
    }
};

/**
 * Переопределение оператора << (вывода) для типа данных Friend
 * @param out
 * @param body - объект, информацию о котором нужно вывести на экран
 * @return
 */
std::ostream &operator<<(std::ostream &out, const Friend body) {
    out << body.name << " " << body.surname;
    return out;
}

/**
 * Переопределение оператора < (меньше) для типа данных Friend
 * @param body - первый сравниваемый объект
 * @param body2 - второй сравниваемый объект
 * @return true, если body < body2; false, если body >= body2
 */
bool operator<(const Friend body, const Friend body2) {
    if (body.name < body2.name) {
        return true;
    } else if (body.name > body2.name) {
        return false;
    } else {
        return body.surname < body2.surname;
    }
}

/**
 * Переопределение оператора == для типа данных Friend
 * @param body - первый сравниваемый объект
 * @param body2 - второй сравниваемый объект
 * @return true, если body == body2, иначе false
 */
bool operator==(const Friend body, const Friend body2) {
    return body.name == body2.name && body.surname == body2.surname;
}

/**
 * Переопределение оператора != для типа данных Friend
 * @param body - первый сравниваемый объект
 * @param body2 - второй сравниваемый объект
 * @return true, если body != body2, иначе false
 */
bool operator!=(const Friend body, const Friend body2) {
    return body.name != body2.name || body.surname != body2.surname;
}

/**
 * @param min - минимальное значение случайного числа
 * @param max - максимальное значение случайного числа
 * @return случайное число в диапазоне [min, max]
 */
int random(int min, int max) {
    return rand() * (max - min) / RAND_MAX + min;
}

#endif //DICTIONARY_HELPER_LIBRARY_H
