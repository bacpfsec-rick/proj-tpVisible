package learn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class counterListener implements ActionListener{
    private counterPanel counterPane;

    public counterListener (counterPanel counp) {
        counterPane = counp;
    }

    public void actionPerformed(ActionEvent evt) {
        String actionCommand = evt.getActionCommand();
        if (actionCommand.equals("Up")) {
            counterPane.increment();
        } else if (actionCommand.equals("Down")) {
            counterPane.decrement();
        } else {
            System.out.println("ERROR COMMAND");
        }
    }
}
