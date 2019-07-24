package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;

/**
 * This class acts as the main launcher for the program
 */
public class DndCombatTracker extends Application {

    public static Stage mainStage;

    public Scene setupScene;
    public Scene encounterScene;

    /*  VERSION NUMBERING SYSTEM
        MAJOR.MINOR.PATCHstage
        Where MAJOR represents some major version of the program (0 is alpha and beta, > 0  is full release)
        MINOR represents some version which is not as big as major, but still a large change
        PATCH represents fixes and small changes between minor changes
        stage is either a, b or blank
            a for alpha
            b for beta
            blank for full release of minimum viable product

    */
    private static final String stageTitle = "D&D Combat Tracker (v0.8.3a)";
    private static final String windowIconURL = "/application/program_icon.png";


    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPages/setupPage.fxml"));
        setupScene = new Scene(root);
        mainStage.setTitle(stageTitle);
        mainStage.getIcons().add(new Image(windowIconURL));

        mainStage.setScene(setupScene);
        mainStage.show();
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
}