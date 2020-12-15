cmake_minimum_required(VERSION 3.17)
project(lab1)

set(CMAKE_CXX_STANDARD 20)

add_subdirectory(test/lib/googletest-release-1.10.0)
include_directories(test/lib/googletest-release-1.10.0/googletest/include)
include_directories(test/lib/googletest-release-1.10.0/googlemock/include)

#add_executable(lab1 main.cpp sort.h sort_impl.h helper_library.h helper_library_impl.h)
add_executable(lab1 test/test.cpp)

target_link_libraries(lab1 gtest gtest_main)