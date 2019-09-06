cmake_minimum_required(VERSION 3.14)
project(pa1 C)

set(CMAKE_C_STANDARD 99)

add_executable(pa1 main.c ipc.c main.h pa1.h common.h ipc.h)