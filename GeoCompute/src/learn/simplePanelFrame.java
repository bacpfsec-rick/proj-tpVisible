package learn;
import java.awt.*;

public class simplePanelFrame extends simpleFrame{
    public simplePanelFrame() {
        simplePanel spWest = new simplePanel(Color.WHITE);
        simplePanel spEast = new simplePanel(Color.RED);
        simplePanel spNorth = new simplePanel(Color.GREEN);
        simplePanel spSouth = new simplePanel(Color.YELLOW);
        simplePanel spCenter = new simplePanel(Color.BLUE,20,20);

        this.getContentPane().add(spWest,BorderLayout.WEST);
        this.getContentPane().add(spEast,BorderLayout.EAST);
        this.getContentPane().add(spNorth,BorderLayout.NORTH);
        this.getContentPane().add(spSouth,BorderLayout.SOUTH);
        this.getContentPane().add(spCenter,BorderLayout.CENTER);
    }
}
