section .data
buf times 255 db 0

section .text	

	string_length:
mov rax, -1
 
.loop:
inc rax
mov rcx, [rdi + rax]
cmp cl, 0
jnz .loop
ret

	print_string:
mov rsi, rdi
call string_length
mov rdx, rax
mov rdi, 1
mov rax, 1
syscall
ret

	string_equals:
xor rax, rax
mov r8, -1

.loop:
inc r8
mov r9, [rdi + r8]
mov r10, [rsi + r8]
cmp r9b, r10b
jne .end
cmp byte[rsi + r8], 0
jne .loop
mov rax, 1

.end:
ret

	read_char:
mov rax, 0
mov rdx, 255
mov rdi, 0
mov rsi, buf
syscall

mov rdi, buf
call string_length
mov byte[buf + rax - 1], 0
ret 
