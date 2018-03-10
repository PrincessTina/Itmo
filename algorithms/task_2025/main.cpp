#include <iostream>
#include <ctime>

using namespace std;

int count(int const *team, int k) {
    int sum = 0;
    int count = 0;

    for (int i = 0; i < k - 1; i++) {
        for (int j = i + 1; j < k; j++) {
            sum += team[j];
        }
        count += team[i] * sum;

        sum = 0;
    }

    return count;
}

void createTeams(int *team, int n, int k) {
    int manCount = n / k;
    int rest = n % k;

    for (int i = 0; i < k; i++) {
        team[i] = manCount;

        if (rest != 0) {
            team[i]++;
            rest--;
        }
    }
}

int test (int n, int k) {
    int team[k];

    createTeams(team, n, k);

    return count(team, k);
}

int main() {
    int number;
    int n;
    int k;
    //unsigned int start_time = clock(); // начальное время

    cin >> number;

    int variants[number];

    for (int i = 0; i < number; i++) {
        cin >> n >> k;

        variants[i] = test(n, k);
    }

    for (int i = 0; i < number; i++) {
        cout << variants[i] << endl;
    }

    //unsigned int end_time = clock(); // конечное время
    //cout << "Time is " << (end_time - start_time) / 1000.0 << endl;
    return 0;
}

