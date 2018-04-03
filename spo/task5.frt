: const 19 ;
: get_positive >r r@ 0 < if r> -1 * else r> then ; 
: loop 2 >r repeat const r@ % if r> 1 + >r r@ else const then const = until r> ;
: write_answer if 0 8 allot >r r@ ! r> else 1 8 allot >r r@ ! r> then .S ; ( 1 if it's prime, 0 if not )
: get_result >r const r> / get_positive 1 - write_answer ;
: check_zero_one const 0 = if -1 else const get_positive 1 = if -1 else 1 then then ;
: start check_zero_one 1 = if loop get_result else 0 write_answer then ;
start
