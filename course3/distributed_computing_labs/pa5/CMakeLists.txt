cmake_minimum_required(VERSION 3.14)
project(pa5 C)

set(CMAKE_C_STANDARD 99)

add_library(libruntime STATIC IMPORTED)

set_property(TARGET libruntime PROPERTY IMPORTED_LOCATION /home/princess/distributed_computing_labs/pa2/lib64/libruntime.so)

add_executable(pa5
        main.c ipc.c banking.c bank_robbery.c print_history.c lamport_time.c priority_queue.c critical_section.c
        main.h common.h ipc.h pa2345.h banking.h lamport_time.h priority_queue.h critical_section.h)

target_link_libraries(pa5 libruntime)