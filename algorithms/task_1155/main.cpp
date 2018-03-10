#include <iostream>

using namespace std;

string giveAnswer(int a, int b, int c, int d, int e, int f, int g, int h) {
    string answer;

    if (a + c + h + f == g + e + b + d) {
        answer = "";

        while (a != 0 || b != 0 || c != 0 || d != 0 || e != 0 || f != 0 || g != 0 || h != 0) {
            if (a != 0 && e != 0) {
                answer += "AE-\n";
                a--;
                e--;
                continue;
            }
            if (a != 0 && d != 0) {
                answer += "AD-\n";
                a--;
                d--;
                continue;
            }
            if (a != 0 && b != 0) {
                answer += "AB-\n";
                a--;
                b--;
                continue;
            }
            if (e != 0 && h != 0) {
                answer += "EH-\n";
                e--;
                h--;
                continue;
            }
            if (e != 0 && f != 0) {
                answer += "EF-\n";
                e--;
                f--;
                continue;
            }
            if (d != 0 && h != 0) {
                answer += "DH-\n";
                d--;
                h--;
                continue;
            }
            if (c != 0 && d != 0) {
                answer += "CD-\n";
                c--;
                d--;
                continue;
            }
            if (b != 0 && f != 0) {
                answer += "BF-\n";
                b--;
                f--;
                continue;
            }
            if (b != 0 && c != 0) {
                answer += "BC-\n";
                c--;
                b--;
                continue;
            }
            if (f != 0 && g != 0) {
                answer += "FG-\n";
                f--;
                g--;
                continue;
            }
            if (g != 0 && h != 0) {
                answer += "GH-\n";
                g--;
                h--;
                continue;
            }
            if (c != 0 && g != 0) {
                answer += "CG-\n";
                c--;
                g--;
                continue;
            }
            if (a != 0 && g != 0) {
                answer += "BF+\nAB-\nFG-\n";
                a--;
                g--;
                continue;
            }
            if (e != 0 && c != 0) {
                answer += "DH+\nCD-\nEH-\n";
                e--;
                c--;
                continue;
            }
            if (b != 0 && h != 0) {
                answer += "EF+\nBF-\nEH-\n";
                h--;
                b--;
                continue;
            }
            if (d != 0 && f != 0) {
                answer += "AC+\nEF-\nAD-\n";
                d--;
                f--;
                continue;
            }
        }
    } else {
        answer = "IMPOSSIBLE";
    }

    return answer;
}

/**
 * Строится граф связей.
 * Составляются смежные пары.
 * Составляются пары, в которых с одного конца до другого можно добраться за две вершины.
 * К этим парам прописываются пути.
 * @return
 */
int main() {
    int a, b, c, d, e, f, g, h;

    cin >> a >> b >> c >> d >> e >> f >> g >> h;
    cout << giveAnswer(a, b, c, d, e, f, g, h) << endl;
    return 0;
}