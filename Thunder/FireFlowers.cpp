#include <complex>
#include <iostream>

using namespace std;

typedef complex<double> point;

double R1, X1, Y1, R2, X2, Y2;
double d;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

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
