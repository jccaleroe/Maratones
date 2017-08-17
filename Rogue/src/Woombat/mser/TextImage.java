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

        String text = "Hola Cigüeña voladora y ...";
        sw = g2.getFontMetrics().stringWidth(text);
        sh = g2.getFontMetrics().getHeight();

        g2.drawString(text, width/2 - sw/2, height/2 - sh/2);
    }

    private static void go(String fontName, int fontStyle, int fontSize){
        TextImage textImage = new TextImage(fontName, fontStyle, fontSize);
        frame.getContentPane().add(textImage);
        textImage.setVisible(true);
        frame.validate();
        frame.repaint();
        textImage.exportText();
        frame.remove(textImage);
        frame.validate();
        frame.repaint();
    }

    private void exportText(){
        try {
            BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            frame.paint(graphics2D);
            ImageIO.write(image,"jpeg", new File("/home/juan/Pictures/mser/"+counter+".jpeg"));
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
        for (counter = 1; counter <= 9; counter++)
            go(Font.SERIF, Font.PLAIN, 10*counter);
        frame.dispose();
    }
}