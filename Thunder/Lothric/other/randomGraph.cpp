//
// Created by juan on 4/09/17.
//
#include <iostream>
#include <iomanip>
#include <random>
#include <set>

using namespace std;

int n = 200, m = 500, g = 10;

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

    freopen("/home/juan/Documents/Maratones/Bending/smallgrouped.json", "w", stdout);

    cout << "{\"nodes\":[\n";
    for (int i = 0; i < n; i++)
        cout << R"({"name":")" << i << R"(", "width":20, "height":20})" << (i != n - 1 ? ",\n" : "\n");
    cout << "],\n\"links\":[\n";
    for (auto &i : s)
        cout << "{\"source\":" << i.first << ",\"target\":" << i.second << "},\n";
    tmp = *s.begin();
    cout << "{\"source\":" << tmp.first << ",\"target\":" << tmp.second << "}\n";
    cout << R"(],"groups":[ {"leaves":[0]},)" << endl;

    set<int> s2;
    int aux, l = n/g;
    for (int i = 0; i < g; i++) {
        cout << R"({"leaves":[)";
        for (int j = 0; j < l; j++) {
            do {
                aux = uniform_dist(e1);
            }while (s2.find(aux) != s2.end());
            s2.insert(aux);
            cout << aux << (j != l - 1 ? "," : "");
        }
        cout << "]}" << (i != g - 1 ? ",\n" : "\n");
    }
    cout << "]}";
}