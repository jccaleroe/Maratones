//
// Created by tourist
//

#include <bits/stdc++.h>
using namespace std;
#define int long long
#define IOS ios::sync_with_stdio(0) , cin.tie(0) , cout.tie(0)
inline int in(){int32_t x ; scanf("%d",&x) ; return x ;}
inline long long lin(){long long x ; scanf("%lld",&x) ; return x;}
const int maxn = 1e6 + 10;
const int inf = 1e18 ;
int cnt[maxn];
int dp[2][2],dp2[2][2];
int32_t main() {
    int n = in();
    for (int i = 0 ; i < n ; i ++) cnt[in()] ++ ;
    dp[0][0] = 0;
    for (int i = 1 ; i < maxn ; i ++)
    {
        memset(dp2,0,sizeof dp);
        dp2[1][0] = cnt[i] * i + max(dp[0][1],dp[0][0]);
        dp2[0][1] = dp[1][0];
        dp2[0][0] = max(dp[0][1],dp[0][0]);
        memcpy(dp,dp2,sizeof dp2);
    }
    int res = 0;
    for (int i = 0 ; i < 2 ; i ++)
        for (int j = 0 ; j < 2 ; j ++) res = max(res,dp[i][j]);
    cout << res << endl;
}