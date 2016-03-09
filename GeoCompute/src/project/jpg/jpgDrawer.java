package project.jpg;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.util.ArrayList;


import com.sun.image.codec.jpeg.*;

public class jpgDrawer {
    public void generate(ArrayList<ArrayList<Boolean>> data)  {
        // Initial setting
        int width = 5;
        int num = data.size();
        int size  = num*width;
        int Black = (new Color(0,0,0)).getRGB();
        int White = (new Color(255,255,255)).getRGB();
        int[] black = {Black,Black,Black,Black,Black};
        int[] white = {White,White,White,White,White};
        BufferedImage pic = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
//        // Check the data
//        for (ArrayList<Boolean> array : data) {
//            for (Boolean tf : array) {
//                System.out.print(tf);
//            }
//            System.out.println();
//        }
        // Assign RGB according to data
        int i, j, startX, startY;
        for (i=0; i<num; i++) {
            for (j=i; j<num; j++) {
                Boolean value = data.get(i).get(j-i);
//                System.out.println("(" + i + "," + (j-i) +"): " + value);
                int[] color = (value==true) ? black : white;
                startX = i*width;
                startY = j*width;
                pic.setRGB(startX,startY,width,width,color,0,0);
                pic.setRGB(startY,startX,width,width,color,0,0);
            }
        }
        // Output jpg
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("c://Result/result.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        try {
            encoder.encode(pic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
