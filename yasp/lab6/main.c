#include <fcntl.h>
#include <sys/mman.h>

#define true 1
#define false 0
#define HEAP_START ((void*)0x04040000)

struct mem  {
    struct mem* next;
    size_t capacity;
    int is_free;
};

void init(size_t initial_size){
    void* ptr = mmap(HEAP_START, initial_size, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
    struct mem_t *head = HEAP_START;

    head->next = NULL;
    head->capacity = round_4096(initial_size) - sizeof(struct mem_t);
    head->is_free = 1;
}


int main(void) {
    init(1);

    return 0;
}