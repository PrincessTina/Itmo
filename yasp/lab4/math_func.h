#include <limits.h>
#include <errno.h>

#ifndef LAB4_MATH_FUNC_H
#define LAB4_MATH_FUNC_H

#define ERROR_CODE 166666666
#define MAX_SQR 46340
#define MAX_CUBE 1290

void print(int x);

void printOnNewLine(int x);

int sign(int x);

int square(int x);

int cube(int x);

int sum(int rax, int x);

int max(int rax, int x);

int min(int rax, int x);

int module(int x);

int mul_two(int x);

#endif
