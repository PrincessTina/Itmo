global _start
%include "words.inc"
%include "dict.asm"

section .data
message: db "error: key wasn't found", 10

section .text

	_start:
call read_char
mov rdi, buf
mov rsi, link
call find_word       ;в rax - адрес

cmp rax, 0
je .error

mov rdi, rax
call print_string

push 0xA
mov rdi, rsp
call print_string
pop rax

jmp .end

.error:
mov rdx, 24
mov rsi, message
mov rax, 1
mov rdi, 2
syscall

.end:
mov rax, 60
syscall
