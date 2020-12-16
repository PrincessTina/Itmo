#include <iostream>
#include "array.h"

int main() {
    Array a;

    a.insert(5);
    a.insert(12);
    a.insert(10);
    a.insert(21);

    std::cout << a[0] << " ";
    std::cout << a[1] << " ";
    std::cout << a[2] << " ";
    std::cout << a[3] << " " << std::endl;

    a.insert(1, 8);

    std::cout << a[0] << " ";
    std::cout << a[1] << " ";
    std::cout << a[2] << " ";
    std::cout << a[3] << " ";
    std::cout << a[4] << " " << std::endl;

    return 0;
}
