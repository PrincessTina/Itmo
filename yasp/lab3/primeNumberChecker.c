#include <stdio.h>

int is_prime(unsigned long number) {
    unsigned long i;
    int count = 0;

    if ((number != 1) && (number != 0)) {
        for (i = 2lu; i < number; i++) {
            if (number % i == 0) {
                count++;
                break;
            }
        }

        if (count > 0) {
            return 0;
        } else {
            return 1;
        }
    } else {
        return 2;
    }
}

int main(int argc, char **argv) {
    unsigned long number;
    long input = 999999; 

    printf("Input number:");

    scanf("%ld", &input);

    if (input == 999999) {
        printf("Error: Incorrect input data format\n");
    } else if (input < 0) {
        printf("The number isn't positive but it has to\n");
    } else {
        number = (unsigned long) input;
        printf("Is this number prime?\n");

        if (is_prime(number) == 2) {
            printf("The number is no\n");
        } else if (is_prime(number)) {
            printf("Yes, it is\n");
        } else {
            printf("No, it isn't\n");
        }
    }

    return 0;
}
