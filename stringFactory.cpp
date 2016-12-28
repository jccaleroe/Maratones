#include<bits/stdc++.h>

using namespace std;

string s;
int dp[80][80];

int maximalFactoring(int a, int b){
    if ( a == b )
        return 1;
    if ( b >= s.size() || a >= s.size() || a > b)
        return 0;
    if (dp[a][b] != 0)
        return dp[a][b];

    int r = INT_MAX, tmp2, c;
    for (int i = 0; i < b-a; i++){
        c = 1;
        while (true){

            if ( (i+1)*(c+1)+a <= b+1 ) {
                if ( s.compare(a, i+1, s.substr( (i+1)*c+a, i+1) ) == 0 ){
                    c++;
                }
                else{
                    //tmp2 = i+1 + maximalFactoring( (i+1)*c+a, b );
                    //cout << i << " " << a << " " << i+a << " " << (i+1)*c+a << " " << b << "\n";
                    tmp2 = maximalFactoring( a, i+a ) + maximalFactoring( (i+1)*c+a, b );
                    //i = b;
                    break;
                }

            }
            else{
                //tmp2 = i+1 + maximalFactoring( (i+1)*c+a, b );

                //cout << i << " " << a << " " << i+a << " " << (i+1)*c+a << " " << b << "\n";
                tmp2 = maximalFactoring( a, i+a ) + maximalFactoring( (i+1)*c+a, b );
                //i = b;
                break;
            }
        }
        //tmp = i+1-a+b-(i+1)*c;
        //cout << i << " " << s.substr(a, i+1+a) << " " << s.substr((i+1)*c+a, b) << " " << (i+1)*c << "\n";
        //if ( c != 1){
            //tmp1 = maximalFactoring( a, i+a );
            //tmp2 = i+1 + maximalFactoring( (i+1)*c+a, b );
        if (tmp2 < r)
            r = tmp2;
    }
    dp[a][b] = r;
    return r;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    //freopen("input.txt", "r", stdin);
    while(true){
        cin >> s;
        if (s == "*")
            break;
        else{
            for (int i = 0; i < 80; i++){
                for (int j = i; j < 80; j++){
                        dp[i][j] = 0;
                    }
            }
            cout << maximalFactoring(0, s.size()-1) << "\n";
        }
    }
}
