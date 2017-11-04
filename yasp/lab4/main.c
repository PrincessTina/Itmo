#include <stdlib.h>
#include <mem.h>
#include <stdio.h>
#include "list_func.h"
#include "order_func.h"
#include "math_func.h"

int main(void) {
    int numberOfDigits = 0;
    int isDigit = true;
    int isFloat = false;
    int isOverflow = false;
    int i;
    char buf[10];
    node *linked_list = NULL;
    char array[1024];
    char *filename = "..\\files\\list.txt";

    memset(array, 0, 1024);
    memset(buf, 0, 10);

    printf("Input some integer:\n");
    gets(array);

    for (i = 0; i < 1024; i++) {
        char c = array[i];

        if (c == 46) {
            isFloat = true;
        } else if (((c >= 48) && (c <= 57)) || ((c == 45) && (numberOfDigits == 0))) {
            buf[numberOfDigits] = c;
            numberOfDigits++;
        } else if ((c == 32) || (c == 0)) {
            if (isFloat) {
                numberOfDigits = 0;
                memset(buf, 0, 10);
                isFloat = false;
            } else if (numberOfDigits > 0) {
                int mean = atoi(buf);

                if (numberOfDigits > 10) {
                    isOverflow = true;
                    break;
                }

                if (linked_list == NULL) {
                    linked_list = list_create(mean);
                } else {
                    linked_list = list_add_front(mean, linked_list);
                }
                numberOfDigits = 0;
                memset(buf, 0, 10);
            }

            if (c == 0) {
                break;
            }
        }
    }

    if (isOverflow) {
        printf("One of the input cased overflow. Please, try again");
    } else {
        if (save(linked_list, filename)) {
            printf("\nList was successfully written in file %s", filename);
        } else {
            printf("\nWriting list in file %s has failed", filename);
        }

        if (load(linked_list, filename)) {
            printf("\nList was successfully loaded from file %s", filename);
        } else {
            printf("\nReading list from file %s has failed", filename);
        }

        filename = "..\\files\\list.bin";
        if (serialize(linked_list, filename)) {
            printf("\nList was successfully serialized in file %s", filename);
        } else {
            printf("\nSerializing list in file %s has failed", filename);
        }

        if (deserialize(linked_list, filename)) {
            printf("\nList was successfully deserialized from file %s", filename);
        } else {
            printf("\nDeserializing list from file %s has failed", filename);
        }

        //Foreach
        printf("\n\nYour list (v1): \n");
        foreach(linked_list, printOnNewLine);
        printf("\nYour list (v2): ");
        foreach(linked_list, print);

        //Square
        printf("\n\nThe squares of the elements of the list: ");
        foreach(map(linked_list, square), print);

        //Cube
        printf("\nThe cubes of the elements of the list: ");
        foreach(map(linked_list, cube), print);

        //Sum
        printf("\nSum of the input (from list_func): %d", list_sum(linked_list));

        //Sum
        printf("\nSum of the input (from math_func): %d", foldl(linked_list, 0, sum));

        //Max
        printf("\nMax of the input: %d", foldl(linked_list, 0, max));

        //Min
        printf("\nMin of the input: %d", foldl(linked_list, foldl(linked_list, 0, max), min));

        //Powers of two
        printf("\nPowers of two: ");
        foreach(iterate(1, 10, mul_two), print);

        //N-element
        printf("\nPlease input the number of the element to see it's value: ");
        memset(buf, 0, 10);
        gets(buf);

        for (i = 0; i < 10; i++) {
            char c = buf[i];

            if (c == 0) {
                break;
            } else if (((c < 48) || (c > 57)) && (c != 32)) {
                isDigit = false;
                break;
            }
        }

        if (isDigit) {
            int key = atoi(buf);
            int value = list_get(linked_list, key);

            if (value == ERROR_CODE) {
                printf("Array is too short for this key\n");
            } else {
                printf("Value: %d\n", value);
            }
        } else {
            printf("You didn't input the right number\n");
        }

        //Modules
        printf("\nThe modules of the elements of the list: ");
        foreach(map_mut(linked_list, module), print);
    }


    list_free(linked_list);
    return 0;
}
