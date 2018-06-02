#include <iostream>
#include <algorithm>

using namespace std;

long long n;

void qsort(long long *array, const long long rightRange) {
    long long middle = array[rightRange / 2];
    long long l = 0;
    long long r = rightRange;

    while (l < r) {
        while (array[l] < middle) {
            l++;
        }

        while (array[r] > middle) {
            r--;
        }

        //change
        if (l <= r) {
            long long timing = array[l];
            array[l] = array[r];
            array[r] = timing;

            l++;
            r--;
        }
    }

    if (r > 0) {
        qsort(array, r);
    }

    if (l < rightRange) {
        qsort(&array[l], rightRange - l);
    }
}

void print(long long average) {
    cout << average;
}

void getAverage(const long long *x, const long long *y) {
    long long average = 0;

    for (long long i = 0; i < n - 1; i++) {
        average += (x[i + 1] - x[i] + y[i + 1] - y[i]) * 2 * (i + 1) * (n - i - 1);
    }

    average /= n * (n - 1);

    print(average);
}

int main() {
    cin >> n;
    long long x[n];
    long long y[n];

    for (long long i = 0; i < n; i++) {
        cin >> x[i] >> y[i];
    }

    qsort(x, n - 1);
    qsort(y, n - 1);

    getAverage(x, y);

    return 0;
}