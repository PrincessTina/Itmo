#include <stdio.h>
#include <stdlib.h>
#include "mchunks.h"

mem_t *look_for(size_t size) {
    mem_t *chunk = HEAP_START;

    for (; chunk; chunk = chunk->next) {
        if (chunk->capacity >= size && chunk->is_free == 1) {
            return chunk;
        }
    }
    return NULL;
}

mem_t *get_free() {
    mem_t *chunk = HEAP_START;

    for (; chunk; chunk = chunk->next) {
        if (chunk->is_free == 1) {
            return chunk;
        }
    }
    return NULL;
}

mem_t *get_last() {
    mem_t *last = HEAP_START;

    while (last->next) {
        last = last->next;
    }

    return last;
}

mem_t *get(const char *chunk) {
    mem_t *head = HEAP_START;

    for (; head; head = head->next) {
        if (head == (mem_t *) (chunk - sizeof(mem_t))) {
            return head;
        }
    }
    return NULL;
}


mem_t *get_prev(mem_t *chunk) {
    mem_t *head = HEAP_START;

    for (; head; head = head->next) {
        if (head->next == chunk) {
            return head;
        }
    }
    return NULL;
}