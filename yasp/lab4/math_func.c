#include <stdio.h>
#include "math_func.h"

void print(int x) {
    if (x == ERROR_CODE) {
        printf("*overflow* ");
    } else {
        printf("%d ", x);
    }
}

void printOnNewLine(int x) {
    printf("%d\n", x);
}

int square(int x) {
    if (module(x) > MAX_SQR)  {
        return ERROR_CODE;
    } else {
        return x * x;
    }
}

int cube(int x) {
    if (module(x) > MAX_CUBE) {
        return ERROR_CODE;
    } else {
        return x * x * x;
    }
}

int sum(int rax, int x) {
    return rax + x;
}

int max(int rax, int x) {
    if (x > rax) {
        return x;
    } else {
        return rax;
    }
}

int min(int rax, int x) {
    if (x < rax) {
        return x;
    } else {
        return rax;
    }
}

int module(int x) {
    if (x > 0) {
        return x;
    } else {
        return -x;
    }
}

int mul_two(int x) {
    return x * 2;
}