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

std::ostream &operator<<(std::ostream &out, const Friend body) {
    out << body.name << " " << body.surname;
    return out;
}

bool operator<(const Friend body, const Friend body2) {
    if (body.name < body2.name) {
        return true;
    } else if (body.name > body2.name) {
        return false;
    } else {
        return body.surname < body2.surname;
    }
}

bool operator==(const Friend body, const Friend body2) {
    return body.name == body2.name && body.surname == body2.surname;
}

bool operator!=(const Friend body, const Friend body2) {
    return body.name != body2.name || body.surname != body2.surname;
}

template<typename K, typename V>
void printTree(Dictionary<K, V> *map) {
    auto it = map->iterator();

    std::cout << "От корня к последнему:" << std::endl;

    for (it = map->iterator(); it.hasNext(); it.next()) {
        std::cout << "(" << it.key() << ", " << it.get() << ")" << std::endl;
    }

    std::cout << "(" << it.key() << ", " << it.get() << ")";
}

#endif //DICTIONARY_HELPER_LIBRARY_H
