package learn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GraphicTest extends JPanel implements ActionListener {
    protected static int margin = 100;
    protected static int windowSize = 1000;

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 32);
        g2.setFont(font);
        g2.drawString("2-Point-Visibility Computer", 325, 50);
        // Drawing display border
        g2.drawLine(margin,margin,windowSize-margin,margin);
        g2.drawLine(margin,margin,margin,windowSize-margin);
        g2.drawLine(windowSize-margin,windowSize-margin,margin,windowSize-margin);
        g2.drawLine(windowSize-margin,windowSize-margin,windowSize-margin,margin);
    }


    public void actionPerformed(ActionEvent e) {
        if ("rec".equals(e.getActionCommand())) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            System.out.println(point);
        }
    }

    // Provide the point record
    public void recordPoint() {
        JButton recordButton = new JButton("Press Alt+R to record current point");
        recordButton.setVerticalTextPosition(AbstractButton.CENTER);
        recordButton.setHorizontalTextPosition(AbstractButton.LEADING);
        recordButton.setMnemonic(KeyEvent.VK_R);
        recordButton.setActionCommand("rec");
        recordButton.addActionListener(this);
        add(recordButton);
    }

    // Assmble the GUI
    public static void createGUI() {
        // Create and set up the window
        JFrame frame = new JFrame();
        frame.getContentPane().add(new GraphicTest());
        frame.setBackground(Color.WHITE);
        frame.setSize(windowSize,windowSize);
        frame.setVisible(true);

        //Create and set up the content pane.
        GraphicTest contents = new GraphicTest();
        contents.setOpaque(true); //content panes must be opaque
        contents.recordPoint();
        frame.setContentPane(contents);

//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
    }

    public static void main(String args[]) {
        createGUI();
    }
}


