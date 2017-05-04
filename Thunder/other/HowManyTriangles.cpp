//
// Created by juan on 1/05/17.
//

#include <iostream>
#define L 10000

using namespace std;

int x[L];
int y[L];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T, N, aux;
    double d;
    long long ans;
    cin >> T;
    for (int c = 1; c <= T; c++){
        cin >> N;
        ans = 0;
        for (int i = 0; i < N; i++)
            cin >> x[i] >> y[i];

        for (int i = 0; i < N; i++)
            for (int j = i+1; j < N; j++)
                for (int h = j+1; h < N; h++)
                    if ((x[i]*y[j] - y[i]*x[j] + x[j]*y[h] - y[j]*x[h] + x[h]*y[i] - y[h]*x[i])%2 == 0)
                        ans++;
        cout << "Scenario #" << c << ":\n" << ans;
        if (c != T)
            cout << "\n\n";
    }
}