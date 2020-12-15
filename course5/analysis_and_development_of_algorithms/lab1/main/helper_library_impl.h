#ifndef LAB1_HELPER_LIBRARY_IMPL_H
#define LAB1_HELPER_LIBRARY_IMPL_H

template<typename T>
void printArray(T *array, int size) {
    for (int i = 0; i < size; i++) {
        std::cout << array[i];

        if (i < size - 1) {
            std::cout << ", ";
        }
    }

    std::cout << std::endl;
}

template<typename T>
int commonCompare(const T first, const T second) {
    if (first > second) {
        return 1;
    }

    if (first < second) {
        return -1;
    }

    return 0; // if first == second
}

template<typename T>
int reverseCompare(const T first, const T second) {
    if (first > second) {
        return -1;
    }

    if (first < second) {
        return 1;
    }

    return 0; // if first == second
}

int gnomeCompare(const Gnome first, const Gnome second) {
    if (first.respect > second.respect) {
        return 1;
    }

    if (first.respect < second.respect) {
        return -1;
    }

    return 0; // if first == second
}

#endif //LAB1_HELPER_LIBRARY_IMPL_H
