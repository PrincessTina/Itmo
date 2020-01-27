function f = invertDCT(image, blockSize)
    f = blkproc(image, [blockSize blockSize], 'idct2(x)');
end

