package project.drawer;

import java.awt.*;

/**
 * Represent point as small circle
 */
public class tpvPoint {
    private double x,y,radius;

    public tpvPoint(double xx, double yy, double rr) {
        x = xx;
        y = yy;
        radius = rr;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
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
