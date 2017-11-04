#include <limits.h>
#include <errno.h>
#include "list_func.h"

#ifndef LAB4_ORDER_FUNC_H
#define LAB4_ORDER_FUNC_H

void foreach(const node *linked_list, void (*func)(int));

node* map(const node *linked_list, int (*func)(int));

int foldl(const node *linked_list, int rax, int (*func)(int, int));

node* map_mut(node *linked_list, int (*func)(int));

node* iterate(int x, int length, int (*func)(int));

void intoArray(const node *linked_list, char *array);

int save(const node *linked_list, const char* filename);

void fromArray(node *linked_list, const char *array);

int load(node *linked_list, const char *filename);

int serialize(const node *linked_list, const char *filename);

int deserialize(node *linked_list, const char *filename);

#endif //LAB4_ORDER_FUNC_H
