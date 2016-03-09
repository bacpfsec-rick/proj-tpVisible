package project.listener;

import project.panel.*;
import project.model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Listen to the mouse motions
 */
public class motionListener implements MouseMotionListener{
    private buttonPanel buttonPane;
    private polygonPanel polygonPane;
    private gcModel model;

    public motionListener(buttonPanel bp, polygonPanel pp, gcModel gc) {
        buttonPane = bp;
        polygonPane = pp;
        model = gc;
    }

    public void mouseMoved(MouseEvent evt) {
        buttonPane.updateX(evt.getX());
        buttonPane.updateY(evt.getY());
    }

    public void mouseDragged(MouseEvent evt) {
        // Do nothing
    }
}
