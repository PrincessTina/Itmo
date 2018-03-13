#include <iostream>
#include <cmath>
#include <iomanip>
#include <list>

using namespace std;

list <int> depth;

int findBelongingToSquare(int x, int y, int number) {
    int half = number / 2 - 1;
    x = x % number;
    y = y % number;

    if (x <= half && y <= half) {
        return 1;
    }

    if (x <= half && y > half) {
        return 2;
    }

    if (x > half && y <= half) {
        return 3;
    }

    if (x > half && y > half) {
        return 4;
    }
}

bool findBelongingToOneSquare(int x1, int y1, int x2, int y2, int number) {
    int square1 = findBelongingToSquare(x1, y1, number);
    int square2 = findBelongingToSquare(x2, y2, number);

    return square1 == square2;
}

void changeCoordinate(int numberOfSquare, int squareSize, int *i1, int *j1, int i0, int j0) {
    if (squareSize == 4) {
        squareSize *= 2;
    }

    switch (numberOfSquare) {
        case 1:
            *i1 = 0 + i0;
            *j1 = 0 + j0;
            break;
        case 2:
            *i1 = 0 + i0;
            *j1 = squareSize / 2 + j0;
            break;
        case 3:
            *i1 = squareSize / 2 + i0;
            *j1 = 0 + j0;
            break;
        case 4:
            *i1 = squareSize / 2 + i0;
            *j1 = squareSize / 2 + j0;
            break;
        default:
            break;
    }
}

void changeSquare(int number, int *squareSize, int *numberOfSquare, int i1, int j1) {
    *numberOfSquare = *numberOfSquare + 1;
    depth.pop_back();
    depth.push_back(*numberOfSquare);

    if (*squareSize == 4 && *numberOfSquare > 4) {
        *squareSize *= 2;
    }

    while (*numberOfSquare > 4 && *squareSize * 2 <= number) {
        *squareSize *= 2;
        *numberOfSquare = findBelongingToSquare(i1, j1, *squareSize);

        if (*numberOfSquare == 4) {
            *numberOfSquare = *numberOfSquare + 1;
            continue;
        } else {
            *numberOfSquare = *numberOfSquare + 1;
            //changeCoordinate(*numberOfSquare, *squareSize, i0, j0, 0, 0);
            *numberOfSquare = 1;
        }
    }
}

void doTask(int number, int x, int y) {
    int array[number][number];
    int half = number / 2;
    int k = 1;
    int i1 = 0;
    int j1 = 0;
    int x1;
    int y1;
    int i0 = 0;
    int j0 = 0;
    int squareSize = number;
    int numberOfSquare = 1;

    //initialize matrix
    for (int i = 0; i < number; i++) {
        for (int j = 0; j < number; j++) {
            if (i != x || j != y) {
                array[i][j] = -1;
            } else {
                array[i][j] = 0;
            }
        }
    }

    //insertCentralSquare
    for (int i = i1 + half - 1; i <= i1 + half; i++) {
        for (int j = j1 + half - 1; j <= j1 + half; j++) {
            if (array[i][j] == -1 && !findBelongingToOneSquare(i, j, x, y, squareSize)) {
                array[i][j] = k;
            }
        }
    }
    k++;

    if (number > 4) {
        while (true) {
            if (squareSize >= 4) {
                //выбрать координаты для квадрата
                changeCoordinate(numberOfSquare, squareSize, &i1, &j1, i0, j0);

                if (squareSize > 4) {
                    squareSize /= 2;
                    depth.push_back(numberOfSquare);
                }

                half = squareSize / 2;

                //найти координаты выколотой точки
                if (array[i1][j1] != -1) {
                    x1 = i1;
                    y1 = j1;
                } else if (array[i1][j1 + squareSize - 1] != -1) {
                    x1 = i1;
                    y1 = j1 + squareSize - 1;
                } else if (array[i1 + squareSize - 1][j1] != -1) {
                    x1 = i1 + squareSize - 1;
                    y1 = j1;
                } else if (array[i1 + squareSize - 1][j1 + squareSize - 1] != -1) {
                    x1 = i1 + squareSize - 1;
                    y1 = j1 + squareSize - 1;
                } else {
                    x1 = x;
                    y1 = y;
                }

                //insertCentralSquare
                for (int i = i1 + half - 1; i <= i1 + half; i++) {
                    for (int j = j1 + half - 1; j <= j1 + half; j++) {
                        if (array[i][j] == -1 && !findBelongingToOneSquare(i, j, x1, y1, squareSize)) {
                            array[i][j] = k;
                        }
                    }
                }
                k++;

                if (squareSize == 4) {
                    squareSize /= 2;
                }
            } else {
                //смена квадрата внутри квадрата и, если надо, координат внешнего квадрата
                squareSize *= 2;

                changeSquare(number, &squareSize, &numberOfSquare, i1, j1, &i0, &j0);

                if (numberOfSquare == 5) {
                    break;
                }
            }
        }
    }

    //write matrix
    cout << "Answer: " << endl;

    for (int i = 0; i < number; i++) {
        for (int j = 0; j < number; j++) {
            cout << setw(3) << array[i][j];
        }
        cout << endl;
    }
}

int main() {
    int n;
    int number;
    int x, y;

    cin >> n;
    cin >> x >> y;

    number = (int) pow(2, n);

    x--;
    y--;

    doTask(number, x, y);
    return 0;
}