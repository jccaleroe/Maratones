#include <bits/stdc++.h>

using namespace std;

void assignVariable(map<int,map<int,int>> d, int I){

    for (map<int, int>::iterator it = d[I].begin(); it != d[I].end(); it++)
        cout << it->first  << " : " << it->second << endl;
}

int main(){
    map<int, map<int,int>> d;
    d[1][2] = 1;
    d[1][3] = 2;
    d[1][4] = 3;
    d[2][3] = 4;
    d[2][1] = 5;
    for (map<int, map<int, int>>::iterator it = d.begin(); it != d.end(); it++)
        for (map<int, int>::iterator it2 = (it->second).begin(); it2 != (it->second).end(); it2++)
            cout << it->first  << " : " << it2->first << " : " << it2->second << endl;

}
