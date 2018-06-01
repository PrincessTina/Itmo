#include <iostream>
#include <regex>

using namespace std;

typedef struct node {
    string name;
    vector <node> children;
} node;

int n;

node * findPlace(node *link, const string &name) {
    for (int i = 0; i < link->children.size(); i++) {
        if (link->children[i].name == name) {
            return &link->children[i];
        }
    }

    node directory;
    directory.name = name;
    link->children.push_back(directory);

    return &link->children.back();
}

void buildTree(string *array, node *root) {
    regex regex("[^\\\\]+");

    for (int i = 0; i < n; i++) {
        node *link = root;

        for (sregex_token_iterator it(begin(array[i]), end(array[i]), regex), last; it != last; ++it) {
            string name = it->str();
            link = findPlace(link, name);
        }
    }
}

void sortTree(node *root) {
    node *link = root;

    if (!link->children.empty()) {
        for (int i = 0; i < link->children.size(); i++) {
            for (int j = i + 1; j < link->children.size(); j++) {
                if (strcmp(link->children[j].name.c_str(), link->children[i].name.c_str()) < 0) {
                    node timing = link->children[i];

                    link->children[i] = link->children[j];
                    link->children[j] = timing;
                }
            }

            node *next = &link->children[i];
            sortTree(next);
        }
    }
}

string getSpaces(int depth) {
    string spaces;

    for (int i = 1; i <= depth; i++) {
        spaces += " ";
    }

    return spaces;
}

void printTree(node *root, int depth) {
    node *link = root;

    for (int i = 0; i < link->children.size(); i++) {
        cout << getSpaces(depth) << link->children[i].name << endl;

        node *next = &link->children[i];
        depth++;
        printTree(next, depth);
        depth--;
    }
}

void getAnswer(string *array) {
    node root;
    root.name = "ROOT";

    buildTree(array, &root);
    sortTree(&root);
    printTree(&root, 0);
}

int main() {
    n = 7;
    //cin >> n;
    string array[n] {"WINNT\\SYSTEM32\\CONFIG", "GAMES", "WINNT\\DRIVERS", "HOME", "WIN\\SOFT", "GAMES\\DRIVERS",
                     "WINNT\\SYSTEM32\\CERTSRV\\CERTCO~1\\X86"};

    //for (int i = 0; i < n; i++) {
    //    cin >> array[i];
    //}

    getAnswer(array);

    return 0;
}