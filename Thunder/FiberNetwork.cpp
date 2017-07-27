#include <iostream>
#include <vector>
#include <cstring>
using namespace std;

vector<vector<pair<int, string> > > v;
bool seen[200], ans[26];
int n, a, b;

void search(int u, int mask){
    if (u == b){
        for (int j = 0; j < 26; j++){
            if ((mask >> j) & 1)
                ans[j] = 1; }
        return;
    }
    seen[u] = 1;
    string aux;
    int tmp;
    for (int i = 0; i < v[u].size(); i++){
        if (!seen[v[u][i].first]){
            aux = v[u][i].second;
            tmp = 0;
            for (int j = 0; j < aux.size(); j++)
                if ((mask >> (aux[j]-'a')) & 1)
                    tmp += (1 << (aux[j]-'a'));
            search(v[u][i].first, tmp);
        }
    }
    seen[u] = 0;
}
void search(){
    seen[a] = 1;
    string aux;
    int mask;
    for (int i = 0; i < v[a].size(); i++){
        aux = v[a][i].second;
        mask = 0;
        for (int j = 0; j < aux.size(); j++)
            mask += (1 << (aux[j]-'a'));
        search(v[a][i].first, mask);
    }
    seen[a] = 0;
}
int main(){
    bool  flag;
    string comp;
    cin >> n;
    while (n != 0){
        v.assign(n, vector<pair<int, string> >());
        cin >> a >> b;
        while (a != 0 || b != 0){
            cin >> comp;
            v[a-1].push_back(make_pair(b-1, comp));
            cin >> a >> b;
        }
        cin >> a >> b;
        while (a != 0 || b != 0){
            a--; b--;
            memset(ans, 0, sizeof(ans));
            flag = false;
            search();
            for (int i = 0; i < 26; i++){
                if (ans[i]) {
                    flag = true;
                    cout << (char) (i + 'a');
                }}
            if (!flag)
                cout << '-';
            cout << endl;
            cin >> a >> b;
        }
        cout << endl;
        cin >> n; }}