//
// Created by juan on 25/02/17.
//

#include <bits/stdc++.h>

using namespace std;

long long C, HR, HB, WR, WB;
//vector<int> dp(1000000000);
map<int, long long> dp;

long long solve(int c){
    if(c <= 0)
        return 0;
    if(dp[c] != 0)
        return dp[c];
    long long tmp1 = 0, tmp2 = 0;
    if(c-WR >= 0)
        tmp1 = solve(c-WR) + HR;
    if(c-WB >= 0)
        tmp2 = solve(c-WB) + HB;
    if(tmp1 > tmp2) {
        dp[c] = tmp1;
        return tmp1;
    }
    dp[c] = tmp2;
    return tmp2;
}

int main(){
    cin >> C >> HR >> HB >> WR >> WB;
    //cout << solve(C) << endl;
    long long ans = 0;
    double limit = sqrt((double)C);
    for (int i = 0; i <= limit && C - i * WR > 0; i++)
        ans = max(ans, i * HR + (C - i * WR) / WB * HB);
    for (int i = 0; i <= limit && C - i * WB > 0; i++)
        ans = max(ans, i * HB + (C - i * WB) / WR * HR);
    printf("%lld\n", ans);
    return 0;
}