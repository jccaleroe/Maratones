//
// Created by juan on 5/04/17.
//

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

typedef pair<int, int> pi;
vector<pi> v;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int n, a, b, r = 1;
    cin >> n;
    for (int i = 0; i < n; i++){
        cin >> a >> b;
        v.push_back(make_pair(a, b));
    }
    sort(v.begin(), v.end());

    for (int i = 0; i < n; i++){
        a = v[i].first;
        b = v[i].second;
        if (b >= r)
            r = b;
        else
            r = a;
    }
    cout << r;
}

