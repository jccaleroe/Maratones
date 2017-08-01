package Woombat.Vectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

public class KNN {

    private static HashMap<String, float[]> vectors = new HashMap<>();
    private static int n, d;
    private static final int maxCacheSize = 1000, defaultK = 30;
    private static LoadingCache<String, String[]> neighbors = CacheBuilder.newBuilder().maximumSize(maxCacheSize)
            .build(new CacheLoader<String, String[]>() {
                @Override
                public String[] load(String s) throws Exception {
                    return knn(s);
                }
            });

    private static double cosineDistance(float[] a, float[] b){
        double aux1 = 0, aux2 = 0, aux3 = 0;
        for (int i = 0; i < d; i++){
            aux1 += a[i]*b[i];
            aux2 += a[i]*a[i];
            aux3 += b[i]*b[i];
        }
        aux2 = Math.sqrt(aux2);
        aux3 = Math.sqrt(aux3);
        return Math.acos(aux1 / (aux2 * aux3));
    }

    private static String[] knn(String a, int k){
        PriorityQueue<VectorComparator> queue = new PriorityQueue<>(n);
        float[] v = vectors.get(a);
        for (String str : vectors.keySet())
            if (!str.equals(a))
                queue.add(new VectorComparator(str, cosineDistance(v, vectors.get(str))));
        String[] r = new String[k];
        for (int i = 0; i < k; i++)
            r[i] = queue.poll().value;
        return r;
    }

    private static String[] knn(String a){
        return knn(a, defaultK);
    }

    static String[] getKnn(String a, int k){
        if (!vectors.containsKey(a))
            return null;
        if (k > defaultK)
            return knn(a, k);
        try {
            return Arrays.copyOf(neighbors.get(a), k);
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void go() throws IOException {
        BufferedReader br = new BufferedReader( new FileReader(
                new File( "/home/juan/apps/wiki.es/wiki.es.vec" ) ) );
        String str, tmp[];
        str = br.readLine();
        tmp = str.split(" ");
        n = Integer.parseInt(tmp[0]);
        d = Integer.parseInt(tmp[1]);

        float[] coords = new float[d];
        while ((str = br.readLine()) != null) {
            if (!str.isEmpty()) {
                tmp = str.split(" ");
                for (int i = 1; i < d; i++)
                    coords[i-1] = Float.parseFloat(tmp[i]);
                vectors.put(tmp[0], Arrays.copyOf(coords, d));
            }
        }
    }

    public static void main(String[] args) throws Exception{
        go();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        String tmp[];
        System.out.println("Finish reading file\n");
        while ((str = reader.readLine()) != null){
            tmp = str.split(" ");
            for (String s : getKnn(tmp[0], Integer.parseInt(tmp[1])))
                System.out.println(s);
        }
    }
}
