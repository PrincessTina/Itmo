f = imread('cat.pgm');
figure, imshow(f);
f = im2double(f);
step = 5;
blockSize = 8;
mask = ones(blockSize);
zigzagOrder = iZigzag(blockSize);
B = directDCT(f, blockSize);

compressionRatio = zeros(1, 12);
correlationCoefficient = zeros(1, 12);
numberOfZeros = zeros(1, 12);

figure

for k = 1:12
    for s = 1:step
        index = zigzagOrder((k - 1) * step + s);
        [i, j] = parseIndex(index, blockSize);

        mask(i, j) = 0;
    end
    
    B2 = quantization(B, blockSize, mask);
    f2 = invertDCT(B2, blockSize);
    
    compressionRatio(k) = compressRatio(f, B2);
    correlationCoefficient(k) = corr2(f, f2);
    numberOfZeros(k) = length(find(mask ~= 1));
    
    disp(k);
    subplot(3, 4, k), imshow(f2), title(numberOfZeros(k));
end

figure,
subplot(2, 1, 1), plot(numberOfZeros, correlationCoefficient),
ylabel('Коэффициент корреляции Пирсона'), 
xlabel('Количество задействованных коэффициентов');
subplot(2, 1, 2), plot(compressionRatio, correlationCoefficient)
ylabel('Коэффициент корреляции Пирсона'), 
xlabel('Степень сжатия');