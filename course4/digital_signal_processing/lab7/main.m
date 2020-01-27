img = imread('Lenna.gif');
secret1 = imresize(imread('cat.gif'), [128 128]) > 20;
secret2 = 'hidden text';
secret3 = uint8([1 2 3 9 8 7 6 4 5]);

container = bitand(img, hex2dec('FE'));
hidden1 = hide_last(img, secret1);
hidden2 = hide_last(img, secret2);
hidden3 = hide_last(img, secret3);

figure
subplot(2,3,1), imshow(img), title('original')
subplot(2,3,2), imshow(container), title(['empty' char(10) num2str(corr2(img, container))])
subplot(2,3,3), imshow(hidden1), title(['filled' char(10) num2str(corr2(img, hidden1))])
subplot(2,3,4), hist(img(:), 256)
subplot(2,3,5), hist(container(:), 256)
subplot(2,3,6), hist(hidden1(:), 256)

figure
imshow(unhide_last(hidden1, 'logical', [128 128]))