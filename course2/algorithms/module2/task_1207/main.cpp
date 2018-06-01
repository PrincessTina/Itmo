#include <iostream>

using namespace std;

typedef struct point {
    int num;
    int x;
    int y;
    double tg;
} point;

int n;

int findLeft(const point *array) {
    point required = array[0];

    for (int i = 1; i < n; i++) {
        if (array[i].x < required.x) {
            required = array[i];
        }
    }

    return required.num;
}

void setTg(int index1, point *array) {
    array[index1].tg = 0;

    for (int i = 0; i < n; i++) {
        if (i != index1) {
            if (array[i].x == array[index1].x) {
                if (array[i].y > array[index1].y) {
                    array[i].tg = INT_MAX;
                } else {
                    array[i].tg = INT_MIN;
                }
            } else {
                array[i].tg = (double) (array[i].y - array[index1].y) / (array[i].x - array[index1].x);
            }
        }
    }
}

void sort(point *array) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = i; j < n; j++) {
            if (array[j].tg < array[i].tg) {
                point timing = array[j];

                array[j] = array[i];
                array[i] = timing;
            }
        }
    }
}

int findSecond(int index1, const point *array) {
    int half = n / 2;
    int count = 0;
    int index2 = 0;

    for (int i = 0; i < n; i++) {
        if (array[i].num != index1) {
            count ++;
        }

        if (count == half) {
            index2 = array[i].num;
            break;
        }
    }

    return index2;
}

void print(int index1, int index2) {
    cout << index1 + 1 << " " << index2 + 1;
}

void getRequiredCoordinate(point *array) {
    int index1 = findLeft(array);
    int index2;

    setTg(index1, array);
    sort(array);
    index2 = findSecond(index1, array);

    print(index1, index2);
}

int main() {
    cin >> n;
    point array[n];

    for (int i = 0; i < n; i++) {
        cin >> array[i].x;
        cin >> array[i].y;
        array[i].num = i;
    }

    getRequiredCoordinate(array);
    return 0;
}