#include<bits/stdc++.h>

using namespace std;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    int T;
    cin >> T;
    while(T--){
        int n, m;
        cin >> n >> m;
        int dp[n+1][m+2];

        for(int i = 0; i < m+2; i++)
            dp[0][i] = 0;

        for(int i = 0; i < n+1; i++){
            dp[i][0] = 0;
            dp[i][m+1] = 0;
        }

        for(int i = 1; i <= n; i++)
            for(int j = 1; j <= m; j++)
                cin >> dp[i][j];

        for(int i = 2; i < n; i++)
            for(int j = 1; j <= m; j++)
                dp[i][j] = max(dp[i-1][j], max(dp[i-1][j-1], dp[i-1][j+1])) + dp[i][j];

        int tmp = 0;
        for(int i = 1; i <= m; i++){
            dp[n][i] = max(dp[n-1][i], max(dp[n-1][i-1], dp[n-1][i+1])) + dp[n][i];
            if(dp[n][i] > tmp)
                tmp = dp[n][i];
        }
        cout << tmp << endl;
    }
}
