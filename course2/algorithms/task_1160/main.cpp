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

vector<connection> connections;
int n;

void getAnswer() {

}

int main() {
    int m;

    cin >> n >> m;

    for (int i = 0; i < m; i++) {
        connection connection;
        cin >> connection.first >> connection.second >> connection.length;

        connections.push_back(connection);
    }

    sort(connections.begin(), connections.end());
    getAnswer();

    return 0;
}