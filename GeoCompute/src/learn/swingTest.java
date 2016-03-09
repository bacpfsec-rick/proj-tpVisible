package learn;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class swingTest {
    public static void simpleFrameTest() {
        simpleFrame sf1 = new simpleFrame();
        sf1.showIt("sf1,200,200 by default");

        simpleFrame sf2 = new simpleFrame();
        sf2.showIt("sf2,500,500",500,500);
    }

    public static void simplePanelFrameTest() {
        simplePanelFrame spf1 = new simplePanelFrame();
        spf1.showIt("Simple Panel Frame");
    }

    public static void layoutFrameTest() {
        layoutFrame dflf = new layoutFrame(new FlowLayout());
        dflf.showIt("Default Flow Layout Frame",60,60);

        layoutFrame lflf = new layoutFrame(new FlowLayout(FlowLayout.LEFT,40,30));
        lflf.showIt("Left Flow Layout Frame",300,60);

        layoutFrame glf = new layoutFrame(new GridLayout());
        glf.showIt("Grid Layout Frame",540,60);
    }
    public static void counterFrameTest() {
        counterFrame ctf = new counterFrame();
        ctf.showIt("Counter");
    }
    public static void projectTest() {

    }
    public static void main(String[] args) {
        counterFrameTest();
    }
}
