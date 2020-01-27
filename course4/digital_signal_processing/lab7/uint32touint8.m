function ret = uint32touint8(val)
    ret = repmat(val(:)', [4 1]);
    ret = bitand(ret, repmat(uint32([hex2dec('FF'); hex2dec('FF00'); hex2dec('FF0000'); hex2dec('FF000000')]), 1, numel(val)));
	ret = uint8(bitshift(ret, repmat(-[0; 8; 16; 24], 1, numel(val))));
end

