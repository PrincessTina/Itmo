#include <iostream>
#include <stack>

using namespace std;

int n;

void print(int answer) {
    switch (answer) {
        case 0 :
            cout << "Not a proof";
            break;
        case 1 :
            cout << "Cheater";
            break;
        default:
            break;
    }
}

void getAnswer(stack <int> balls, const int *array) {
    int answer = 0;
    int maxTrashBall = 0;

    for (int  i = 0; i < n; i++) {
        int newBall = array[i];

        if (newBall > maxTrashBall) {
            for (int ball = maxTrashBall + 1; ball < newBall; ball++) {
                balls.push(ball);
            }

            maxTrashBall = newBall;
        } else {
            if (newBall == balls.top()) {
                balls.pop();
            } else {
                answer = 1;
                break;
            }
        }
    }

    print(answer);
}

int main() {
    cin >> n;
    int array[n];
    stack <int> balls;

    for (int i = 0; i < n; i++) {
        cin >> array[i];
    }

    getAnswer(balls, array);

    return 0;
}