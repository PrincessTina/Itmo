#include <iostream>
#include <map>
#include <set>
#include <vector>

using namespace std;

map<int, int> country_color;
int n;
bool isBadColored = false;

void coloring(int currentCountryNumber, map<int, set<int>> &countries) {
    auto it = countries.find(currentCountryNumber);
    auto it_neighbours = it->second.begin();
    vector<int> trashList;
    int colorOfPaint;

    if (country_color.find(it->first)->second == 0) {
        colorOfPaint = 1;
    } else {
        colorOfPaint = 0;
    }

    // раскраска
    while (it_neighbours != it->second.end()) {
        int neighbourColor = country_color.find(*it_neighbours)->second;

        if (neighbourColor == -1) {
            country_color[*it_neighbours] = colorOfPaint;
        } else {
            if (neighbourColor != colorOfPaint) {
                isBadColored = true;
            }
            trashList.push_back(*it_neighbours);
        }

        it_neighbours++;
    }

    // очистка списка соседей
    for (int i : trashList) {
        it->second.erase(i);
    }

    it_neighbours = it->second.begin();

    // повторяем для соседей
    while (it_neighbours != it->second.end()) {
        coloring(*it_neighbours, countries);
        it_neighbours++;
    }
}

void addNeighbours(map<int, set<int>> &countries) {
    auto it = countries.begin();

    while (it != countries.end()) {
        int searchedNum = it->first;

        if (searchedNum != 1) {
            auto it_deep = countries.begin();

            while (it_deep != countries.end()) {
                if (it_deep != it) {
                    if (it_deep->second.find(searchedNum) != it_deep->second.end()) {
                        it->second.insert(it_deep->first);
                    }
                }

                it_deep++;
            }
        }
        it++;
    }
}

void checkColoring(string &sequence) {
    auto it = country_color.begin();

    while (it != country_color.end()) {
        if (it->second == -1) {
            sequence = "-1";
            break;
        } else {
            sequence += to_string(it->second);
        }

        it++;
    }
}

void printAnswer(string &sequence) {
    cout << sequence;
}

void getAnswer(map<int, set<int>> &countries) {
    string sequence;
    addNeighbours(countries);

    country_color[1] = 0;
    coloring(1, countries);

    if (isBadColored) {
        sequence = "-1";
    } else {
        checkColoring(sequence);
    }

    printAnswer(sequence);
}

int main() {
    map<int, set<int>> countries;
    cin >> n;

    for (int i = 0; i < n; i++) {
        set<int> neighbours;
        int num;

        cin >> num;

        while (num != 0) {
            neighbours.insert(num);

            cin >> num;
        }
        countries[i + 1] = neighbours;
        country_color[i + 1] = -1;
    }

    getAnswer(countries);

    return 0;
}