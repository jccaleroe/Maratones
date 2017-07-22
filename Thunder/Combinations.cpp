// Program to print all combination of size r in an array of size n
#include<stdio.h>
#include <string.h>
#include <vector>
#include <iostream>

using namespace std;

/* Function to swap values at two pointers */
void swap(char *x, char *y) {
    char temp;
    temp = *x;
    *x = *y;
    *y = temp;
}

/* Function to print permutations of string
   This function takes three parameters:
   1. String
   2. Starting index of the string
   3. Ending index of the string. */
void permute(char *a, int l, int r) {
    int i;
    if (l == r)
        printf("%sn", a);
    else {
        for (i = l; i <= r; i++) {
            swap((a+l), (a+i));
            permute(a, l+1, r);
            swap((a+l), (a+i)); //backtrack
        }
    }
}

vector<int> people;
vector<int> combination;

void pretty_print(const vector<int>& v) {
    static int count = 0;
    cout << "combination no " << (++count) << ": [ ";
    for (int i = 0; i < v.size(); ++i) { cout << v[i] << " "; }
    cout << "] " << endl;
}

void go(int offset, int k) {
    if (k == 0) {
        pretty_print(combination);
        return;
    }
    for (int i = offset; i <= people.size() - k; ++i) {
        combination.push_back(people[i]);
        go(i+1, k-1);
        combination.pop_back();
    }
}

int main() {


    char str[] = "ABC";
    int n = strlen(str);
    permute(str, 0, n-1);
}