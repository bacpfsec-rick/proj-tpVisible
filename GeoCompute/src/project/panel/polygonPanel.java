package project.panel;

import project.model.*;
import project.listener.*;
import project.drawer.*;

import javax.swing.*;
import java.awt.*;


public class polygonPanel extends JPanel{
    private gcModel model;

    // Data structure for drawing
    private double radius = 5.0;
    private polygonVertex points;
    private tpvResult result;
    private boolean paintPolygon;
    private boolean paintResult;

    public polygonPanel(gcModel gc) {
        // Reference to the gcMpdel
        model = gc;

        // Basic setting for the button panel
        this.setPreferredSize(new Dimension(1000,1000));
        this.setBackground(Color.LIGHT_GRAY);

        JLabel hint = new JLabel("Polygon Window",SwingConstants.CENTER);
        this.add(hint);

        // Initialize the data structure
        points = new polygonVertex();
        result = new tpvResult();
        paintPolygon = false;
        paintResult = false;
    }

    public void listenMotion(motionListener mml) {
        this.addMouseMotionListener(mml);
    }

    public void listenClick(clickListener ml) {
        this.addMouseListener(ml);
    }

    // Draw the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        points.drawAllPoints(g);
        if (paintPolygon) {
            points.drawPolygon(g);
        }
        if (paintResult) {
            result.drawResult(g);
        }
    }

    public void addPoint(int x, int y) {
        points.addPoint(new dotPoint(x,y,radius));
        // repaint the graphics
        repaint();
    }

    public void drawPolygon() {
        paintPolygon = true;
        repaint();
    }

    public void drawResult() {
        paintResult = true;
        result.loadResult(model.getTpVisiblePoints());
        repaint();
    }
    // Undo-helper for reset button
    public boolean undo() {
        if (points.size()==0) {
            return false;
        }
        points.undo();
        repaint();
        return true;
    }

    // Stop-helper for stop button
    public boolean validStopCheck(){
        return points.validPolygon();
    }

    // Reset-helper for reset button
    public void reset() {
        points.reset();
        paintPolygon = false;
        paintResult = false;
        repaint();
    }


}
