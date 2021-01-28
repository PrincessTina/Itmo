#ifndef MEMORY_MANAGEMENT_MEMORY_ALLOCATOR_IMPL_H
#define MEMORY_MANAGEMENT_MEMORY_ALLOCATOR_IMPL_H

#include "memory_allocator.h"

MemoryAllocator::MemoryAllocator() {

}

MemoryAllocator::~MemoryAllocator() {

}

void MemoryAllocator::init() {

}

void MemoryAllocator::destroy() {

}

void *MemoryAllocator::alloc(size_t size) {
    // virtual alloc ? (reserve, commit)
    // VirtualAlloc();
}

void MemoryAllocator::free(void *p) {
    // virtual free ?
}

void MemoryAllocator::dumpStat() const {

}

void MemoryAllocator::dumpBlocks() const {

}

#endif //MEMORY_MANAGEMENT_MEMORY_ALLOCATOR_IMPL_H
