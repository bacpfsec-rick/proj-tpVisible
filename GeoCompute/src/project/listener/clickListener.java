package project.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import project.panel.*;
import project.model.*;
import java.awt.Graphics;
/**
 * Listen to the mouse clicking
 */
public class clickListener implements MouseListener{
    private buttonPanel buttonPane;
    private polygonPanel polygonPane;
    private gcModel model;

    public clickListener(buttonPanel bp, polygonPanel pp, gcModel gc) {
        buttonPane = bp;
        polygonPane = pp;
        model = gc;
    }

    public void mouseEntered(MouseEvent evt) {
        // Do nothing
    }

    public void mouseExited(MouseEvent evt) {
        // Do nothing
    }

    public void mouseClicked(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        // Perform point recording
        if (model.getRecordStatus()) {
            if (model.addPoint(x,y)) {
                buttonPane.updateInfo("Add point("+x+","+y+")");
                // Draw the point
                polygonPane.addPoint(x,y);
            } else {
                buttonPane.updateInfo("Invalid point");
            }
        // Perform initial point selection
        } else if (model.getSelectStatus()) {
            model.selectInitialPoint(x,y);
            if (model.getSimulateStatus()) {
                buttonPane.updateInfo("Initial point ("+model.getInitialX()+","+model.getInitialY()+")");
            }
        }
    }

    public void mousePressed(MouseEvent evt) {
        // Do nothing
    }

    public void mouseReleased(MouseEvent evt) {
        // Do nothing
    }
}
