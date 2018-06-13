#include <iostream>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

int countOfBands = 0;

void incrementCountOfBands(int distance, map<int, vector<int>> :: iterator it, int index, map<int, vector<int>> &array,
                           bool key, int range) {
    if (distance > 1) {
        countOfBands++;
    } else if (distance == 1 && key) {
        if ((it == array.begin() && it->first != 0) || (next(it) == array.end() && it->first != range)) {
            return;
        }

        if (it != array.begin()) {
            int current = it->first;
            it--;

            if (it->first != current - 1) {
                return;
            } else {
                auto it_deep = lower_bound(it->second.begin(), it->second.end(), index);

                if (it->second[it_deep - it->second.begin()] != index) {
                    return;
                }
            }

            it++;
        }

        if (next(it) != array.end()--) {
            int current = it->first;
            it++;

            if (it->first != current + 1) {
                return;
            } else {
                auto it_deep = lower_bound(it->second.begin(), it->second.end(), index);

                if (it->second[it_deep - it->second.begin()] != index) {
                    return;
                }
            }
        }

        countOfBands++;
    }
}

void findCountOfBands(map<int, vector<int>> array, int range, int maxExpectedKey, bool key) {
    int expectedKey = 0;
    auto it = array.begin();

    while (it != array.end()) {
        vector<int> collection = it->second;

        // solve problem with several empty elements before or between
        if (it->first != expectedKey) {
            countOfBands += it->first - expectedKey;
            expectedKey = it->first;
        }

        for (int i = 0; i < collection.size(); i++) {
            int distance;

            if (i + 1 < collection.size()) {
                distance = collection[i + 1] - collection[i] - 1;
                incrementCountOfBands(distance, it, collection[i] + 1, array, key, maxExpectedKey);
            }

            if (i == 0) {
                distance = collection[i];
                incrementCountOfBands(distance, it, collection[i] - 1, array, key, maxExpectedKey);
            }

            if (i == collection.size() - 1) {
                distance = range - collection[i];
                incrementCountOfBands(distance, it, collection[i] + 1, array, key, maxExpectedKey);
            }
        }

        it++;
        expectedKey++;
    }

    // solve problem with several empty elements after all
    if (expectedKey <= maxExpectedKey) {
        countOfBands += maxExpectedKey - expectedKey + 1;
    }
}

void printAnswer() {
    printf("%d", countOfBands);
}

void transformIntoColumns(map<int, vector<int>> rows, map<int, vector<int>> &columns) {
    auto it_rows = rows.begin();

    while (it_rows != rows.end()) {
        vector<int> collection = it_rows->second;

        for (int column : collection) {
            auto it = columns.find(column);

            if (it == columns.end()) {
                vector<int> array;

                array.push_back(it_rows->first);
                columns[column] = array;
            } else {
                columns[column].push_back(it_rows->first);
            }
        }

        it_rows++;
    }
}

void sort(map<int, vector<int>> &array) {
    auto it = array.begin();

    while (it != array.end()) {
        sort(it->second.begin(), it->second.end());
        it++;
    }
}

void getAnswer(map<int, vector<int>> rows, int rowRange, int columnRange) {
    map<int, vector<int>> columns;

    if (columnRange != 0) {
        sort(rows);
        findCountOfBands(rows, columnRange, rowRange, true);
    }

    if (rowRange != 0) {
        transformIntoColumns(rows, columns);
        findCountOfBands(columns, rowRange, columnRange, columnRange == 0);
    }

    if (columnRange == 0 && rowRange == 0 && rows.empty()) {
        countOfBands = 1;
    }

    printAnswer();
}

int main() {
    int n;
    int rowRange;
    int columnRange;
    map<int, vector<int>> rows;

    scanf("%d %d %d", &rowRange, &columnRange, &n);
    rowRange--;
    columnRange--;

    for (int i = 0; i < n; i++) {
        int x;
        int y;

        scanf("%d %d", &x, &y);
        x--;
        y--;

        auto it = rows.find(x);

        if (it == rows.end()) {
            vector<int> array;

            array.push_back(y);
            rows[x] = array;
        } else {
            rows[x].push_back(y);
        }
    }

    getAnswer(rows, rowRange, columnRange);

    return 0;
}