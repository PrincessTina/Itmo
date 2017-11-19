#include <signal.h>
#include "mem.h"
#include "mchunks.h"

size_t pages_for(const size_t size) {
   return size < PAGE_SIZE ? PAGE_SIZE : PAGE_SIZE * (size / PAGE_SIZE);
}

void *allocate(char *pointer, size_t size) {
    return mmap(pointer, pages_for(size), PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
}

void init(size_t initial_size) {
    allocate(HEAP_START, initial_size);

    mem_t *head = HEAP_START;
    head->next = NULL;
    head->capacity = pages_for(initial_size) - sizeof(mem_t);
    head->is_free = 1;
}

mem_t *create_new_chunk(mem_t *last, size_t size) {
    char *address = (char *) last + last->capacity + sizeof(mem_t);

    size_t useful_memory = size + sizeof(mem_t);
    size_t all_memory = pages_for(useful_memory);
    mem_t *chunk;
    mem_t *unused = NULL;

    allocate(address, all_memory);
    chunk = (mem_t *) (address);


    if (all_memory > useful_memory) {
        unused = (mem_t *) (address + useful_memory);

        unused->capacity = all_memory - useful_memory - sizeof(mem_t);
        unused->is_free = 1;
        unused->next = NULL;
    }

    last->next = chunk;

    chunk->capacity = size;
    chunk->is_free = 0;
    chunk->next = unused;
    return chunk;
}

mem_t enlarge(mem_t *chunk, size_t size) { //С free БЛОКОМ МОЖЕТ ЗАЛЕЗТЬ НА ДРУГОЙ, НАХОДЯСЬ МЕЖДУ БЛОКАМИ
    char *address = (char *) chunk + chunk->capacity + sizeof(mem_t);

    size_t useful_memory = size - chunk->capacity;
    size_t all_memory = pages_for(useful_memory);
    mem_t *unused = NULL;

    allocate(address, all_memory);

    if (all_memory > useful_memory) {
        unused = (mem_t *) (address + useful_memory + sizeof(mem_t));

        unused->capacity = all_memory - useful_memory - sizeof(mem_t);
        unused->is_free = 1;
        unused->next = NULL;
    }

    chunk->capacity = size;
    chunk->next = unused;
}

void *_malloc(size_t size) {
    mem_t *chunk = look_for(size);

    if (chunk) {
        if (chunk->capacity == size) {
            chunk->is_free = 0;
        } else {
            mem_t *new = (mem_t *) ((char *) chunk + size + sizeof(mem_t));
            new->capacity = chunk->capacity - size - sizeof(mem_t);
            new->is_free = 1;
            new->next = chunk->next;

            chunk->next = new;
            chunk->capacity = size;
            chunk->is_free = 0;
        }
    } else {
        chunk = get_free();

        if (chunk) {
            enlarge(chunk, size);
            chunk->is_free = 0;
        } else {
            chunk = create_new_chunk(get_last(), size);
        }
    }

    return (char *) chunk + sizeof(mem_t);
}

void _free(void *mem) {
    mem_t *chunk = get((char *) mem);
    mem_t *prev = get_prev(chunk);

    if (chunk) {
        chunk->is_free = 1;

        if (chunk->next) {
            if (chunk->next->is_free == 1) {
                chunk->capacity += chunk->next->capacity + sizeof(mem_t);
                chunk->next = chunk->next->next;
            }
        }

        if (prev) {
            if (prev->is_free == 1) {
                prev->capacity += chunk->capacity + sizeof(mem_t);
                prev->next = chunk->next;
            }
        }
    }
}

void memalloc_debug_struct_info(mem_t *address) {
    size_t i;

    printf("address: %p\nsize: %lu\nis_free: %d\n", (void *) address, address->capacity, address->is_free);

    for (i = 0; i < DEBUG_FIRST_BYTES && i < address->capacity; i++) {
        printf("%X\n", ((char *) address)[sizeof(mem_t) + i]);
    }

    printf("\n");
}

void memalloc_debug_heap() {
    mem_t *pointer = HEAP_START;

    for (; pointer; pointer = pointer->next) {
        memalloc_debug_struct_info(pointer);
    }
}