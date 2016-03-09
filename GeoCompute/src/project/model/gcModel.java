package project.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import uk.co.geolib.geolib.*;
import uk.co.geolib.geopolygons.*;

import project.geometry.*;
import project.jpg.*;


/**
 * Model class for the geometry computation
 */
public class gcModel {
    // Data structure
    private ArrayList<C2DPoint> points;
    private ArrayList< ArrayList<Double> > tpVisiblePoints; // x,y,0/1
    private int numOfPoints;
    private C2DPoint initialPoint;
    private double stepsize = 50.0;

    // Helper for GUI
    private boolean recordStatus;
    private boolean selectStatus;
    private boolean simulateStatus;
    private boolean resultStatus;

    // Helper for jpg
    private ArrayList<C2DPolygon> vpSet = new ArrayList<C2DPolygon>();
    private ArrayList< ArrayList<Boolean> > vpResult = new ArrayList<ArrayList<Boolean> >();

    // Default constructor
    public gcModel() {
        points = new ArrayList<C2DPoint>();
        tpVisiblePoints = new ArrayList< ArrayList<Double> >();
        numOfPoints = 0;
        initialPoint = new C2DPoint();
        recordStatus = false;
        selectStatus = false;
        simulateStatus = false;
        resultStatus = false;
    }

    // Getters for the status
    public boolean getRecordStatus() {
        return recordStatus;
    }

    public boolean getSelectStatus() {
        return selectStatus;
    }

    public boolean getSimulateStatus() {
        return simulateStatus;
    }

    public boolean getResultStatus() {
        return resultStatus;
    }

    public double getInitialX(){
        return initialPoint.x;
    }

    public double getInitialY(){
        return initialPoint.y;
    }

    public ArrayList< ArrayList<Double> > getTpVisiblePoints() {
        return tpVisiblePoints;
    }

    // Add points in region [0,1000]x[0,1000]
    public boolean  addPoint(int x, int y) {
        if (x<0 || x>1000 || y<0 || y>1000) {
            System.out.println("Invalid point");
            return false;
        }
        points.add(new C2DPoint((double)x,(double)y));
        numOfPoints ++;
        System.out.println("Adding Point("+x+","+y+")");
        return true;
    }

    // Reset points
    public void reset() {
        points.clear();
        tpVisiblePoints.clear();
        numOfPoints = 0;
        initialPoint = new C2DPoint();
        recordStatus = false;
        selectStatus = false;
        simulateStatus = false;
        resultStatus = false;
        vpSet.clear();
        vpResult.clear();
        System.out.println("Reset the program");
    }

    // Record-helper for button
    public void record() {
        recordStatus = true;
        System.out.println("Start to record");
    }

    // Stop-helper for button
    public void stop() {
        if (recordStatus) {
            recordStatus = false;
            System.out.println("Stop to record");
        }
    }

    // Undo-helper for button
    public void undo() {
        if (recordStatus) {
            numOfPoints--;
            points.remove(numOfPoints);
            System.out.println("Remove the last point");
        }
    }

    // Select-helper for motion
    public void select() {
        selectStatus = true;
        System.out.println("Start to select a initial dotPoint");
    }

    // Run the select point algorithm
    public void selectInitialPoint(int x, int y){
        for (int i=0; i<numOfPoints; i++) {
            double xRecord = points.get(i).x;
            double yRecord = points.get(i).y;
            if(pointRelation.closeTo((double)x,(double)y,xRecord,yRecord)) {
                initialPoint = new C2DPoint(xRecord,yRecord);
                selectStatus = false;
                simulateStatus = true;
                System.out.println("Initial dotPoint: ("+initialPoint.x+","+initialPoint.y+")");
            }
        }
    }

    // Run the simulate algorithm
    public void simulate() {
        // Generate the polygon
        ArrayList vertex = new ArrayList();
        for (C2DPoint c2dpoint : points) {
            vertex.add(new Point((int)c2dpoint.x,(int)c2dpoint.y));
        }
        // Compute the visibility polygon from the initial point
        VisibilityPolygon vp1 = new VisibilityPolygon();
        vp1.setOriginalVertices(vertex);
        vp1.setPoint(new Point((int)initialPoint.x,(int)initialPoint.y));
        vp1.VisibilityCompute();
        C2DPolygon c2dVp1;
        ArrayList<C2DPoint> c2dL1 = new ArrayList<C2DPoint>();
        for (Object vertice : vp1.getVertices()) {
            Point p = (Point)vertice;
            c2dL1.add(new C2DPoint(p.x,p.y));
        }
        c2dVp1 = new C2DPolygon(c2dL1,false);
        vpSet.add(c2dVp1);
        // loop over all line to find test points
        for (int i=0; i<numOfPoints; i++) {
            double x1 = points.get(i%numOfPoints).x;
            double y1 = points.get(i%numOfPoints).y;
            double x2 = points.get((i+1)%numOfPoints).x;
            double y2 = points.get((i+1)%numOfPoints).y;
            double xStepsize = (x1<x2) ? stepsize : -stepsize;
            double yStepsize = (y1<y2) ? stepsize : -stepsize;
            double testX = x1;
            double testY = y1;
            double modifiedX;
            double modifiedY;
            // Case with slope < 1
            if (Math.abs(x1-x2)>Math.abs(y1-y2)){
                while (true) {
                    testX += xStepsize;
                    testY = (y1 - y2) / (x1 - x2) * (testX - x1)+y1;
//                    System.out.println("Origin point -("+testX+","+testY+")");
                    // check if the test point is still in the line segment
                    if (((x1<x2)&&(testX>x2)) || ((x1>x2)&&(testX<x2))) {
                        break;
                    }
                    // Slightly modify the test point so that the (int) is still in polygon
                    modifiedX = testX;
                    modifiedY = (x1<x2)?Math.ceil(testY):Math.floor(testY);
                    VisibilityPolygon vp2 = new VisibilityPolygon();
                    vp2.setOriginalVertices(vertex);
                    vp2.setPoint(new Point((int)modifiedX,(int)modifiedY));
                    vp2.VisibilityCompute();
                    C2DPolygon c2dVp2;
                    ArrayList<C2DPoint> c2dL2 = new ArrayList<C2DPoint>();
                    for (Object vertice : vp2.getVertices()) {
                        Point p = (Point)vertice;
                        c2dL2.add(new C2DPoint(p.x,p.y));
                    }
                    c2dVp2 = new C2DPolygon(c2dL2,false);
                    vpSet.add(c2dVp2);
                    if (c2dVp1.Overlaps(c2dVp2)) {
                        tpVisiblePoints.add(new ArrayList<Double>(Arrays.asList(testX,testY)));
                    }
                }
            // Case with slope >=1
            } else {
                while (true) {
                    testY += yStepsize;
                    testX = (x1 - x2) / (y1 - y2) * (testY - y1)+x1;
//                    System.out.println("Origin point -("+testX+","+testY+")");
                    // check if the test point is still in the line segment
                    if (((y1<y2)&&(testY>y2)) || ((y1>y2)&&(testY<y2))) {
                        break;
                    }
                    // Slightly modify the test point so that the (int) is still in polygon
                    modifiedX = (y1<y2)?Math.floor(testX):Math.ceil(testX);
                    modifiedY = testY;
                    VisibilityPolygon vp2 = new VisibilityPolygon();
                    vp2.setOriginalVertices(vertex);
                    vp2.setPoint(new Point((int)modifiedX,(int)modifiedY));
                    vp2.VisibilityCompute();
                    C2DPolygon c2dVp2;
                    ArrayList<C2DPoint> c2dL2 = new ArrayList<C2DPoint>();
                    for (Object vertice : vp2.getVertices()) {
                        Point p = (Point)vertice;
                        c2dL2.add(new C2DPoint(p.x,p.y));
                    }
                    c2dVp2 = new C2DPolygon(c2dL2,false);
                    vpSet.add(c2dVp2);
                    if (c2dVp1.Overlaps(c2dVp2)) {
                        tpVisiblePoints.add(new ArrayList<Double>(Arrays.asList(testX,testY)));
                    }
                }
            }
        }
        // print the results
        resultStatus = true;
        System.out.println("Print 2-point-visible points from initial point("+initialPoint.x+","+initialPoint.y+")");
        tpVisiblePoints.forEach((point)-> {
            System.out.println("2-point-visible point: ("+point.get(0)+","+point.get(1)+")");
        });
        // generate the visibility matrix
        int numOfRows = vpSet.size();
        for (int i=0; i<numOfRows; i++) {
            ArrayList<Boolean> rowResult = new ArrayList<Boolean>();
            for (int j=i; j<numOfRows; j++) {
                Boolean pointVP;
                if (j!=i) {
                    pointVP = vpSet.get(i).Overlaps(vpSet.get(j));
                } else {
                    pointVP = true;
                }
                rowResult.add(pointVP);
            }
            vpResult.add(rowResult);
        }
        drawJPG();
        System.out.println("JPG result is generated: c:/Result/result.jpg");
    }

    public void drawJPG() {
        jpgDrawer drawer = new jpgDrawer();
        drawer.generate(vpResult);
    }
}

