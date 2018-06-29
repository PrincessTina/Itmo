( str - num )
: string-hash 
  0 >r ( init accumulator )
  repeat 
    dup c@ ( stacks: str char, acc ) 
    dup if ( not end of the line )  
        r> 13 * + 65537 % ( iteration of hash computations )
        >r 1 +  0 
    else ( end of line )
         drop drop r> 1
    then 
  until
;
: init m" Орлова" ;
: var init string-hash dup 3 % ;
: current 5 ;
: write_symbol . ."  " ; 
: loop repeat dup dup write_symbol 2 % if 3 * 1 + else 2 / then dup 1 = until ;
: start var drop drop current loop ;
start . cr
