CFLAGS 	= -ansi -pedantic -Wall -Werror
SOURCES = list_func.c math_func.c order_func.c main.c

all: clean main

clean:
	touch main
	rm main

main:
	gcc $(CFLAGS) -lm -o main $(SOURCES) 
