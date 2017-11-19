#include <signal.h>
#include "mem.h"
#include "mchunks.h"

static size_t mem_from_pages(const size_t n) {
    return PAGE_SIZE * n;
}

static size_t pages_for(const size_t n) {
    return (n / PAGE_SIZE + 1) * PAGE_SIZE;
}

static void *allocate(char *ptr, size_t query) {
    return mmap(ptr, pages_for(query), PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
}

static void *allocate_by_kernel(size_t query) {
    return mmap(NULL, pages_for(query), PROT_WRITE | PROT_READ, MAP_ANONYMOUS | MAP_PRIVATE, -1, 0);
}

void *init(size_t initial_size) {
    void *chunk = allocate(HEAP_START, mem_from_pages(initial_size));

    mem_t *head = HEAP_START;
    head->next = NULL;
    head->capacity = mem_from_pages(initial_size) - sizeof(mem_t);
    head->is_free = 1;

    return chunk;
}


static mem_t *create_new_chunk(mem_t *last, size_t query) {
    char *alloc_address = (char *) last + last->capacity + sizeof(mem_t);
    size_t alloc_size = query + sizeof(mem_t);
    size_t allocated_memory = 0;
    mem_t *chunk;
    mem_t *unused = NULL;

    if ((chunk = allocate(alloc_address, alloc_size)) == MAP_FAILED && (chunk = allocate_by_kernel(alloc_size)) == MAP_FAILED) { //СЮДА НЕ ЗАХОДИМ
        return NULL;
    }
    allocated_memory = pages_for(alloc_size);

    if (alloc_size + sizeof(mem_t) < allocated_memory) {
        unused = (mem_t *) (alloc_address + alloc_size); //НЕВНЯТНОЕ ПОДОБИЕ ВЫДЕЛЕНИЯ ПАМЯТИ - ДЛЯ ЧЕГО?
        unused->capacity = allocated_memory - alloc_size - sizeof(mem_t);
        unused->is_free = 1;
        unused->next = NULL;
    }

    last->next = chunk;

    chunk->capacity = query;
    chunk->is_free = 0;
    chunk->next = unused;
    return chunk;
}

static mem_t *enlarge(mem_t *chunk, size_t query) {
    char *alloc_start = (char *) chunk + chunk->capacity + sizeof(mem_t);
    size_t alloc_size = query - chunk->capacity;
    size_t alloc_mem = 0;
    mem_t *unused = NULL;

    if (query < chunk->capacity) { /* undefined situation for resizing */
        raise(SIGILL);
    }

    if (allocate(alloc_start, alloc_size) != MAP_FAILED) {
        alloc_mem = pages_for(alloc_size);
        if (alloc_size + sizeof(mem_t) < alloc_mem) {
            unused = (mem_t *) (alloc_start + alloc_size);
            unused->capacity = alloc_mem - alloc_size - sizeof(mem_t);
            unused->is_free = 1;
            unused->next = NULL;
        }

        chunk->capacity = query;
        chunk->next = unused;
        return chunk;
    } else {
        return NULL;
    }
}

void *s_malloc(size_t query) {
    mem_t *chunk = mchunks_look_up(HEAP_START, query); /*trying to find free block */
    mem_t *new = NULL;

    if (chunk) {
        //ЧТО ЗА ХУЙНЯ
        if (chunk->capacity == query || chunk->capacity - query - sizeof(mem_t) > chunk->capacity) {
            /* if chunk is perfect for query or cannot be splited */
            chunk->is_free = 0;
            return (char *) chunk + sizeof(mem_t);
        }

        /* else split chunk */
        new = (mem_t *) ((unsigned char *) chunk + sizeof(mem_t) + query);

        new->capacity = chunk->capacity - query - sizeof(mem_t);
        new->is_free = 1;
        new->next = chunk->next;
        chunk->next = new;
        chunk->capacity = query;
        chunk->is_free = 0;
    } else {
        chunk = mchunks_last(HEAP_START);
        if (chunk->is_free && enlarge(chunk, query)) {
            chunk->is_free = 0;
        } else if ((new = create_new_chunk(chunk, query))) {
            chunk = new;
            chunk->is_free = 0; //НЕ НУЖНО
        } else {
            return NULL;
        }
    }
    return (char *) chunk + sizeof(mem_t); // НЕ НУЖНО
}

void s_free(void *mem) {
    mem_t *chunk = mchunks_get(HEAP_START, (char *) mem);
    mem_t *prev = mchunks_get_prev(HEAP_START, chunk);
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

void memalloc_debug_struct_info(FILE *f, const mem_t *const address) {
    size_t i;
    fprintf(f, "start: %p\nsize: %lu\nis_free: %d\n",
            (void *) address,
            address->capacity,
            address->is_free);

    for (i = 0; i < DEBUG_FIRST_BYTES && i < address->capacity; i++) {
        fprintf(f, "%X", ((unsigned char *) address)[sizeof(mem_t) + i]);
        putc('\n', f);
    }
}

void memalloc_debug_heap(FILE *f, const mem_t *ptr) {
    for (; ptr; ptr = ptr->next) {
        memalloc_debug_struct_info(f, ptr);
    }
}