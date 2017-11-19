#ifndef LAB6_MCHUNKS_H
#define LAB6_MCHUNKS_H

#include "mem.h"

mem_t *mchunks_look_up(mem_t *list, size_t request);

mem_t *mchunks_last(mem_t *list);

mem_t *mchunks_get(mem_t *list, char *ptr);

mem_t *mchunks_get_prev(mem_t *list, mem_t *blk);

#endif