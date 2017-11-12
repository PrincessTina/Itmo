#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "order_func.h"

void foreach(const node *linked_list, void (*func)(int)) {
    while (linked_list != NULL) {
        func(linked_list->v);
        linked_list = linked_list->next;
    }
}

node *map(const node *linked_list, int (*func)(int)) {
    node *new_list = NULL;

    while (linked_list != NULL) {
        int x = func(linked_list->v);

        if (new_list == NULL) {
            new_list = list_create(x);
        } else {
            list_add_back(x, new_list);
        }

        linked_list = linked_list->next;
    }

    return new_list;
}

int foldl(const node *linked_list, int rax, int (*func)(int, int)) {
    while (linked_list != NULL) {
        rax = func(rax, linked_list->v);
        linked_list = linked_list->next;
    }

    return rax;
}

node *map_mut(node *linked_list, int (*func)(int)) {
    node *list = linked_list;

    while (linked_list != NULL) {
        linked_list->v = func(linked_list->v);
        linked_list = linked_list->next;
    }

    return list;
}

node *iterate(int s, int length, int (*func)(int)) {
    node *new_list = NULL;
    int i;

    for (i = 0; i < length; i++) {
        if (i != 0) {
            s = func(s);
        }

        if (new_list == NULL) {
            new_list = list_create(s);
        } else {
            list_add_back(s, new_list);
        }
    }
    return new_list;
}

void intoArray(const node *linked_list, char *array) {
    int i;
    int k = 0;
    char buf[10];

    memset(array, 0, 1024);
    memset(buf, 0, 10);

    for (i = 0; i < list_length(linked_list); i++) {
        int value = list_get(linked_list, i);
        int j;

	    sprintf(buf, "%d", value);

        for (j = 0; j < 10; j++) {
            char c = buf[j];

            if (c == 0) {
                array[k] = 32;
                k++;
                break;
            } else {
                array[k] = c;
                k++;
            }
        }
    }
}

int save(const node *linked_list, const char *filename) {
    FILE *file;
    char array[1024];

    memset(array, 0, 1024);
    intoArray(linked_list, array);

    if ((file = fopen(filename, "w")) == NULL) {
        return false;
    }

    fputs(array, file);
    fclose(file);
    return true;
}

void fromArray(node *linked_list, const char *array) {
    int i;
    int k = 0;
    int numberOfDigits = 0;
    char buf[10];
    node *new_list = NULL;

    memset(buf, 0, 10);

    for (i = 0; i < 1024; i++) {
        char c = array[i];

        if ((c == 32) || (c == 0)) {
            if (numberOfDigits > 0) {
                if (new_list == NULL) {
                    new_list = list_create(atoi(buf));
                } else {
                    list_add_back(atoi(buf), new_list);
                }

                k++;
                numberOfDigits = 0;
                memset(buf, 0, 10);
            }

            if (c == 0) {
                break;
            }
        } else {
            buf[numberOfDigits] = c;
            numberOfDigits++;
        }
    }

    while (linked_list != NULL) {
        linked_list->v = new_list->v;
        linked_list = linked_list->next;
        new_list = new_list->next;
    }
}

int load(node *linked_list, const char *filename) {
    char array[1024];
    FILE *file;

    memset(array, 0, 1024);

    if ((file = fopen(filename, "r")) == NULL) {
        return false;
    }

    fgets(array, 1024, file);
    fclose(file);
    fromArray(linked_list, array);
    return true;
}

int serialize(const node *linked_list, const char *filename) {
    FILE *file;
    int i;
    char array[1024];

    memset(array, 0, 1024);
    intoArray(linked_list, array);

    if ((file = fopen(filename, "wb")) == NULL) {
        return false;
    }

    for (i = 0; i < 1024; i++) {
        putc(array[i], file);
    }

    fclose(file);
    return true;
}

int deserialize(node *linked_list, const char *filename) {
    char array[1024];
    FILE *file;
    char c;
    int i = 0;

    memset(array, 0, 1024);

    if ((file = fopen(filename, "rb")) == NULL) {
        return false;
    }

    while ((c = getc(file)) != EOF) {
        array[i] = c;
        i ++;
    }

    fclose(file);
    fromArray(linked_list, array);
    return true;
}
