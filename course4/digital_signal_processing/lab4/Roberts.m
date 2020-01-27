function filtered = Roberts(img, t)
    FX = [0 0 0; 0 -1 0; 0 0 1];
	FY = [0 0 0; 0 0 -1; 0 1 0];
    
	filtered = sqrt(conv2(img, FX, 'same').^2 + conv2(img, FY, 'same').^2 );
	filtered = filtered ./ max(max(filtered));
	filtered(filtered < t) = 0;
	filtered = filtered .* 255;
end

