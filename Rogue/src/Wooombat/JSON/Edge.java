package Wooombat.JSON;


class Edge {

    private int source, target;
    private float value, w;
    private String type, color, text;

    Edge(int source, int target, float value, String type, String color, String text, float w ) {
        this.source = source;
        this.target = target;
        this.value = value;
        this.w = w;
        this.type = type;
        this.color = color;
        this.text = text;
    }
}
