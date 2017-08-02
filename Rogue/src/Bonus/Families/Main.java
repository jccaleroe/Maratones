package Bonus.Families;

public class Main {

    public static void main(String args[]){
        Node node = new Searcher().backtrack(new Node());
        if(node == null)
            System.out.println("Didn't find a solution");
        else {
            System.out.println();
            System.out.println(node);
            System.out.println(node.inference());
        }
    }
}
