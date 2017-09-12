import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TextImage extends JPanel{

    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static int width = gd.getDisplayMode().getWidth(), height = gd.getDisplayMode().getHeight();
    private static JFrame frame = new JFrame("Text");
    private int fontStyle, fontSize;
    private static String imgPath, txtPath;
    private Font font;
    private StringBuilder builder = new StringBuilder();
    private static TextImage textImage;
    private static String text;

    private TextImage(Font font, int fontStyle, int fontSize) {
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.font = font;
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
        font = font.deriveFont(fontStyle, fontSize);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv;
        String punctuations = ".,:;", tmp;
        String[] words;
        builder.delete(0, builder.length());
        int sw = g2.getFontMetrics().stringWidth(text);
        int sh = g2.getFontMetrics().getHeight();
        int pad = 40, w2 = width-(3*pad);
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
            if (sh + y >= height)
                break;
            h = pad;
            for (String s : words){
                k = s.length();
                while (k > 0 && punctuations.indexOf(s.charAt(k-1)) != -1)
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

    private static void go(Font font, int fontStyle, int fontSize){
        textImage = new TextImage(font, fontStyle, fontSize);
        frame.getContentPane().add(textImage);
        textImage.setVisible(true);
        frame.validate();
        frame.repaint();
        textImage.exportText();
        frame.remove(textImage);
    }

    private static void invert(BufferedImage image){
        java.util.Random r = new java.util.Random();
        double noise;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);
                noise = Math.abs(r.nextGaussian() * Math.sqrt(0.05) + 0.84);
                noise = noise > 1.0 ? 1.0 : noise;
                col = new Color((int)((255 - col.getRed()) * noise), (int)((255 - col.getGreen()) * noise), (int)((255 - col.getBlue()) * noise));
                image.setRGB(x, y, col.getRGB());
            }
        }
    }

    private void exportText(){
        try {
            BufferedImage text = new BufferedImage(textImage.getWidth(), textImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = text.createGraphics();
            BufferedImage img = new BufferedImage(textImage.getWidth(), textImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D2 = img.createGraphics();
            BufferedImage coffee = ImageIO.read(new File("images/coffee-stain.png"));
            BufferedImage ink = ImageIO.read(new File("images/black-ink-splatter.png"));
            BufferedImage splatter = ImageIO.read(new File("images/coffee-splatter.png"));

            float[] scales = {1f, 1f, 1f, 0.5f};
            float[] offsets = new float[4];
            RescaleOp rop = new RescaleOp(scales, offsets, null);

            textImage.paint(graphics2D);
            invert(text);

            graphics2D2.drawImage(text, 0, 0, null);
            graphics2D2.drawImage(ink, rop, width/3+20, height/10);
            graphics2D2.drawImage(coffee, rop, 10, 0);
            graphics2D2.drawImage(splatter, rop, width/6, height/4);

            ImageIO.write(img,"jpg", new File(imgPath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(txtPath)));
            writer.append(builder);
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Must specify 5 arguments: Font, FontStyle(0:Plain, 1:Bold, 2:Italic, font size, " +
                    "text to display, path to save image and path to save ground truth");
            return;
        }
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = e.getAllFonts();
        Font font = null;
        boolean flag = false;
        for (Font f : fonts){
            if (f.getName().equals(args[0])){
                flag = true;
                font = f;
                break;
            }
        }
        if (!flag){
            System.out.println("That font does not exist on this computer, following are the installed fonts:\n");
            for (Font f : fonts)
                System.out.println(f.getName());
            return;
        }
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        text = args[3];
        imgPath = args[4];
        txtPath = args[5];
        go(font, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        frame.dispose();
    }
}