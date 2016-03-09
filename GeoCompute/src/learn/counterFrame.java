package learn;
import java.awt.*;

public class counterFrame extends simpleFrame{
    public counterFrame() {
        counterPanel counterPane = new counterPanel();
        this.getContentPane().add(counterPane,BorderLayout.CENTER);
    }
}
