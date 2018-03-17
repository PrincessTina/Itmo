#include <iostream>

using namespace std;

struct point {
    int x;
    int y;
} typedef point;

int getRequiredCoordinate(point *array, int n) {
    int index1 = 0;
    int index2 = 0;
    int x1 = array[0].x;
    int y1 = array[0].y;

    for (int j = n - 1; j > 0; j--) {
        int count = 0;
        int x2 = array[j].x;
        int y2 = array[j].y;

        for (int k = 1; k < n; k++) {
            if (k!= j) {
                int x3 = array[k].x;
                int y3 = array[k].y;

                if ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1) > 0) {
                    count++;
                }

                if (count > (n - 2) / 2) {
                    break;
                }
            }
        }

        if (count == (n - 2) / 2) {
            index2 = j;

            goto exit;
        }
    }

    exit:
    cout << index1 + 1 << " " << index2 + 1;
}

int main() {
    int number;
    cin >> number;

    point array[number];

    for (int i = 0; i < number; i++) {
        cin >> array[i].x >> array[i].y;
    }

    getRequiredCoordinate(array, number);
    return 0;
}