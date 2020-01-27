function f = contravarianceFilter(block)
    q = 1;
    
    f = sum(sum(block .^ (q+1))) / sum(sum(block .^ q));
end

