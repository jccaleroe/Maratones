package HeadFirstJava.chap13;

import javax.swing.*;
import java.awt.*;

public class MyFirstPanel {
    public static void main(String[] args) {
        MyFirstPanel gui = new MyFirstPanel();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        JButton button = new JButton("shock me");
        JButton buttonTwo = new JButton("bliss");
        JButton buttonThree = new JButton("huh");
        JButton buttonFour = new JButton("boooya");
        panel.add(button);
        panel.add(buttonTwo);
        panel.add(buttonThree);
        panel.add(buttonFour);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
