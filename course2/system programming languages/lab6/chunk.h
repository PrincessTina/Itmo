#ifndef LAB6_MCHUNKS_H
#define LAB6_MCHUNKS_H

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include "mem.h"

mem_t *look_for(size_t size);

mem_t *get_free();

mem_t *get_last();

mem_t *get(const char *chunk);

mem_t *get_prev(mem_t *chunk);

#endif
