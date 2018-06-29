%include "lib.asm"

section .data

section .text

	find_word:
mov r11, rdi  ;адрес заданного ключа
mov r12, rsi  ;метка

.loop:
xor r13, r13
mov rax, 0
cmp r12, 0
je .end

mov r13, r12
add r13, 8

mov r12, [r12]   ;пытаюсь записать новую метку

mov rsi, r11
mov rdi, r13
call string_equals
cmp rax, 0
je .loop

mov rdi, r13
call string_length   ;узнаю длину ключа

add r13, 1
add r13, rax
mov rax, r13

.end:
ret
