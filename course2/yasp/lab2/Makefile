all: lib dict main.o main clean

lib:
	nasm -felf64 lib.asm

dict:
	nasm -felf64 dict.asm

main.o:
	nasm -felf64 main.asm

main:
	ld main.o -o main

clean:
	rm dict.o lib.o main.o


