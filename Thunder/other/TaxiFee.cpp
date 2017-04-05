//
// Created by juan on 27/03/17.
//

#include <iostream>
#include <map>
#include <vector>

using namespace std;

typedef pair<int, int> iPair;

int minutes = 1440;
int rushHour = 360;
map<string, int> m;
vector<iPair> v;


int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    string s;
    int a, b, i, h;
    while(cin >> s){
        if (s != "--"){
            v.clear();
            m.clear();
            i = 0;
            while (s != "$") {
                cin >> a >> b;
                m[s] = i;
                v.push_back(make_pair(a, b));
                i++;
                cin >> s;
            }
            cin >> s;
            a = m[s];
            cin >> s;
            b = m[s];
            cin >> s;
            h = stoi(s.substr(0, 2)) * 60;
            h += stoi(s.substr(3, 2));
            cin >> s;
            int h1, li, mi;
            long k = 0, t = 0;
            double cost = 0;
            k = 0;
            for (i = a; i <= b; i++){
                iPair p = v[i];
                li = p.first;
                mi = p.second;
                for (long j = k; j < k+li; j++){
                    h1 = (mi + h) % minutes;
                    t += mi;
                    bool flag = false;
                    for(int m = 0; m < mi; m++){
                        h = (1 + h) % minutes;
                        if (h > 0 && h <= rushHour) {
                            flag = true;
                            break;
                        }
                    }
                    if (j < 10){
                        if (flag)
                            cost += 1.2*1000;
                        else
                            cost += 1000;
                    }
                    else if (j < 30){
                        if (flag)
                            cost += 1.2*250;
                        else
                            cost += 250;
                    }
                    else{
                        if (flag)
                            cost += 1.2*100;
                        else
                            cost += 100;
                    }
                    h = h1;
                }
                k += li;
            }
            if (k * 1.0 / t < 0.5)
                cost *= 1.1;
            cout << ((int)(cost)) << endl;
        }
        else
            break;
    }
}