package learn;

import java.awt.*;

public class layoutFrame extends simpleFrame {
    public layoutFrame(LayoutManager layout) {
        this.getContentPane().setLayout(layout);

        simplePanel sp1 = new simplePanel(Color.red,30,20);
        simplePanel sp2 = new simplePanel(Color.yellow,40,20);
        simplePanel sp3 = new simplePanel(Color.green);
        simplePanel sp4 = new simplePanel(Color.blue);
        simplePanel sp5 = new simplePanel(Color.white,80,20);

        this.getContentPane().add(sp1);
        this.getContentPane().add(sp2);
        this.getContentPane().add(sp3);
        this.getContentPane().add(sp4);
        this.getContentPane().add(sp5);
    }
}
