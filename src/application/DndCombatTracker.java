package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class acts as the main launcher for the program
 */
public class DndCombatTracker extends Application {

    private static ControllerManager controllerManager;

    /*  VERSION NUMBERING SYSTEM
        MAJOR.MINOR.PATCHstage
        Where MAJOR represents some major version of the program (0 is alpha and beta, > 0  is full release)
        MINOR represents some version which is not as big as major, but still a large change/addition
        PATCH represents bug fixes or additions between versions but not every addition to make it the next version
        stage is either a, b or blank
            a for alpha
            b for beta
            blank for full release of minimum viable product

    */
    private static final String stageTitle = "D&D Combat Tracker (v0.9.1b)";
    private static final String windowIconURL = "/icons/program_icon.png";



    @Override
    public void start(Stage stage) throws Exception {

        controllerManager = new ControllerManager();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.SETUP));
        loader.setController(controllerManager.getSetupPageController());
        Parent root = loader.load();
        controllerManager.setRootSetupScene(root);
        controllerManager.setMainStage(stage);
        controllerManager.getMainStage().setTitle(stageTitle);
        controllerManager.getMainStage().getIcons().add(new Image(windowIconURL));

        //hard width format to ensure the program looks ok
        stage.setMinWidth(618);
        stage.setMaxWidth(618);
        stage.setMinHeight(600);
        
        controllerManager.setSceneToSetupScene();
        controllerManager.getMainStage().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    

    public static String getStageTitle() {
        return stageTitle;
    }

    public static String getWindowIconURL() {
        return windowIconURL;
    }

    /**
     * Returns the instance of the controller manager
     * @return The instance of the controller manager
     */
    public static ControllerManager getControllerManager() {
        return controllerManager;
    }
}