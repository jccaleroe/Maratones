#include<bits/stdc++.h>
#define whatis(x) cerr<<#x<<" is "<<x<<endl
#define mp(x,y) make_pair(x,y)
using namespace std;
char trash;

typedef pair<int, int> pii;
typedef pair<pii, string> tiis;
vector<tiis> v;

void query(int t){
  for(int i = 0; i < v.size(); i++){
    int s = v[i].first.first;
    int f = v[i].first.second;
    if(t<= f){
      if(t >= s){
        cout<<v[(i + 1)%v.size()].second<<"\n";
        return;
      }
      else{
        cout<<v[i].second<<"\n";
        return;
      }
    }
  }
  cout<<v[0].second<<"\n";
  return;
}

int main(){
  ios_base::sync_with_stdio(0);
  cin.tie(0);
#ifdef LOCAL
  freopen("in.txt","r",stdin);
  freopen("out.txt","w",stdout);
#endif // LOCAL
  int TC;
  cin>>TC;
  while(TC--){
    v.clear();
    int n,q;
    int hhb,mmb, hhe, mme;
    cin>>n;
    while(n--){
      string s;
      cin>>hhb>>trash>>mmb>>hhe>>trash>>mme>>s;
      v.push_back( mp( mp(hhb*60 + mmb, hhe*60 + mme), s  ) );
    }
    sort(v.begin(), v.end());
    cin>>q;
    while(q--){
      cin>>hhb>>trash>>mmb;
      int t = hhb*60 + mmb;
      query(t);
    }
  }
}



