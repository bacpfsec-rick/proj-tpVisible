package project.tester;



import project.geometry.VisibilityPolygon;

import java.awt.*;
import java.util.ArrayList;

public class testVP {
    public static void main(String[] args) {
        ArrayList vertex = new ArrayList();
        vertex.add(new Point(160,354));
        vertex.add(new Point(466,298));
        vertex.add(new Point(544,479));
        vertex.add(new Point(645,315));
        vertex.add(new Point(810,465));
        vertex.add(new Point(750,654));
        vertex.add(new Point(377,685));

        VisibilityPolygon testVP = new VisibilityPolygon();
        testVP.setOriginalVertices(vertex);

        Point testPoint = new Point(160,354);
        testVP.setPoint(testPoint);
        if (testVP.pointVerify()) {
            testVP.VisibilityCompute();
        }


        testPoint = new Point(466,298);
        testVP.setPoint(testPoint);
        if (testVP.pointVerify()) {
            testVP.VisibilityCompute();
        }

        testPoint = new Point(544,479);
        testVP.setPoint(testPoint);
        if (testVP.pointVerify()) {
            testVP.VisibilityCompute();
        }
    }
}
