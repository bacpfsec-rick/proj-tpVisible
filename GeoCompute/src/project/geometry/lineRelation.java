package project.geometry;

import uk.co.geolib.geolib.C2DLine;
import uk.co.geolib.geolib.C2DPoint;

/**
 * The functions are used for point relation
 */
public class lineRelation {
    public static double area(C2DPoint p1, C2DPoint p2, C2DPoint p3) {
        double s = Math.abs((p1.x*p2.y+p2.x*p3.y+p3.x*p1.y-p1.x*p3.y-p2.x*p1.y-p3.x*p2.y)/2);
        return s;
    }

    public static boolean overlap(C2DPoint p1, C2DPoint p2, C2DPoint p3, C2DPoint p4) {
        // Take floating error into consideration
        return (area(p1,p2,p3)<0.00001)&&(area(p1,p2,p4)<0.00001);
    }

    public static boolean overlap(C2DLine l1, C2DLine l2) {
        return overlap(l1.GetPointFrom(),l1.GetPointTo(),l2.GetPointFrom(),l2.GetPointTo());
    }
    private static double determinant(double a, double b, double c, double d){
        return (a*d-b*c);
    }

    // From http://dec3.jlu.edu.cn/webcourse/t000096/graphics/chapter5/01_1.
    public static boolean intersect(C2DPoint p1, C2DPoint p2, C2DPoint p3, C2DPoint p4, C2DPoint intersection) {
        double delta = determinant(p2.x-p1.x, p3.x-p4.x, p2.y-p1.y, p3.y-p4.y);
        if ( Math.abs(delta)<=0.00001)
        {
            return false;
        }
        double lamda = determinant(p3.x-p1.x, p3.x-p4.x, p3.y-p1.y, p3.y-p4.y) / delta;
        if ( lamda>1 || lamda<0 )
        {
            return false;
        }
        double miu = determinant(p2.x-p1.x, p3.x-p1.x, p2.y-p1.y, p3.y-p1.y) / delta;
        if ( miu>1 || miu<0 )
        {
            return false;
        }
        intersection.x = p1.x+lamda*(p2.x-p1.x);
        intersection.y = p1.y+lamda*(p2.y-p1.y);
        return true;
    }



}
