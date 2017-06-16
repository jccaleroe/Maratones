package Wooombat;


import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CityMatcher {

    private static String[][] matchCities(List<String> a, List<String> b){
        String[][] r = new String[Math.max(a.size(), b.size())][2];
        int max, tmp;
        String tmp2, aux;
        List<String> a2, b2;
        if (a.size() > b.size()){
            a2 = a;
            b2 = b;
        }
        else {
            a2 = b;
            b2 = a;
        }

        for (int i = 0; i < a2.size(); i++){
            max = 0;
            tmp2 = "-1";
            aux = a2.get(i).toLowerCase();
            for (String aux2 : b2){
                tmp = FuzzySearch.ratio(aux, aux2);
                if (tmp > max){
                    max = tmp;
                    tmp2 = aux2;
                }
            }
            r[i][0] = a2.get(i);
            r[i][1] = tmp2;
        }
        return r;
    }

    public static void main(String[] args) {
        String[] ar = {"Santa Maria", "Satan Marta", "Bogota", "Cali", "V/vicencio"};
        String[] br = {"Villavicencio", "santa maria", "S. Marta", "Bogota D.C.", "Cali", "Barranquilla"};
        ArrayList<String> a = new ArrayList<>(Arrays.asList(ar));
        ArrayList<String> b = new ArrayList<>(Arrays.asList(br));
        String[][] r = CityMatcher.matchCities(a, b);
        for (String[] aR : r)
            System.out.println(aR[0] + "\t\t" + aR[1]);
    }
}
