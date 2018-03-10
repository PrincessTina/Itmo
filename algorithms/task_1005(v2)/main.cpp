#include <iostream>
#include <cmath>

using namespace std;

int count(int const *array, int n, int sum) {
    int min = -1;
    int k = 0;
    int last = 0;
    int old = 0;
    int number = (int) pow(2, n) - 1;
    int variants[number];
    int indexes[number];

    for (int i = 0; i < number; i++) {
        if (i < n) {
            variants[i] = array[i];

            if (i + 1 < n) {
                indexes[i] = i + 1;
            } else {
                indexes[i] = 0;
            }

            k++;
        } else {
            variants[i] = 0;
            indexes[i] = 0;
        }
    }

    if (k == number) {
        min = variants[0];
    }

    while (k != number) {
        old = k;

        for (int i = last; i < old; i++) {
            int index = indexes[i];

            if (index != 0) {
                int digit = variants[i];
                int x = 0;

                for (int j = index; j < n; j++) {
                    x = digit + variants[j];
                    variants[k] = x;

                    if (j + 1 < n) {
                        indexes[k] = j + 1;
                    } else {
                        indexes[k] = 0;
                    }

                    k++;

                    if (min == -1 || min > abs(sum - 2 * x)) {
                        min = abs(sum - 2 * x);
                    }
                }
            }
        }

        last = old;
    }

    return min;
}

/**
 * Перебор.
 * Сначала массив заполняется исходными числами.
 * Далее он последовательно расширяется суммами чисел с исходными числами,
 * на которые указывают индексы из соответствующего массива.
 * Если индекс 0, то с этим числом больше не нужно работать.
 * В процессе составления новых сумм выбирается минимальная разность между суммарными весами кучек.
 * @return
 */
int main() {
    int number;
    int sum = 0;
    cin >> number;

    int array[number];

    for (int i = 0; i < number; i++) {
        cin >> array[i];
        sum += array[i];
    }

    cout << count(array, number, sum) << endl;
    return 0;
}