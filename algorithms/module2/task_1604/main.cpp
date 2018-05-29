#include <iostream>

using namespace std;

typedef struct sign {
    int count;
    int number;
} sign;

typedef struct heap {
    int countOfSigns;
    int countOfTypes;
} heap;

int n;

// returns array sorted in descending order
void sort(sign *array) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (array[j].count > array[i].count) {
                sign timing = array[i];
                array[i] = array[j];
                array[j] = timing;
            }
        }
    }
}

void print(const string &sequence) {
    cout << sequence;
}

void findAllForHeaps(heap *heaps, int size, const sign *array) {
    int j = 0;
    heaps[0].countOfSigns = array[0].count;
    heaps[0].countOfTypes = 1;

    for (int i = 1; i < size; i++) {
        heaps[i].countOfTypes = 0;
        heaps[i].countOfSigns = 0;
    }

    for (int i = 1; i < n; i++) {
        if (array[i].count == heaps[j].countOfSigns) {
            heaps[j].countOfTypes ++;
        } else {
            j++;

            if (j == size) {
                break;
            }

            heaps[j].countOfSigns = array[i].count;
            heaps[j].countOfTypes = 1;
        }
    }
}

void getRequiredSequence(sign *array) {
    heap heaps[2];
    string sequence;

    while (array[0].count != 0) {
        findAllForHeaps(heaps, 2, array);

        if (array[1].count == 0 || n == 1) {
            for (int i = 0; i < array[0].count; i++) {
                sequence += to_string(array[0].number) + " ";
            }
            break;
        }

        if (heaps[0].countOfTypes > 1) {
            for (int i = 0; i < heaps[0].countOfTypes; i++) {
                array[i].count --;
                sequence += to_string(array[i].number) + " ";
            }
        } else if (heaps[0].countOfTypes == 1 && heaps[1].countOfTypes == 1) {
            array[0].count --;
            array[1].count --;
            sequence += to_string(array[0].number) + " " + to_string(array[1].number) + " ";
        } else if (heaps[0].countOfTypes == 1 && heaps[1].countOfTypes > 1) {
            array[0].count --;
            array[heaps[1].countOfTypes].count --;
            sequence += to_string(array[0].number) + " " + to_string(array[heaps[1].countOfTypes].number) + " ";
        }
    }

    print(sequence);
}

int main() {
    cin >> n;
    sign array[n];

    for (int i = 0; i < n; i++) {
        cin >> array[i].count;
        array[i].number = i + 1;
    }

    sort(array);

    getRequiredSequence(array);

    return 0;
}