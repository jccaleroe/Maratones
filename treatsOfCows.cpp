#include<bits/stdc++.h>

using namespace std;
typedef pair<int, int> ii;

int *v;
map <ii, long long> dict;

long long answer(int d, int a, int b){
    if(a == b){
        cout << a << " " << b << " " << d << endl;
        return d*v[a];
    }
    ii tmp = make_pair(a,b);
    if(!(dict.find(tmp) == dict.end())){
        //cout << a << " " << b <<  " " << d << endl;
        return dict[tmp];
    }

    dict[tmp] = max(d*v[b] + answer(d+1,a,b-1), d*v[a] + answer(d+1,a+1,b));
    cout << a << " " << b << " " << d << endl;
    return dict[tmp];
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // def

    int n;
    cin >> n;
    int v[n];
    //v = tmp;
    vector<vector<long long> > dp(n, vector<long long> (n));

    for(int i = 0; i < n; i++)
        cin >> v[i];
    //cout << answer(1, 0, n-1) << endl;

    for(int i = 0; i < n; i++)
        dp[i][i] = v[i] * n;
    for(int i = n-1; i>= 0; i--){
        int aux = n-1;
        for(int j = i+1; j < n; j++){
            dp[i][j] = max(dp[i+1][j]+aux*v[i], dp[i][j-1]+aux*v[j]);
            aux--;
        }
    }
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++)
            cout << dp[i][j] << " ";
        cout << endl;
    }
    cout << dp[0][n-1] << endl;
}
