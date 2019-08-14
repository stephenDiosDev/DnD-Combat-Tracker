package fxmlControllers;

import application.DndCombatTracker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import entities.*;
import javafx.event.ActionEvent;
import application.Scenes;

/**
 * The controller for the "add new character" window that pops up during and encounter.
 */
public class AddNewCharacterPageController implements Initializable {

    //needed for the menu selector for ally or enemy
    private final String allyMenuText = "Ally";
    private final String enemyMenuText = "Enemy";

    //the separate stage for this window
    public static Stage stage;

    @FXML
    private TextField healthBox;

    @FXML
    private TextField nameBox;

    @FXML
    private MenuButton typeMenu;

    @FXML
    private TextField initiativeBox;

    @FXML
    private Button addToEncounterBtn;

    @FXML
    private MenuItem allyMenuItem;

    @FXML
    private MenuItem enemyMenuItem;

    //tooltips for the above fxml items
    Tooltip healthBoxTooltip = new Tooltip("The total health of the character. Must be an integer!");
    Tooltip nameBoxTooltip = new Tooltip("The name of the character.");
    Tooltip initiativeBoxTooltip = new Tooltip("The initiative total of the character. Must be an integer!");
    Tooltip typeMenuTooltip = new Tooltip("Choose the type of your new character, either Ally or Enemy.");
    Tooltip addToEncounterTooltip = new Tooltip("Adds the new character to the existing encounter.");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        healthBox.setTooltip(healthBoxTooltip);
        nameBox.setTooltip(nameBoxTooltip);
        initiativeBox.setTooltip(initiativeBoxTooltip);
        typeMenu.setTooltip(typeMenuTooltip);
        addToEncounterBtn.setTooltip(addToEncounterTooltip);
    }

    /**
     * The method that occurs when you select Ally in the drop down menu. Disables the health box (only for enemies)
     * @param event
     */
    @FXML
    private void chooseAlly(ActionEvent event) {
        typeMenu.setText(allyMenuText);
        healthBox.setDisable(true);
    }

    /**
     * The method that occurs when you select Enemy in the drop down menu. Enables the health box (only for enemies)
     * @param event
     */
    @FXML
    private void chooseEnemy(ActionEvent event) {
        typeMenu.setText(enemyMenuText);
        healthBox.setDisable(false);
    }

    /**
     * Adds a new character to the in-progress encounter. When this method is fired, all the information in the
     * FXML page (the textfields) should be filled.
     * @param event
     * @throws IOException
     */
    @FXML
    private void addToEncounter(ActionEvent event) throws IOException{
        boolean applyNextTurn = false;

        int currentTurn = DndCombatTracker.getControllerManager().getEncounterPageController().getCurrentTurnIndex();

        if(typeMenu.getText().equalsIgnoreCase(allyMenuText)) { //if Ally is chosen in the drop down menu

            //create new ally using the info from the name and initiative field and add it to the actor list in
            //controller manager
            Ally newAlly = new Ally(nameBox.getText(), Integer.parseInt(initiativeBox.getText()));
            DndCombatTracker.getControllerManager().getActorList().add(newAlly);

            //if new actor initiative is greater than the actor at the current turn, update current turn so turn icon
            //displays the correct turn
            if(newAlly.getInitiativeTotal() > DndCombatTracker.getControllerManager().getActorList().get(currentTurn).getInitiativeTotal()) {
                applyNextTurn = true;
            }

        } else if (typeMenu.getText().equalsIgnoreCase(enemyMenuText)) {    //if Enemy is chosen in the drop down menu

            //create new enemy using the info from the name, initiative field and health field and add it to the
            //actor list in controller manager
            Enemy newEnemy = new Enemy(nameBox.getText(), Integer.parseInt(initiativeBox.getText()), Integer.parseInt(healthBox.getText()));
            DndCombatTracker.getControllerManager().getActorList().add(newEnemy);

            //if new actor initiative is greater than the actor at the current turn, update current turn so turn icon
            //displays the correct turn
            if (newEnemy.getInitiativeTotal() > Integer.parseInt(DndCombatTracker.getControllerManager().getActorList().get(currentTurn).getInitiativeLabel().getText())) {
                applyNextTurn = true;
            }
        }

        //sort the actor list and refresh the in-progress encounter page to accommodate the newly added actor
        DndCombatTracker.getControllerManager().sortActorListDescending();
        DndCombatTracker.getControllerManager().getEncounterPageController().refreshPageForNewCharacterAddition(applyNextTurn);

        //reload the encounter scene to show the new changes
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.ENCOUNTER));
        loader.setController(DndCombatTracker.getControllerManager().getEncounterPageController());
        Parent root = loader.load();

        DndCombatTracker.getControllerManager().setRootEncounterScene(root);
        Stage newStage = DndCombatTracker.getControllerManager().getMainStage();

        newStage.setTitle(DndCombatTracker.getStageTitle());
        newStage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        DndCombatTracker.getControllerManager().setSceneToEncounterScene();

        stage.close();

        newStage.show();
    }
}