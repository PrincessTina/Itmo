function f = gaussianNoise(m, n)
    a = 0;
    b = 0.15;
    
    f = a + b * randn(m, n);
end

