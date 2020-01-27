function f = harmonicFilter(block)
    f = numel(block) / sum(sum(1 ./ block));
end

