package project.tester;

import project.geometry.lineRelation;
import uk.co.geolib.geolib.C2DPoint;
import uk.co.geolib.geoview.ScreenManager;

import java.util.ArrayList;

/**
 * Test program for class lineRelation
 */
public class testLineRelation {
    public static void testArea(){
        C2DPoint p1 = new C2DPoint(1.0,1.0);
        C2DPoint p2 = new C2DPoint(1.0,5.0);
        C2DPoint p3 = new C2DPoint(5.0,5.0);
        C2DPoint p4 = new C2DPoint(1.0,7.0);
        System.out.println(lineRelation.area(p1,p2,p4)==0.0);
        System.out.println(lineRelation.area(p1,p2,p3)!=0.0);
    }

    public static void testOverlap() {
        C2DPoint p1 = new C2DPoint(1.0,1.0);
        C2DPoint p2 = new C2DPoint(3.0,3.0);
        C2DPoint p3 = new C2DPoint(5.0,5.0);
        C2DPoint p4 = new C2DPoint(1.0,7.0);
        System.out.println(lineRelation.overlap(p1,p2,p2,p3)==true);
        System.out.println(lineRelation.overlap(p1,p2,p3,p4)==false);
    }

    public static void testIntersect() {
        C2DPoint p1 = new C2DPoint(1.0,1.0);
        C2DPoint p2 = new C2DPoint(5.0,5.0);
        C2DPoint p3 = new C2DPoint(1.0,5.0);
        C2DPoint p4 = new C2DPoint(5.0,1.0);
        C2DPoint p5 = new C2DPoint(2.0,4.0);
        C2DPoint pi1 = new C2DPoint();
        C2DPoint pi2 = new C2DPoint();
        System.out.println(lineRelation.intersect(p1,p2,p3,p4,pi1)==true);
        System.out.println(pi1.x==3.0);
        System.out.println(pi1.y==3.0);
        System.out.println(lineRelation.intersect(p1,p2,p3,p5,pi2)==false);
    }

    public static void main(String[] args) {
//        testArea();
//        testOverlap();
        testIntersect();
    }
}
