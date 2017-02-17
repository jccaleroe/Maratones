#include<bits/stdc++.h>

using namespace std;

bool comparison( pair<string, string> i, pair<string, string> j ){
    string author1= i.first, author2 = j.first, book1 = i.second, book2 = j.second;
    if( author1.compare( author2 ) == 0 ){
        if( book1.compare(book2) > 0 ){
            return false;
        }
        else if( book1.compare(book2) < 0 ){
            return true;
        }
    }
    else if( author1.compare( author2 ) > 0 ){
        return false;
    }
    return true;
}

int main(){
    //freopen("output.out", "w", stdout );
    #ifdef LOCAL
    freopen("input.txt", "r", stdin);
    #endif
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    string book, author;
    vector< pair< string, string > >books;
    vector<int> order;
    while( getline( cin, book ) && book != "END" ){
        for( int i = 1; i < book.length(); i++ ){
            if( book[i] == '"' ){
                author = book.substr( i+5, book.length()-(i+6) );
                book = book.substr(0, i+1);
                books.push_back( make_pair( author, book ) );
                order.push_back( 1 );
                break;
            }
        }
    }
    sort(books.begin(), books.end(), comparison );
    vector< pair<string, string > > returned;
    while( getline( cin, book ) && book != "END" ){
        if( book == "SHELVE" ){
            sort(returned.begin(), returned.end(), comparison );
            for( int b = 0; b < returned.size(); b++ ){
                pair< string, string >book = returned[b];
                int c, l = -1;
                for( c = 0; c < books.size(); c++ ){
                    if( books[c] == book ){
                        break;
                    }else if( order[c] == 1 ){
                        l = c;
                    }
                }
                order[c] = 1;
                if ( c == 0 || ( c > 0 && l == -1) )
                    cout << "Put " << books[c].second << " first" << endl;
                else
                    cout << "Put " << books[c].second << " after " << books[l].second << endl;
            }
            cout << "END\n";
            returned.clear();
        }else{
            author = book.substr( 0, 6 );
            book = book.substr( 7, book.length()-7 );
            while( book[book.length()-1] != '"' ){
                book = book.substr( 0, book.length()-1 );
            }
            if( author == "BORROW" ){
                for( int c = 0; c < books.size(); c++ ){
                    if( books[c].second == book )
                        order[c] = 0;
                }
            }
            else if( author == "RETURN" ){
                for( int c = 0; c < books.size(); c++ ){
                    if( books[c].second == book )
                        returned.push_back(make_pair( books[c].first, book)) ;
                }
            }
        }
    }
    //fclose(stdout);
}
