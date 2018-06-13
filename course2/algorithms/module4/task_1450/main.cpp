#include <iostream>
#include <vector>
#include <map>

using namespace std;

typedef struct {
    int num;
    long long gain;
} apex;

map<int, vector<apex>> apexes;
vector<long long> apex_gain;
string answer = "No solution";

void printAnswer() {
    printf("%s", answer.c_str());
}

void getAnswer(int finish) {
    long long result;
    int n = apex_gain.size();
    bool isChanged = true;
    int iteration = 1;

    while (iteration < n && isChanged) {
        auto it = apexes.begin();
        isChanged = false;

        while (it != apexes.end()) {
            long long currentGain = apex_gain[it->first - 1];

            if (currentGain != -1) {
                for (apex nextApex: it->second) {
                    long long suggestedGain = currentGain + nextApex.gain;

                    if (apex_gain[nextApex.num - 1] < suggestedGain) {
                        apex_gain[nextApex.num - 1] = suggestedGain;
                        isChanged = true;
                    }
                }
            }

            it++;
        }

        iteration++;
    }

    result = apex_gain[finish - 1];

    if (result != -1) {
        answer = to_string(result);
    }

    printAnswer();
}

int main() {
    int n, m;
    int start, finish;

    scanf("%d %d", &n, &m);

    for (int i = 0; i < n; i++) {
        apex_gain.push_back(-1);
    }

    for (int i = 0; i < m; i++) {
        int mainApex;
        apex nextApex;

        scanf("%d %d %lld", &mainApex, &nextApex.num, &nextApex.gain);

        auto it = apexes.find(mainApex);

        if (it != apexes.end()) {
            it->second.push_back(nextApex);
        } else {
            vector<apex> paths;
            paths.push_back(nextApex);
            apexes[mainApex] = paths;
        }
    }

    scanf("%d %d", &start, &finish);

    apex_gain[start - 1] = 0;
    getAnswer(finish);

    return 0;
}