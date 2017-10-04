import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TextImage extends JPanel implements Runnable{

    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static int width = gd.getDisplayMode().getWidth(), height = gd.getDisplayMode().getHeight(), wordsCount,
            wordsTracker, wordsNum;
    private JFrame frame = new JFrame("Text");
    private int fontStyle, fontSize;
    private static String wordsPath = "words", imgPath;
    private String text, wordID;
    private Font font;
    private static StringBuilder builder = new StringBuilder();
    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private Boolean isUnderline, isStrikethrough, hasGaussian, hasStains, exporting;
    private static float[] scales = {1f, 1f, 1f, 0.5f}, offsets = new float[4];
    private static RescaleOp rop = new RescaleOp(scales, offsets, null);
    private static BufferedImage coffee, ink, splatter;
    private static Font[] fonts;
    private static ArrayList<String> words = new ArrayList<>();
    private static String[] fontNames = {"Andale Mono", "Arial", "Arial Black", "Calibri", "Cambria Italic", "Comic Sans MS",
            "Courier New", "Tahoma", "Times New Roman", "Verdana", "Georgia", "Impact", "Trebuchet MS", "Candara",
            "Palatino Linotype", "Century Schoolbook L Italic", "Lucida Bright Demibold"};

    private TextImage(){}

    private static synchronized void appendWord(String wordID, String str, String path){
        builder.append(wordID); builder.append("\t"); builder.append(str); builder.append(",");
        builder.append(path); builder.append("\n");
    }

    private void invert(BufferedImage image){
        java.util.Random r = new java.util.Random();
        double noise = 1;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);
                if (hasGaussian) {
                    noise = Math.abs(r.nextGaussian() * Math.sqrt(0.05) + 0.92);
                    noise = noise > 1.0 ? 1.0 : noise;
                }
                col = new Color((int)((255 - col.getRed()) * noise), (int)((255 - col.getGreen()) * noise),
                        (int)((255 - col.getBlue()) * noise));
                image.setRGB(x, y, col.getRGB());
            }
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        font = font.deriveFont(fontStyle, fontSize);
        Map attributes = font.getAttributes();
        if (isUnderline)
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (isStrikethrough)
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        font = font.deriveFont(attributes);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv;
        String tmp;
        String[] words;
        boolean isExporting = exporting;
        //if (!isExporting)
            rectangles.clear();
        int sw = g2.getFontMetrics().stringWidth(text);
        int sh = g2.getFontMetrics().getHeight();
        int pad = 40, w2 = width - (3 * pad);
        int lines = sw / w2;
        int chars = (w2 * text.length()) / sw, a = 0, b = chars;
        int h, y, space = g2.getFontMetrics().stringWidth(" ");
        Rectangle rectangle;
        for (int i = 0; i <= lines; i++) {
            String aux = text.substring(a, Math.min(b, text.length()));
            for (int j = aux.length() - 1; j >= 0; j--) {
                if (Character.isWhitespace(aux.charAt(j)) || a + j + 1 == text.length()) {
                    b -= aux.length() - j - 1;
                    aux = aux.substring(0, j + 1);
                    break;
                }
            }
            a = b;
            b += chars;
            aux = aux.trim();
            words = aux.split(" ");
            y = (sh) * (i + 1);
            if (sh + y >= height)
                break;
            h = pad;
            for (String s : words) {
                tmp = s.substring(0, s.length());
                gv = g2.getFont().createGlyphVector(frc, tmp);
                rectangle = gv.getPixelBounds(null, h, y);
                if (!rectangle.isEmpty())
                    rectangles.add(rectangle);
                h += g2.getFontMetrics().stringWidth(tmp) + space;
            }
            g2.drawString(aux, pad, y);
        }
        g2.dispose();
    }

    private void exportText(){
        exporting = true;
        try {
            BufferedImage text = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = text.createGraphics();

            this.paint(graphics2D);
            invert(text);

            BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics2D2 = img.createGraphics();
            graphics2D2.drawImage(text, 0, 0, null);
            if (hasStains) {
                graphics2D2.drawImage(ink, rop, -(int)(Math.random()*width / 2), -(int)(Math.random()*height / 3));
                graphics2D2.drawImage(coffee, rop, -(int)(Math.random()*width), -(int)(Math.random()*height));
                graphics2D2.drawImage(splatter, rop, -(int)(Math.random()*width / 2), -(int)(Math.random()*height / 3));
            }
            int aux1, aux2, aux3, aux4;
            for (Rectangle rectangle : rectangles) {
                aux1 = Math.max(0,rectangle.x-4);
                if (aux1 == 0) aux1 = rectangle.x;
                aux2 = Math.max(0,rectangle.y-4);
                if (aux2 == 0) aux2 = rectangle.y;
                aux3 = aux1 != rectangle.x ? Math.min(rectangle.width+8, img.getWidth()) : Math.min(rectangle.width+4, img.getWidth());
                aux4 = aux2 != rectangle.y ? Math.min(rectangle.height+8, img.getHeight()) : Math.min(rectangle.height+4, img.getHeight());
                BufferedImage dest = img.getSubimage(aux1, aux2, aux3, aux4);
                ImageIO.write(dest, "jpg", new File(wordsPath + "/" + imgPath + "/" + wordID + ".jpg"));
                appendWord(wordID, this.text, wordsPath + "/" + imgPath + "/" + wordID + ".jpg");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        exporting = false;
    }

    private void go(){
        frame.getContentPane().add(this);
        frame.validate();
        frame.repaint();
        exportText();
        frame.remove(this);
    }

    private void initialize(String fontName, int fontSize, int bold, int italic, boolean isUnderline,
                                   boolean isStrikethrough, boolean hasGaussian, boolean hasStains){
        Font font = null;
        boolean flag = false;
        for (Font f : fonts){
            if (f.getName().equals(fontName)){
                flag = true;
                font = f;
                break;
            }
        }
        if (!flag){
            System.out.println("That font does not exist on this computer, next are the installed fonts:\n");
            for (Font f : fonts)
                System.out.println(f.getName());
            return;
        }
        int fontStyle = bold + italic;
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.isUnderline =  isUnderline;
        this.isStrikethrough = isStrikethrough;
        this.hasGaussian = hasGaussian;
        this.hasStains = hasStains;
        this.font = font;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        go();
    }

    private synchronized boolean develop(){
        if (wordsCount >= wordsNum)
            return false;
        wordID = Integer.toString(wordsCount);
        text = words.get(wordsCount);
        wordsCount++;
        if (wordsCount - wordsTracker >= 1000) {
            wordsTracker = wordsCount;
            imgPath = wordID;
            try {
                Files.createDirectories(Paths.get(wordsPath + "/" + imgPath));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (wordsCount < wordsNum) {
            if (develop()) {
                //System.out.println(Thread.currentThread().getName() + " with word " + wordID);
                initialize(fontNames[(int) (Math.random() * fontNames.length)], (int) (Math.random() * 16) + 9, Math.random() <= 0.2 ? 1 : 0,
                        Math.random() <= 0.2 ? 2 : 0, Math.random() <= 0.2, Math.random() <= 0.2,
                        Math.random() <= 0.6, Math.random() <= 0.9);
            }
        }
    }

    private static void quiet(){
        try {
            String str;
            BufferedReader reader = new BufferedReader(new FileReader(new File("/home/juan/Desktop/spanish.txt")));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("spanish.txt")));
            String[] openenig = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'", "+",
                "[", "{", "¿", "¡", "<", "("};
            String[] ending = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'", "+",
                "]", "}", "?", "!", ">", ")"};

            while ((str = reader.readLine()) != null) {
                //words.add(str);
                if (Math.random() <= 0.2)
                    str = openenig[(int) (Math.random() * openenig.length)] + str;
                if (Math.random() <= 0.2)
                    str = str + ending[(int) (Math.random() * ending.length)];
                writer.append(str);
                writer.append("\n");
            }
            /*
            wordsNum = words.size();
            reader.close();

            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            fonts = e.getAllFonts();
            coffee = ImageIO.read(new File("images/coffee-stain.png"));
            ink = ImageIO.read(new File("images/black-ink-splatter.png"));
            splatter = ImageIO.read(new File("images/coffee-splatter.png"));
            builder.delete(0, builder.length());
            wordsCount = 0;
            wordsTracker = 0;
            imgPath = Integer.toString(wordsCount);

            Files.createDirectories(Paths.get(wordsPath));
            Files.createDirectories(Paths.get(wordsPath + "/" + imgPath));

            TextImage textImage = new TextImage();
            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                threads.add(new Thread(textImage));
                threads.get(i).setName(Integer.toString(i));
            }
            for (Thread thread : threads)
                thread.start();
            try {
                for (Thread thread : threads)
                    thread.join();
            }catch (InterruptedException interrupted){
                interrupted.printStackTrace();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(wordsPath, "words.csv")));
            writer.append(builder);
            writer.close();
*/
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        quiet();
    }
}