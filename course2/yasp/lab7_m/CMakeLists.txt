cmake_minimum_required(VERSION 3.8)
project(lab7_m)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES code.asm main.c main.h)
add_executable(lab7_m ${SOURCE_FILES})