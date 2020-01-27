function [uniform, gaussian, logNormal, relay, exponential, erlang] = getNoises(m, n)
    uniform = uniformNoise(m, n);
    gaussian = gaussianNoise(m, n);
    logNormal = logNormalNoise(m, n);
    relay = relayNoise(m, n);
    exponential = exponentialNoise(m, n);
    erlang = erlangNoise(m, n);
end

