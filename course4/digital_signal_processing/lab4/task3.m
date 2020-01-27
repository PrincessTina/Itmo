I = imread('face3.pgm');
%disp([max(img(:)), min(img(:))]);
a = splitmerge(I, 3, @predicate);
figure, imshow(I);
figure, imshow(a);

