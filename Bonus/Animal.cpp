#include<iostream>
#include<stdlib.h>
using namespace std;

struct animal{

	string name;
	string species;
	string diet;
	string habitad;
	animal(string name, string species, string diet, string habitad):
	name(name), species(species), diet(diet), habitad(habitad){}
	animal(){}
	void print() {
		cout << "Animal:(" << name << " " << species << " " << diet << " " << habitad << ")" << endl << endl;
	}
};

int main(){
	int n, land = 0, air = 0, water = 0;
	string some_name,some_species,some_diet,some_habitad;
	cout << "Cuantos animal tiene?" << endl;
	cin >> n;
	cout << "\n";

	for(int i=0; i < n; i++){

        cin >> some_name >> some_species >> some_diet >> some_habitad;
        animal i_animal(some_name, some_species, some_diet, some_habitad);

        i_animal.print();

        if(i_animal.habitad == "tierra")
            land += 1;
        else if(i_animal.habitad == "aire")
            air += 1;
        else
            water += 1;
	}
	cout << "Tierra " << land << endl << "Agua " << water << endl << "Aire " << air << endl;
	system("pause");
	return 0;
}
