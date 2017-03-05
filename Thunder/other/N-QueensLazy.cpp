//
// Created by juan on 24/02/17.
//

#include <iostream>

using namespace std;

int N;
int r[300];

void printArray(){
    cout << N << endl;
    for(int i = 0; i < N; i++)
        cout << r[i] << " ";
    cout << endl;
}

void solve(int n){
    if (n % 2 == 0 && n % 6 != 2){
        for(int i = 1; i<=n/2; i++){
            r[(i*2)-1] = i-1;
            r[(i*2)-2] = n/2 + i - 1;
        }
    }
    else if (n % 2 == 0 && n % 6 != 0){
        for(int i = 1; i<=n/2; i++){
            r[ ( 2*(i-1) + n/2 - 1 ) % n ] = i-1;
            r[ n - 1 - (2*(i-1) + n/2 - 1) % n ] = n - i;
        }
    }
    else if (n % 2 == 1){
        solve(n-1);
        r[n-1] = n-1;
    }
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    cin >> T;
    for(int c = 0; c < T; c++) {
        cin >> N;
        solve(N);
        printArray();
    }
}
