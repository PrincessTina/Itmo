#include <iostream>
#include <vector>
#include <cstring>
#include <algorithm>

using namespace std;

typedef struct radix {
    int day = 1;
} radix;

typedef struct billionaire {
    string surname;
    unsigned long long wealth;
    int townOfPresence;
} billionaire;

typedef struct town {
    string name;
    unsigned long long capital = 0;
    int recordDays = 0;
} town;

struct comparator {
    inline bool operator() (const town &town1, const town &town2) {
        return (town1.name < town2.name);
    }
} comparator;

int n; //count of billionaires
int m; //count of days
int k; //count of migrations

// if town was found by name, returns it's index; else returns -1
int findTown(vector<town> towns, const string &name) {
    for (int i = 0; i < towns.size(); i++) {
        if (towns[i].name == name) {
            return i;
        }
    }

    return -1;
}

int findBillionaire(vector<billionaire> billionaires, const string &surname) {
    for (int i = 0; i < billionaires.size(); i++) {
        if (billionaires[i].surname == surname) {
            return i;
        }
    }
}

// if town was found by name, only returns it's index; else add it and returns index of the last
int addTown(vector<town> &towns, const town town) {
    int index = findTown(towns, town.name);

    if (index != -1) {
        towns[index].capital += town.capital;
        return index;
    } else {
        towns.push_back(town);
        return towns.size() - 1;
    }
}

void findLeadingTown(vector<town> &towns, int days) {
    int indexOfLeadingTown = 0;
    unsigned long long maxCapital = towns[0].capital;
    bool notOne = false;

    for (int i = 1; i < towns.size(); i++) {
        unsigned long long capital = towns[i].capital;

        if (capital > maxCapital) {
            maxCapital = capital;
            indexOfLeadingTown = i;

            if (notOne) {
                notOne = false;
            }
        } else if (capital == maxCapital) {
            notOne = true;
        }
    }

    if (!notOne) {
        towns[indexOfLeadingTown].recordDays += days;
    }
}

void changeTownCapitals(vector<town> &towns, int currentTown, int newTown, unsigned long long wealth) {
    towns[currentTown].capital -= wealth;
    towns[newTown].capital += wealth;
}

void inputInitialStates(vector<billionaire> &billionaires, vector<town> &towns) {
    for (int i = 0; i < n; i++) {
        billionaire billionaire;
        town town;

        cin >> billionaire.surname >> town.name >> billionaire.wealth;
        town.capital = billionaire.wealth;

        if (i == 0) {
            billionaire.townOfPresence = 0;
            towns.push_back(town);
        } else {
            billionaire.townOfPresence = addTown(towns, town);
        }
        billionaires.push_back(billionaire);
    }
}

void goThroughTheJournal(vector<town> &towns, vector<billionaire> &billionaires, radix *root) {
    int day;
    string surname;
    town town;

    for (int i = 0; i < k; i++) {
        int newTown;
        int currentTown;
        int billionaire;

        cin >> day >> surname >> town.name;

        findLeadingTown(towns, day - root->day + 1);

        root->day = day + 1;

        billionaire = findBillionaire(billionaires, surname);
        newTown = addTown(towns, town);
        currentTown = billionaires[billionaire].townOfPresence;
        billionaires[billionaire].townOfPresence = newTown;

        changeTownCapitals(towns, currentTown, newTown, billionaires[billionaire].wealth);
    }
}

void supplementTheJournal(vector<town> &towns, radix *root) {
    findLeadingTown(towns, m - root->day + 1);

    root->day = m + 1;
}

void findNecessaryTowns(vector<town> towns, vector<town> &searchedTowns) {
    for (int i = 0; i < towns.size(); i++) {
        if (towns[i].recordDays != 0) {
            searchedTowns.push_back(towns[i]);
        }
    }
}

void sortTowns(vector<town> &searchedTowns) {
    for (int i = 0; i < searchedTowns.size() - 1; i++) {
        for (int j = i + 1; j < searchedTowns.size(); j++) {
            if (strcmp(searchedTowns[j].name.c_str(), searchedTowns[i].name.c_str()) < 0) {
                town timing = searchedTowns[i];
                searchedTowns[i] = searchedTowns[j];
                searchedTowns[j] = timing;
            }
        }
    }
}

void printTowns(vector<town> towns) {
    vector<town> searchedTowns;

    findNecessaryTowns(towns, searchedTowns);

    if (!searchedTowns.empty()) {
        //sortTowns(searchedTowns);
        sort(searchedTowns.begin(), searchedTowns.end(), comparator);

        for (int i = 0; i < searchedTowns.size(); i++) {
            cout << searchedTowns[i].name << " " << searchedTowns[i].recordDays << endl;
        }
    }
}

int main() {
    radix root;
    vector<billionaire> billionaires;
    vector<town> towns;

    cin >> n;
    inputInitialStates(billionaires, towns); // be ready to input n rows
    cin >> m >> k;
    goThroughTheJournal(towns, billionaires, &root); // be ready to input k rows
    supplementTheJournal(towns, &root);

    printTowns(towns);

    return 0;
}