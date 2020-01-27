I = imread('face4.pgm');
IR = Roberts(I, 0.2);
IS = Sobel(I, 0.2);
IP = Prewitt(I, 0.2);

subplot(1,4,1), imshow(I), title('Original');
subplot(1,4,2), imshow(IR), title('Roberts');
subplot(1,4,3), imshow(IS), title('Sobel');
subplot(1,4,4), imshow(IP), title('Prewitt');
