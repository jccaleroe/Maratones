#include<iostream>

using namespace std;

const double EPS = 10e-10;

double fabs( const double x ){
   if( x >= 0 )
       return x;
   return -x;
}

double find_square_root( double n , double low, double high ){
   if( n < 0 || high-low < 0  )
       return -1;

   if( fabs( 1 - n ) < EPS )
       return 1;

   double mid = (low + high) / 2.0;
   double mid_square = mid*mid;
   double delta = fabs( mid_square - n );

   if( delta < EPS )
       return mid;

   if( mid_square > n )
       return find_square_root( n , low , mid );
   else
       return find_square_root( n , mid , high );
}

double find_square_root( double x ){
   if( fabs(x) < EPS  )
       return 0;

   if( x < 0 )
       return -1;

   return find_square_root( x , 0 , x+1 );
}
int main() {
   double a,r;
   cin >> a;
   r = find_square_root( a );
   cout << "La raiz cuadrada de " << a << " es " << r << endl;
   return 0;
}
