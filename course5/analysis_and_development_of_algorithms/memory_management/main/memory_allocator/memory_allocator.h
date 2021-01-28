#ifndef MEMORY_MANAGEMENT_MEMORY_ALLOCATOR_H
#define MEMORY_MANAGEMENT_MEMORY_ALLOCATOR_H

class MemoryAllocator {
public:
    MemoryAllocator();

    virtual ~MemoryAllocator();

    virtual void init();

    virtual void destroy();

    virtual void *alloc(size_t size);

    virtual void free(void *p);

    virtual void dumpStat() const;

    virtual void dumpBlocks() const;
};

#include "memory_allocator_impl.h"

#endif //MEMORY_MANAGEMENT_MEMORY_ALLOCATOR_H
