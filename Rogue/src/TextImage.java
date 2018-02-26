import sun.awt.Graphics2Delegate;
import sun.java2d.loops.GraphicsPrimitive;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


/**
 * Created by juan on 04/10/17.
 */
public class TextImage implements Runnable{

    private static int width = 1366/2, height = 768/2, w2 = width / 2 + 48,
            h2 = height/2 + 48, wordsCount, wordsTracker, wordsNum, threadsNum = 4;
    private static String wordsPath = "words", imgPath, prefix = "", spanish = "spanishTest1.txt";
    private static StringBuilder builder = new StringBuilder();
    private static float[] scales = {1f, 1f, 1f, 0.5f}, offsets = new float[4];
    private static RescaleOp rop = new RescaleOp(scales, offsets, null);
    private static BufferedImage coffee, ink, splatter;
    private static ArrayList<Font> fonts = new ArrayList<>();
    private static ArrayList<String> words = new ArrayList<>();
    private static String[] fontNames = {"Andale_Mono.ttf", "Arial.ttf", "cambriai.ttf", "comic.ttf", "Georgia.ttf",
            "LucidaBrightDemibold.ttf", "tahoma.ttf", "trebuc.ttf", "Arial_Black.ttf", "calibri.ttf", "Candara.ttf",
            "Courier_New.ttf", "Impact.ttf", "pala.ttf", "Times_New_Roman.ttf", "Verdana.ttf"},
            openenig = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*",
                    "'", "+", "[", "{", "¿", "¡", "<", "(", ",", ",", ",", ".", "."},
            ending = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'",
                    "+", "]", "}", "?", "!", ">", ")", ",", ",", ",", ".", "."},

    symbols = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'",
                    "+", "]", "}", "?", "!", ">", ")", ",", ".", "[", "{", "¿", "¡", "<", "(", ",", "."};

    private static synchronized void appendWord(String text){
        builder.append(text);
    }

    private static double[][] getYRotation(double theta){
        double[][] rotation = new double[3][3];
        rotation[0][0] = Math.cos(theta); rotation[0][1] = 0; rotation[0][2] = Math.sin(theta);
        rotation[1][0] = 0; rotation[1][1] = 1; rotation[1][2] = 0;
        rotation[2][0] = -Math.sin(theta); rotation[2][1] = 0; rotation[2][2] = Math.cos(theta);
        return rotation;
    }

    private static double[][] getXRotation(double theta){
        double[][] rotation = new double[3][3];
        rotation[0][0] = 1; rotation[0][1] = 0; rotation[0][2] = 0;
        rotation[1][0] = 0; rotation[1][1] = Math.cos(theta); rotation[1][2] = -Math.sin(theta);
        rotation[2][0] = 0; rotation[2][1] = Math.sin(theta); rotation[2][2] = Math.cos(theta);
        return rotation;
    }

    private static double[][] getZRotation(double theta){
        double[][] rotation = new double[3][3];
        rotation[0][0] = Math.cos(theta); rotation[0][1] = -Math.sin(theta); rotation[0][2] = 0;
        rotation[1][0] = Math.sin(theta); rotation[1][1] = Math.cos(theta); rotation[1][2] = 0;
        rotation[2][0] = 0; rotation[2][1] = 0; rotation[2][2] = 1;
        return rotation;
    }

    private double[] getMatrixValue(double[] pos, double[][] rotation){
        double[] values = new double[3];
        double aux;
        for (int i = 0; i < 3; i++){
            aux = 0;
            for (int j = 0; j < 3; j++)
                aux += rotation[i][j]*pos[j];
            values[i] = aux;
        }
        return values;
    }

    private ArrayList<double[]> rotate(double[][] rotation, ArrayList<double[]> points){
        ArrayList<double[]> list = new ArrayList<>();
        for (double[] a : points){
            double[] pos = getMatrixValue(a, rotation);
            list.add(new double[]{pos[0], pos[1], pos[2], a[3]});
        }
        return list;
    }

    private BufferedImage project(ArrayList<double[]> list, int width, int height, int trueW, int trueH){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                image.setRGB(x, y, Color.WHITE.getRGB());

        int aux1 = trueW / 2, aux2 = trueH / 2;
        Comparator<? super double[]> comparator = (Comparator<double[]>) (doubles, t1) -> -Double.compare(doubles[2], t1[2]);
        list.sort(comparator);
        for (double[] a : list){
            long x = Math.round(a[0] + aux1), y = Math.round(a[1] + aux2);
            if (x >= 0 && y >= 0 && x < width && y < height)
                image.setRGB((int)x, (int)y, (int)a[3]);
        }
        return image;
    }

    private void invert(BufferedImage image, boolean hasGaussian){
        java.util.Random r = new java.util.Random();
        double noise = 1;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);
                if (hasGaussian) {
                    noise = Math.abs(r.nextGaussian() * Math.sqrt(0.05) + 0.98);
                    noise = noise > 1.0 ? 1.0 : noise;
                }
                col = new Color((int)((255 - col.getRed()) * noise), (int)((255 - col.getGreen()) * noise),
                        (int)((255 - col.getBlue()) * noise));
                image.setRGB(x, y, col.getRGB());
            }
        }
    }

    private Rectangle paint(Graphics2D g2, Font font, String text) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        GlyphVector gv;
        int sw = g2.getFontMetrics().stringWidth(text);
        int sh = g2.getFontMetrics().getHeight();

        int w = w2 - sw/2 , h = h2 - sh/2;

        AffineTransform affineTransform = g2.getTransform();

        FontRenderContext frc = g2.getFontRenderContext();
        gv = g2.getFont().createGlyphVector(frc, text);
        Rectangle rectangle = gv.getPixelBounds(null, w, h);

        g2.drawString(text, w, h);
        g2.setTransform(affineTransform);
        g2.dispose();
        return rectangle;

    }

    private boolean exportText(BufferedImage frame, Rectangle rectangle, boolean hasGaussian, boolean hasStains,
                               String wordID, String text, String path, double xRotation,
                               double yRotation, double zRotation){
        try {
            List<Integer> order = new ArrayList<>(3);
            order.add(0);
            order.add(1);
            order.add(2);
            Collections.shuffle(order);// random para las giros en X, Y y Z

            invert(frame, hasGaussian);

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics2D2 = img.createGraphics();
            graphics2D2.drawImage(frame, 0, 0, null);
            if (hasStains) {
                graphics2D2.drawImage(ink, rop, -(int) (Math.random() * width / 2), -(int) (Math.random() * height / 3));
                graphics2D2.drawImage(coffee, rop, -(int) (Math.random() * width/2), -(int) (Math.random() * height/3));
                graphics2D2.drawImage(splatter, rop, -(int) (Math.random() * width / 2), -(int) (Math.random() * height / 3));
            }

            double width = rectangle.width;
            double height = rectangle.height;
            BufferedImage tmp = img.getSubimage(rectangle.x, rectangle.y, (int)width, (int)height);

            ArrayList<double[]> list = new ArrayList<>();
            double aux1 = width / 2.0, aux2 = height / 2.0;
            for (int x = 0; x < tmp.getWidth(); x++)
                for (int y = 0; y < tmp.getHeight(); y++)
                    if (tmp.getRGB(x, y) != Color.WHITE.getRGB())
                        list.add(new double[]{x - aux1, y - aux2, 0, tmp.getRGB(x, y)});
            double wd1 = rectangle.width;
            double hd1 = rectangle.height;
            for(int i=0; i<=2; i++) {
                double aux3=order.get(i);
                if ((aux3 == 0)) {
                    hd1 = hd1 * Math.abs(Math.cos(xRotation));
                    list = rotate(getXRotation(xRotation), list);
                }
                if ((aux3 == 1)) {
                    wd1 = wd1 * Math.abs(Math.cos(yRotation));
                    list = rotate(getYRotation(yRotation), list);
                }
                if ((aux3 == 2)) {
                    list = rotate(getZRotation(zRotation), list);
                    double th = hd1;
                    hd1 = wd1 * Math.abs(Math.sin(zRotation)) + hd1 * Math.abs(Math.cos(zRotation));
                    wd1 = wd1 * Math.abs(Math.cos(zRotation)) + th * Math.abs(Math.sin(zRotation));
                }
            }
            double wd = Math.round(wd1);
            double hd = Math.round(hd1);

            BufferedImage dest2 = project(list, (int) wd, (int) hd, (int) wd, (int) hd);

            if(hd>14) {
                dest2 = erode(dest2);
                dest2 = dilate(dest2);
            }

            ImageIO.write(dest2, "jpg", new File(wordsPath + "/" + path + "/" + wordID + ".jpg"));
            return true;
        }
        catch(Exception e) {
            System.out.println("Restaring word " + text + " with id: "+ wordID);
            words.add(text);
            wordsNum++;

        }
        return false;
    }

    private BufferedImage dilate (BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int color = image.getRGB(x, y);
                int blue = (color & 0xff);
                newImage.setRGB(x, y, color);
                if(blue > 0 && Math.random() <= 0.25) {
                    if(x > 0 && ((image.getRGB(x - 1, y)) & 0xff) < blue )
                        newImage.setRGB(x - 1, y, color);
                    if(y > 0 && ((image.getRGB(x, y - 1)) & 0xff) < blue)
                        newImage.setRGB(x, y - 1, color);
                    if(x + 1 < width && ((image.getRGB(x + 1, y)) & 0xff) < blue)
                        newImage.setRGB(x + 1, y, color);
                    if(y + 1 < height && ((image.getRGB(x, y + 1)) & 0xff) < blue)
                        newImage.setRGB(x, y + 1, color);
                }
            }
        }
        return newImage;
    }

    private BufferedImage erode (BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int color = image.getRGB(x, y);
                int blue = (color & 0xff);
                newImage.setRGB(x, y, color);
                if(Math.random() <= 0.5) {
                    if (x > 0 && ((image.getRGB(x - 1, y)) & 0xff) > blue)
                        newImage.setRGB(x - 1, y, color);
                    if (y > 0 && ((image.getRGB(x, y - 1)) & 0xff) > blue)
                        newImage.setRGB(x, y - 1, color);
                    if (x + 1 < width && ((image.getRGB(x + 1, y)) & 0xff) > blue)
                        newImage.setRGB(x + 1, y, color);
                    if (y + 1 < height && ((image.getRGB(x, y + 1)) & 0xff) > blue)
                        newImage.setRGB(x, y + 1, color);
                }
            }
        }
        return newImage;
    }

    private void go(Font font, int fontSize, int bold, int italic, boolean isUnderline, boolean isStrikethrough,
                    boolean hasGaussian, boolean hasStains, String wordID, String text, String path){
        int fontStyle = bold + italic;
        font = font.deriveFont(fontStyle, fontSize);
        Map attributes = font.getAttributes();
        if (isUnderline) attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (isStrikethrough) attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        font = font.deriveFont(attributes);
        double xRotation = 0, yRotation = 0, zRotation = 0;
        if (Math.random() <= 0.25){
            double d = Math.random() * Math.PI * (3.2 / 18.0);
            int ne = Math.random() <= 0.5 ? 1 : -1;
            xRotation = ne * d;
        }
        if (Math.random() <= 0.25){
            double d = Math.random() * Math.PI * (3.2 / 18.0);
            int ne = Math.random() <= 0.5 ? 1 : -1;
            yRotation = ne * d;
        }
        if (Math.random() <= 0.2) {
            double d = Math.random() * Math.PI * (0.7 / 18.0);
            int ne = Math.random() <= 0.5 ? 1 : -1;
            zRotation = ne * d;
        }

        BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        if (exportText(frame, paint(frame.createGraphics(), font, text), hasGaussian, hasStains,
                wordID, text, path, xRotation, yRotation,zRotation)) {
            appendWord(wordID + "\t" + text + "\t" + wordsPath + "/" + path + "/" + wordID + ".jpg" + "\t" +
                    font.getName() + "\t" + fontSize + "\t" + fontStyle + "\t" + isStrikethrough + "\t" + isUnderline
                    + "\t" + hasGaussian + "\t" + hasStains + "\t" + xRotation +
                    "\t" + yRotation + "\t" + zRotation +"\n");
        }
    }

    private synchronized String[] develop(){
        if (wordsCount >= wordsNum) return null;
        String wordID = Integer.toString(wordsCount) + prefix;
        String text = words.get(wordsCount);
        String path = imgPath;
        wordsCount++;
        if (wordsCount - wordsTracker >= 1000) {
            try {
                Files.createDirectories(Paths.get(wordsPath + "/" + wordID));
            } catch (IOException e) {
                wordsCount--;
                return null;
            }
            imgPath = wordID;
            path = imgPath;
            wordsTracker = wordsCount;
        }
        return new String[]{wordID, text, path};
    }

    @Override
    public void run() {
        while (wordsCount < wordsNum) {
            String[] tmp = develop();
            if (tmp != null)
                go(fonts.get((int) (Math.random() * fonts.size())), (int) (Math.random() * 18) + 9,
                        Math.random() <= 0.12 ? 1 : 0, Math.random() <= 0.14 ? 2 : 0, Math.random() <= 0.1,
                        Math.random() <= 0.12, Math.random() <= 0.15,
                        Math.random() <= 0.25, tmp[0], tmp[1], tmp[2]);
        }
    }

    private static void quiet(){
        try {
            //int times = 56, nums = 12000, sym = 112, digits = 200;
            int times = 1, nums = 0, sym = 0, digits = 0;
            long maxNum = Long.MAX_VALUE;
            int f1 = 99999999, f2 = 999999999;
            String str, tmp;
            BufferedReader reader = new BufferedReader(new FileReader(new File(spanish)));
            while ((str = reader.readLine()) != null && !str.isEmpty()) {
                for (int i = 0; i < times; i++) {
                    tmp = str;
                    double d = Math.random();
                    if (d >= 0.75){
                        if (d >= 0.9)
                            tmp = tmp.toUpperCase();
                        else
                            tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
                    }
                    if (Math.random() <= 0.12)
                        tmp = openenig[(int) (Math.random() * openenig.length)] + tmp; //simbolo random al principio de la palabra
                    if (Math.random() <= 0.12)
                        tmp = tmp + ending[(int) (Math.random() * ending.length)]; //simbolo random al final de la palabra
                    words.add(tmp);
                }
            }
            for (int j = 0; j < digits; j++)
                for (int i = 0; i <= 9; i++)
                    words.add(Integer.toString(i));

            for (int i = 0; i < sym; i++)
                words.addAll(Arrays.asList(symbols));
            long k;
            for (int i = 0; i < nums/3; i++){
                k = (long)(Math.random() * maxNum);
                words.add(Long.toString(k));
                words.add((int)(Math.random()*f1) + "." + (int)(Math.random()*f2));
                words.add((int)(Math.random()*f1) + "," + (int)(Math.random()*f2));
            }
            reader.close();
            wordsNum = words.size();
            System.out.println(wordsNum + " words will be generated");

            for (String b : fontNames)
                fonts.add(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/"+b)));
            fonts.add(Font.createFont(Font.TYPE1_FONT, new File("fonts/CenturySchoolbookLItalic.pfb")));

            coffee = ImageIO.read(new File("images/coffee-stain.png"));
            ink = ImageIO.read(new File("images/black-ink-splatter.png"));
            splatter = ImageIO.read(new File("images/coffee-splatter.png"));
            builder.delete(0, builder.length());
            wordsCount = 0; wordsTracker = 0;
            imgPath = Integer.toString(wordsCount);

            Files.createDirectories(Paths.get(wordsPath));
            Files.createDirectories(Paths.get(wordsPath + "/" + imgPath));

            ArrayList<Thread> threads = new ArrayList<>();
            TextImage textImage = new TextImage();
            for (int i = 0; i < threadsNum; i++) {
                threads.add(new Thread(textImage));
                threads.get(i).setName(Integer.toString(i));
            }
            for (Thread thread : threads)
                thread.start();
            for (Thread thread : threads)
                thread.join();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(wordsPath, "words.tsv")));
            writer.append(builder);
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        } catch (FontFormatException e) {
            System.out.println("Failed to open a font");
            e.printStackTrace();
        }
        catch (InterruptedException interrupted){
            System.out.println("Fail joinig threads");
            interrupted.printStackTrace();
        }
    }

    public static void main(String[] args){
        if (args.length >= 3){
            prefix = args[0];
            wordsPath = wordsPath + prefix.toUpperCase();
            spanish = args[1];
            threadsNum = Integer.parseInt(args[2]);
        }
        quiet();
    }
}