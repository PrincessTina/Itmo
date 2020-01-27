image = imread('panda.pgm');
image = im2double(image);

[uniform, gaussian, logNormal, relay, exponential, erlang] = getNoises(256, 256);

subplot(2,3,1), imshow(image + uniform), title('Uniform');
subplot(2,3,2), imshow(image + gaussian), title('Gaussian');
subplot(2,3,3), imshow(image + logNormal), title('Logarithmically normal');
subplot(2,3,4), imshow(image + relay), title('Relay');
subplot(2,3,5), imshow(image + exponential), title('Exponential');
subplot(2,3,6), imshow(image + erlang), title('Erlang');