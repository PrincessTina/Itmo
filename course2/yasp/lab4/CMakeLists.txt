cmake_minimum_required(VERSION 3.8)
project(lab4)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.c list_func.c list_func.h order_func.c order_func.h math_func.c math_func.h)
add_executable(lab4 ${SOURCE_FILES})