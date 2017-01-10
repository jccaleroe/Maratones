import java.io.*;
import java.util.*;

public class Keys implements Comparable<Keys>{

    HashMap<Character, HashSet<Character>> graph;
    int depth, ringMoves, keyMoves, heuristic;
    boolean adamDone, brendaDone;

    Keys(HashMap<Character, HashSet<Character>> g){
        graph = g;
        depth = ringMoves = keyMoves = heuristic = 0;
        int a, b;
        for (char ring : graph.keySet()){
            a = b = 0;
            for (char ring2 : graph.get(ring)) {
                if (ring2 <= 'M')
                    a++;
                else if (ring2 <= 'Z')
                    b++;
            }
            heuristic += Math.min(a, b);
        }
    }

    Keys(Keys keys){
        graph = new HashMap<>();
        for (char a : keys.graph.keySet())
            graph.put(a, new HashSet<>(keys.graph.get(a)));
        depth = keys.depth;
        ringMoves = keys.ringMoves;
        keyMoves = keys.keyMoves;
        heuristic = keys.heuristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keys keys = (Keys) o;

        return graph != null ? graph.equals(keys.graph) : keys.graph == null;
    }

    @Override
    public int hashCode() {
        return graph != null ? graph.hashCode() : 0;
    }

    @Override
    public int compareTo(Keys o) {
//        if ( depth > o.depth )
//            return 1;
//        if ( depth < o.depth )
//            return -1;
        if ( keyMoves > o.keyMoves )
            return 1;
        if ( keyMoves < o.keyMoves )
            return -1;
        if ( ringMoves > o.ringMoves )
            return 1;
        if ( ringMoves < o.ringMoves )
            return -1;

        //return heuristic + depth - ( o.heuristic + o.depth );
        return heuristic - o.heuristic;
    }

    int differentKeys(char ring){
        int a, b;
        a = b = 0;
        for (char tmp : graph.get(ring)) {
            if (tmp <= 'M')
                a++;
            else if (tmp <= 'Z')
                b++;
        }
        return Math.min(a, b);
    }

    void moveKey(char ring1, char ring2, char key){
        keyMoves++;
        depth++;

        heuristic -= differentKeys(ring1);
        heuristic -= differentKeys(ring2);

        graph.get(ring1).remove(key);
        graph.get(ring2).add(key);

        heuristic += differentKeys(ring1);
        heuristic += differentKeys(ring2);
    }

    boolean bfs( char root, HashSet<Character> seen ){
        ArrayDeque<Character> arrayDeque = new ArrayDeque<>();
        arrayDeque.add(root);
        seen.add(root);
        boolean adamFlag = false, tmp1 = false;
        boolean brendaFlag = false, tmp2 = false;

        while (!arrayDeque.isEmpty()){
            char u = arrayDeque.poll();
            for (char ring : graph.get(u)){
                if (ring >= 'a'){
                    if (seen.add(ring))
                        arrayDeque.add(ring);
                }
                else if (ring <= 'M'){
                    if (brendaFlag)
                        return false;
                    if ( adamDone )
                        return false;
                    adamFlag = tmp1 = true;
                }
                else {
                    if ( adamFlag )
                        return false;
                    if ( brendaDone )
                        return false;
                    brendaFlag = tmp2 = true;
                }
            }
        }
        adamDone = tmp1;
        brendaDone = tmp2;
        return true;
    }

    boolean done(){
        if (heuristic != 0)
            return false;
        int connectedSets = 0;
        HashSet<Character> seen = new HashSet<>();
        adamDone = brendaDone = false;
        for (char ring : graph.keySet()){
            if (!seen.contains(ring)){
                connectedSets++;
                if (connectedSets > 2)
                    return false;
                if ( !bfs(ring, seen) )
                    return false;
            }
        }
        return true;
    }

    static Keys search(Keys root){
        PriorityQueue<Keys> priorityQueue = new PriorityQueue<>();
        HashSet<Keys> seen = new HashSet<>();
        priorityQueue.add(root);
        seen.add(root);
        while (!priorityQueue.isEmpty()){
            Keys u = priorityQueue.poll();
            if (u.done())
                return u;
            int size = u.graph.size();
            Character[] tmp = u.graph.keySet().toArray(new Character[size]);
            HashSet<Character> hashSet;
            for (int i = 0; i < size-1; i++){
                for (int j = i+1; j < size; j++){
                    Keys keys = new Keys(u);
                    hashSet = keys.graph.get(tmp[i]);
                    if (hashSet.contains(tmp[j])){
                        hashSet.remove(tmp[j]);
                        keys.graph.get(tmp[j]).remove(tmp[i]);
                        keys.ringMoves++;
                        keys.depth++;
                    }
                    else {
                        hashSet.add(tmp[j]);
                        keys.graph.get(tmp[j]).add(tmp[i]);
                        keys.ringMoves++;
                        keys.depth++;
                    }
                    if ( seen.add(keys) )
                        priorityQueue.add(keys);
                }
            }
            ArrayList<Character> keysToMove = new ArrayList<>();
            for (char ring : u.graph.keySet()){
                keysToMove.clear();
                for (char ring2 : u.graph.get(ring)){
                    if ( ring2 < 'a' )
                        keysToMove.add(ring2);
                }
                for (char ring2 : u.graph.get(ring)){
                    if ( ring2 >= 'a' ){
                        for ( char aux : keysToMove ){
                            Keys keys = new Keys(u);
                            keys.moveKey(ring, ring2, aux);
                            if ( seen.add(keys) )
                                priorityQueue.add(keys);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException{
        //BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        BufferedReader br = new BufferedReader( new FileReader(
                new File( "/home/juan/Documents/Maratones/Rogue/src/input.txt" ) ) );

        String s;
        HashMap<Character, HashSet<Character>> graph = new HashMap<>();
        char[] pair;
        int i = 0;

        while ( true ){
            s = br.readLine();
            if ( s == null )
                break;
            if ( s.equals("0")) {
                Keys keys = new Keys(graph);
                keys = Keys.search(keys);
                i++;
                if ( keys == null)
                    System.out.println("Case " + i + ": impossible");
                else
                    System.out.println("Case " + i + ": " + keys.keyMoves + " " + keys.ringMoves);
                graph.clear();
            }
            else {
                pair = s.toCharArray();
                if (pair[0] >= 'a') {
                    if (pair[1] >= 'a') {
                        graph.computeIfAbsent(pair[0], k -> new HashSet<>());
                        graph.computeIfAbsent(pair[1], k -> new HashSet<>());
                        graph.get(pair[0]).add(pair[1]);
                        graph.get(pair[1]).add(pair[0]);
                    } else {
                        graph.computeIfAbsent(pair[0], k -> new HashSet<>());
                        graph.get(pair[0]).add(pair[1]);
                    }
                } else if (pair[1] >= 'a') {
                    graph.computeIfAbsent(pair[1], k -> new HashSet<>());
                    graph.get(pair[1]).add(pair[0]);
                }
            }
        }
    }
}
