//output the positions of each occurence
//For the pattern "ABCDE", lps[] is [0, 0, 0, 0, 0]
//For the pattern "AAAAA", lps[] is [0, 1, 2, 3, 4]
//For the pattern "AAABAAA", lps[] is [0, 1, 2, 0, 1, 2, 3]
#include<bits/stdc++.h>

using namespace std;

int n;
string text;

int LPSArray(char *pat, int *lps ){
    lps[0] = 0;
    int i = 1, len = 0;

    while(i < n){
        if(pat[i] == pat[len]){
            len++;
            lps[i] = len;
            i++;
        }
        else{
            if(len != 0)
                len = lps[len-1];
            else{
                lps[i] = 0;
                i++;
            }
        }
    }
}

int main(){
    while(cin >> n){
        char pat[n];
        int lps[n];
        cin >> pat;
        cin >> text;

        LPSArray(pat, lps);

        int j = 0, i = 0;
        int N = text.size();
        while(i < N){
            if(pat[j] == text[i]){
                i++;
                j++;
            }
            if(j == n){
                cout << i-j << endl;
                j = lps[j-1];
            }
            else if(i < N && pat[j] != text[i]){
                if(j != 0)
                    j = lps[j-1];
                else
                    i++;
            }
        }
        cout << endl;
    }
}
