f = imread('C:/Users/krist/Documents/repo/itmo/course4/digital_signal_processing/lab4/face1.pgm');

seeds = zeros(size(f));
seedx = [175 135 190 216];
seedy = [30 102 50 102];
seeds(sub2ind(size(f), seedy, seedx)) = 1;

[g, NR, SI] = regiongrow(f, seeds, 40);
masked = f;
masked(g == 0) = 0;

figure, imshow(f);
figure, imshow(g);
figure, imshow(masked);
figure, imshow(SI);