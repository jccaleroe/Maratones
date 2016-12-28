package DataStructures;

import DataStructures.unal.datastructures.ArrayLinearListImproved;


import java.io.*;

class Nonameble {
    public static void main(String[] args) {
        ArrayLinearListImproved<String> j =
                new ArrayLinearListImproved<>();
        j.add(0, "Pointer");
        j.add(0, "Boldemor");
        j.add(0, "Jabon Rey");
        j.add(0, "Lord Baldomedo");
        j.add(2, "Jabon Fab Liquido");
        j.add(0, "Yesterday");

        System.out.println(j);

        j.save("boldemor.txt");

        ArrayLinearListImproved<String> k =
                new ArrayLinearListImproved<>();
        k.load("boldemor.txt");
        k.sort();
        for (String julia : k)
            System.out.println("**" + julia + "**");
        Prisionero jose = new Prisionero(15, "Jose Cara Cortada", "robo celular en la 45");
        System.out.println(jose);
        ArrayLinearListImproved<Prisionero> azkaban =
                new ArrayLinearListImproved<>();
        azkaban.add(0, jose);
        azkaban.add(1, new Prisionero(1000, "tetrix", "llegar tarde a clase"));
        azkaban.add(2, new Prisionero(2, "actrix", "enganho portugues"));
        azkaban.add(3, new Prisionero(15, "na-troll", "no preparar clase y hacer quices tempranos"));
        System.out.println(azkaban);

        float codena_average = 0.0f;
        for (Prisionero celacho : azkaban)
            codena_average += celacho.codena;
        codena_average /= azkaban.size();
        System.out.println("codena promedio = " + codena_average);

        System.out.println(azkaban.indexOf(new Prisionero("tetrix")));
    }
}

class Prisionero implements Serializable, Comparable<Prisionero> {
    //fields
    int codena;
    String name;
    String delito;

    public Prisionero() {
        this(30, "N/A", "N/A");
    }

    public Prisionero(String name) {
        this(20, name, "muchos");
    }

    public Prisionero(int c, String n, String d) {
        codena = c;
        name = n;
        delito = d;
    }

    @Override
    public String toString() {
        return "[" + codena + ", " + name + ", " + delito + "]";

    }

    @Override
    public int compareTo(Prisionero otro) {
        // return this.codena - otro.codena;
        return this.name.compareTo(otro.name);
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null) return false;
        if (otro == this) return true;
        if (!(otro instanceof Prisionero)) return false;
        return this.name.equals(((Prisionero) otro).name);
    }
}
