//
// Created by juan on 22/03/17.
//


#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> points;
vector<vector<int> > graph;
vector<string> r;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int N, x, y, y2, x2, x3, y3, x4, y4, num, u, u2, u3, T = 0;
    char c;
    string tmp;
    while(true) {
        cin >> N;
        if (N == 0)
            break;
        num = 0;
        T++;
        points.assign(N, 0);
        graph.assign(N, vector<int>());
        r.clear();

        for (int i = 0; i < N; i++) {
            cin >> c >> x >> y;
            points[i] = c-'A' + 32*x + 2048*y;
        }
        sort(points.begin(), points.end());
        for (int i = 0; i < N; i++) {
            x = points[i] >> 5 & 63;
            y = points[i] >> 11;
            for(int j = i+1; j < N; j++){
                x2 = points[j] >> 5 & 63;
                y2 = points[j] >> 11;
                if ( y == y2 || x == x2){
                    graph[i].push_back(j);
                    graph[j].push_back(i);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            x = points[i] >> 5 & 63;
            y = points[i] >> 11;
            for(int j = 0; j < graph[i].size(); j++){
                u = graph[i][j];
                x2 = points[u] >> 5 & 63;
                y2 = points[u] >> 11;
                if (y == y2 && x < x2){
                    for(int h = 0; h < graph[u].size(); h++){
                        u2 = graph[u][h];
                        x3 = points[u2] >> 5 & 63;
                        y3 = points[u2] >> 11;
                        if (x2 == x3 && y2 > y3){
                            for(int g = 0; g < graph[u2].size(); g++){
                                u3 = graph[u2][g];
                                x4 = points[u3] >> 5 & 63;
                                y4 = points[u3] >> 11 & 63;
                                if (x4 == x){
                                    c = (points[i] & 31) + 'A';
                                    tmp = c;
                                    c = (points[u] & 31) + 'A';
                                    tmp += c;
                                    c = (points[u2] & 31) + 'A';
                                    tmp += c;
                                    c = (points[u3] & 31) + 'A';
                                    tmp += c;
                                    r.push_back(tmp);
                                    num ++;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(num > 0) {
            sort(r.begin(), r.end());

            cout << "Point set " << T << ":\n";
            int i;
            for (i = 0; i < r.size(); i++) {
                cout << " " << r[i];
                if ((i + 1) % 10 == 0)
                    cout << endl;
            }
            if (i % 10 != 0)
                cout << endl;
        }
        else
            cout << "Point set " << T << ": No rectangles" << endl;
    }
}