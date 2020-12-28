cmake_minimum_required(VERSION 3.17)
project(dictionary)

set(CMAKE_CXX_STANDARD 20)

add_subdirectory(test/lib/googletest-release-1.10.0)
include_directories(test/lib/googletest-release-1.10.0/googletest/include)
include_directories(test/lib/googletest-release-1.10.0/googlemock/include)

add_executable(dictionary test/dictionary_tests.cpp)

target_link_libraries(dictionary gtest gtest_main)