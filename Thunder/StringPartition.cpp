#include <bits/stdc++.h>

using namespace std;

string s;
int p[201], n;
long long dp[201][201];

long long mixtures(int a, int b){
    if(a == b)
        return p[a];

    if(dp[a][b] != -1)
        return dp[a][b];

    long r = INT_MIN;
    long tmp, tmp1, tmp2 = 0, tmp3;

    if (p[a] != 0){
        string aux = s.substr(a, b-a+1);
        if (aux.size() <= 10) {
            long aux2 = stol(aux);
            if(aux2 <= INT_MAX)
                tmp2 = aux2;
        }
    }
    for(int i = a; i < b; i++){
        tmp = mixtures(a, i);
        tmp1 = mixtures(i+1, b);
        tmp3 = tmp + tmp1;
        if ( tmp3 > r && tmp3 > tmp2)
            r = tmp3;
        else if(tmp2 > r)
            r = tmp2;
    }
    dp[a][b] = r;
    return r;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    cin >> T;
    for(int c = 0; c < T; c++){
        cin >> s;
        n = s.size();
        for(int i = 1; i <= n; i++)
            p[i] = s[i-1] - '0';

        int i, j, k, L;
        long long q;
        long q2;

        for (i = 1; i <= n; i++)
            dp[i][i] = p[i];

        for (L=2; L <= n; L++){
            for (i=1; i <= n-L+1; i++){
                j = i+L-1;
                dp[i][j] = INT_MIN;
                q2 = 0;
                if (p[i] != 0){
                    string aux = s.substr(i-1, j-i+1);
                    if (aux.size() <= 10) {
                        long aux2 = stol(aux);
                        if(aux2 <= INT_MAX)
                            q2 = aux2;
                    }
                }
                for (k=i; k <= j-1; k++){
                    q = dp[i][k] + dp[k+1][j];
                    if (q > dp[i][j] && q > q2)
                        dp[i][j] = q;
                    else if (q2 > dp[i][j])
                        dp[i][j] = q2;
                }
            }
        }
        cout << dp[1][n] << endl;
    }
}