#include<iostream>
#include<stdlib.h>
#include<string>

using namespace std;

string n;
long l;

void add(int p){

	if(p == 0){
        fill( n.begin(), n.begin()+l+1, '0');
        n[0] = '1' ;
        n = n + '1';
        cout << n;
        return;
    }

    while(n[p-1] == '9'){
        p--;
        if(p == 0){
            fill( n.begin(), n.begin()+l+1, '0');
            n[0] = '1' ;
            n = n + '1';
            cout << n;
            return;
        }
    }

    n[p-1] = 1 + n[p-1];
    for(int j = p; j <=l-p; j++)
        n[j] = '0';
    n[l-p+1] = n[p-1];
    cout << n;
}

void nextPal(){
	l = n.length();
	int mid = l/2;
	l -= 1;
	bool flag = false;

	for(int i = 0; i <= mid; i++){
		if(n[i] > n[l-i]){
			n[l-i] = n[i];
			flag = true;
		}
		else if(n[i] < n[l-i]){
			n[l-i] = n[i];
			flag = false;
		}
	}
	if(!flag){
		if(n[mid] == '9'){
			add(mid);
			return;
		}
		n[mid] = 1 + n[mid];
		n[l-mid] = n[mid];
	}
	cout << n;
}

int main(){
    #ifdef LOCAL
    freopen("input.txt", "r", stdin);
    #endif
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    long T;
    cin >> T;
	while(T--){
		cin >> n;
		nextPal();
		cout << "\n";
	}
    return 0;
}
