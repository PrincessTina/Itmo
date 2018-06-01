CFLAGS 	= -ansi -pedantic -Wall -Werror -std=gnu99



all: clean main



clean:
	touch main
	
	rm main



main:
	gcc $(CFLAGS) -lm -o main chunk.c mem.c main.c 
