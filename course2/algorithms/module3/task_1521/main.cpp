#include <iostream>

using namespace std;

int n;
int k;

void print(const string &answer) {
    cout << answer;
}

void rebuildArray(int *array, int stopIndex, int size) {
    if (stopIndex >= 0 && stopIndex < size) {
        for (int i = stopIndex; i <= size - 1; i++) {
            array[i] = array[i + 1];
        }
    }

    /*for (int i = 0; i < n; i++) {
        cout << array[i] << " ";
    }
    cout << endl;*/
}

void getSequence(int *array) {
    string answer;
    int size = n;
    int stopIndex = 0;

    while (size > 0) {
        if (stopIndex + k - 1 <= size - 1) {
            stopIndex += k - 1;
        } else {
            stopIndex = (stopIndex + k - 1) % size;
        }

        answer += to_string(array[stopIndex]) + " ";
        printf("%d ", array[stopIndex]);
        size--;
        rebuildArray(array, stopIndex, size);

        if (stopIndex > size - 1) {
            stopIndex = 0;
        }
    }

    print(answer);
}

int main() {
    cin >> n >> k;
    int array[n];

    for (int i = 0; i < n; i++) {
        array[i] = i + 1;
    }

    getSequence(array);

    return 0;
}
