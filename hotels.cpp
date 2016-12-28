#include<bits/stdc++.h>

using namespace std;

int arreglo[300000];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL

    int N;
    int M;
    cin >> N >> M;
    for(int i = 0; i < N; i++)
        cin >> arreglo[i];

    long maxSoFar = 0, maxEndingHere = 0;
    int aux = 0;
    for(int i = 0; i < N; i++){
        maxEndingHere = arreglo[i] + maxEndingHere;
        if(maxEndingHere == M){
            maxSoFar = maxEndingHere;
            break;
        }
        while(maxEndingHere > M){
            maxEndingHere -= arreglo[aux];
            aux++;
        }
        maxSoFar = max(maxSoFar, maxEndingHere);
    }
    cout << maxSoFar;
}
