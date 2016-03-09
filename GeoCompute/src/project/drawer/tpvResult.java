package project.drawer;

import java.awt.*;
import java.util.*;

/**
 * Used to draw the result set of 2-point-visible points
 */
public class tpvResult {
    private Vector<tpvPoint> result;
    private double radius = 3.0;

    public tpvResult() {
        result = new Vector<tpvPoint>();
    }

    public void loadResult(ArrayList< ArrayList<Double> > r) {
        r.forEach((point)-> {
           result.add(new tpvPoint(point.get(0),point.get(1),radius));
        });
    }
    public void drawResult(Graphics g) {
        for (int i = 0; i< result.size(); i++) {
            result.get(i).draw(g);
        }
    }
}
