package DataStructures.unal.applications;

import DataStructures.unal.datastructures.ArrayLinearListImproved;


import java.io.*;

class Salsa {

    public static void main(String[] args) {

        ArrayLinearListImproved<String> animals = new ArrayLinearListImproved<String>();
        animals.add(0, "Pinguino");
        animals.add(0, "Krill");
        animals.add(0, "Ballena");
        animals.add(0, "Orca");
        animals.add(0, "Oso");
        animals.add(0, "Foca");
        animals.add(0, "Leon Marino");
        animals.add(0, "Frailecillo");

        System.out.println(animals);

        animals.save("DataStructures/saveTheArtic.txt");

        ArrayLinearListImproved<String> recovery = new ArrayLinearListImproved<String>();

        recovery.load("DataStructures/saveTheArtic.txt");

        ArrayLinearListImproved<String> names = new ArrayLinearListImproved<String>();
        names.add(0, "Caperusa");
        names.add(0, "Delma");
        names.add(0, "Alcatraz");
        names.add(0, "Aldo");
        names.add(0, "Alien");
        names.add(0, "Explorer");
        names.add(0, "Dona");
        names.add(0, "Cholo");

        if (names.size() >= recovery.size())
            for (int i = 0; i < recovery.size(); i++)
                recovery.add(i, recovery.remove(i) + " alias " + names.get(i));

        System.out.println("\nThe List\n");
        for (String a : recovery)
            System.out.println(a);

        ArrayLinearListImproved<Family> zoologico = new
                ArrayLinearListImproved<Family>();
        zoologico.add(0, new Family("Caperusa", "Pinguino", "Aves", 4));
        zoologico.add(0, new Family("Pepa", "Loro", "Aves", 10));
        zoologico.add(0, new Family("Lucas", "Pollo", "Aves", 1));
        zoologico.add(0, new Family("Nico", "Perro", "Mam�feros", 5));
        zoologico.add(0, new Family("Alcatraz", "Ballena", "Mam�feros", 17));
        zoologico.add(0, new Family("Delma", "Krill", "Malacostracas", 0));
        zoologico.add(0, new Family("Alien", "Oso", "Mam�feros", 3));

        ArrayLinearListImproved<String> clases = new ArrayLinearListImproved<String>();
        clases.add(0, "Mam�feros");
        clases.add(0, "Malacostracas");
        clases.add(0, "Aves");

        System.out.println("\n**The Zoo**");

        System.out.println(zoologico);

        System.out.println("\n**The Zoo**");
        for (String a : clases) {
            System.out.println("\n" + a);
            for (Family b : zoologico)
                if (b.clase.equals(a))
                    System.out.println(b);
        }

        System.out.println("\nIndex of Alien and Pacho");
        System.out.println(zoologico.indexOf(new Family("Pacho", "Oso", "Mam�feros", 7)));

    }
}

class Family implements Serializable, Comparable<Family> {

    String name;
    String especie;
    String clase;
    Integer age;

    @Override
    public int compareTo(Family a) {
        return this.especie.compareTo(a.especie);
    }

    public Family(String na, String es, String cla, int a) {
        name = na;
        especie = es;
        clase = cla;
        age = a;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Family)) return false;
        return this.clase.equals(((Family) o).clase) && this.especie.equals(((Family) o).especie);
    }

    @Override
    public String toString() {
        return this.name + " " + this.especie + " years " + this.age;
    }
}