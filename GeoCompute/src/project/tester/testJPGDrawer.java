package project.tester;

import java.util.*;
import project.jpg.*;

public class testJPGDrawer {
    public static void main(String[] args) throws Exception {
        // Generate 2D boolean array for test
        int size = 1000;
        ArrayList< ArrayList<Boolean> > data = new ArrayList< ArrayList<Boolean> >();
        // Assign Random values
        Random rnd = new Random();
        rnd.setSeed(32);
        for (int i=0; i<size; i++) {
            ArrayList<Boolean> row = new ArrayList<Boolean>();
            for (int j=i; j<size; j++) {
                int x = rnd.nextInt();
                int y = rnd.nextInt();
                row.add(x>y);
            }
            data.add(row);
        }
        // Print the array
//        for (ArrayList<Boolean> array : data) {
//            for (Boolean tf : array) {
//                System.out.print(tf);
//            }
//            System.out.println();
//        }
        // Generate the picture
        jpgDrawer drawer = new jpgDrawer();
        drawer.generate(data);
    }
}
