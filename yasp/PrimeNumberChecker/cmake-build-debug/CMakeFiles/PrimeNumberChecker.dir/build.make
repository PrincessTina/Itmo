# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.8

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2017.2.3\bin\cmake\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2017.2.3\bin\cmake\bin\cmake.exe" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = C:\Users\krist\CLionProjects\PrimeNumberChecker

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Users\krist\CLionProjects\PrimeNumberChecker\cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/PrimeNumberChecker.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/PrimeNumberChecker.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/PrimeNumberChecker.dir/flags.make

CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj: CMakeFiles/PrimeNumberChecker.dir/flags.make
CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\krist\CLionProjects\PrimeNumberChecker\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\G__~1.EXE  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\PrimeNumberChecker.dir\main.cpp.obj -c C:\Users\krist\CLionProjects\PrimeNumberChecker\main.cpp

CMakeFiles/PrimeNumberChecker.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/PrimeNumberChecker.dir/main.cpp.i"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\G__~1.EXE $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E C:\Users\krist\CLionProjects\PrimeNumberChecker\main.cpp > CMakeFiles\PrimeNumberChecker.dir\main.cpp.i

CMakeFiles/PrimeNumberChecker.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/PrimeNumberChecker.dir/main.cpp.s"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\G__~1.EXE $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S C:\Users\krist\CLionProjects\PrimeNumberChecker\main.cpp -o CMakeFiles\PrimeNumberChecker.dir\main.cpp.s

CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.requires:

.PHONY : CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.requires

CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.provides: CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.requires
	$(MAKE) -f CMakeFiles\PrimeNumberChecker.dir\build.make CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.provides.build
.PHONY : CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.provides

CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.provides.build: CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj


# Object files for target PrimeNumberChecker
PrimeNumberChecker_OBJECTS = \
"CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj"

# External object files for target PrimeNumberChecker
PrimeNumberChecker_EXTERNAL_OBJECTS =

PrimeNumberChecker.exe: CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj
PrimeNumberChecker.exe: CMakeFiles/PrimeNumberChecker.dir/build.make
PrimeNumberChecker.exe: CMakeFiles/PrimeNumberChecker.dir/linklibs.rsp
PrimeNumberChecker.exe: CMakeFiles/PrimeNumberChecker.dir/objects1.rsp
PrimeNumberChecker.exe: CMakeFiles/PrimeNumberChecker.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=C:\Users\krist\CLionProjects\PrimeNumberChecker\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable PrimeNumberChecker.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\PrimeNumberChecker.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/PrimeNumberChecker.dir/build: PrimeNumberChecker.exe

.PHONY : CMakeFiles/PrimeNumberChecker.dir/build

CMakeFiles/PrimeNumberChecker.dir/requires: CMakeFiles/PrimeNumberChecker.dir/main.cpp.obj.requires

.PHONY : CMakeFiles/PrimeNumberChecker.dir/requires

CMakeFiles/PrimeNumberChecker.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\PrimeNumberChecker.dir\cmake_clean.cmake
.PHONY : CMakeFiles/PrimeNumberChecker.dir/clean

CMakeFiles/PrimeNumberChecker.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\krist\CLionProjects\PrimeNumberChecker C:\Users\krist\CLionProjects\PrimeNumberChecker C:\Users\krist\CLionProjects\PrimeNumberChecker\cmake-build-debug C:\Users\krist\CLionProjects\PrimeNumberChecker\cmake-build-debug C:\Users\krist\CLionProjects\PrimeNumberChecker\cmake-build-debug\CMakeFiles\PrimeNumberChecker.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/PrimeNumberChecker.dir/depend
