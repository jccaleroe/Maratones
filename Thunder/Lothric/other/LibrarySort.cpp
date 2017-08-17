//
// Created by juan on 10/04/17.
//

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<string> v;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int n;
    string s;
    while (cin >> n){
        v.clear();
        for (int i = 0; i < n; i++){
            cin >> s;
            v.push_back(s);
        }
        sort(v.begin(), v.end());
        for (int i = 0; i < n; i++)
            cout << v[i] << endl;
    }
}