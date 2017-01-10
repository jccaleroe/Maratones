#include<bits/stdc++.h>

using namespace std;

int main(){
    int n;
    cin >> n;
    while ( n != 0){

        unsigned long long r = ( ( (n-1) * n * (2 * (n-1) + 1) ) / 6 ) + ( ( (n-1) * n ) / 2 );

//        for ( int i = 1; i <= n; i++)
//            for ( int j = 1; j <= n; j++)
//                for ( int k = 1; k <= n; k++)
//                    for ( int l = 1; l <= n; l++)
//                        for ( int m = 1; m <= n; m++){
//                            r2 += ( abs(i - j) * abs(j - k) * abs(k - l) * abs(l - m) * abs(m - i) );
//                            r2 %= 10007;
//                        }

        unsigned long long r2 = 0;

        for ( int i = 1; i <= n; i++)
            for ( int j = 1; j <= n; j++)
                for ( int k = 1; k <= n; k++)
                    for ( int l = 1; l <= n; l++){
                        r2 += abs(i - j) * abs(j - k) * abs(k - l);
                    }

        unsigned long long r3 = 0;

        for ( int i = 0; i < n; i++){
            int tmp = ( (n-i-1) * (n-i) / 2 + i * (i + 1) / 2 );
            int tmp2 = tmp * tmp;
            for ( int j = 0; j < n; j++){
                    int tmp3 = ( (n-j-1) * (n-j) / 2 + j * (j + 1) / 2 );
                    r3 += tmp3 * tmp3;
            }
        }

        r = ( r * n * (n-1) ) / 2;
        cout << r%10007 << " " << r2%10007 << " " << r3%10007 << endl;

        cin >> n;
    }
}
