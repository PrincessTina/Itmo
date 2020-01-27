function f = quantization(image, blockSize, mask)
    f = blkproc(image, [blockSize blockSize], 'P1.*x', mask);  
end

