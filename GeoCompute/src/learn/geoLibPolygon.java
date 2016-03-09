package learn;

import uk.co.geolib.geolib.*;
import uk.co.geolib.geopolygons.*;
import java.util.*;

public class geoLibPolygon {
    public static void overlapTest() {
        ArrayList<C2DPoint> pts1 = new ArrayList<C2DPoint>();
        pts1.add(new C2DPoint(1.0,1.0));
        pts1.add(new C2DPoint(1.0,10.0));
        pts1.add(new C2DPoint(10.0,10.0));
        pts1.add(new C2DPoint(10.0,1.0));
        C2DPolygon poly1 = new C2DPolygon(pts1,false);

        ArrayList<C2DPoint> pts2 = new ArrayList<C2DPoint>();
        pts2.add(new C2DPoint(4.0,4.0));
        pts2.add(new C2DPoint(4.0,7.0));
        pts2.add(new C2DPoint(7.0,7.0));
        pts2.add(new C2DPoint(7.0,4.0));
        C2DPolygon poly2 = new C2DPolygon(pts2,false);

        ArrayList<C2DPoint> pts3 = new ArrayList<C2DPoint>();
        pts3.add(new C2DPoint(9.0,9.0));
        pts3.add(new C2DPoint(9.0,11.0));
        pts3.add(new C2DPoint(11.0,11.0));
        pts3.add(new C2DPoint(11.0,9.0));
        C2DPolygon poly3 = new C2DPolygon(pts3,false);

        System.out.println(poly1.Overlaps(poly2));
        System.out.println(poly2.Overlaps(poly3));
        System.out.println(poly3.Overlaps(poly1));
    }

    public static void projectTest() {
        C2DPoint p1 = new C2DPoint(1,1);
        C2DPoint p2 = new C2DPoint(3,3);
        C2DPoint p3 = new C2DPoint(0,10);
        C2DPoint p4 = new C2DPoint(10,0);
        C2DPoint p5 = new C2DPoint(10,10);
        C2DVector vec = new C2DVector(p1,p2);
        C2DPolygon poly = new C2DPolygon(new ArrayList<C2DPoint>(Arrays.asList(p1,p2,p3)),false);
        poly.Project(vec,new CInterval());
        C2DLine line1 = new C2DLine(p1,p2);
        C2DLine line2 = new C2DLine(p1,p2);
    }

    public static void containTest() {
        ArrayList<C2DPoint> pts1 = new ArrayList<C2DPoint>();
        pts1.add(new C2DPoint(1.0,1.0));
        pts1.add(new C2DPoint(1.0,10.0));
        pts1.add(new C2DPoint(10.0,10.0));
        pts1.add(new C2DPoint(10.0,1.0));
        C2DPolygon poly1 = new C2DPolygon(pts1,false);

        C2DLine line1 = new C2DLine(new C2DPoint(4.0,4.0),new C2DPoint(5.0,5.0));
        C2DLine line2 = new C2DLine(new C2DPoint(4.0,4.0),new C2DPoint(0.0,0.0));
        C2DLine line3 = new C2DLine(new C2DPoint(1.0,2.0),new C2DPoint(1.0,6.0));
        C2DLine line4 = new C2DLine(new C2DPoint(1.0,2.0),new C2DPoint(5.0,5.0));
        C2DLine line5 = new C2DLine(new C2DPoint(1.000001,2.0),new C2DPoint(5.0,5.0));
        System.out.println(poly1.Contains(line1));
        System.out.println(poly1.Contains(line2));
        System.out.println(poly1.Contains(line3));
        System.out.println(poly1.Contains(line4));
        System.out.println(poly1.Contains(line5));
    }
    public static void main(String[] args) {
//        overlapTest();
        containTest();
    }
}
