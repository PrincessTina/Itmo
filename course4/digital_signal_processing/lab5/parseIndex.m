function [i, j] = parseIndex(index, n)
    i = fix(index / n) + 1;
    j = index - (i - 1) * n;
    
    if j == 0
        i = i - 1;
        j = n;
    end
end

