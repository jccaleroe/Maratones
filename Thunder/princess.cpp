#include<bits/stdc++.h>

using namespace std;

typedef pair<long long, bool> lb;

int main(){
    #ifdef LOCAL
    freopen("input.txt", "r", stdin);
    #endif
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    cin >> T;
    int jk = 1;
    while(jk <= T){
        int n;
        cin >> n;
        long m[n+1];
        for(int i = 1; i <= n; i++)
            cin >> m[i];

        vector<lb> sums;
        vector<lb> tmp;
        tmp.push_back(make_pair(0, true));
        for(int i = 0; i < n; i++){
            sums = tmp;
            tmp = vector<lb>();
            for(int j = 0; j < sums.size(); j++){
                lb aux = sums[j];
              //  cout << aux.first << " ";
            }
            //cout << endl;
            long long aux3 = -1;
            for(int j = 0; j < sums.size(); j++){
                    lb aux = sums[j];
                    if(aux.second == true){
                        if(aux.first > aux3)
                            aux3 = aux.first;
                    }
                    else{
                        tmp.push_back(make_pair(aux.first, true));
                    }
            }
            if(aux3 >= 0){
                long long aux2 = aux3 + m[i+1];
                tmp.push_back(make_pair(aux3, true));
                tmp.push_back(make_pair(aux2, false));
            }
        }
        sums = tmp;
            tmp = vector<lb>();
        long long r = 0;
        for(int j = 0; j < sums.size(); j++){
            lb aux = sums[j];
            //cout << aux.first << " " ;
            if(aux.first > r)
                r = aux.first;
        }
        //cout << endl;
        cout << "Case " << jk << ": " << r << endl;
        jk++;
    }
}
