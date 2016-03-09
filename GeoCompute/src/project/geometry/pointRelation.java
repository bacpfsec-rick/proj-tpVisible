package project.geometry;

import uk.co.geolib.geolib.C2DPoint;

/**
 * The functions are used for point relation
 */
public class pointRelation {
    private static int closeToRadius = 10;

    public static boolean closeTo(double x1, double y1, double x2, double y2) {
        return ((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))<=(closeToRadius*closeToRadius);
    }

    public static boolean closeTo(C2DPoint p1, C2DPoint p2) {
        return closeTo(p1.x,p1.y,p2.x,p2.y);
    }

    public static double distanceSquare(C2DPoint p1, C2DPoint p2) {
       return (p1.x-p2.x)* (p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y);
    }
}
