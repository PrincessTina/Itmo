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
CMAKE_SOURCE_DIR = C:\Users\krist\Documents\itmo\yasp\lab4

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/lab4.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/lab4.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/lab4.dir/flags.make

CMakeFiles/lab4.dir/main.c.obj: CMakeFiles/lab4.dir/flags.make
CMakeFiles/lab4.dir/main.c.obj: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/lab4.dir/main.c.obj"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\lab4.dir\main.c.obj   -c C:\Users\krist\Documents\itmo\yasp\lab4\main.c

CMakeFiles/lab4.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/lab4.dir/main.c.i"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E C:\Users\krist\Documents\itmo\yasp\lab4\main.c > CMakeFiles\lab4.dir\main.c.i

CMakeFiles/lab4.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/lab4.dir/main.c.s"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S C:\Users\krist\Documents\itmo\yasp\lab4\main.c -o CMakeFiles\lab4.dir\main.c.s

CMakeFiles/lab4.dir/main.c.obj.requires:

.PHONY : CMakeFiles/lab4.dir/main.c.obj.requires

CMakeFiles/lab4.dir/main.c.obj.provides: CMakeFiles/lab4.dir/main.c.obj.requires
	$(MAKE) -f CMakeFiles\lab4.dir\build.make CMakeFiles/lab4.dir/main.c.obj.provides.build
.PHONY : CMakeFiles/lab4.dir/main.c.obj.provides

CMakeFiles/lab4.dir/main.c.obj.provides.build: CMakeFiles/lab4.dir/main.c.obj


CMakeFiles/lab4.dir/list_func.c.obj: CMakeFiles/lab4.dir/flags.make
CMakeFiles/lab4.dir/list_func.c.obj: ../list_func.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/lab4.dir/list_func.c.obj"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\lab4.dir\list_func.c.obj   -c C:\Users\krist\Documents\itmo\yasp\lab4\list_func.c

CMakeFiles/lab4.dir/list_func.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/lab4.dir/list_func.c.i"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E C:\Users\krist\Documents\itmo\yasp\lab4\list_func.c > CMakeFiles\lab4.dir\list_func.c.i

CMakeFiles/lab4.dir/list_func.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/lab4.dir/list_func.c.s"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S C:\Users\krist\Documents\itmo\yasp\lab4\list_func.c -o CMakeFiles\lab4.dir\list_func.c.s

CMakeFiles/lab4.dir/list_func.c.obj.requires:

.PHONY : CMakeFiles/lab4.dir/list_func.c.obj.requires

CMakeFiles/lab4.dir/list_func.c.obj.provides: CMakeFiles/lab4.dir/list_func.c.obj.requires
	$(MAKE) -f CMakeFiles\lab4.dir\build.make CMakeFiles/lab4.dir/list_func.c.obj.provides.build
.PHONY : CMakeFiles/lab4.dir/list_func.c.obj.provides

CMakeFiles/lab4.dir/list_func.c.obj.provides.build: CMakeFiles/lab4.dir/list_func.c.obj


CMakeFiles/lab4.dir/order_func.c.obj: CMakeFiles/lab4.dir/flags.make
CMakeFiles/lab4.dir/order_func.c.obj: ../order_func.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object CMakeFiles/lab4.dir/order_func.c.obj"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\lab4.dir\order_func.c.obj   -c C:\Users\krist\Documents\itmo\yasp\lab4\order_func.c

CMakeFiles/lab4.dir/order_func.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/lab4.dir/order_func.c.i"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E C:\Users\krist\Documents\itmo\yasp\lab4\order_func.c > CMakeFiles\lab4.dir\order_func.c.i

CMakeFiles/lab4.dir/order_func.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/lab4.dir/order_func.c.s"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S C:\Users\krist\Documents\itmo\yasp\lab4\order_func.c -o CMakeFiles\lab4.dir\order_func.c.s

CMakeFiles/lab4.dir/order_func.c.obj.requires:

.PHONY : CMakeFiles/lab4.dir/order_func.c.obj.requires

CMakeFiles/lab4.dir/order_func.c.obj.provides: CMakeFiles/lab4.dir/order_func.c.obj.requires
	$(MAKE) -f CMakeFiles\lab4.dir\build.make CMakeFiles/lab4.dir/order_func.c.obj.provides.build
.PHONY : CMakeFiles/lab4.dir/order_func.c.obj.provides

CMakeFiles/lab4.dir/order_func.c.obj.provides.build: CMakeFiles/lab4.dir/order_func.c.obj


CMakeFiles/lab4.dir/math_func.c.obj: CMakeFiles/lab4.dir/flags.make
CMakeFiles/lab4.dir/math_func.c.obj: ../math_func.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building C object CMakeFiles/lab4.dir/math_func.c.obj"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\lab4.dir\math_func.c.obj   -c C:\Users\krist\Documents\itmo\yasp\lab4\math_func.c

CMakeFiles/lab4.dir/math_func.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/lab4.dir/math_func.c.i"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E C:\Users\krist\Documents\itmo\yasp\lab4\math_func.c > CMakeFiles\lab4.dir\math_func.c.i

CMakeFiles/lab4.dir/math_func.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/lab4.dir/math_func.c.s"
	C:\PROGRA~2\MINGW-~1\I686-7~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S C:\Users\krist\Documents\itmo\yasp\lab4\math_func.c -o CMakeFiles\lab4.dir\math_func.c.s

CMakeFiles/lab4.dir/math_func.c.obj.requires:

.PHONY : CMakeFiles/lab4.dir/math_func.c.obj.requires

CMakeFiles/lab4.dir/math_func.c.obj.provides: CMakeFiles/lab4.dir/math_func.c.obj.requires
	$(MAKE) -f CMakeFiles\lab4.dir\build.make CMakeFiles/lab4.dir/math_func.c.obj.provides.build
.PHONY : CMakeFiles/lab4.dir/math_func.c.obj.provides

CMakeFiles/lab4.dir/math_func.c.obj.provides.build: CMakeFiles/lab4.dir/math_func.c.obj


# Object files for target lab4
lab4_OBJECTS = \
"CMakeFiles/lab4.dir/main.c.obj" \
"CMakeFiles/lab4.dir/list_func.c.obj" \
"CMakeFiles/lab4.dir/order_func.c.obj" \
"CMakeFiles/lab4.dir/math_func.c.obj"

# External object files for target lab4
lab4_EXTERNAL_OBJECTS =

lab4.exe: CMakeFiles/lab4.dir/main.c.obj
lab4.exe: CMakeFiles/lab4.dir/list_func.c.obj
lab4.exe: CMakeFiles/lab4.dir/order_func.c.obj
lab4.exe: CMakeFiles/lab4.dir/math_func.c.obj
lab4.exe: CMakeFiles/lab4.dir/build.make
lab4.exe: CMakeFiles/lab4.dir/linklibs.rsp
lab4.exe: CMakeFiles/lab4.dir/objects1.rsp
lab4.exe: CMakeFiles/lab4.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Linking C executable lab4.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\lab4.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/lab4.dir/build: lab4.exe

.PHONY : CMakeFiles/lab4.dir/build

CMakeFiles/lab4.dir/requires: CMakeFiles/lab4.dir/main.c.obj.requires
CMakeFiles/lab4.dir/requires: CMakeFiles/lab4.dir/list_func.c.obj.requires
CMakeFiles/lab4.dir/requires: CMakeFiles/lab4.dir/order_func.c.obj.requires
CMakeFiles/lab4.dir/requires: CMakeFiles/lab4.dir/math_func.c.obj.requires

.PHONY : CMakeFiles/lab4.dir/requires

CMakeFiles/lab4.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\lab4.dir\cmake_clean.cmake
.PHONY : CMakeFiles/lab4.dir/clean

CMakeFiles/lab4.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\krist\Documents\itmo\yasp\lab4 C:\Users\krist\Documents\itmo\yasp\lab4 C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug C:\Users\krist\Documents\itmo\yasp\lab4\cmake-build-debug\CMakeFiles\lab4.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/lab4.dir/depend

