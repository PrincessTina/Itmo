function flag = predicate(region)
    matOj = 4; 
    minMean = 0; 
    maxMean = 255;
    
	sD = std2(region);
	average = mean2(region);
    
	flag = (sD >= matOj) & (average >= minMean) & (average <= maxMean);
end

