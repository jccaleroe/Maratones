 //
// Created by juan on 19/04/17.
//

#include <iostream>
#include <set>
#include <vector>

using namespace std;

int N, K, pi, tmp;
vector<int> v;
set<int> e, g, l;

void insertLess(int a){
    for (int j = a; j < pi+a; j++) {
        tmp = v[j];
        if (g.count(tmp) != 0){
            g.erase(tmp);
            e.insert(tmp);
        } else if (e.count(tmp) == 0)
            l.insert(tmp);
    }
}

void insertGreater(int a){
    for (int j = a; j < pi+a; j++) {
        tmp = v[j];
        if (l.count(tmp) != 0){
            l.erase(tmp);
            e.insert(tmp);
        } else if (e.count(tmp) == 0)
            g.insert(tmp);
    }
}

void insertEqual(){
    for (int j = 0; j < pi*2; j++) {
        tmp = v[j];
        if (l.count(tmp) != 0)
            l.erase(tmp);
        else if (g.count(tmp) != 0)
            g.erase(tmp);
        e.insert(tmp);
    }
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    cin >> N >> K;
    char d;
    for (int i = 0; i < K; i++){
        cin >> pi;
        v.clear();
        for (int j = 0; j < pi*2; j++){
            cin >> tmp;
            v.push_back(tmp);
        }
        cin >> d;
        if (d == '>') {
            insertGreater(0);
            insertLess(pi);
        }
        else if (d == '<') {
            insertLess(0);
            insertGreater(pi);
        }
        else {
            insertEqual();
        }
    }
    if (g.size() == 1 && l.size() == 0)
        cout << *g.begin() << endl;
    else if (l.size() == 1 && g.size() == 0)
        cout << *l.begin() << endl;
    else
        cout << 0 << endl;
}