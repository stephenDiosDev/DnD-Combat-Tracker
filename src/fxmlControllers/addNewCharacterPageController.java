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
import managers.Scenes;

public class AddNewCharacterPageController implements Initializable {

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

    @FXML
    private void chooseAlly(ActionEvent event) {
        typeMenu.setText(allyMenuText);
        healthBox.setDisable(true);
    }

    @FXML
    private void chooseEnemy(ActionEvent event) {
        typeMenu.setText(enemyMenuText);
        healthBox.setDisable(false);
    }

    //adds new character to existing encounter
    @FXML
    private void addToEncounter(ActionEvent event) throws IOException{
        Actor newActor;

        if(typeMenu.getText().equalsIgnoreCase(allyMenuText)) { //ally

            newActor = new Ally(nameBox.getText(), Integer.parseInt(initiativeBox.getText()));
            DndCombatTracker.getControllerManager().getActorList().add(newActor);
            EncounterPageController.refreshPageForNewCharacterAddition(newActor);

        } else if (typeMenu.getText().equalsIgnoreCase(enemyMenuText)) {    //enemy

            newActor = new Enemy(nameBox.getText(), Integer.parseInt(initiativeBox.getText()), Integer.parseInt(healthBox.getText()));
            DndCombatTracker.getControllerManager().getActorList().add(newActor);
            EncounterPageController.refreshPageForNewCharacterAddition(newActor);
        }

        int currentTurn = EncounterPageController.currentTurnIndex;

        Parent root = FXMLLoader.load(getClass().getResource(Scenes.ENCOUNTER));
        DndCombatTracker.getControllerManager().setRootEncounterScene(root);
        Stage newStage = DndCombatTracker.getControllerManager().getMainStage();

        newStage.setTitle(DndCombatTracker.getStageTitle());
        newStage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        DndCombatTracker.getControllerManager().setSceneToEncounterScene();

        stage.close();

        newStage.show();

        EncounterPageController.currentTurnIndex = currentTurn;
    }
}