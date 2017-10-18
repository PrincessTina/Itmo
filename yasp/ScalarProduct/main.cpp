#include <stdio.h>

const int firstVector[] = {1, 2, 3};
const int secondVector[] = {4, 5, 6};

int getScalarProduct(size_t count) {
    size_t i;
    int product = 0;

    for (i = 0; i < count; i++) {
        product = product + firstVector[i] * secondVector[i];
    }
    return product;
}

int main(int argc, char **argv) {
    printf("The product is: %d\n", getScalarProduct(sizeof(firstVector)/ sizeof(int)));
    return 0;
}


