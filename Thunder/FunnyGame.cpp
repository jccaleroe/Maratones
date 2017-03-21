//
// Created by juan on 15/03/17.
//

#include <iostream>
#include <climits>

using namespace std;

int stickers[200000];
int dp[200000];
int sums[200000];
int N;

int maxValue(int index, int indexValue, int sum1, int sum2, int a, int b);
int minValue(int index, int indexValue, int sum1, int sum2, int a, int b);

int maxValue(int index, int indexValue, int sum1, int sum2, int a, int b){
    if(index == N-1)
        return sum1-sum2;

    if(dp[index] != 0)
        return dp[index];

    int v = INT_MIN, sum = indexValue;
    bool flag = false;
    for(int i = index+1; i < N; i++){
        sum += stickers[i];
        if(i == N-1 || (stickers[i] < 0 && flag)) {
            v = max(v, minValue(i, sum, sum + sum1, sum2, a, b));
            if (v >= b) {
                dp[index] = v;
                return v;
            }
            a = max(a, v);
        }
        else{
            flag = true;
        }
    }
    dp[index] = v;
    return v;
}

int minValue(int index, int indexValue, int sum1, int sum2, int a, int b){
    if(index == N-1)
        return sum1-sum2;

    if(dp[index] != 0)
        return dp[index];

    int v = INT_MAX, sum = indexValue;
    bool flag = false;
    for(int i = index+1; i < N; i++){
        sum += stickers[i];
        if(i == N-1 || (stickers[i] < 0 && flag)) {
            v = min(v, maxValue(i, sum, sum1, sum + sum2, a, b));
            if (v <= a) {
                dp[index] = v;
                return v;
            }
            b = min(b, v);
        }
        else{
            flag = true;
        }
    }
    dp[index] = v;
    return v;
}

int alphaBetaSearch(){
    return maxValue(0, stickers[0], 0, 0, INT_MIN, INT_MAX);
}

int boring(){
    dp[N-1] = 0;
    dp[N-2] = sums[N-1];

    for(int i = N-3; i >= 0; i--){
        dp[i] = max(dp[i+1], sums[i+1] - dp[i+1]);
        //cout << dp[i] << endl;
    }
    return dp[0];
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    cin >> N;
    int sum = 0;
    for (int i = 0; i < N; i++) {
        cin >> stickers[i];
        sum += stickers[i];
        sums[i] = sum;
    }
    //cout << alphaBetaSearch() << endl;
    cout << boring() << endl;
}