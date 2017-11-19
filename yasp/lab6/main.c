#include <stdio.h>
#include "mem.h"

size_t round(size_t);

int main(void) {
    void *test4076, *test512, *test4096, *testbig, *testsmall;

    init(1);

    test4076 = s_malloc(4076 * sizeof(unsigned char));
    test512 = s_malloc(512 * sizeof(unsigned char));
    test4096 = s_malloc(4096 * sizeof(unsigned char));
    testbig = s_malloc(1024l * 4096 * 1024 * sizeof(unsigned char));
    testsmall = s_malloc(255 * sizeof(unsigned char));

    puts("malloc: ");
    memalloc_debug_heap(stdout, HEAP_START);

    s_free(test4076);
    s_free(test4096);
    s_free(testbig);
    s_free(testsmall);
    s_free(test512);

    puts("free: ");
    memalloc_debug_heap(stdout, HEAP_START);
    return 0;
}