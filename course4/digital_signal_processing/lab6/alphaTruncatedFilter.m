function f = alphaTruncatedFilter(block)
    d = 3;
    
    f = sum(sum(block)) / (numel(block) - d);
end

