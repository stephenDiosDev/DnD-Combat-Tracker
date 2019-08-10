package managers;

import java.util.ArrayList;

import entities.Actor;
import entities.Ally;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class manages the fxml controllers, and by extension aspects of the UI
 * as well (any shared objects betweent he managers). This class also functions as
 * the "UI" manager.
 */
public class ControllerManager {

    private Stage mainStage;

    private Scene setupPage;
    private Scene encounterPage;
    private Scene addNewCharacterPage;

    private ArrayList<Actor> actorList;
    private ArrayList<Ally> allyList;

    /**
     * This constructor only gets used on program start, so it specifically sets the 
     * setup page with the given root! DO NOT CALL THIS CONSTRUCTOR ANYWHERE!
     */
    public ControllerManager() {
        mainStage = new Stage();

        actorList = new ArrayList<>();
        allyList = new ArrayList<>();
    }

    public void setRoot(Parent root) {
        setupPage = new Scene(root);
    }
    public Stage getMainStage() {
        return this.mainStage;
    }

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    public void setSceneToSetupScene() {
        mainStage.setScene(setupPage);
    }

    public ArrayList<Actor> getActorList() {
        return this.actorList;
    }

    public void setActorList(ArrayList<Actor> newActorList) {
        this.actorList = newActorList;
    }
}