#include <iostream>
#include <list>

using namespace std;

struct aPoint {
    int num;
    int square;
    int x;
    int y;
} typedef aPoint;

void incIndexes(int *lastIndexes, int max, int current) {
    for (int i = current; i < max; i++) {
        lastIndexes[i]++;
    }
}

int checkInterval(int x, int y, int square) {
    if (x == square && y > -square && y <= square) {
        return 1;
    }

    if (y == square && x >= -square && x < square) {
        return 2;
    }

    if (x == -square && y >= -square && y < square) {
        return 3;
    }

    if (y == -square && x > -square && x <= square) {
        return 4;
    }
}

/**
 * returns true if a > b
 * @param a
 * @param b
 * @param square
 * @return
 */
bool compare(aPoint a, aPoint b, int square) {
    if (a.x == square && a.y == -square) {
        return true;
    }

    if (b.x == square && b.y == -square) {
        return false;
    }

    if (a.x == b.x && a.x == square) {
        return a.y > b.y;
    }

    if (a.y == b.y && a.y == square) {
        return a.x < b.x;
    }

    if (a.x == b.x && a.x == -square) {
        return a.y < b.y;
    }

    if (a.y == b.y && a.y == -square) {
        return a.x > b.x;
    }

    return checkInterval(a.x, a.y, square) > checkInterval(b.x, b.y, square);
    //return a.x == -square || a.y == -square;
}

void getPumpkinOrder(aPoint *array, int n, int max) {
    list<aPoint> aPoints;
    int lastIndexes[max];

    for (int i = 0; i < max; i++) {
        lastIndexes[i] = -1;
    }

    for (int i = 1; i < n; i++) {
        bool wasBroken = false;
        int start;
        int index;
        int end;
        aPoint a = array[i];

        incIndexes(lastIndexes, max, a.square - 1);

        if (a.square == 1) {
            start = 0;
        } else {
            start = lastIndexes[a.square - 2] + 1;
        }

        end = lastIndexes[a.square - 1];
        index = start;
        auto iter = aPoints.cbegin();

        if (aPoints.empty()) {
            aPoints.push_back(a);
        } else {
            for (aPoint b: aPoints) {
                if (index > 0) {
                    iter++;
                    index--;
                } else {
                    if (start == end) {
                        aPoints.insert(iter, a);
                        wasBroken = true;
                        break;
                    } else if (compare(b, a, a.square)) {
                        aPoints.insert(iter, a);
                        wasBroken = true;
                        break;
                    }

                    start++;
                    iter++;
                }
            }

            if (!wasBroken) {
                aPoints.push_back(a);
            }
        }

        /*cout << endl;
        for (aPoint k: aPoints) {
            cout << k.num << " ";
        }*/
    }

    cout << n << endl;
    cout << 1 << endl;
    for (aPoint k: aPoints) {
        cout << k.num << endl;
    }

    /*for (int i = 0; i < max; i++) {
        aPoint current[n];

        for (int j = 1; j < n; j++) {

        }
    }*/
}

int main() {
    int number;
    int max = 0;
    int a, b;

    cin >> number;
    cin >> a >> b;

    aPoint array[number];

    for (int i = 0; i < number; i++) {
        if (i == 0) {
            array[0].x = 0;
            array[0].y = 0;
        } else {
            cin >> array[i].x >> array[i].y;
            array[i].x -= a;
            array[i].y -= b;
            array[i].num = i + 1;

            if (abs(array[i].x) > abs(array[i].y)) {
                array[i].square = abs(array[i].x);
            } else {
                array[i].square = abs(array[i].y);
            }

            if (array[i].square > max) {
                max = array[i].square;
            }
        }
    }

    getPumpkinOrder(array, number, max);
    return 0;
}