//
// Created by juan on 16/04/17.
//

#include <iostream>

using namespace std;

typedef long long ll;

ll GCD(ll a,ll b) {
    while (b > 0) {
        a = a % b;
        a ^= b;
        b ^= a;
        a ^= b;
    }
    return a;
}

ll LCM(ll a, ll b){
    ll t = a/GCD(a,b);
    return t*b;
}

int main() {
    cout << GCD(4,8) << endl;
    cout << LCM(4,8) << endl;
    return 0;
}