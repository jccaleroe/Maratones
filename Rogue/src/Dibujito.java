import processing.core.PApplet;
import processing.core.PImage;


public class Dibujito extends PApplet{

    PImage image;

    @Override
    public void settings(){
        size ( 400, 400, P3D );
    }

    @Override
    public void draw(){

    }

    @Override
    public void setup() {
        image = loadImage("/home/juan/Documents/Maratones/Rogue/words/0/3.jpg");
        noFill();
        fill(154, 143, 236);
        int r1 = 300, r2 = 200, x = 200, y = 200;
        ellipse(x, y, r1, r1);
        ellipse(x, y, r2, r2);
        rotateX(0.5f);
        image.save("hola.jpg");
        //rotateY(0.1f);
        //rotateZ(0.1f);
        /*float d = (r1 - r2) / 2;
        float r = (r1 + r2) / 2;
        for ( float i = 0; i <= (4 * Math.PI); i += 0.1){
            ellipse( d * cos(i) + x,  d * sin(i) + y, r, r );
        }*/
    }

    public static void main(String[] args) {
        PApplet.main("Dibujito");
    }
}