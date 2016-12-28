#include<bits/stdc++.h>

using namespace std;

int NC, SC;
string NOS[1000], SOS[1000];
int NTradeValues[1000], STradeValues[1000];

int bridges( int c, vector<int> NNodes, vector<int> SNodes ){
    return 0;
}

int main(){
    int T;
    string s;

    cin >> T;
    for (int c = 0; c < T; c++){
        cin >> NC;
        for (int i = 0; i < NC; i++)
            cin >> s >> NOS[i] >> NTradeValues[i];
        cin >> SC;
        for (int i = 0; i < SC; i++)
            cin >> s >> SOS[i] >> STradeValues[i];

        cout << bridges(0, vector<int>(), vector<int>());
    }
}
