package project.drawer;

import java.awt.*;
import java.util.Vector;

/**
 * Vector for collecting and drawing point
 */
public class polygonVertex {
    private Vector<dotPoint> vertex;

    public polygonVertex() {
        vertex = new Vector<dotPoint>();
    }

    public void addPoint(dotPoint p) {
        vertex.add(p);
    }

    public void drawAllPoints(Graphics g) {
        for (int i = 0; i< vertex.size(); i++) {
            vertex.get(i).draw(g);
        }
    }

    // Valid-helper for button stop
    public boolean validPolygon() {
        return (vertex.size()>=3);
    }

    public void drawPolygon(Graphics g) {
        if (vertex.size()<3) {
            return;
        }
        // Cast graphics to graphics2d
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setColor(Color.BLACK);
        BasicStroke stroke = new BasicStroke(3);
        g2d.setStroke(stroke);
        for (int i = 1; i< vertex.size(); i++) {
            g2d.drawLine(vertex.get(i-1).getX(), vertex.get(i-1).getY(),
                    vertex.get(i).getX(), vertex.get(i).getY());
        }
        g2d.drawLine(vertex.get(0).getX(), vertex.get(0).getY(),
                vertex.lastElement().getX(), vertex.lastElement().getY());
    }

    public void reset() {
        vertex.clear();
    }

    public void undo() {
        vertex.remove(vertex.size()-1);
    }

    public int size() {
        return vertex.size();
    }
}
