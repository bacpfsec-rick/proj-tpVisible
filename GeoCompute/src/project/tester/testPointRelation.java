package project.tester;

import project.geometry.*;

import java.util.ArrayList;

/**
 * Test program for class pointRelation
 */
public class testPointRelation {
    public static void testCloseTo(){
        System.out.println(pointRelation.closeTo(1,1,2,2)==true);
        System.out.println(pointRelation.closeTo(0,0,3,4)==true);
        System.out.println(pointRelation.closeTo(0,0,3,5)==false);
    }

    public static void main(String[] args) {
//        testCloseTo();
    }
}
