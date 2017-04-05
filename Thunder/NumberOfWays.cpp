//
// Created by juan on 4/04/17.
//

#include <iostream>

using namespace std;

int tmp, n;
long long sums[500000];
long long r, f, s, c;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    cin >> n;
    if(n < 3){
        cout << 0;
        return 0;
    }

    for(int i = 0; i < n; i++) {
        cin >> tmp;
        r += tmp;
        sums[i] = r;
    }

    r = 0;
    if (sums[n-1] % 3 == 0){
        f = sums[n-1] / 3;
        s = 2 * sums[n-1] / 3;
        for (int i = 0; i < n-1; i++){
            if(sums[i] == s)
                r += c;
            if(sums[i] == f)
                c++;
        }
    }
    cout << r;
}
