package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entities.Actor;
import entities.Ally;
import fxmlControllers.AddNewCharacterPageController;
import fxmlControllers.EncounterPageController;
import fxmlControllers.SetupPageController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class manages the fxml controllers, and by extension aspects of the UI
 * as well (any shared objects between the managers). This class also functions as
 * the "UI" manager.
 */
public class ControllerManager {

    private Stage mainStage;

    private Scene setupPage;
    private Scene encounterPage;
    private Scene addNewCharacterPage;

    private SetupPageController setupPageController;
    private EncounterPageController encounterPageController;
    private AddNewCharacterPageController addNewCharacterPageController;

    /**
     * actorList contains every actor (allies and enemies) in the encounter.
     * allyList contains just the allies so they can be reused after an encounter.
     */
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

        setupPageController = new SetupPageController();
        encounterPageController = new EncounterPageController();
        addNewCharacterPageController = new AddNewCharacterPageController();
    }

    public SetupPageController getSetupPageController() {
        return setupPageController;
    }

    public EncounterPageController getEncounterPageController() {
        return encounterPageController;
    }

    public AddNewCharacterPageController getAddNewCharacterPageController() {
        return addNewCharacterPageController;
    }

    public void setSetupPageController(SetupPageController setupPageController) {
        this.setupPageController = setupPageController;
    }

    public void setEncounterPageController(EncounterPageController encounterPageController) {
        this.encounterPageController = encounterPageController;
    }

    public void setAddNewCharacterPageController(AddNewCharacterPageController addNewCharacterPageController) {
        this.addNewCharacterPageController = addNewCharacterPageController;
    }

    /**
     * Only used on initial start up. This sets the setup page as a new
     * scene with a parent given by root
     * @param root The parent root for the setup page scene
     */
    public void setRootSetupScene(Parent root) {
        setupPage = new Scene(root);
    }

    public void setRootEncounterScene(Parent root) {
        encounterPage = new Scene(root);
    }

    public void setRootAddNewCharacterScene(Parent root) {
        addNewCharacterPage = new Scene(root);
    }

    public Scene getAddNewCharacterPage() {
        return this.addNewCharacterPage;
    }

    /**
     * Returns the main stage (program window)
     * @return Returns the main stage (program window)
     */
    public Stage getMainStage() {
        return this.mainStage;
    }

    /**
     * Sets the main stage (program window) to the given stage
     * @param stage The new main stage
     */
    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    /**
     * This sets the main stage to the setup encounter scene
     */
    public void setSceneToSetupScene() {
        mainStage.setScene(setupPage);
    }

    public void setSceneToEncounterScene() {
        mainStage.setScene(encounterPage);
    }

    public ArrayList<Ally> getAllyList() {
        return this.allyList;
    }

    public void setAllyList(ArrayList<Ally> newAllyList) {
        this.allyList = newAllyList;
    }

    /**
     * Returns the list of all actors in the encounter (unsorted)
     * @return The list of all actors in the encounter (unsorted)
     */
    public ArrayList<Actor> getActorList() {
        return this.actorList;
    }

    /**
     * Sets the actor list to the given list
     * @param newActorList The new actor list
     */
    public void setActorList(ArrayList<Actor> newActorList) {
        this.actorList = newActorList;
    }

    //sorts the full actor list into descending order based on initiative
    //highest initiative first, lowest last
    public void sortActorListDescending() {
        Collections.sort(this.actorList, Comparator.comparingInt(Actor::getInitiativeTotal).reversed());
    }
}