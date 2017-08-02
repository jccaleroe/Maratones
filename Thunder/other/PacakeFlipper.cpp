//
// Created by juan on 8/04/17.
//

#include <iostream>

using namespace std;

int k, n, ans, ones;
int permut[1000];
bool happy[1000];
string s;

void swap(int i, int j) {
    int temp;
    temp = permut[i];
    permut[i] = permut[j];
    permut[j] = temp;

}

void flip(int p){
    for(int i = p; i < k+p; i++){
        if (happy[i]){
            ones--;
            happy[i] = 0;
        }
        else{
            ones++;
            happy[i] = 1;
        }
    }
}

void permute (int l, int r) {
    if (l > r) {
        return;
    }

    for (int i = l; i <= r; i++) {
        swap(l, i);
        flip(permut[l]);
        if (ones == n){
            if (l < ans)
                ans = l;
            flip(permut[l]);
            swap(l, i); //backtrack
            return;
        }
        permute(l+1, r);
        flip(permut[l]);
        swap(l, i); //backtrack
    }
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    cin >> T;
    for (int c = 1; c <= T; c++){
        cin >> s;
        cin >> k;
        n = s.size();
        ans = 2000;
        ones = 0;
        for(int i = 0; i < n; i++){
            permut[i] = i;
            if (s[i] == '+'){
                happy[i] = 1;
                ones++;
            }
            else
                happy[i] = 0;
        }
        if (ones == n) {
            cout << "Case #" << c << ": " << 0 << endl;
            continue;
        }

        permute(0, n-k);

        if (ans < 2000)
            cout << "Case #" << c << ": " << ans+1 << endl;
        else
            cout << "Case #" << c << ": " << "IMPOSSIBLE\n";
    }
}
