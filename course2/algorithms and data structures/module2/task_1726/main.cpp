#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

long long n;

void print(long long average) {
    cout << average;
}

void getAverage(vector<int> x, vector<int> y) {
    long long average = 0;

    for (int i = 0; i < n - 1; i++) {
        average += ((long long) (x[i + 1] - x[i] + y[i + 1] - y[i]) * 2 * (i + 1) * (n - i - 1));
    }

    average /= n * (n - 1);

    print(average);
}

int main() {
    cin >> n;
    vector<int> x;
    vector<int> y;

    for (int i = 0; i < n; i++) {
        int xMean;
        int yMean;

        cin >> xMean >> yMean;

        x.push_back(xMean);
        y.push_back(yMean);
    }

    sort(x.begin(), x.end());
    sort(y.begin(), y.end());

    getAverage(x, y);

    return 0;
}