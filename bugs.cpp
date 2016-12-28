#include <bits/stdc++.h>

using namespace std;

int bugs;

vector<vector<int>> matrix (2000, vector<int> (2000));

bool disjunction(int I, int J){
    for (int i = 0; i < bugs; i++){
        if ( matrix[I][i] == 1 && matrix[J][i] == 1)
            return false;

        if ( matrix[I][i] == 1)
            matrix[J][i] = -1;

        if ( matrix[J][i] == 1)
            matrix[I][i] = -1;
    }

    return true;
}

bool disjunction2(int I, int J){
    for (int i = 0; i < bugs; i++)
        if ( matrix[I][i] == -matrix[J][i] && matrix[I][i] != 0)
            return false;
    return true;
}

bool inference(){
    for (int i = 0; i < bugs; i++){
        for (int j = i+1; j < bugs; j++){
            if ( matrix[i][j] == 1){
                if ( !disjunction(i, j) )
                    return false;
            }
            if ( matrix[i][j] == -1){
                if ( !disjunction2(i, j) )
                    return false;
            }
        }
    }
    return true;
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
        cin >> bugs >> interactions;
        for (i = 0; i < interactions; i++){
            cin >> a >> b;
            matrix[a-1][b-1] = 1;
            matrix[b-1][a-1] = 1;
        }

        cout << "Scenario #" << I << ":\n";
        if ( inference() )
            cout << "No suspicious bugs found!\n";
        else
            cout << "Suspicious bugs found!\n";

//        for (int i = 0; i < bugs; i++){
//            for (int j = 0; j < bugs; j++){
//                cout << matrix[i][j] << " ";
//            }
//            cout << endl;
//        }
        I += 1;
        if ( I > cases )
            break;

        for (i = 0; i < bugs; i++)
            for (j = 0; j < bugs; j++)
                matrix[i][j] = 0;
    }
}
