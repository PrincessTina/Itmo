function hidden = hide_last(img, data)
    container = bitand(img, hex2dec('FE'));
    
	if (isa(data, 'logical'))
        if (numel(data) + 4 > numel(img))
            error('data can not fit into image');
        end
        
		data = reshape(data, [], 1);
		l = uint8tological(uint32touint8(uint32(numel(data))));
        l = l(:);
		data = reshape(resize([l; data], [numel(container) 1]), size(container));
		hidden = container + data;
    else
        if (isa(data, 'char'))
            data = uint8(data);
        end
        if (numel(data)*8 > numel(img))
            error('data can not fit into image');
        end
        
		l = uint8tological(uint32touint8(uint32(numel(data))));
        l = l(:);
		secret = uint8tological(data);
		secret = reshape(resize([l; secret], [numel(container) 1]), size(container));
		hidden = container + secret;
	end
end

