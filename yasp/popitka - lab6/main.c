#include <stdio.h>
#include <malloc.h>
#include "mem.h"


int main(void) {
    char *test4076, *test512, *test4096, *testbig, *testsmall;

    init(1);

    test4076 = _malloc(4076);
    *test4076 = 'b';
    test512 = _malloc(512);
    test4096 = _malloc(4096);
    testbig = _malloc(1024l * 4096 * 1024);
    testsmall = _malloc(255);

    puts("malloc: ");
    memalloc_debug_heap();

    _free(test4076);
    _free(test4096);
    _free(testbig);
    _free(testsmall);
    _free(test512);

    puts("free: ");
    memalloc_debug_heap();
    return 0;
}