#include <stdlib.h>
#include "list_func.h"

node *list_create(int value) {
    node *last_element = (node *) malloc(sizeof(node));
    last_element->v = value;
    last_element->next = NULL;

    return last_element;
}

node *list_add_front(int value, node *link) {
    node *element = list_create(value);
    element->next = link;

    return element;
}

int list_length(const node *linked_list) {
    int count = 0;

    if (linked_list != NULL) {
        node result = *linked_list;
        count++;

        while (true) {
            if (result.next == NULL) {
                break;
            }

            count++;
            result = *result.next;
        }
    }
    return count;
}

node *list_node_at(node *linked_list, int key) {
    int size = list_length(linked_list);
    int count = -1;
    node *n = NULL;

    if (size < key) {
        return NULL;
    } else {
        if (linked_list != NULL) {
            node result = *linked_list;
            node last_el = *linked_list;
            count++;

            while (true) {
                if (result.next == NULL) {
                    if (count == key) {
                        if (key == 0) {
                            n = linked_list;
                        } else {
                            n = last_el.next;
                        }
                    }
                    break;
                }

                if (count == key) {
                    n = last_el.next;
                    break;
                }

                last_el = result;

                count++;
                result = *result.next;
            }
        }
        return n;
    }
}

void list_add_back(int value, node *linked_list) {
    node *element = list_create(value);
    list_node_at(linked_list, list_length(linked_list) - 1)->next = element;
}

int list_sum(const node *linked_list) {
    int sum = 0;

    if (linked_list != NULL) {
        node result = *linked_list;

        while (true) {
            if (result.next == NULL) {
                sum += result.v;
                break;
            }

            sum += result.v;
            result = *result.next;
        }
    }
    return sum;
}

int list_get(const node *linked_list, int key) {
    int size = list_length(linked_list);
    int count = -1;
    int n = ERROR_CODE;

    if (size < key) {
        return ERROR_CODE;
    } else {
        if (linked_list != NULL) {
            node result = *linked_list;
            count++;

            while (true) {
                if (result.next == NULL) {
                    if (count == key) {
                        n = result.v;
                    }
                    break;
                }

                if (count == key) {
                    n = result.v;
                    break;
                }

                count++;
                result = *result.next;
            }
        }
        return n;
    }
}

void list_free(node *linked_list) {
    free(linked_list);
}





