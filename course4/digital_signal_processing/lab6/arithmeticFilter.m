function f = arithmeticFilter(block)
    f = sum(sum(block)) / numel(block);
end

