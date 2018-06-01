
%define s r11
%define array r12
%define size r13
%define address r14

max: dd 255, 255, 255, 255
test: dd 50, 40, 50, 50

global sepia

; rdi - array, rsi - array's length, rdx - new array's address
sepia:
    mov array, rdi
    mov size, rsi
    mov address, rdx

    movaps xmm6, [max]

    test size, size
    jz .end

.loop:
;pinsrb - packed insert byte - первый аргумент xmm регистр, второй - байт памяти или 8-разрядный регистр. третий -
;позиция в xmm регистре, куда нужно положить этот байт.

;shufps используется для того, чтобы тасовать блоки по 4 байта. Получив в нулевом блоке нужное число, мы
;распространяем его на 1 и 2 блок. В результате получаем в регистре, в котором было [b, 0, 0, 0]
;числа [b, b, b, b].

;pextrb - packed extract byte. Извлекает из xmm регистра (второй аргумент) один байт на соответствующей
;позиции (третий аргумент) и помещает его в укзанный байт в памяти, или в 8-разрядный регистр.
;Таким образом, мы кладем четыре обработанные компоненты в результирующий массив.

        ; first
        pxor xmm0, xmm0
        pxor xmm1, xmm1
        pxor xmm2, xmm2
        mov r9, 255

        pinsrb xmm0, byte[array], 0
        pinsrb xmm0, byte[array + 1], 4
        pinsrb xmm0, byte[array + 2], 8
        pinsrb xmm0, byte[array + 3], 12

        pinsrb xmm1, byte[array + 4], 0
        pinsrb xmm1, byte[array + 5], 4
        pinsrb xmm1, byte[array + 6], 8
        pinsrb xmm1, byte[array + 7], 12

        pinsrb xmm2, byte[array + 8], 0
        pinsrb xmm2, byte[array + 9], 4
        pinsrb xmm2, byte[array + 0xA], 8
        pinsrb xmm2, byte[array + 0xB], 12

        ;first byte
        pextrb s, xmm0, 0
        pextrb r10, xmm0, 4
        add s, r10
        pextrb r10, xmm0, 8
        add s, r10
        mov rax, s
        mov cl, 3
        div cl
        mov s, rax
        add s, 30

        pinsrb xmm0, r11b, 8
        sub s, 15
        pinsrb xmm0, r11b, 4
        sub s, 15
        pinsrb xmm0, r11b, 0

        pminsd xmm0, xmm6

        ;second byte
        pextrb s, xmm0, 12
        pextrb r10, xmm1, 0
        add s, r10
        pextrb r10, xmm1, 4
        add s, r10
        mov rax, s
        mov cl, 3
        div cl
        mov s, rax
        add s, 30

        pinsrb xmm1, r11b, 4
        sub s, 15
        pinsrb xmm1, r11b, 0
        sub s, 15
        pinsrb xmm0, r11b, 12

        pminsd xmm1, xmm6

        ;third byte
        pextrb s, xmm1, 8
        pextrb r10, xmm1, 12
        add s, r10
        pextrb r10, xmm2, 0
        add s, r10
        mov rax, s
        mov cl, 3
        div cl
        mov s, rax
        add s, 30

        pinsrb xmm2, r11b, 0
        sub s, 15
        pinsrb xmm1, r11b, 12
        sub s, 15
        pinsrb xmm1, r11b, 8

        ;fourth byte
        pextrb s, xmm2, 4
        pextrb r10, xmm2, 8
        add s, r10
        pextrb r10, xmm2, 12
        add s, r10
        mov rax, s
        mov cl, 3
        div cl
        mov s, rax
        add s, 30

        pinsrb xmm2, r11b, 12
        sub s, 15
        pinsrb xmm2, r11b, 8
        sub s, 15
        pinsrb xmm2, r11b, 4

        pminsd xmm0, xmm6

        ;addps xmm0, [test]
        ;addps xmm1, [test]
        ;addps xmm2, [test]

        ;pminsd xmm0, xmm6
        ;pminsd xmm1, xmm6
        ;pminsd xmm2, xmm6

        pextrb [address], xmm0, 0
        pextrb [address + 1], xmm0, 4
        pextrb [address + 2], xmm0, 8
        pextrb [address + 3], xmm0, 12
        pextrb [address + 4], xmm1, 0
        pextrb [address + 5], xmm1, 4
        pextrb [address + 6], xmm1, 8
        pextrb [address + 7], xmm1, 12
        pextrb [address + 8], xmm2, 0
        pextrb [address + 9], xmm2, 4
        pextrb [address + 0xA], xmm2, 8
        pextrb [address + 0xB], xmm2, 12

        add array, 12
        add address, 12

        sub size, 4
        jnz .loop
.end:
    ret

