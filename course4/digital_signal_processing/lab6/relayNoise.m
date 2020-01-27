function f = relayNoise(m , n)
    b = 1;
    
    f = sqrt(-b * log(1 - rand(m, n)));
end

