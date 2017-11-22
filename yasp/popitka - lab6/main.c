#include <stdio.h>
#include <malloc.h>
#include "mem.h"


int main(void) {
    char *test4076, *test512, *test4096, *testbig, *testsmall;

    init(1);

    test4076 = _malloc(4076);
    test4076[0] = 'a';
    test4076[1] = '9';
    test4076[2] = 'k';
    test4076[3] = '8';

    test512 = _malloc(512);
    test512[0] = 'o';
    test512[1] = 'p';
    test512[2] = 'r';
    test512[3] = 's';

    test4096 = _malloc(4096);
    test4096[0] = 'a';
    test4096[1] = '9';
    test4096[2] = 'k';
    test4096[3] = '8';

    testbig = _malloc(1024l * 4096 * 1024);
    testbig[0] = 'o';
    testbig[1] = 'o';
    testbig[2] = 'p';
    testbig[3] = 's';

    testsmall = _malloc(255);
    testsmall[0] = 'a';
    testsmall[1] = 'k';
    testsmall[2] = 'v';
    testsmall[3] = 'a';

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