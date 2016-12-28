#include<bits/stdc++.h>

using namespace std;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL

    while(true){
        int n;
        cin >> n;
        if(n == 0)
            break;
        queue<int> q;
        int tmp;
        for(int i = 0; i < n; i++){
            cin >> tmp;
            q.push(tmp);
        }
        int i = 1;
        stack<int> s;
        stack<int> r;
        while(i <= n && !q.empty()){
            if(!s.empty() && s.top() == i){
                r.push(i);
                s.pop();
                i++;
                continue;
            }
            while(!q.empty() && q.front() != i){
                s.push(q.front());
                q.pop();
            }
            if(!q.empty()){
                r.push(i);
                q.pop();
                i++;
            }
        }
        bool flag = true;
        while(!s.empty()){
            int aux = s.top();
            s.pop();
            if(aux != i){
                flag = false;
                break;
            }
            i++;
        }
        if(flag)
            cout << "yes" << endl;
        else
            cout << "no" << endl;
    }
}
