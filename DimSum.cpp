#include<bits/stdc++.h>

using namespace std;

int N, x, K;
int KPrices[100], T;
int favourIndex[100];
int dp[100][1101][22];

int dimSum(int dish, int moneySpent, int totalDishes){
    if (dish >= K || totalDishes > 2*N || moneySpent > x)
        return 0;
    if (dp[dish][moneySpent][totalDishes] != -1)
        return dp[dish][moneySpent][totalDishes];

    int r;
    r = dimSum(dish+1, moneySpent, totalDishes);

    if ( ceil ( ((double) (moneySpent + KPrices[dish] + T*N)) * 1.1l ) <= x && totalDishes + 1 <= 2*N){
        int tmp2 = favourIndex[dish] + dimSum(dish+1, moneySpent + KPrices[dish], totalDishes + 1);
        r = max(r, tmp2);
        if ( ceil ( ((double) (moneySpent + 2*KPrices[dish] + T*N)) * 1.1l ) <= x && totalDishes + 2 <= 2*N){
            int tmp3 = favourIndex[dish]*2 + dimSum(dish+1, moneySpent + 2*KPrices[dish], totalDishes + 2);
            r = max(r, tmp3);
        }
    }
    dp[dish][moneySpent][totalDishes] = r;
    return r;
}


int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    //freopen("input.txt", "r", stdin);
    while (true){
        cin >> N >> x >> T >> K;
        if (N == 0 && x == 0 && T == 0 && K == 0){
            break;
        }
        else{
            N++;
            x *= N;
            int tmp;
            for (int i = 0; i < K; i++){
                cin >> KPrices[i];
                favourIndex[i] = 0;
                for (int j = 0; j < N; j++){
                    cin >> tmp;
                    favourIndex[i] += tmp;
                }
                for (int j = 0; j <= x; j++)
                    for (int h = 0; h < N*2; h++)
                        dp[i][j][h] = -1;
            }
            printf( "%.2lf\n", (double)(dimSum(0,0,0)) / (double)N );
        }
    }
}
