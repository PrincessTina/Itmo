#include <iostream>
#include <cmath>

using namespace std;

typedef struct node {
    int x;
    node *left;
    node *right;
} node;

void show(node *&tree) {
    cout << "Tree: " << endl;

    if (tree != nullptr) {
        show(tree->left);
        cout << tree->x;
        show(tree->right);
    }
}

void buildTree(int n, node *&tree) {
    auto m;

    if (n == 1) {
        m = 1;
    } else {
        m = (int) ceil(log(n)/log(2));
    }

    if (tree == nullptr) {
        tree = new node;

        tree->x = n;
        tree->left = nullptr;
        tree->right = nullptr;
    }
}

int main() {
    int n, k;
    node *tree = nullptr;
    cin >> n >> k;

    //buildTree(n, tree);
    //show(tree);

    return 0;
}