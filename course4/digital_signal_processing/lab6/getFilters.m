function f = getFilters()
    f = {
		@arithmeticFilter, 'Arithmetic'
		@geometricFilter, 'Geometric'
		@harmonicFilter, 'Harmonic'
		@contravarianceFilter, 'Contravariance'
		@medianFilter, 'Median'
		@maximumFilter, 'Maximum'
		@minimumFilter, 'Minimum'
		@middlePointFilter, 'Middle point'
		@alphaTruncatedFilter, 'Alpha-truncated'
	};
end

