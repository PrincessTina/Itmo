function f = compressRatio(original, compressed)
    f = numel(original) / numel(find(compressed ~= 0));
end

