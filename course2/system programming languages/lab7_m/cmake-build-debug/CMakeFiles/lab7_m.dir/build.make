# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.8

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

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/princess/clion-2017.2.3/bin/cmake/bin/cmake

# The command to remove a file.
RM = /home/princess/clion-2017.2.3/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/princess/itmo/yasp/lab7_m

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/princess/itmo/yasp/lab7_m/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/lab7_m.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/lab7_m.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/lab7_m.dir/flags.make

CMakeFiles/lab7_m.dir/main.c.o: CMakeFiles/lab7_m.dir/flags.make
CMakeFiles/lab7_m.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/princess/itmo/yasp/lab7_m/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/lab7_m.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/lab7_m.dir/main.c.o   -c /home/princess/itmo/yasp/lab7_m/main.c

CMakeFiles/lab7_m.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/lab7_m.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/princess/itmo/yasp/lab7_m/main.c > CMakeFiles/lab7_m.dir/main.c.i

CMakeFiles/lab7_m.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/lab7_m.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/princess/itmo/yasp/lab7_m/main.c -o CMakeFiles/lab7_m.dir/main.c.s

CMakeFiles/lab7_m.dir/main.c.o.requires:

.PHONY : CMakeFiles/lab7_m.dir/main.c.o.requires

CMakeFiles/lab7_m.dir/main.c.o.provides: CMakeFiles/lab7_m.dir/main.c.o.requires
	$(MAKE) -f CMakeFiles/lab7_m.dir/build.make CMakeFiles/lab7_m.dir/main.c.o.provides.build
.PHONY : CMakeFiles/lab7_m.dir/main.c.o.provides

CMakeFiles/lab7_m.dir/main.c.o.provides.build: CMakeFiles/lab7_m.dir/main.c.o


# Object files for target lab7_m
lab7_m_OBJECTS = \
"CMakeFiles/lab7_m.dir/main.c.o"

# External object files for target lab7_m
lab7_m_EXTERNAL_OBJECTS =

lab7_m: CMakeFiles/lab7_m.dir/main.c.o
lab7_m: CMakeFiles/lab7_m.dir/build.make
lab7_m: CMakeFiles/lab7_m.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/princess/itmo/yasp/lab7_m/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable lab7_m"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/lab7_m.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/lab7_m.dir/build: lab7_m

.PHONY : CMakeFiles/lab7_m.dir/build

CMakeFiles/lab7_m.dir/requires: CMakeFiles/lab7_m.dir/main.c.o.requires

.PHONY : CMakeFiles/lab7_m.dir/requires

CMakeFiles/lab7_m.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/lab7_m.dir/cmake_clean.cmake
.PHONY : CMakeFiles/lab7_m.dir/clean

CMakeFiles/lab7_m.dir/depend:
	cd /home/princess/itmo/yasp/lab7_m/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/princess/itmo/yasp/lab7_m /home/princess/itmo/yasp/lab7_m /home/princess/itmo/yasp/lab7_m/cmake-build-debug /home/princess/itmo/yasp/lab7_m/cmake-build-debug /home/princess/itmo/yasp/lab7_m/cmake-build-debug/CMakeFiles/lab7_m.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/lab7_m.dir/depend
