#ifndef LAB6_MEM_H
#define LAB6_MEM_H

#include <stddef.h>
#include <stdint.h>
#include <stdio.h>

#include <sys/mman.h>

#define HEAP_START ((void*)0x04040000)
#define PAGE_SIZE 4096

#pragma pack(push, 1)
typedef struct _mem_t {
    struct _mem_t *next;
    size_t capacity;
    int is_free;
} mem_t;
#pragma pack(pop)

void *_malloc(size_t size);

void _free(void *mem);

void init(size_t initial_size);

#define DEBUG_FIRST_BYTES 4

void memalloc_debug_struct_info(mem_t *address);

void memalloc_debug_heap();

#endif
