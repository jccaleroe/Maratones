#include<iostream>

#define L 2000000000

using namespace std;

string n;
int l;
//long long pals[L];

void add(int p){
	if(p == 0){
        fill( n.begin(), n.begin()+l+1, '0');
        n[0] = '1' ;
        n = n + '1';
        //cout << n;
        return;
    }
    while(n[p-1] == '9'){
        p--;
        if(p == 0){
            fill( n.begin(), n.begin()+l+1, '0');
            n[0] = '1' ;
            n = n + '1';
            //cout << n;
            return;
        }
    }
    n[p-1] = 1 + n[p-1];
    for(int j = p; j <=l-p; j++)
        n[j] = '0';
    n[l-p+1] = n[p-1];
    //cout << n;
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
	//cout << n;
}

int a, b, c = 0;
bool flag;

void mid(int a){
    for (int i = 1; i < 10; i++) {
        n[a] = 1 + n[a];
        c++;
    }
    n[a] = '0';
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    n = "0";

    while (c < L){
        l = n.length();
        a = l/2;
        flag = l % 2 == 1;
        if (flag) {
            mid(a);
        }
        for (int i = a-1; i >= a; i--){
            n[i] = n[i]+1;
            n[l-i-1] = n[i]+1;
            if (flag)
                mid(a);
            c++;
        }
        fill( n.begin(), n.begin()+l+1, '0');
        n[0] = '1' ;
        n = n + '1';
        c++;
    }
    c;
    //cin >> T;
    cout << n;
	while(true){
        cin >> c;
        if (c == 0)
            return 0;
		//cout << pals[c-1] << "\n";
	}
}
