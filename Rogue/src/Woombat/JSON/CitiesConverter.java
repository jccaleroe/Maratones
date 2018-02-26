/*package Woombat.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//http://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.1
import com.google.gson.Gson;

public class CitiesConverter {

    private Collection<Node> nodes;
    private Collection<Edge> links;
    private Collection<Group> groups;
    private Collection<Constraint> constraints;

    CitiesConverter(Collection<Node> nodes, Collection<Edge> links, Collection<Group> groups, Collection<Constraint> constraints) {
        this.nodes = nodes;
        this.links = links;
        this.groups = groups;
        this.constraints = constraints;
    }

    @Override
    public String toString() {
        return "CitiesConverter{" + "\n" +
                "nodes=" + nodes + "\n" +
                ", links=" + links + "\n" +
                ", groups=" + groups + "\n" +
                ", constraints=" + constraints + "\n" +
                '}';
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        ArrayList<Node> tmp = new ArrayList<>();
        ArrayList<Edge> tmp2 = new ArrayList<>();
        ArrayList<Group> tmp3 = new ArrayList<>();
        ArrayList<Constraint> tmp1 = new ArrayList<>();
        tmp.add(new Node("lol", "lol", "lol", "lol", "lol", 1, 1, "lol"));
        tmp2.add(new Edge(0, 0, 0, "lol", "lol", "lol", 0));
        tmp3.add(new Group(new ArrayList<>(Arrays.asList( 1,2,3 ) ), "Red"));
        tmp1.add(new Constraint("lol", "x", new ArrayList<>(
                Arrays.asList(new Offset("1", "1"), new Offset("2", "2")))));
        CitiesConverter converter = new CitiesConverter(tmp, tmp2, tmp3, tmp1);
        String json = gson.toJson(converter);
        System.out.println("Using Gson.toJson(): \n" + json);
        CitiesConverter converter1 = gson.fromJson(json, CitiesConverter.class);
        System.out.println(converter1);
    }
}
*/