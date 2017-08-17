#include <iostream>

using namespace std;

int main(){
    long long r, g, b;
    cin >> r >> g >> b;
    long long aux = min(r, min(g, b));
    long long c, d;
    if (r - aux == 0){
        c = max(g, b);
        d = min(g, b);
    }
    else if (g - aux == 0){
        c = max(r, b);
        d = min(r, b);
    }
    else {
        c = max(r, g);
        d = min(r, g);
    }
    d += aux;
    long long e = max(c, d);
    long long f = min(c, d);
    long long ans = min (e - f, f);
    e -= ans*2;
    f -= ans;
    if (e == f)
        ans += (e+f) / 3;
    cout << ans;
}