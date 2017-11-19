#include <stdio.h>
#include <stdlib.h>
#include "mchunks.h"

mem_t *mchunks_look_up(mem_t *chunk, size_t request) {
    size_t capacity;

    while (chunk != NULL) {
        capacity = chunk->capacity;

        if (capacity >= request && chunk->is_free == 1) {
            return chunk;
        }

        chunk = chunk->next;
    }

    return NULL;
}

mem_t *mchunks_last(mem_t *chunk) {
    while (chunk->next != NULL) {
        chunk = chunk->next;
    }
    return chunk;
}

mem_t *mchunks_get(mem_t *chunk, char *ptr) {
    while (chunk != NULL) {
        if (chunk == (mem_t *) (ptr - sizeof(mem_t))) {
            return chunk;
        }
        chunk = chunk->next;
    }
    return NULL;
}


mem_t *mchunks_get_prev(mem_t *chunk, mem_t *blk) {
    while (chunk != NULL) {
        if (chunk->next == blk) {
            return chunk;
        }
        chunk = chunk->next;
    }
    return NULL;
}