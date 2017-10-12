import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by juan on 04/10/17.
 */
public class TextImage implements Runnable{

    private static int width = 1366/2, height = 768/2, w2 = width / 2 + 48,
            h2 = height/2 + 48, wordsCount, wordsTracker, wordsNum, threadsNum = 4;
    private BufferedImage frame;
    private int fontStyle, fontSize;
    private static String wordsPath = "words", imgPath, prefix = "", spanish = "spanishTest.txt";
    private String text, wordID;
    private Font font;
    private static StringBuilder builder = new StringBuilder();
    private Rectangle rectangle;
    private Boolean isUnderline, isStrikethrough, hasGaussian, hasStains;
    private static float[] scales = {1f, 1f, 1f, 0.5f}, offsets = new float[4];
    private static RescaleOp rop = new RescaleOp(scales, offsets, null);
    private static BufferedImage coffee, ink, splatter;
    private static ArrayList<Font> fonts = new ArrayList<>();
    private static ArrayList<String> words = new ArrayList<>();
    private static String[] fontNames = {"Andale Mono", "Arial", "Arial Black", "Calibri", "Cambria Italic",
            "Comic Sans MS", "Courier New", "Tahoma", "Times New Roman", "Verdana", "Georgia", "Impact", "Trebuchet MS",
            "Candara", "Palatino Linotype", "Century Schoolbook L Italic", "Lucida Bright Demibold"},
            openenig = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*",
                    "'", "+", "[", "{", "¿", "¡", "<", "(", ",", ",", ",", ".", "."},
            ending = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'",
                    "+", "]", "}", "?", "!", ">", ")", ",", ",", ",", ".", "."},
            symbols = {"@", "#", "-", "_", ".", ";", ",", "\"", "$",  "%", "&", "/", "\\", "~", "|", "¬", "°", "*", "'",
                    "+", "]", "}", "?", "!", ">", ")", ",", ".", "[", "{", "¿", "¡", "<", "(", ",", "."};

    private static synchronized void appendWord(String wordID, String str, String path){
        builder.append(wordID); builder.append("\t"); builder.append(str); builder.append("\t");
        builder.append(path); builder.append("\n");
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

    private BufferedImage project(ArrayList<double[]> list, int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                image.setRGB(x, y, Color.WHITE.getRGB());
        double x, y;
        for (double[] a : list){
            x = a[0]; y = a[1];
            if (x >= 0 && y >= 0 && x < width && y < height)
                image.setRGB((int)a[0], (int)a[1], (int)a[3]);
        }
        return image;
    }

    private void invert(BufferedImage image){
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

    private void paint(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        font = font.deriveFont(fontStyle, fontSize);
        Map attributes = font.getAttributes();
        if (isUnderline)
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (isStrikethrough)
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        font = font.deriveFont(attributes);
        g2.setFont(font);
        GlyphVector gv;
        int sw = g2.getFontMetrics().stringWidth(text);
        int sh = g2.getFontMetrics().getHeight();
        int w = w2 - sw/2 - 40, h = h2 - sh/2 - 50;
        AffineTransform affineTransform = g2.getTransform();
        if (Math.random() <= 0.08) {
            double d = Math.random();
            int ne = Math.random() <= 0.5 ? 1 : -1;
            if (d >= 0.4) {
                if (ne == -1)
                    d = -0.4;
                else if (d > 0.48)
                    d = 0.48 * ne;
            }
            else
                d *= ne;
            g2.shear(d, 0);
        }
        if (Math.random() <= 0.08) {
            double d = Math.random();
            int ne = Math.random() <= 0.5 ? 1 : -1;
            if (d >= 0.24) {
                if (ne == -1)
                    d = -0.24;
                else if (d > 0.28)
                    d = 0.28 * ne;
            }
            else
                d *= ne;
            g2.rotate(d);
        }
        FontRenderContext frc = g2.getFontRenderContext();
        gv = g2.getFont().createGlyphVector(frc, text);
        rectangle = gv.getPixelBounds(null, w, h);
        g2.drawString(text, w, h);
        g2.setTransform(affineTransform);
        g2.dispose();
    }

    private void exportText(){
        try {
            invert(frame);
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics2D2 = img.createGraphics();
            graphics2D2.drawImage(frame, 0, 0, null);
            if (hasStains) {
                graphics2D2.drawImage(ink, rop, -(int)(Math.random()*width / 2), -(int)(Math.random()*height / 3));
                graphics2D2.drawImage(coffee, rop, -(int)(Math.random()*width), -(int)(Math.random()*height));
                graphics2D2.drawImage(splatter, rop, -(int)(Math.random()*width / 2), -(int)(Math.random()*height / 3));
            }
            int aux1, aux2, aux3, aux4, augmentation = 4;
            aux1 = Math.max(0,rectangle.x-augmentation);
            if (aux1 == 0) aux1 = rectangle.x;
            aux2 = Math.max(0,rectangle.y-augmentation);
            if (aux2 == 0) aux2 = rectangle.y;
            aux3 = aux1 != rectangle.x ? Math.min(rectangle.width+augmentation*2,
                    img.getWidth()) : Math.min(rectangle.width+augmentation, img.getWidth());
            aux4 = aux2 != rectangle.y ? Math.min(rectangle.height+augmentation*2,
                    img.getHeight()) : Math.min(rectangle.height+augmentation, img.getHeight());
            BufferedImage dest = img.getSubimage(aux1, aux2, aux3, aux4);
            ArrayList<double[]> list = new ArrayList<>();
            for (int x = 0; x < dest.getWidth(); x++)
                for (int y = 0; y < dest.getHeight(); y++)
                    if (dest.getRGB(x, y) != Color.WHITE.getRGB())
                        list.add(new double[]{x, y, 0, dest.getRGB(x, y)});
            double w = dest.getWidth(), h = dest.getHeight();
            boolean flag = false;
            if (Math.random() <= 0.08){
                flag = true;
                double d = Math.random();
                int ne = Math.random() <= 0.5 ? 1 : -1;
                d = d > 0.4 ? 0.4 * ne : d * ne;
                h = h - Math.abs(d) * 10;
                list = rotate(getXRotation(d), list);
            }
            if (Math.random() <= 0.08){
                flag = true;
                double d = Math.random();
                int ne = Math.random() <= 0.5 ? 1 : -1;
                d = d > 0.4 ? 0.4 * ne : d * ne;
                w = w - Math.abs(d) * 10;
                list = rotate(getYRotation(d), list);
            }
            if (flag) {
                BufferedImage dest2 = project(list, (int) w, (int) h);
                ImageIO.write(dest2, "jpg", new File(wordsPath + "/" + imgPath + "/" + wordID + ".jpg"));
            }
            else
                ImageIO.write(dest, "jpg", new File(wordsPath + "/" + imgPath + "/" + wordID + ".jpg"));
        }
        catch(Exception e) {
            System.out.println("Restarting word " + text + " with ID " + wordID);
            exportText();
        }
    }

    private void go(){
        frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        paint(frame.createGraphics());
        exportText();
        appendWord(wordID, this.text, wordsPath + "/" + imgPath + "/" + wordID + ".jpg");
    }

    private void initialize(Font font, int fontSize, int bold, int italic, boolean isUnderline,
                                   boolean isStrikethrough, boolean hasGaussian, boolean hasStains){
        int fontStyle = bold + italic;
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
        if (wordsCount >= wordsNum) return false;
        wordID = Integer.toString(wordsCount) + prefix;
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
        while (wordsCount < wordsNum)
            if (develop())
                initialize(fonts.get((int) (Math.random() * fonts.size())), (int) (Math.random() * 18) + 9,
                        Math.random() <= 0.16 ? 1 : 0, Math.random() <= 0.16 ? 2 : 0, Math.random() <= 0.12,
                        Math.random() <= 0.08, Math.random() <= 0.08, Math.random() <= 0.16);
    }

    private static void quiet(){
        try {
            int times = 52, nums = 8100, sym = 112, digits = 200;
            long maxNum = Long.MAX_VALUE;
            int f1 = 99999999, f2 = 999999999;
            String str, tmp;
            BufferedReader reader = new BufferedReader(new FileReader(new File(spanish)));
            while ((str = reader.readLine()) != null && !str.isEmpty()) {
                for (int i = 0; i < times; i++) {
                    tmp = str;
                    if (Math.random() <= 0.12)
                        tmp = openenig[(int) (Math.random() * openenig.length)] + tmp;
                    if (Math.random() <= 0.12)
                        tmp = tmp + ending[(int) (Math.random() * ending.length)];
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

            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font[] fontsTmp = e.getAllFonts();

            for (String a : fontNames){
                boolean flag = false;
                for (Font f : fontsTmp){
                    if (f.getName().equals(a)){
                        flag = true;
                        fonts.add(f);
                        break;
                    }
                }
                if (!flag){
                    System.out.println("That font does not exist on this computer, next are the installed fonts:\n");
                    for (Font f : fonts)
                        System.out.println(f.getName());
                    System.out.println("Or install: " + a);
                    return;
                }
            }
            coffee = ImageIO.read(new File("images/coffee-stain.png"));
            ink = ImageIO.read(new File("images/black-ink-splatter.png"));
            splatter = ImageIO.read(new File("images/coffee-splatter.png"));
            builder.delete(0, builder.length());
            wordsCount = 0;
            wordsTracker = 0;
            imgPath = Integer.toString(wordsCount);

            Files.createDirectories(Paths.get(wordsPath));
            Files.createDirectories(Paths.get(wordsPath + "/" + imgPath));

            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < threadsNum; i++) {
                threads.add(new Thread(new TextImage()));
                threads.get(i).setName(Integer.toString(i));
            }
            try {
                for (Thread thread : threads) {
                    thread.start();
                    Thread.sleep(100);
                }
                for (Thread thread : threads)
                    thread.join();
            }catch (InterruptedException interrupted){
                interrupted.printStackTrace();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(wordsPath, "words.tsv")));
            writer.append(builder);
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        if (args.length >= 3){
            prefix = args[0];
            spanish = args[1];
            threadsNum = Integer.parseInt(args[2]);
        }
        quiet();
    }
}