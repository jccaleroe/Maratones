//
// Created by juan on 5/04/17.
//

#include <iostream>

using namespace std;

//char answer[2000000];

int main(){
    int n, m, N, cnt = 0;
    cin >> n >> m;
    if (n == m+1){
        for (int i = 0; i < m; i++)
            cout << "01";
        cout << '0';
        return 0;
    }
    if (m < n) {
        cout << "-1";
        return 0;
    }
    if (m > n*2+2) {
        cout << "-1";
        return 0;
    }

    N = n;
    string s = "";
    for (int i = 0; i < N; i++){
        if (m > n+1){
            //s = s + "110";
            cout << "110";
//            answer[cnt] = '1';
//            cnt++;
//            answer[cnt] = '1';
//            cnt++;
//            answer[cnt] = '0';
//            cnt++;
            m -= 2;
            n -= 1;

        }
        else{
            //s = s + "10";
            cout << "10";
//            answer[cnt] = '1';
//            cnt++;
//            answer[cnt] = '0';
//            cnt++;
            m -= 1;
            n -= 1;
        }
    }
    if (m > 1){
//        answer[cnt] = '1';
//        cnt++;
//        answer[cnt] = '1';
        cout << "11";
    }
    else if(m == 1){
        cout << '1';
    }
}