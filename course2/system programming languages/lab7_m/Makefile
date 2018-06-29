CC=gcc

CFLAGS=-ggdb -Wall -ansi -pedantic

ASM=nasm

ASMFLAGS=-f elf64

all: main.o code.o end clean

main.o:
	$(CC) $(CFLAGS) -c main.c -o main.o

code.o:
	$(ASM) $(ASMFLAGS) code.asm -o code.o

end:
	$(CC) $(CFLAGS) main.o code.o -o main

clean:
	rm main.o code.o 
