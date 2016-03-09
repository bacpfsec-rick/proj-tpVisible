package learn;
import java.awt.*;
import javax.swing.JPanel;

public class simplePanel extends JPanel {
    // create a JPanel with color
    public simplePanel(Color color) {
        this.setBackground(color);
    }

    // create a JPanel with color and size
    public simplePanel(Color color, int width, int height){
        this.setBackground(color);
        this.setPreferredSize(new Dimension(width,height));
    }
}
