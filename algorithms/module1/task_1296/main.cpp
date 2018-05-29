#include <iostream>

using namespace std;

int count(int const *array, int n) {
    int max = 0;
    int sum = 0;

    for (int i = 0; i < n; i++) {
        sum += array[i];

        if (sum < 0) {
            sum = 0;
        }

        if (sum > max) {
            max = sum;
        }
    }

    return max;
}

int main() {
    int number;
    cin >> number;

    int array[number];

    for (int i = 0; i < number; i++) {
        cin >> array[i];
    }

    cout << count(array, number) << endl;
    return 0;
}