package fxmlControllers;

import application.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import entities.*;
import java.util.*;

import com.sun.javafx.scene.control.skin.TextFieldSkin;

import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.image.*;

public class setupPageController implements Initializable{

    private int initialAllyAmount = 5;  //this determines, on start up, how many ally/enemy fields to show
    private int initialEnemyAmount = 5;

    //holds the sorted initiative list of all allies/enemies
    protected static ArrayList<Actor> sortedActorList;

    //holds the (unsorted) list of all ally names
    protected ArrayList<TextField> allyNames;

    //holds the (unsorted) list of all aly initiatives
    protected ArrayList<TextField> allyInitiatives;

    //holds the (unsorted) list of all enemy names
    protected ArrayList<TextField> enemyNames;

    //holds the (unsorted) list of all enemy initiatives
    protected ArrayList<TextField> enemyInitiatives;

    //holds the (unsorted) list of all enemy health textfields
    protected ArrayList<TextField> enemyHealths;



    @FXML
    private Button runEncounterBtn;

    @FXML
    private Button addEnemyBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button addAllyBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //these 4 lines "clear" out these arraylists to be brand new
        sortedActorList = new ArrayList<>();
        allyNames = new ArrayList<>(initialAllyAmount);
        allyInitiatives = new ArrayList<>(initialAllyAmount);
        enemyNames = new ArrayList<>(initialEnemyAmount);
        enemyInitiatives = new ArrayList<>(initialEnemyAmount);
        enemyHealths = new ArrayList<>(initialEnemyAmount);

        //fill ally lists with blank textfields
        for(int i = 0; i < initialAllyAmount; i++) {
            allyNames.add(new TextField());
            allyInitiatives.add(new TextField());
        }

        //fill enemy lists with blank textfields
        for(int i = 0; i < initialEnemyAmount; i++) {
            enemyNames.add(new TextField());
            enemyInitiatives.add(new TextField());
            enemyHealths.add(new TextField());
        }

        //these variables specify the layouts of various options of the textfields
        int normalTextFieldPrefWidth = 123;     //pref width and heights of a "normal" textfield
        int normalTextFieldPrefHeight = 25;

        int initiativeTextFieldPrefWidth = 61;  //pref width/height of the initiative textfield
        int initiativeTextFieldPrefHeight = normalTextFieldPrefHeight;

        int healthTextFieldPrefWidth = 61;      //pref width and height of the health textfield (not normal)
        int healthTextFieldPrefHeight = normalTextFieldPrefHeight;

        int xCord = 14; //these 3 specify the layout of the textfields
        int yCord = 14;
        int yIncrease = 33;

        int xCordSpacing = 10;  //amount of space between side-by-side textfields

        //Add ally textfields to the main pane with proper layout
        for(int i = 0; i < allyNames.size(); i++) {
            TextField allyName = allyNames.get(i);
            TextField allyInitiative = allyInitiatives.get(i);

            allyName.setLayoutX(xCord);
            allyName.setLayoutY(yCord);
            allyName.setPrefSize(normalTextFieldPrefWidth, normalTextFieldPrefHeight);

            allyInitiative.setLayoutX(xCord + normalTextFieldPrefWidth + xCordSpacing);
            allyInitiative.setLayoutY(yCord);
            allyInitiative.setPrefSize(initiativeTextFieldPrefWidth, initiativeTextFieldPrefHeight);

            yCord += yIncrease;

            mainPane.getChildren().add(allyName);
            mainPane.getChildren().add(allyInitiative);
        }


        int xCordEnemyNormalTextField = 348;   //these 3 are for the specific layout of the enemy name/initiative textfields
        yCord = 14;
        yIncrease = 33;

        //add enemy textfields to the main pane with proper layout
        for(int i = 0; i < enemyNames.size(); i++) {
            TextField enemyName = enemyNames.get(i);
            TextField enemyInitiative = enemyInitiatives.get(i);
            TextField enemyHealth = enemyHealths.get(i);

            enemyName.setLayoutX(xCordEnemyNormalTextField);
            enemyName.setLayoutX(yCord);
            enemyName.setPrefSize(normalTextFieldPrefWidth, normalTextFieldPrefHeight);

            enemyInitiative.setLayoutX(xCordEnemyNormalTextField + normalTextFieldPrefWidth + xCordSpacing);
            enemyInitiative.setLayoutY(yCord);
            enemyInitiative.setPrefSize(initiativeTextFieldPrefWidth, initiativeTextFieldPrefHeight);

            enemyHealth.setLayoutX(enemyInitiative.getLayoutX() + initiativeTextFieldPrefWidth + xCordSpacing);
            enemyHealth.setLayoutY(yCord);
            enemyHealth.setPrefSize(healthTextFieldPrefWidth, healthTextFieldPrefHeight);

            yCord +=yIncrease;

            mainPane.getChildren().add(enemyName);
            mainPane.getChildren().add(enemyInitiative);
            mainPane.getChildren().add(enemyHealth);

        }
        
    }

    /**
     * This action is triggered by the "Add Ally" button. It simply adds a new row of ally textfields
     */
    @FXML
    private void addAlly(ActionEvent event) {
        TextField lastTextField = allyNames.get(allyNames.size() - 1);
        TextField secondLastTextField = allyNames.get(allyNames.size() - 2);

        int yIncrease = (int)(lastTextField.getLayoutY() - secondLastTextField.getLayoutY());

        int xPosition = (int)lastTextField.getLayoutX();
        int yPosition = (int)(lastTextField.getLayoutY() + yIncrease);

        int prefWidth = (int)lastTextField.getPrefWidth();
        int prefHeight = (int)lastTextField.getPrefHeight();

        allyNames.add(new TextField());

        allyNames.get(allyNames.size() - 1).setLayoutX(xPosition);
        allyNames.get(allyNames.size() - 1).setLayoutY(yPosition);
        allyNames.get(allyNames.size() - 1).setPrefSize(prefWidth, prefHeight);

        mainPane.getChildren().add(allyNames.get(allyNames.size() - 1));

        reorderTabOrder();
    }


    /**
     * This action is triggered by the "Add Enemy" button. It simply adds a new row of enemy textfields
     */
    @FXML
    private void addEnemy(ActionEvent event) {
        //get the last 2 textfields to determine some of the layout attributes
        TextField lastTextField = enemyNames.get(enemyNames.size() - 1);
        TextField secondLastTextField = enemyNames.get(enemyNames.size() - 2);

        //layout attributes for every new textfield
        int yIncrease = (int)(lastTextField.getLayoutX() - secondLastTextField.getLayoutY());
        int xCordSpacing = 10;

        int xPosition = (int)lastTextField.getLayoutX();
        int yPosition = (int)lastTextField.getLayoutY();

        int namePrefWidth = (int)lastTextField.getPrefWidth();
        int namePrefHeight = (int)lastTextField.getPrefHeight();

        int initiativePrefWidth = (int)enemyInitiatives.get(enemyInitiatives.size() - 1).getPrefWidth();
        int initiativePrefHeight = (int)enemyInitiatives.get(enemyInitiatives.size() - 1).getPrefHeight();

        int healthPrefWidth = (int)enemyHealths.get(enemyHealths.size() - 1).getPrefWidth();
        int healthPrefHeight = (int)enemyHealths.get(enemyHealths.size() - 1).getPrefHeight();

        //layout is ready, create new textfields
        TextField newEnemyName = new TextField();
        TextField newEnemyInitiative = new TextField();
        TextField newEnemyHealth = new TextField();

        //apply layout to each textfield

        //x position layout
        newEnemyName.setLayoutX(xPosition);
        newEnemyInitiative.setLayoutX(xPosition + xCordSpacing);
        newEnemyHealth.setLayoutX(xPosition + xCordSpacing + xCordSpacing);

        //y position layout
        newEnemyName.setLayoutY(yPosition + yIncrease);
        newEnemyInitiative.setLayoutY(yPosition + yIncrease);
        newEnemyHealth.setLayoutY(yPosition + yIncrease);

        //pref size layout
        newEnemyName.setPrefSize(namePrefWidth, namePrefHeight);
        newEnemyInitiative.setPrefSize(initiativePrefWidth, initiativePrefHeight);
        newEnemyHealth.setPrefSize(healthPrefWidth, healthPrefHeight);

        //add textfields to both the main pane and their arraylist
        enemyNames.add(newEnemyName);
        enemyInitiatives.add(newEnemyInitiative);
        enemyHealths.add(newEnemyHealth);

        mainPane.getChildren().add(newEnemyName);
        mainPane.getChildren().add(newEnemyInitiative);
        mainPane.getChildren().add(newEnemyHealth);

        //call function to move all textfields to proper tab order
        reorderTabOrder();

    }

    @FXML
    private void runEncounter(ActionEvent event) throws IOException{   
        addGoodTextFieldsToActorList();
        sortActorListDescending();

        //print for debug
        for (Actor actor : sortedActorList) {
            System.out.println(actor.toString());
        } 

        //switch FXML page to encounter page
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPages/encounterPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = DndCombatTracker.mainStage;

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        stage.setScene(scene);
        stage.show();
    }

    private void sortActorListDescending() {
        Collections.sort(sortedActorList, Comparator.comparingInt(Actor::getInitiativeTotal).reversed());
    }

    /**
     * This removes any empty or otherwise "bad" textfields from the ally and enemy lists
     */
    private void addGoodTextFieldsToActorList() {
        //add allies to actor list
        for(int i = 0; i < allyNames.size(); i++) {
            TextField name = allyNames.get(i);      //place current textfield into temp variable
            TextField initiative = allyInitiatives.get(i);

            //name should not be empty, initiative should only be numbers
            if(!name.getText().isEmpty() && initiative.getText().matches(".*[0-9].*")) {
                sortedActorList.add(new Ally(name.getText(), Integer.parseInt(initiative.getText())));
            }
        }

        //add enemies to actor list
        for(int i = 0; i < enemyNames.size(); i++) {
            TextField name = enemyNames.get(i);
            TextField initiative = enemyInitiatives.get(i);
            TextField health = enemyHealths.get(i);

            //name should not be empty, initiative and health should only be numbers
            if(!name.getText().isEmpty() && initiative.getText().matches(".*[0-9].*") && health.getText().matches(".*[0-9].*")) {
                sortedActorList.add(new Enemy(name.getText(), Integer.parseInt(initiative.getText()), Integer.parseInt(health.getText())));
            }
        }
       
    }

    /**
     * Simply reorders the tab order on the main pane (heavily inefficient!)
     */
    private void reorderTabOrder() {
        //clear out main pane so we aren't adding duplicates
        mainPane.getChildren().clear();

        //add allies 
        for(int i = 0; i < allyNames.size(); i++) {
            mainPane.getChildren().add(allyNames.get(i));
            mainPane.getChildren().add(allyInitiatives.get(i));
        }

        //add enemies
        for(int i = 0; i < enemyNames.size(); i++) {
            mainPane.getChildren().add(enemyNames.get(i));
            mainPane.getChildren().add(enemyInitiatives.get(i));
            mainPane.getChildren().add(enemyHealths.get(i));
        }
    }

}