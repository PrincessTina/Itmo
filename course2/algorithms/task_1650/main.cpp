#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

typedef struct billionaire {
    string surname;
    unsigned long long wealth;
    int townOfPresence;
} billionaire;

typedef struct town {
    string name;
    unsigned long long capital = 0;
    unsigned int recordDays = 0;
} town;

struct nameTownComparator {
    inline bool operator()(const town &town1, const town &town2) {
        return (town1.name < town2.name);
    }
} nameTownComparator;

unsigned int n; //count of billionaires
unsigned int m; //count of days
unsigned int k; //count of migrations
unsigned int root = 1;
vector<town> towns;
vector<billionaire> billionaires;

// if town was found by name, returns it's index; else returns -1
int findTown(const string &name) {
    for (int i = 0; i < towns.size(); i++) {
        if (towns[i].name == name) {
            return i;
        }
    }

    return -1;
}

int findBillionaire(const string &surname) {
    for (int i = 0; i < billionaires.size(); i++) {
        if (billionaires[i].surname == surname) {
            return i;
        }
    }
}

// if town was found by name, only returns it's index; else add it and returns index of the last
int addTown(const town town) {
    int index = findTown(town.name);

    if (index != -1) {
        towns[index].capital += town.capital;
        return index;
    } else {
        towns.push_back(town);
        return towns.size() - 1;
    }
}

void findLeadingTown(unsigned int days) {
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

void changeTownCapitals(int currentTown, int newTown, unsigned long long wealth) {
    towns[currentTown].capital -= wealth;
    towns[newTown].capital += wealth;
}

void inputInitialStates() {
    for (int i = 0; i < n; i++) {
        billionaire billionaire;
        town town;

        cin >> billionaire.surname >> town.name >> billionaire.wealth;
        town.capital = billionaire.wealth;

        if (i == 0) {
            billionaire.townOfPresence = 0;
            towns.push_back(town);
        } else {
            billionaire.townOfPresence = addTown(town);
        }
        billionaires.push_back(billionaire);
    }
}

void goThroughTheJournal() {
    unsigned int day;
    string surname;
    town town;

    for (int i = 0; i < k; i++) {
        int newTown;
        int currentTown;
        int billionaire;

        cin >> day >> surname >> town.name;

        if (day >= root) {
            findLeadingTown(day - root + 1);

            root = day + 1;
        }

        billionaire = findBillionaire(surname);
        newTown = addTown(town);
        currentTown = billionaires[billionaire].townOfPresence;
        billionaires[billionaire].townOfPresence = newTown;

        changeTownCapitals(currentTown, newTown, billionaires[billionaire].wealth);
    }
}

void supplementTheJournal() {
    findLeadingTown(m - root + 1);

    root = m + 1;
}

void findNecessaryTowns(vector<town> &searchedTowns) {
    for (int i = 0; i < towns.size(); i++) {
        if (towns[i].recordDays != 0) {
            searchedTowns.push_back(towns[i]);
        }
    }
}

void printTowns() {
    vector<town> searchedTowns;

    findNecessaryTowns(searchedTowns);

    if (!searchedTowns.empty()) {
        sort(searchedTowns.begin(), searchedTowns.end(), nameTownComparator);

        for (int i = 0; i < searchedTowns.size(); i++) {
            cout << searchedTowns[i].name << " " << searchedTowns[i].recordDays << endl;
        }
    }
}

int main() {
    cin >> n;
    inputInitialStates(); // be ready to input n rows
    cin >> m >> k;
    goThroughTheJournal(); // be ready to input k rows
    supplementTheJournal();

    printTowns();

    return 0;
}