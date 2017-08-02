#include<bits/stdc++.h>

using namespace std;

int dp[101][501];
int dp2[101][501];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif

    while(true){
        int W, n;
        cin >> W >> n;

        if (W == 0 && n == 0)
            break;

        for(int i = 0; i <= W; i++){
            dp[0][i] = 0;
            dp2[0][i] = 0;
        }

        int fun[n];
        int cost[n];

        int aux, aux2;
        for(int i = 0; i < n; i++){
            cin >> aux >> aux2;
            cost[i] = aux;
            fun[i] = aux2;
        }
        for(int i = 1; i <= n; i++){
            for(int j = 0; j <= W; j++){
                if(cost[i-1] > j){
                    dp[i][j] = dp[i-1][j];
                    dp2[i][j] = dp2[i-1][j];
                }
                else{
                    int tmp = dp[i-1][j];
                    int tmp2 = dp[i-1][j-cost[i-1]]+fun[i-1];
                    if(tmp2 > tmp){
                        dp[i][j] = tmp2;
                        dp2[i][j] = dp2[i-1][j-cost[i-1]]+cost[i-1];
                    }
                    else if(tmp2 == tmp){
                        dp2[i][j] = min(dp2[i-1][j], dp2[i-1][j-cost[i-1]]+cost[i-1]);
                        dp[i][j] = tmp2;
                    }
                    else{
                        dp[i][j] = tmp;
                        dp2[i][j] = dp2[i-1][j];
                    }
                }
            }
        }
        cout << dp2[n][W] << " " << dp[n][W] << endl;
    }
}
