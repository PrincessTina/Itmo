%define link 0

%macro colon 2
%1: dq link   	;адрес
db %2, 0	;ключ
%define link %1
%endmacro

