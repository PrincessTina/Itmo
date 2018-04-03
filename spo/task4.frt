: const 19 ;
: get_positive >r r@ 0 < if r> -1 * else r> then ; 
: loop 2 >r repeat const r@ % if r> 1 + >r r@ else const then const = until r> ;
: write_answer if ." This is a composite number" else ." This is a prime number" then cr ;
: get_result >r const r> / get_positive 1 - write_answer ;
: check_zero_one const 0 = if -1 else const get_positive 1 = if -1 else 1 then then ;
: start check_zero_one 1 = if loop get_result else 0 write_answer then ;
start
