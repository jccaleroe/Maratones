#include <bits/stdc++.h>

using namespace std;

map<int, map<int,int>> d;
int bugs;

bool assignVariable(int I, int J, int value){
    if ( d[I][J] == value )
        return true;
    if ( d[I][J] != 0 )
        return false;

    for (map<int, int>::iterator it = d[I].begin(); it != d[I].end(); it++){
        if ( it->second == 1 ){
            if ( !assignVariable( J, it->first, -value) )
                return false;
        }
        else if ( it->second == -1 ){
            if ( !assignVariable( J, it->first, value) )
                return false;
        }
    }

    d[I][J] = value;

    return assignVariable( J, I, value );
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL

    int cases, I = 1, interactions, a, b, i, j;

    cin >> cases;
    while (true){
        bool flag = true;
        cin >> bugs >> interactions;

        for (i = 0; i < interactions; i++){
            cin >> a >> b;
            if (flag)
                flag = assignVariable(a-1, b-1, 1);
        }

        cout << "Scenario #" << I << ":\n" << (flag ? "No suspicious bugs found!\n" : "Suspicious bugs found!\n");

        I += 1;
        if ( I > cases )
            break;

        d.clear();
    }
}
