package Wooombat;


import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CityMatcher {

    private static ArrayList<String> a = new ArrayList<>();
    private static ArrayList<String> b = new ArrayList<>();

    private static HashMap<String, String> matchCities(List<String> a, List<String> b){
        HashMap<String, String> r = new HashMap<>();
        List<String> a2, b2;
        if (a.size() > b.size()){
            for (int i = 0; i < a.size() - b.size(); i++)
                b.add("-1");
            a2 = a;
            b2 = b;
        }
        else {
            for (int i = 0; i < b.size() - a.size(); i++)
                a.add("-1");
            a2 = b;
            b2 = a;
        }
        int n = a.size();
        int[][] cost = new int[n][n];
        String aux;


        for (int i = 0; i < n; i++){
            aux = a2.get(i).toLowerCase();
            for (int j = 0; j < n; j++){
                cost[i][j] = FuzzySearch.ratio(aux, b2.get(j).toLowerCase());
            }
        }
        int[] match = Hungarian.go(cost);
        for (int i = 0; i < n; i++){
            r.put(a2.get(i), b2.get(match[i]));
        }
        return r;
    }

    static void loadFiles(String path1, String path2) throws IOException {
        String string;
        a.clear();
        BufferedReader br = new BufferedReader(new FileReader(new File(path1)));
        while ((string = br.readLine()) != null)
            a.add(string);

        b.clear();
        br = new BufferedReader( new FileReader(new File(path2)));
        while ((string = br.readLine()) != null)
            b.add(string);

        br.close();
    }

    public static void main(String[] args) throws IOException{

        //String[] ar = {"Santa Maria", "Satan Marta", "Bogota", "Cali", "V/vicencio"};
        //String[] br = {"Villavicencio", "santa maria", "S. Marta", "Bogota D.C.", "Cali", "Barranquilla"};
        loadFiles("/home/juan/Documents/Maratones/Rogue/src/Wooombat/list_locations1.txt",
                "/home/juan/Documents/Maratones/Rogue/src/Wooombat/list_igac.txt");
        HashMap<String, String> r = CityMatcher.matchCities(a, b);
        for (String aux : r.keySet())
            System.out.println(aux + "\t\t" + r.get(aux));
    }
}
