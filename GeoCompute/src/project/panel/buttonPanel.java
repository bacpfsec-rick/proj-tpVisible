package project.panel;

import project.listener.buttonListener;
import project.model.gcModel;

import javax.swing.*;
import java.awt.*;

public class buttonPanel extends JPanel{
    private JLabel information;
    private JLabel xLocation;
    private JLabel yLocation;

    private gcModel model;

    private JButton recordButton;
    private JButton undoButton;
    private JButton stopButton;
    private JButton resetButton;
    private JButton selectButton;
    private JButton simuButton;
    private JButton drawButton;
    private JButton resultButton;

    public buttonPanel(gcModel gc) {
        // Reference to the gcModel
        model = gc;

        // Basic setting for the button panel
        this.setPreferredSize(new Dimension(200,1000));
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(11,1));

        // Add information
        information = new JLabel("Program status",SwingConstants.CENTER);
        this.add(information);

        // Add xy location
        xLocation = new JLabel("x - ?",SwingConstants.CENTER);
        yLocation = new JLabel("y - ?",SwingConstants.CENTER);
        this.add(xLocation);
        this.add(yLocation);

        // Add buttons
        recordButton = new JButton("Record");
        undoButton = new JButton("Undo");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        drawButton = new JButton("Draw");
        selectButton = new JButton("Select");
        simuButton = new JButton("Simulate");
        resultButton = new JButton("Result");
        this.add(recordButton);
        this.add(undoButton);
        this.add(stopButton);
        this.add(drawButton);
        this.add(selectButton);
        this.add(simuButton);
        this.add(resultButton);
        this.add(resetButton);
    }

    public void updateInfo(String info) {
        information.setText(info);
    }

    public void listenButton(buttonListener bl) {
        recordButton.addActionListener(bl);
        undoButton.addActionListener(bl);
        stopButton.addActionListener(bl);
        resetButton.addActionListener(bl);
        simuButton.addActionListener(bl);
        selectButton.addActionListener(bl);
        drawButton.addActionListener(bl);
        resultButton.addActionListener(bl);
    }

    public void updateX(int xcord) {
        xLocation.setText("x - "+xcord);
    }
    public void updateY(int ycord) {
        yLocation.setText("y - "+ycord);
    }
}
