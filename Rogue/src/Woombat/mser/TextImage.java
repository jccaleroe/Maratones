package Woombat.mser;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
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
    private int fontStyle;
    private int fontSize;
    private static int counter;
    private String fontName;
    private StringBuilder builder = new StringBuilder();
    private static TextImage textImage;

    private TextImage(String fontName, int fontStyle, int fontSize) {
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.fontName = fontName;
    }

    private void appendBuilder(Rectangle rectangle){
        builder.append(rectangle.x);
        builder.append(" ");
        builder.append(rectangle.y);
        builder.append(" ");
        builder.append(rectangle.width);
        builder.append(" ");
        builder.append(rectangle.height);
        builder.append("\n");
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font(fontName, fontStyle, fontSize);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv;
        String punctuations = ".,:;", tmp;
        String[] words;
        builder.delete(0, builder.length());
        String text = "Ser o no ser, esa es la cuestión, que es más indigno para el alma?, sufrir los golpes y dardos de la insultate fortuna, o haciendole frente acabar con ellos, comer domir, nada más, y pensar que con un sueño le damos fin a los interminables dolores del alma y del corazón.";
        int sw = g2.getFontMetrics().stringWidth(text);
        int sh = g2.getFontMetrics().getHeight();
        int pad = 38, w2 = width-(2*pad);
        int lines = sw / w2;
        int chars = (w2 * text.length()) / sw, a = 0, b = chars;
        int h, y, space = g2.getFontMetrics().stringWidth(" "), k;
        Rectangle rectangle;
        for (int i = 0; i <= lines; i++){
            String aux = text.substring(a, Math.min(b, text.length()));
            for (int j = aux.length()-1; j >= 0; j--){
                if (Character.isWhitespace(aux.charAt(j)) || a+j+1 == text.length()) {
                    b -= aux.length() - j - 1;
                    aux = aux.substring(0, j + 1);
                    break;
                }
            }
            a = b;
            b += chars;
            aux = aux.trim();
            words = aux.split(" ");
            y = (sh)*(i+1);
            h = pad;
            for (String s : words){
                k = s.length();
                while (punctuations.indexOf(s.charAt(k-1)) != -1)
                    k--;
                tmp = s.substring(0, k);
                gv = g2.getFont().createGlyphVector(frc, tmp);
                rectangle = gv.getPixelBounds(null, h, y);
                h += g2.getFontMetrics().stringWidth(tmp);
                appendBuilder(rectangle);
                tmp = s.substring(k, s.length());
                if (!tmp.isEmpty()) {
                    gv = g2.getFont().createGlyphVector(frc, tmp);
                    rectangle = gv.getPixelBounds(null, h, y);
                    h += g2.getFontMetrics().stringWidth(tmp);
                    appendBuilder(rectangle);
                }
                h += space;
            }
            g2.drawString(aux, pad, y);
        }
        g2.dispose();
    }

    private static void go(String fontName, int fontStyle, int fontSize){
        textImage = new TextImage(fontName, fontStyle, fontSize);
        frame.getContentPane().add(textImage);
        textImage.setVisible(true);
        frame.validate();
        frame.repaint();
        textImage.exportText();
        frame.remove(textImage);
        //frame.validate();
        //frame.repaint();
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void exportText(){
        try {
            BufferedImage image = new BufferedImage(textImage.getWidth(), textImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            textImage.paint(graphics2D);
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int rgba = image.getRGB(x, y);
                    Color col = new Color(rgba, true);
                    col = new Color(255 - col.getRed(),
                            255 - col.getGreen(),
                            255 - col.getBlue());
                    image.setRGB(x, y, col.getRGB());
                }
            }
            ImageIO.write(image,"jpg", new File("/home/juan/Documents/Maratones/Rogue/src/Woombat/mser"+counter+".jpg"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/juan/Documents/Maratones/Rogue/src/Woombat/mser"+counter+".txt")));
            writer.append(builder);
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
        for (counter = 1; counter <= 1; counter++)
            go(Font.SERIF, Font.PLAIN, 30*counter);
        frame.dispose();
    }
}