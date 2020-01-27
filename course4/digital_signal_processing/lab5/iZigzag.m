function f = iZigzag(n)
    operation = 1; %number of operation (one of four)
    eF = 0; %end flag
    k = 1; %new array's index
    i = n;
    j = n;

    array = zeros(n);

    while 1
        index = (i - 1) * n + j;
        array(k) = index;

        switch operation
            case 1
                if j > 1
                    j = j - 1;
                else
                    if i > 1
                        i = i - 1;
                    else
                        eF = 1;
                    end
                end

                operation = operation + 1;
            case 2
                if i > 1 && j < n
                    i = i - 1;
                    j = j + 1;
                else
                    operation = operation + 1;
                    k = k - 1;
                end
            case 3
                if i > 1
                    i = i - 1;
                else
                    if j > 1
                        j = j - 1;
                    else
                        eF = 1;
                    end
                end

                operation = operation + 1;
            case 4
                if j > 1 && i < n
                    j = j - 1;
                    i = i + 1;
                else
                    operation = 1;
                    k = k - 1;
                end
        end

        if eF == 1 
            break;
        end

        k = k + 1;
    end
    
    f = array;
end

