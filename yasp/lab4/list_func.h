#include <limits.h>
#include <errno.h>

#ifndef LAB4_LIST_FUNC_H
#define LAB4_LIST_FUNC_H

#define true 1
#define false 0
#define ERROR_CODE 166666666

typedef struct node {
    int v;
    struct node *next;
} node;

node *list_create(int value);

node *list_add_front(int value, node *link);

int list_length(const node *linked_list);

node *list_node_at(node *linked_list, int key);

void list_add_back(int value, node *linked_list);

int list_sum(const node *linked_list);

int list_get(const node *linked_list, int key);

void list_free(node *linked_list);

#endif //LAB4_LIST_FUNC_H
