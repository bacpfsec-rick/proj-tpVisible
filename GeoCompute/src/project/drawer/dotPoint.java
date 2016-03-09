package project.drawer;

import java.awt.*;

/**
 * Represent point as small circle
 */
public class dotPoint {
    private double x,y,radius;

    public dotPoint(double xx, double yy, double rr) {
        x = xx;
        y = yy;
        radius = rr;
    }

    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillOval((int)Math.round((x-radius)),(int)Math.round(y-radius),
                (int)Math.round(2.0*radius),(int)Math.round(2.0*radius));
    }

    public int getX(){
        return (int)Math.round(x);
    }

    public int getY() {
        return (int)Math.round(y);
    }
}
