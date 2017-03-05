//
// Created by juan on 20/02/17.
//

#include<iostream>
#include<string.h>
#define MAX 26

using namespace std;

string ct, m;
int countP[MAX];
int countW[MAX];

bool isEqual(){
    for(int i = 0; i < MAX; i++){
        if(countP[i] != countW[i])
            return false;
    }
    return true;
}

//void printA(){
//    for(int i = 0; i < MAX; i++)
//        cout << countP[i] << " ";
//    cout << endl;
//
//    for(int i = 0; i < MAX; i++)
//        cout << countW[i] << " ";
//    cout << endl;
//}

bool check(){
    if(isEqual())
        return true;

    int ms = m.size();
    int cts = ct.size();

    for(int i = ms; i < cts; i++){
        countW[ct[i]-'a']++;
        countW[ct[i-ms]-'a']--;
        if(isEqual())
            return true;
    }
    return false;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    cin >> T;
    for(int c = 0; c < T; c++){
        cin >> ct >> m;
        memset(countP, 0, sizeof(countP));
        memset(countW, 0, sizeof(countW));
        for(int i = 0; i < m.size(); i++) {
            countP[m[i]-'a']++;
            countW[ct[i]-'a']++;
        }
        check() ? (cout << "YES\n") : (cout << "NO\n");
    }
}
