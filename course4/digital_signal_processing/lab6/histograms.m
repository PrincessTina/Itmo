m = 256;
n = m;

[uniform, gaussian, logNormal, relay, exponential, erlang] = getNoises(m, n);

subplot(3,2,1), hist(uniform, 50), title('Uniform noise');
subplot(3,2,2), hist(gaussian, 50), title('Gaussian noise');
subplot(3,2,3), hist(logNormal, 50), title('Logarithmically normal noise');
subplot(3,2,4), hist(relay, 50), title('Relay noise');
subplot(3,2,5), hist(exponential, 50), title('Exponential noise');
subplot(3,2,6), hist(erlang, 50), title('Erlang noise');