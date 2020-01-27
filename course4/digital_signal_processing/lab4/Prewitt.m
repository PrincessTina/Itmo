function filtered = Prewitt(img, t)
    FX = [-1 -1 -1; 0 0 0; 1 1 1];
	FY = [-1 0 1; -1 0 1; -1 0 1];
    
	filtered = sqrt(conv2(img, FX, 'same').^2 + conv2(img, FY, 'same').^2 );
	filtered = filtered ./ max(max(filtered));
	filtered(filtered < t) = 0;
	filtered = filtered .* 255;
end
