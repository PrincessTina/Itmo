image = imread('panda.pgm');
figure, imshow(image);
image = im2double(image);

[uniform, gaussian, logNormal, relay, exponential, erlang] = getNoises(256, 256);

uniformNoised = image + uniform;
gaussianNoised = image + gaussian;
logNormalNoised = image + logNormal;
relayNoised = image + relay;
exponentialNoised = image + exponential;
erlangNoised = image + erlang;

noisedImages = {uniformNoised; gaussianNoised; logNormalNoised; 
    relayNoised; exponentialNoised; erlangNoised};
filters = getFilters();

filtersCount = length(filters);
imagesCount = length(noisedImages);
correlationCoefficients = zeros(filtersCount, imagesCount);

for j = 1:imagesCount
    figure
    for i = 1:filtersCount
        finalImage = nlfilter(noisedImages{j}, [3 3], filters{i});
        correlationCoefficients(i, j) = corr2(image, finalImage);
        subplot(3,3,i), imshow(finalImage), title(filters{i, 2});
    end
end