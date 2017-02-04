#include<bits/stdc++.h>

using namespace std;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T, N, a, d;
    cin >> T;
    while ( T > 0 ){
        T--;
        cin >> N;
        int i;
        for ( i = 1; i <= N; i +=1 )
            if ( i * (i + 1) / 2 >= N )
                break;

        cout << "TERM " << N << " IS ";
        a = i * (i + 1) / 2;
        d = a - N;
        if ( i % 2 == 0 )
            cout << i - d << '/' << 1 + d << endl;
        else
            cout << 1 + d << '/' << i - d << endl;
    }
}
