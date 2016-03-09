package project.frame;

import project.listener.*;
import project.model.*;
import project.panel.*;

import javax.swing.JFrame;
import java.awt.*;

public class gcFrame extends JFrame{
    private gcModel model;
    private buttonPanel buttonPane;
    private polygonPanel polygonPane;

    public gcFrame(gcModel gc){
        // Reference to the gcModel
        model = gc;

        // Basic setting for the GUI
        this.setLocation(250,250);
        this.setSize(1230,1080);
        this.setTitle("2 Point Visibility Computation");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
//        this.setLayout(new GridLayout());

        // Add the polygonPanel
        polygonPane = new polygonPanel(model);
        this.getContentPane().add(polygonPane);

        // Add the buttonPanel
        buttonPane = new buttonPanel(model);
        this.getContentPane().add(buttonPane);

        // Activate the button listener
        buttonListener bListener = new buttonListener(buttonPane,polygonPane,model);
        buttonPane.listenButton(bListener);

        // Activate the motion listener
        motionListener mmListener = new motionListener(buttonPane,polygonPane,model);
        polygonPane.listenMotion(mmListener);

        // Activate the mouse listener
        clickListener cListener = new clickListener(buttonPane,polygonPane,model);
        polygonPane.listenClick(cListener);


    }

}
