function f = erlangNoise(m, n)
    a = 2;
    b = 5;
    f = 0;
    
    for i = 1:b
        f = f - log(1 - rand(m, n)) / a;
    end
end

