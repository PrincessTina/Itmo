function f = exponentialNoise(m, n)
    a = 1;
    
    f = -log(1 - rand(m, n)) / a;
end

