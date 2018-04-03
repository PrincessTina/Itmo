: word1 m" Hello, " ;
: word2 m" world !!!!!" ;
: size1 word1 count ;
: size2 word2 count ;
: size size1 size2 1 + + ;
: allocate size heap-alloc ;
: loop1 word1 size1 0 do >r r@ c@ swap >r r@ c! r> 1 + r> 1 + loop ;
: loop2 word2 size2 0 do >r r@ c@ swap >r r@ c! r> 1 + r> 1 + loop ;
: write_answer size 1 - - prints .S cr ;
: start allocate loop1 drop loop2 drop write_answer ;
start
