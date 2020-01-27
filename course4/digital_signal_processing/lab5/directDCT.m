function f = directDCT(image, blockSize)
    f = blkproc(image, [blockSize blockSize], 'dct2(x)');
end

