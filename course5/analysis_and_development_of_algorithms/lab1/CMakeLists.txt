# cmake_minimum_required(VERSION <specify CMake version here>)
project(lab1)

set(CMAKE_CXX_STANDARD 20)

add_executable(lab1 main.cpp sort.h sort_impl.h helper_library.h helper_library_impl.h)