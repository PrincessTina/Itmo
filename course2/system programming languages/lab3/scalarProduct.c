#include <stdio.h>

const int firstVector[] = {1, 2, 3, 7, 3};
const int secondVector[] = {4, 5, 6};

int getScalarProduct(size_t size1, size_t size2) {
    size_t i;
    size_t count;
    int product = 0;

    if (size1 > size2) {
	count = size2;
    } else {
	count = size1;
    }

    for (i = 0; i < count; i++) {
        product = product + firstVector[i] * secondVector[i];
    }

    return product;
}

int main(int argc, char **argv) {
    printf("The product is: %d\n", getScalarProduct(sizeof(firstVector)/ sizeof(int), sizeof(secondVector)/ sizeof(int)));
    
	return 0;
}


