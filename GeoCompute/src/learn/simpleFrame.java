package learn;

import javax.swing.JFrame;

public class simpleFrame extends JFrame {
    // default constructor
    public simpleFrame() {
        this.setSize(200,200);
        this.setLocation(200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Make the frame invisible
    public void showIt() {
        this.setVisible(true);
    }

    // Make the frame invisible with title
    public void showIt(String title) {
        this.setTitle(title);
        this.setVisible(true);
    }

    // Makes the frame visible and sets the title text and the position of the window.
    public void showIt(String title, int x, int y) {
        this.setTitle(title);
        this.setLocation(x,y);
        this.setVisible(true);
    }
    // Make the frame invisible
    public void hideIt()   {
        this.setVisible(false);
    }
}
