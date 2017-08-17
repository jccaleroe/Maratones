#include <complex>
#include <iostream>
using namespace std;

typedef long long ll;
typedef complex<double> point;

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
    int R1, X1, Y1, R2, X2, Y2;
    double d;
    while(cin >> R1 >> X1 >> Y1 >> R2 >> X2 >> Y2){
        point a(X1, Y1);
        point b(X2, Y2);

        d = abs(a - b);
        if (R1 >= d+R2)
            cout << "RICO\n";
        else
            cout << "MORTO\n";
    }
}