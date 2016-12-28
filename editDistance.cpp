#include<bits/stdc++.h>

using namespace std;

char str1[2001];
char str2[2001];

int dp[2001][2001];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL

    int t;
	cin>>t;
	while(t--){
        cin>>str1>>str2;
        int d1, d2, d3, i, len1, len2;
        len1 = strlen(str1);
        len2 = strlen(str2);

        for(i = 0; i <= len1; i++)
            dp[i][0] = i;
        for(i = 1; i <= len2; i++)
            dp[0][i] = i;

        for(i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                d1 = (str1[i-1] == str2[j-1] ? 0:1) + dp[i-1][j-1]; //Substitution

                d2 = dp[i][j-1]+1; //Insertion

                d3 = dp[i-1][j]+1; //Deletion

                dp[i][j] = min(d1, min(d2, d3));
            }
        }
        cout << dp[len1][len2] << endl;
    }
}
