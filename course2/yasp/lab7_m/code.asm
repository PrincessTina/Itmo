%define array r12
%define size r13
%define address r14

c1_1: dd 0.272, 0.349, 0.393, 0.272
c2_1: dd 0.543, 0.686, 0.769, 0.543
c3_1: dd 0.131, 0.168, 0.189, 0.131

c1_2: dd 0.349, 0.393, 0.272, 0.349
c2_2: dd 0.686, 0.769, 0.543, 0.686
c3_2: dd 0.168, 0.189, 0.131, 0.168

c1_3: dd 0.393, 0.272, 0.349, 0.393
c2_3: dd 0.769, 0.543, 0.686, 0.769
c3_3: dd 0.189, 0.131, 0.168, 0.189

max: dd 255, 255, 255, 255

global sepia

; rdi - array, rsi - array's length, rdx - new array's address
sepia:
    mov array, rdi
    mov size, rsi
    mov address, rdx

    movaps xmm6, [max]

    cmp size, 4
    jb .end

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

        pinsrb xmm0, byte[array], 0
        pinsrb xmm0, byte[array + 3], 12
        shufps xmm0, xmm0, 0b00000011

        pinsrb xmm1, byte[array + 1], 0
        pinsrb xmm1, byte[array + 4], 12
        shufps xmm1, xmm1, 0b00000011

        pinsrb xmm2, byte[array + 2], 0
        pinsrb xmm2, byte[array + 5], 12
        shufps xmm2, xmm2, 0b00000011


        movaps xmm3, [c1_1]
        movaps xmm4, [c2_1]
        movaps xmm5, [c3_1]

        mulps xmm0, xmm3
        mulps xmm1, xmm4
        mulps xmm2, xmm5

        addps xmm0, xmm1
        addps xmm0, xmm2

        pminsd xmm0, xmm6

        pextrb [address], xmm0, 0
        pextrb [address + 1], xmm0, 4
        pextrb [address + 2], xmm0, 8
        pextrb [address + 3], xmm0, 12

        add array, 3
        add address, 4

        ;second
        pxor xmm0, xmm0
        pxor xmm1, xmm1
        pxor xmm2, xmm2

        pinsrb xmm0, byte[array], 0
        pinsrb xmm0, byte[array + 3], 8
        shufps xmm0, xmm0, 0b00001010

        pinsrb xmm1, byte[array + 1], 0
        pinsrb xmm1, byte[array + 4], 8
        shufps xmm1, xmm1, 0b00001010

        pinsrb xmm2, byte[array + 2], 0
        pinsrb xmm2, byte[array + 5], 8
        shufps xmm2, xmm2, 0b00001010


        movaps xmm3, [c1_2]
        movaps xmm4, [c2_2]
        movaps xmm5, [c3_2]

        mulps xmm0, xmm3
        mulps xmm1, xmm4
        mulps xmm2, xmm5

        addps xmm0, xmm1
        addps xmm0, xmm2

        pminsd xmm0, xmm6

        pextrb [address], xmm0, 0
        pextrb [address + 1], xmm0, 4
        pextrb [address + 2], xmm0, 8
        pextrb [address + 3], xmm0, 12

        add array, 3
        add address, 4

        ;third
        pxor xmm0, xmm0
        pxor xmm1, xmm1
        pxor xmm2, xmm2

        pinsrb xmm0, byte[array], 0
        pinsrb xmm0, byte[array + 3], 4
        shufps xmm0, xmm0, 0b00010101

        pinsrb xmm1, byte[array + 1], 0
        pinsrb xmm1, byte[array + 4], 4
        shufps xmm1, xmm1, 0b00010101

        pinsrb xmm2, byte[array + 2], 0
        pinsrb xmm2, byte[array + 5], 4
        shufps xmm2, xmm2, 0b00010101


        movaps xmm3, [c1_3]
        movaps xmm4, [c2_3]
        movaps xmm5, [c3_3]

        mulps xmm0, xmm3
        mulps xmm1, xmm4
        mulps xmm2, xmm5

        addps xmm0, xmm1
        addps xmm0, xmm2

        pminsd xmm0, xmm6

        pextrb [address], xmm0, 0
        pextrb [address + 1], xmm0, 4
        pextrb [address + 2], xmm0, 8
        pextrb [address + 3], xmm0, 12

        add array, 6
        add address, 4

        sub size, 4
        jnz .loop
.end:
    ret

