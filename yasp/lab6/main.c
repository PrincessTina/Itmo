#include "mem.h"


int main(void) {
    char *test4076, *test512, *test4096, *testbig;

    init(1);

    test4076 = _malloc(1001);
    test4076[0] = 'a';
    test4076[1] = '9';
    test4076[2] = 'k';

    test512 = _malloc(2002);
    test512[0] = 'o';
    test512[1] = 'p';
    test512[2] = 'r';

    test4096 = _malloc(3003);
    test4096[0] = 'a';
    test4096[1] = '9';
    test4096[2] = 'k';

    testbig = _malloc(4004);
    testbig[0] = 'o';
    testbig[1] = 'o';
    testbig[2] = 'p';

    puts("malloc: ");
    memalloc_debug_heap();

    //_free(test4076);
    _free(test512);
    //_free(test4096);
    //_free(testbig);

    puts("free: ");
    memalloc_debug_heap();

    //_free(test4076);
    //_free(test512);
    _free(test4096);
    //_free(testbig);

    puts("free: ");
    memalloc_debug_heap();
    return 0;
}