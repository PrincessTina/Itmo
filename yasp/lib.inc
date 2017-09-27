section .data
buf times 20 db 0

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


	print_char:
push rdi
mov rsi, rsp
mov rdi, 1
mov rdx, 1
mov rax, 1
syscall
pop rdi
ret

	print_newline:
mov rdi, 0xA
call print_char
ret


	print_uint:
mov rax, rdi
mov rcx, 10
mov r9, rsp
push 0
.loop:
xor rdx, rdx
div rcx
add dl, '0'
dec rsp
mov [rsp], dl
test rax, rax
jnz .loop
mov rdi, rsp
call print_string
mov rsp, r9
ret


	print_int:
mov r8, rdi
test r8, r8          
jns print_uint   
mov rdi, '-'         
call print_char
neg  r8              
mov  rdi, r8
call  print_uint
ret
  

	read_char:
push 0
mov rax, 0
mov rdx, 1
mov rdi, 0
mov rsi, rsp
syscall
pop rax
ret 

	read_word:                
mov rdx, 256            
mov rdi, 0                
mov rsi, buf   
mov rax, 0                
syscall
mov r8, -1

.check:                       
inc r8
cmp byte[buf + r8], 0x09
jz  .check
cmp byte[buf + r8], 0x0A
jz  .check
cmp byte[buf + r8], 0x20
jz  .check
    
mov r9, r8
cmp byte[buf + r8], 0      
jz  .finish

.read_next:
inc r8
cmp byte[buf + r8], 0x09
jz  .finish
cmp byte[buf + r8], 0x0A
jz  .finish
cmp byte[buf + r8], 0x20
jz  .finish
cmp byte[buf + r8], 0
jz  .finish
jmp .read_next

.finish:
mov byte[buf + r8], 0
sub r8, r9
mov rdx, r8
mov rax, 0
cmp rdx, 20
ja .end
lea rax, [buf + r9]
.end:
ret
	
	parse_uint:
xor rcx,rcx
xor rax, rax
xor r10, r10
mov r9, 10

.check_num:
cmp byte[rdi+rcx], '0'       
jb .finish
cmp byte[rdi+rcx], '9'       
ja .finish
mul r9                            
mov r10b, byte[rdi+rcx]  ; обращаемся к младшему байту
sub r10b, '0' 	; приводим к числу
add rax, r10
inc rcx
jnz .check_num

.finish:
mov rdx, rcx
ret

	parse_int:
cmp byte[rdi], '-'
jne parse_uint
inc rdi
push rdi
call parse_uint
pop rdi
neg rax
inc rdx
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
cmp byte[rdi + r8], 0
jne .loop

mov rax, 1
.end:
ret


	string_copy:
call string_length
mov rcx, rax          
inc rcx                
push rdi          ; в rdi приемник в rsi источник
mov rdi, rsi
pop rsi
rep movsb         ; копируем строки rep(пока rcx != 0)
ret
