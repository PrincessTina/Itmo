#include <iostream>
#include <cstring>

typedef struct node {
    int v;
    struct node *next;
};

node *list_create(int value) {
    auto *last_element = (node *) malloc(sizeof(node));
    last_element->v = value;
    last_element->next = nullptr;

    return last_element;
}

node *list_add_front(int value, node *link) {
    node *element = list_create(value);
    element->next = link;

    return element;
}

int list_length(node *linked_list) {
    int count = 0;

    if (linked_list != nullptr) {
        count++;
        node result = *linked_list;

        while (true) {
            if (result.next == nullptr) {
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
    node *n = nullptr;

    if (size < key) {
        return nullptr;
    } else {
        if (linked_list != nullptr) {
            count++;
            node result = *linked_list;
            node last_el = *linked_list;

            while (true) {
                if (result.next == nullptr) {
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

int list_sum(node *linked_list) {
    int sum = 0;

    if (linked_list != nullptr) {
        node result = *linked_list;

        while (true) {
            if (result.next == nullptr) {
                sum += result.v;
                break;
            }

            sum += result.v;
            result = *result.next;
        }
    }
    return sum;
}

int list_get(node *linked_list, int key) {
    int size = list_length(linked_list);
    int count = -1;
    int n = -1000000;

    if (size < key) {
        return -1000000;
    } else {
        if (linked_list != nullptr) {
            count++;
            node result = *linked_list;

            while (true) {
                if (result.next == nullptr) {
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

int main() {
    printf("Input some integer:\n");

    char array[1024];
    char buf[10];
    memset(array, 0, 1024);
    memset(buf, 0, 10);
    gets(array);
    int numberOfDigits = 0;
    node *linked_list = nullptr;

    for (char c: array) {
        if ((c >= 48) && (c <= 57)) {
            buf[numberOfDigits] = c;
            numberOfDigits++;
        } else if ((c == 32) || (c == 0)) {
            if (numberOfDigits > 0) {
                if (linked_list == nullptr) {
                    linked_list = list_create(atoi(buf));
                } else {
                    linked_list = list_add_front(atoi(buf), linked_list);
                }
                numberOfDigits = 0;
                memset(buf, 0, 10);
            }

            if (c == 0) {
                break;
            }
        }
    }

    //Sum
    printf("Sum of the input: %d", list_sum(linked_list));

    //N-element
    int key;
    char buf2[10];
    memset(buf2, 0, 10);
    printf("\nPlease input the number of the element to see it's value: ");
    scanf("%s", buf2);

    if ((buf2[0] >= 48) && (buf2[0] <= 57)) {
        key = atoi(buf2);

        int value = list_get(linked_list, key);
        if (value == -1000000) {
            printf("Array is too short for this key");
        } else {
            printf("Value: %d", value);
        }
    } else {
        printf("You didn't input the number");
    }

    printf("%d", sizeof(linked_list));
    list_free(linked_list);
    printf("%d", sizeof(linked_list));
    return 0;
}