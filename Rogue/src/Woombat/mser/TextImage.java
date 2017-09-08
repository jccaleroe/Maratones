package Woombat.mser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TextImage extends JPanel{

    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static int width = gd.getDisplayMode().getWidth();
    private static int height = gd.getDisplayMode().getHeight();
    private static JFrame frame = new JFrame("Text");
    private int sw, sh, fontStyle, fontSize;
    private static int counter;
    private String fontName;

    private TextImage(String fontName, int fontStyle, int fontSize) {
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.fontName = fontName;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font(fontName, fontStyle, fontSize);
        g2.setFont(font);

        String text = "Ser o no ser, esa es la cuestión\n. ¿Cuál es más digna acción del ánimo, sufrir los tiros penetrantes de la fortuna injusta, u oponer los brazos a este torrente de calamidades, y darlas fin con atrevida resistencia? Morir es dormir. ¿No más? ¿Y por un sueño, diremos, las aflicciones se acabaron y los dolores sin número, patrimonio de nuestra débil naturaleza?... Este es un término que deberíamos solicitar con ansia. Morir es dormir... y tal vez soñar. Sí, y ved aquí el grande obstáculo, porque el considerar que sueños podrán ocurrir en el silencio del sepulcro, cuando hayamos abandonado este despojo mortal, es razón harto poderosa para detenernos. Esta es la consideración que hace nuestra infelicidad tan larga. ¿Quién, si esto no fuese, aguantaría la lentitud de los tribunales, la insolencia de los empleados, las tropelías que recibe pacífico el mérito de los hombres más indignos, las angustias de un mal pagado amor, las injurias y quebrantos de la edad, la violencia de los tiranos, el desprecio de los soberbios? Cuando el que esto sufre, pudiera procurar su quietud con solo un puñal. ¿Quién podría tolerar tanta opresión, sudando, gimiendo bajo el peso de una vida molesta si no fuese que el temor de que existe alguna cosa más allá de la Muerte (aquel país desconocido de cuyos límites ningún caminante torna) nos embaraza en dudas y nos hace sufrir los males que nos cercan.";
        sw = g2.getFontMetrics().stringWidth(text);
        sh = g2.getFontMetrics().getHeight();
        int pad = 24, w2 = width-(2*pad);
        int line = sw / w2;
        int chars = (w2 * text.length()) / sw, a = 0, b = chars, c;
        for (int i = 0; i <= line; i++){
            String aux = text.substring(a, Math.min(b, text.length()));
            for (int j = aux.length()-1; j >= 0; j--){
                if (aux.charAt(j) == ' ' || aux.charAt(j) == '.') {
                    b -= aux.length() - 1 - j;
                    aux = aux.substring(0, j + 1);
                    break;
                }
            }
            a = b;
            b += chars;
            g2.drawString(aux, pad/2, (sh)*(i+1));
        }
        //g2.drawString(text, 0, sh);
    }

    private static void go(String fontName, int fontStyle, int fontSize){
        TextImage textImage = new TextImage(fontName, fontStyle, fontSize);
        frame.getContentPane().add(textImage);
        textImage.setVisible(true);
        //frame.validate();
        //frame.repaint();
        //textImage.exportText();
        //frame.remove(textImage);
        //frame.validate();
        //frame.repaint();
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void exportText(){
        try {
            BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            frame.paint(graphics2D);
            ImageIO.write(image,"jpeg", new File("/home/juan/Documents/Maratones/Rogue/src/Woombat/mser"+counter+".jpeg"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/juan/Pictures/mser/"+counter+".txt")));
            writer.append(Integer.toString(sw)); //width
            writer.newLine();
            writer.append(Integer.toString(sh)); //height
            writer.newLine();
            writer.append(Integer.toString(width/2 - sw/2)); //x
            writer.newLine();
            writer.append(Integer.toString(height/2 - sh/2)); //y
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        go(Font.SERIF, Font.PLAIN, 30);
        //frame.dispose();
    }
}