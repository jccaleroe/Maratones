/*package Woombat.Matcher;

//Package taken from https://github.com/xdrop/fuzzywuzzy
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CityMatcher {

    private static ArrayList<String> a = new ArrayList<>();
    private static ArrayList<String> b = new ArrayList<>();
    private static String[] cities = {"Abejorral","Zzzipaquirá","Zona Bananera (PRADO - SEVILLA)","Ábrego","Íquira","Úmbita"};
    private static String[] igac = {"EL COCUY","OCAMONTE","SAN ANDRES","SIMACOTA","CONCEPCION","MATANZA","CHARTA","SILOS"};
    private static HashMap<String, String> matches;

    private static HashMap<String, String> matchCities(List<String> a, List<String> b){
        HashMap<String, String> r = new HashMap<>();
        if (a.size() > b.size())
            for (int i = 0; i < a.size() - b.size(); i++)
                b.add("-1");

        else
            for (int i = 0; i < b.size() - a.size(); i++)
                a.add("-1");

        int n = a.size();
        int[][] cost = new int[n][n];
        String aux;

        for (int i = 0; i < n; i++){
            aux = a.get(i).toLowerCase();
            for (int j = 0; j < n; j++){
                cost[i][j] = FuzzySearch.ratio(aux, b.get(j).toLowerCase());
            }
        }
        int[] match = Hungarian.go(cost);
        for (int i = 0; i < n; i++)
            if (!(a.get(i).equals("-1") || b.get(match[i]).equals("-1")))
                r.put(a.get(i), b.get(match[i]));
        return r;
    }

    public static void loadFiles() {
        a.clear();
        b.clear();
        Collections.addAll(a, cities);
        Collections.addAll(b, igac);

        matches = CityMatcher.matchCities(a, b);
        for (String aux : matches.keySet())
            System.out.println(aux + "\t\t" + matches.get(aux));
    }

    public static String getMacth(String city){
        return matches.get(city);
    }

    public static void main(String[] args) throws IOException {
        loadFiles();
        System.out.println("\nConsulting Zona Bananera (PRADO - SEVILLA)\n");
        System.out.println(getMacth("Zona Bananera (PRADO - SEVILLA)"));
    }
}
*/