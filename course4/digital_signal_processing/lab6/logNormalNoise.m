function f = logNormalNoise(m, n)
    a = 1;
    b = 0.25;
    
    f = a * exp(b * randn(m, n));
end

