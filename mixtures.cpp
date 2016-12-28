//When mixing two mixtures of colors a and b, the resulting mixture
//will have the color (a+b) mod 100.
//Also, there will be some smoke in the process. The amount of smoke
//generated when mixing two mixtures of colors a and b is a*b.

#include<bits/stdc++.h>

using namespace std;

int p[101];
int c[101][101];
long s[101][101];

pair<int,int> mixtures(int a, int b){
    if(a == b)
         return make_pair(p[a], 0);
    int r = INT_MAX, aux, r2;

    for(int i = a; i < b; i++){
        pair<int,int> tmp,tmp1;
        tmp = mixtures(a, i);
        tmp1 = mixtures(i+1, b);

        aux = tmp.first * tmp1.first + tmp.second + tmp1.second;
        if(aux < r){
            r = aux;
            r2 = (tmp.first + tmp1.first) % 100;
        }
    }
    return make_pair(r2, r);
}

int main(){
    int n;

    while(cin >> n){
        for(int i = 1; i <= n; i++)
            cin >> p[i];
        //cout << mixtures(1, n).second << endl;
        int i, j, k, L;
        long q;
        for (i = 1; i <= n; i++){
            c[i][i] = p[i];
            s[i][i] = 0;
        }

        for (L=1; L <= n; L++){
            for (i=1; i <= n-L; i++){
                j = i+L;
                s[i][j] = INT_MAX;
                for (k=i; k < j; k++){
                    q = c[i][k] * c[k+1][j] + s[i][k] + s[k+1][j];
                    if (q < s[i][j]){
                        s[i][j] = q;
                        c[i][j] = (c[i][k] + c[k+1][j])%100;
                    }
                }
            }
        }
        cout << s[1][n] << endl;
    }
}
