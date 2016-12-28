#include<iostream>
#include<stdlib.h>
#include<string>
#include<map>

using namespace std;

map<long, long> dict;

bool isValid(string str){
	for(int i = 0; i < str.size(); i++){
		if(str[i] == '0'){
			if(i-1 >= 0){
				if(str[i-1] != '1' && str[i-1] != '2'){
					return false;
				}
			}
			else{
				return false;
			}
		}
	}
	return true;
}

long acode(string str){
	if(!isValid(str)){
		return 0;
	}
	long n = atol(str.c_str());
	if(n <= 10)
		return 1;
	if(n == 20)
		return 1;
	if( n < 27)
		return 2;
	map<long, long>::iterator it = dict.find(n);
	if(it == dict.end()){
		string s = str.substr(0, str.size()-1);
		string s2 = str.substr(str.size()-2, 2);
		long r2 = atol(s2.c_str());
		if(r2 == 10){
			long r = acode(str.substr(0, str.size()-2));
			dict[n] = r;
			return r;
		}
		if(r2 == 20){
			long r = acode(str.substr(0, str.size()-2));
			dict[n] = r;
			return r;
		}
		else if(str[str.size()-2] == '0'){
			long r = acode(s);
			dict[n] = r;
			return r;
		}
		long r = acode(s);
		if(r2 < 27)
			r += acode(str.substr(0, str.size()-2));
		dict[n] = r;
		return r;
	}
	else
		return it->second;
}

int main(){
    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL
    cin.tie(0);
    ios_base::sync_with_stdio(0);

	string str;
	cin >> str;
	while(str[0] != '0'){
		printf("%ld\n", acode(str));
		cin >> str;
	}
}
