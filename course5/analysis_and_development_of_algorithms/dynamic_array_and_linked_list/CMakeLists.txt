cmake_minimum_required(VERSION 3.17)
project(dynamic_array_and_linked_list)

set(CMAKE_CXX_STANDARD 20)

add_subdirectory(test/lib/googletest-release-1.10.0)
include_directories(test/lib/googletest-release-1.10.0/googletest/include)
include_directories(test/lib/googletest-release-1.10.0/googlemock/include)

#add_executable(dynamic_array_and_linked_list main/main.cpp)
add_executable(dynamic_array_and_linked_list test/list_tests.cpp)
#add_executable(dynamic_array_and_linked_list test/array_tests.cpp)

target_link_libraries(dynamic_array_and_linked_list gtest gtest_main)