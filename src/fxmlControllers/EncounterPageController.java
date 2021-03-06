package fxmlControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.image.*;
import application.*;
import entities.*;
import javafx.scene.control.Tooltip;

public class EncounterPageController implements Initializable{
    Tooltip endEncounterTooltip = new Tooltip("Ends the encounter and auto-fills all the current allies back in the setup page. " + 
                                              "Pressing the ESC key also does the same thing!");
    Tooltip nextTurnTooltip = new Tooltip("Shifts the turn tracker to the next ally or enemy.");
    Tooltip healthTooltip = new Tooltip("The current health of the character. Must be an integer!");


    //this list contains the actual actor objects

    private int currentTurnIndex = 0;

    private int iconYPosition;
    private int iconXPosition;


    //the following int's are all for the layout of the various labels and textboxes
    private int yPosition;
    private int yHealthPosition;
    private int yIncrease;

    private int prefHeight;
    private int healthPrefHeight;

    private int initiativePrefWidth;
    private int namePrefWidth;
    private int healthPrefWidth;

    private int initiativeXPosition;
    private int nameXPosition;
    private int healthXPosition;

    @FXML
    private Button endEncounterBtn;

    @FXML
    private Button nextTurnBtn;

    @FXML
    private Button addNewCharacterBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private VBox vboxPane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private AnchorPane bottomPane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Label initiativeLabel;

    private ImageView turnIcon = new ImageView("/icons/turn_icon_SWORD.png");


    private void setBackgroundColour() {
        scrollpane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        vboxPane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        topPane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        bottomPane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));

        if(DndCombatTracker.needHighContrast()) {
            initiativeLabel.setTextFill(Color.web("#FFFFFF"));
        } else {
            initiativeLabel.setTextFill(Color.web("#000000"));
        }
    }

    //move focus to the next actor in line
    @FXML
    void nextTurn(ActionEvent event) {
        //update current turn index
        if(currentTurnIndex + 1 >= DndCombatTracker.getControllerManager().getActorList().size()) {
            currentTurnIndex = 0;
        } else {
            currentTurnIndex++;
        }

        //if current actor is dead, we want to skip them
        if(DndCombatTracker.getControllerManager().getActorList().get(currentTurnIndex).getIsDead()) {   //if dead
            nextTurn(event);
        } else {    //if alive, update the icon position
            iconYPosition = 5 + (currentTurnIndex * yIncrease);
            turnIcon.setLayoutY(iconYPosition);

            mainPane.getChildren().remove(turnIcon);
            mainPane.getChildren().add(turnIcon);
        }
        
    }



    @FXML
    void endEncounter(ActionEvent event) throws IOException{    //switches fxml page back to setup page and ends current encounter
        //send ally names back to be reused
        ArrayList<Ally> reusedNames = new ArrayList<>();
        for (Actor actor : DndCombatTracker.getControllerManager().getActorList()) {
            if(actor instanceof Ally) {
                Ally tempAlly = (Ally)actor;
                tempAlly.getInitiativeBox().clear();    //clear past initiatives so box is empty
                reusedNames.add(tempAlly);
            }
        }

        DndCombatTracker.getControllerManager().setAllyList(reusedNames);
        
        //switch FXML page to setup page
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.SETUP));
        loader.setController(DndCombatTracker.getControllerManager().getSetupPageController());
        Parent root = loader.load();
        DndCombatTracker.getControllerManager().setRootSetupScene(root);
        Stage stage = DndCombatTracker.getControllerManager().getMainStage();

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        DndCombatTracker.getControllerManager().setSceneToSetupScene();
        stage.show();
    }

    //opens new window to add new character
    @FXML
    void addNewCharacter(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.ADD_NEW_CHARACTER));
        loader.setController(DndCombatTracker.getControllerManager().getAddNewCharacterPageController());
        Parent root = loader.load();
        DndCombatTracker.getControllerManager().setRootAddNewCharacterScene(root);
        //since this is a separate window, we use a new stage, NOT the main stage in
        //controller manager!
        Stage stage = new Stage();

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        stage.setScene(DndCombatTracker.getControllerManager().getAddNewCharacterPage());
        stage.show();
        AddNewCharacterPageController.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBackgroundColour();

        //reset index to ensure it isn't using a garbage value
        setCurrentTurnIndex(0);

        iconYPosition = 5;
        iconXPosition = 300;

        yPosition = 5;
        yHealthPosition = yPosition - 5;
        yIncrease = 55;

        prefHeight = 17;
        healthPrefHeight = 17;

        initiativePrefWidth = 67;
        namePrefWidth = 118;
        healthPrefWidth = 62;

        initiativeXPosition = 25;
        nameXPosition = 100;
        healthXPosition = nameXPosition + namePrefWidth;

        endEncounterBtn.setTooltip(endEncounterTooltip);
        nextTurnBtn.setTooltip(nextTurnTooltip);

        Enemy tempEnemy;

        //set labels for all actors in actorList
        for (Actor actor : DndCombatTracker.getControllerManager().getActorList()) {
            actor.setNameLabel();
            actor.setInitiativeLabel();
            
            actor.getInitiativeLabel().setLayoutX(initiativeXPosition);
            actor.getInitiativeLabel().setLayoutY(yPosition);
            actor.getInitiativeLabel().setPrefSize(initiativePrefWidth, prefHeight);
            actor.getInitiativeLabel().setFont(new Font(24.0));


            actor.getNameLabel().setLayoutX(nameXPosition);
            actor.getNameLabel().setLayoutY(yPosition);
            actor.getNameLabel().setPrefSize(namePrefWidth, prefHeight);
            actor.getNameLabel().setFont(new Font(24.0));

            if(actor instanceof Enemy) {
                tempEnemy = (Enemy)actor;

                tempEnemy.getHealthBox().setText(Integer.toString(tempEnemy.getCurrentHealth()));

                //set health textfield layout properties
                tempEnemy.getHealthBox().setLayoutX(healthXPosition);
                tempEnemy.getHealthBox().setLayoutY(yHealthPosition);
                tempEnemy.getHealthBox().setPrefSize(healthPrefWidth, healthPrefHeight);
                tempEnemy.getHealthBox().setFont(new Font(16.0));
                tempEnemy.getHealthBox().setTooltip(healthTooltip);
            }

            yPosition += yIncrease;
            yHealthPosition += yIncrease;


        }
        
        turnIcon.setLayoutX(iconXPosition);
        turnIcon.setLayoutY(iconYPosition + (currentTurnIndex * yIncrease));


        if(DndCombatTracker.getControllerManager().getActorList().isEmpty()){
            turnIcon.setOpacity(0.0);   //makes picture invisible
        } else {
            turnIcon.setOpacity(1.0);
        }

        for (Actor actor : DndCombatTracker.getControllerManager().getActorList()) {
            //set labels to high contrast if needed
            if(DndCombatTracker.needHighContrast()) {
                actor.getNameLabel().setTextFill(Color.web("#FFFFFF"));
                actor.getInitiativeLabel().setTextFill(Color.web("#FFFFFF"));
            } else {
                actor.getNameLabel().setTextFill(Color.web("#000000"));
                actor.getInitiativeLabel().setTextFill(Color.web("#000000"));
            }
            mainPane.getChildren().add(actor.getNameLabel());
            mainPane.getChildren().add(actor.getInitiativeLabel());

            if(actor instanceof Enemy) {
                tempEnemy = (Enemy)actor;
                mainPane.getChildren().add(tempEnemy.getHealthBox());
            }

        }

        mainPane.getChildren().add(turnIcon);

    }

    /**
     * This sorts the actor list
     */
    public void refreshPageForNewCharacterAddition(Boolean applyNextTurn) {
        if(applyNextTurn) {
            nextTurn(new ActionEvent());
        }
    }

    public int getCurrentTurnIndex() {
        return this.currentTurnIndex;
    }

    public void setCurrentTurnIndex(int currentTurnIndex) {
        this.currentTurnIndex = currentTurnIndex;
    }


}
