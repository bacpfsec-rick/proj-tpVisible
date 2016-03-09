package learn;

import javax.swing.*;
import java.awt.*;

public class counterPanel extends JPanel{
    private counterModel counter;
    private JLabel valueLabel;

    public counterPanel() {
        counter = new counterModel();

        BorderLayout bordlay = new BorderLayout();
        this.setLayout(bordlay);

        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        valueLabel = new JLabel(""+counter.getValue(),SwingConstants.CENTER);

        this.add(upButton,BorderLayout.WEST);
        this.add(downButton,BorderLayout.EAST);
        this.add(valueLabel,BorderLayout.CENTER);

        // incorporate with listener
        counterListener countList = new counterListener(this);
        upButton.addActionListener(countList);
        downButton.addActionListener(countList);
    }

    public void increment() {
        counter.increment();
        valueLabel.setText(""+counter.getValue());
    }

    public void decrement() {
        counter.decrement();
        valueLabel.setText(""+counter.getValue());
    }
}
