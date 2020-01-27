function f = geometricFilter(block)
    f = nthroot(prod(prod(block)), numel(block));
end

