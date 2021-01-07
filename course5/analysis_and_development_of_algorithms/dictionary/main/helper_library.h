#ifndef DICTIONARY_HELPER_LIBRARY_H
#define DICTIONARY_HELPER_LIBRARY_H

#include "dictionary/dictionary.h"

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
