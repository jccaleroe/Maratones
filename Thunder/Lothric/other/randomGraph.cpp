//
// Created by juan on 4/09/17.
//
#include <iostream>
#include <iomanip>
#include <random>
#include <set>

using namespace std;

int n = 100, m = 500, g = 10;

int main() {
    random_device r;
    default_random_engine e1(r());
    uniform_int_distribution<int> uniform_dist(0, n - 1);
    set<pair<int, int>> s;
    pair<int, int> tmp;
    int a, b;
    for (int i = 0; i < m; i++) {
        do {
            a = uniform_dist(e1);
            b = uniform_dist(e1);
            tmp = make_pair(a, b);
        } while (s.find(tmp) != s.end());
        s.insert(tmp);
    }
    int aux;
    uniform_int_distribution<int> uniform_dist2(1, g);

    freopen("/home/juan/Documents/Maratones/Bending/miserable.json", "w", stdout);

    cout << "{\"nodes\":[\n";
    for (int i = 0; i < n; i++) {
        aux = i/g;
        cout << R"({"id":")" << i << R"(", "group":)" << aux << (i != n - 1 ? "},\n" : "}\n");
    }

    cout << "],\n\"links\":[\n";
    for (auto &i : s)
        cout << R"({"source":")" << i.first << R"(","target":")" << i.second << R"(","value":)" << 1 << "},\n";
    tmp = *s.begin();
    cout << R"({"source":")" << tmp.first << R"(","target":")" << tmp.second << R"(","value":)" << 1 << "}\n";

    cout << "]}";
}