package project.runner;

import project.frame.gcFrame;
import project.model.gcModel;

/**
    Runner for the project
 */
public class gcRunner {
    public static void main(String[] args) throws Exception {
        // Create gcModel for geometry computation
        gcModel model = new gcModel();

        // Create the GUI
        gcFrame frame = new gcFrame(model);

    }
}
