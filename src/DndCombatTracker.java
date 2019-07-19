import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;

/**
 * This class acts as the main launcher for the program
 */
public class DndCombatTracker extends Application {

    public Stage mainStage;

    public Scene setupScene;
    public Scene encounterScene;

    private final String stageTitle = "D&D Combat Tracker (v0.1)";
    private final String windowIconURL = "program_icon.png";


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
    

    public String getStageTitle() {
        return this.stageTitle;
    }
}