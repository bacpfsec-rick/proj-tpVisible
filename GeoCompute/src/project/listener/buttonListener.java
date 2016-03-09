package project.listener;

import project.panel.*;
import project.model.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class buttonListener implements ActionListener{
    private buttonPanel buttonPane;
    private polygonPanel polygonPane;
    private gcModel model;

    public buttonListener(buttonPanel bp, polygonPanel pp,gcModel gc) {
        buttonPane = bp;
        polygonPane = pp;
        model = gc;
    }

    public void actionPerformed(ActionEvent evt){
        String cmd = evt.getActionCommand();
        if (cmd.equals("Record")) {
            buttonPane.updateInfo("Start to record");
            model.record();
        } else if (cmd.equals("Undo")) {
            if (polygonPane.undo()) {
                buttonPane.updateInfo("Remove last point");
                model.undo();
            } else {
                buttonPane.updateInfo("No points to remove");
            }
        } else if (cmd.equals("Stop")) {
            if (polygonPane.validStopCheck()) {
                buttonPane.updateInfo("Stop to record");
                model.stop();
            } else {
                buttonPane.updateInfo("Not enough points");
            }
        } else if (cmd.equals("Reset")) {
            buttonPane.updateInfo("Reset points");
            model.reset();
            polygonPane.reset();
        } else if (cmd.equals("Draw")) {
            buttonPane.updateInfo("Run simulation");
            polygonPane.drawPolygon();
        } else if (cmd.equals("Select")) {
            buttonPane.updateInfo("Select initial");
            model.select();
        } else if (cmd.equals("Simulate")) {
            model.simulate();
        } else if (cmd.equals("Result")) {
            if (model.getResultStatus()) {
                buttonPane.updateInfo("2-point-visible points");
                polygonPane.drawResult();
            } else {
                buttonPane.updateInfo("Result not ready");
            }
        } else {
            buttonPane.updateInfo("Invalid Command");
        }
    }

}
