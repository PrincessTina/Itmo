cmake_minimum_required(VERSION 3.8)
project(lab7)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES strings.c strings.h sepia_example.c nfilerw.c nfilerw.h main.c imagelib.c imagelib.h imagestorage.h sepia_c.h)
add_executable(lab7 ${SOURCE_FILES})