#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

typedef struct {
    int first;
    int second;
    int length;
} connection;

bool operator<(const connection &lhs, const connection &rhs) {
    return lhs.length < rhs.length;
}

int n;
int m;
vector<connection> connections;

void printAnswer(int maxLength, int count, string &answer) {
    cout << maxLength << endl << count << endl << answer;
}

void getAnswer() {
    vector<int> apexes;
    int i = 0;
    int countOfTheSame = 1;
    int maxLength = 0;
    string answer;

    for (int j = 0; j < n; j++) {
        apexes.push_back(j);
    }

    while (countOfTheSame != n) {
        connection connection = connections[i];
        maxLength = connection.length;

        if (apexes[connection.first - 1] != apexes[connection.second - 1]) {
            int newSet = apexes[connection.first - 1];
            int oldSet = apexes[connection.second - 1];

            for (int j = 0; j < n; j++) {
                if (apexes[j] == oldSet) {
                    apexes[j] = newSet;
                }
            }

            countOfTheSame++;
        }

        answer += to_string(connection.first) + " " + to_string(connection.second) + "\n";

        i++;
    }

    printAnswer(maxLength, i, answer);
}

int main() {
    cin >> n >> m;

    for (int i = 0; i < m; i++) {
        connection connection{};
        cin >> connection.first >> connection.second >> connection.length;

        connections.push_back(connection);
    }

    sort(connections.begin(), connections.end());
    getAnswer();

    return 0;
}