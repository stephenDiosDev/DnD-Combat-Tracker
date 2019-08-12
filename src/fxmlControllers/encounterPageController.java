package fxmlControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
    private ArrayList<Actor> encounterActorList = new ArrayList<>();

    private int currentTurnIndex = 0;

    private int iconYPosition = 5;
    private int iconXPosition = 300;



    //the following int's are all for the layout of the various labels and textboxes
    private int yPosition = 5;
    private int yHealthPosition = yPosition - 5;
    private int yIncrease = 55;

    private int prefHeight = 17;
    private int healthPrefHeight = 17;

    private int initiativePrefWidth = 67;
    private int namePrefWidth = 118;
    private int healthPrefWidth = 62;

    private int initiativeXPosition = 25;
    private int nameXPosition = 100;
    private int healthXPosition = nameXPosition + namePrefWidth;

    @FXML
    private Button endEncounterBtn;

    @FXML
    private Button nextTurnBtn;

    @FXML
    private Button addNewCharacterBtn;

    @FXML
    private AnchorPane mainPane;

    private ImageView turnIcon = new ImageView("icons\\turn_icon_SWORD.png");


    //move focus to the next actor in line
    @FXML
    void nextTurn(ActionEvent event) {
        //update current turn index
        if(currentTurnIndex + 1 >= encounterActorList.size()) {
            currentTurnIndex = 0;
        } else {
            currentTurnIndex++;
        }

        //if current actor is dead, we want to skip them
        if(encounterActorList.get(currentTurnIndex).getIsDead()) {   //if dead
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
        for (Actor actor : encounterActorList) {
            if(actor instanceof Ally) {
                Ally tempAlly = (Ally)actor;
                tempAlly.getInitiativeBox().clear();    //clear past initiatives so box is empty
                reusedNames.add(tempAlly);
            }
        }

        DndCombatTracker.getControllerManager().setAllyList(reusedNames);
        
        //switch FXML page to encounter page
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPages/setupPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = DndCombatTracker.getControllerManager().getMainStage();

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        stage.setScene(scene);
        stage.show();
    }

    //opens new window to add new character
    @FXML
    void addNewCharacter(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPages/addNewCharacterPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        AddNewCharacterPageController.stage = stage;

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        stage.setScene(scene);
        stage.show();
    }

    public static void addNewCharacterToEncounter(Actor actor) {
        System.out.println(actor.toString());


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        endEncounterBtn.setTooltip(endEncounterTooltip);
        nextTurnBtn.setTooltip(nextTurnTooltip);

        encounterActorList = DndCombatTracker.getControllerManager().getActorList();
        Enemy tempEnemy;

        //set labels for all actors in actorList
        for (Actor actor : encounterActorList) {
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

                //add listener to health textfield
                tempEnemy.getHealthBox().textProperty().addListener((obs, oldText, newText) -> {
                    if(Integer.parseInt(newText) <= 0) {    //if new health entered is 0 or lower
                        actor.setIsDead(true);  //actor is dead

                        //set labels to be RED
                        actor.getInitiativeLabel().setTextFill(Color.RED);
                        actor.getNameLabel().setTextFill(Color.RED);
                    } else {
                        actor.setIsDead(false); //otherwise "revive" enemy if their health goes above 0

                        //set labels to be BLACK
                        actor.getInitiativeLabel().setTextFill(Color.BLACK);
                        actor.getNameLabel().setTextFill(Color.BLACK);
                    }
                });

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
        turnIcon.setLayoutY(iconYPosition);


        if(encounterActorList.isEmpty()){
            turnIcon.setOpacity(0.0);   //makes picture invisible
        }

        //TO DO change this to a loop for all actor labels and boxes
        for (Actor actor : encounterActorList) {
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
     * Parses input for + or -, then changes current health accordingly
     * @param input
     */
    private String handleHealthChange(String input) {
        Enemy target;

        if(encounterActorList.get(currentTurnIndex) instanceof Enemy) {
            target = (Enemy)encounterActorList.get(currentTurnIndex);
            //check if input is ONLY digits (new current health) or EXACTLY ONE plus followed by ONLY digits (heal)
            //or EXACTLY ONE minus followed by ONLY digits (damage)
            if(input.matches("[0-9]+") || input.matches("^\\+?\\d+$") || input.matches("^\\-?\\d+$")) { 
                if(!input.contains("+") && !input.contains("-")) {  //ONLY DIGITS = update current health
                    target.changeCurrentHealth(Integer.parseInt(input));
                } else if(input.contains("+")) {    //heal
                    target.heal(Integer.parseInt(input.substring(1)));
                } else if(input.contains("-")) {    //take damage
                    target.takeDamage(Integer.parseInt(input.substring(1)));
                }

                return Integer.toString(target.getCurrentHealth());
            }
        }

        return input;
       
    }
}
