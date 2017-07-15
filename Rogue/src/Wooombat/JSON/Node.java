package Wooombat.JSON;


class Node {

    private String name, shape, color, text, img, tooltip;
    private float width, height;

    Node(String name, String shape, String color, String text, String img, float width, float height, String tooltip) {
        this.name = name;
        this.shape = shape;
        this.color = color;
        this.text = text;
        this.img = img;
        this.width = width;
        this.height = height;
        this.tooltip = tooltip;
    }
}
