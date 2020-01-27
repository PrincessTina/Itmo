function ret = uint8tological(val)
    ret = repmat(val(:)', [8 1]);
	ret = bitand(ret, repmat(uint8([hex2dec('1'); hex2dec('2'); hex2dec('4'); hex2dec('8'); hex2dec('10'); hex2dec('20'); hex2dec('40'); hex2dec('80')]), ...
			[1 numel(val)]));
	ret = (ret ~= 0);
end

