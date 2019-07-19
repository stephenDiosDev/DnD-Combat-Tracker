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
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.image.*;

public class setupPageController implements Initializable{

    private int initialAllyAmount = 5;  //this determines, on start up, how many ally/enemy fields to show
    private int initialEnemyAmount = 5;

    //holds the sorted initiative list of all allies/enemies
    protected ArrayList<Actor> sortedActorList;

    //holds the (unsorted) list of all ally textfields
    protected ArrayList<TextField> allyListTextFields;

    //holds the (unsorted) list of all enemy textfields (name and initiative only)
    protected ArrayList<TextField> enemyListTextFields;

    //holds the (unsorted) list of all enemy health textfields
    protected ArrayList<TextField> enemyListHealthTextFields;



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
        allyListTextFields = new ArrayList<>(initialAllyAmount);
        enemyListTextFields = new ArrayList<>(initialEnemyAmount);
        enemyListHealthTextFields = new ArrayList<>(initialEnemyAmount);

        for(int i = 0; i < initialAllyAmount; i++) {
            allyListTextFields.add(new TextField());
        }

        for(int i = 0; i < initialEnemyAmount; i++) {
            enemyListTextFields.add(new TextField());
            enemyListHealthTextFields.add(new TextField());
        }

        int normalTextFieldPrefWidth = 149;
        int normalTextFieldPrefHeight = 25;

        int healthTextFieldPrefWidth = 61;
        int healthTextFieldPrefHeight = normalTextFieldPrefHeight;

        int xCord = 14; //these 3 specify the layout of the textfields
        int yCord = 14;
        int yIncrease = 33;

        //Add ally textfields to the main pane
        for (TextField allyText : allyListTextFields) {
            allyText.setLayoutX(xCord);
            allyText.setLayoutY(yCord);
            allyText.setPrefSize(normalTextFieldPrefWidth, normalTextFieldPrefHeight);

            yCord += yIncrease;

            mainPane.getChildren().add(allyText);

        }

        int xCordMainTextField = 348;   //these 3 are for the specific layout of the enemy name/initiative textfields
        yCord = 14;
        yIncrease = 33;

        int xCordHealthTextField = 524; //this is for the specific x layout of the health textfield

        //this loop adds all enemy textfields and health textfields with correct position and layout
        //and adds them to the main pane in the correct tab order (main textfield -> health textField)
        for(int i = 0; i < enemyListTextFields.size(); i++) {
            enemyListTextFields.get(i).setLayoutX(xCordMainTextField);
            enemyListTextFields.get(i).setLayoutY(yCord);
            enemyListTextFields.get(i).setPrefSize(normalTextFieldPrefWidth, normalTextFieldPrefHeight);

            enemyListHealthTextFields.get(i).setLayoutX(xCordHealthTextField);
            enemyListHealthTextFields.get(i).setLayoutY(yCord);
            enemyListHealthTextFields.get(i).setPrefSize(healthTextFieldPrefWidth, healthTextFieldPrefHeight);

            yCord += yIncrease;

            mainPane.getChildren().add(enemyListTextFields.get(i));
            mainPane.getChildren().add(enemyListHealthTextFields.get(i));
        }
        
    }

    @FXML
    private void addAlly(ActionEvent event) {
        TextField lastTextField = allyListTextFields.get(allyListTextFields.size() - 1);
        TextField secondLastTextField = allyListTextFields.get(allyListTextFields.size() - 2);

        int yIncrease = (int)(lastTextField.getLayoutY() - secondLastTextField.getLayoutY());

        int xPosition = (int)lastTextField.getLayoutX();
        int yPosition = (int)(lastTextField.getLayoutY() + yIncrease);

        int prefWidth = (int)lastTextField.getPrefWidth();
        int prefHeight = (int)lastTextField.getPrefHeight();

        allyListTextFields.add(new TextField());

        allyListTextFields.get(allyListTextFields.size() - 1).setLayoutX(xPosition);
        allyListTextFields.get(allyListTextFields.size() - 1).setLayoutY(yPosition);
        allyListTextFields.get(allyListTextFields.size() - 1).setPrefSize(prefWidth, prefHeight);

        mainPane.getChildren().add(allyListTextFields.get(allyListTextFields.size() - 1));

        reorderTabOrder();
    }

    @FXML
    private void addEnemy(ActionEvent event) {
        TextField lastTextField = enemyListTextFields.get(enemyListTextFields.size() - 1);
        TextField secondLastTextField = enemyListTextFields.get(enemyListTextFields.size() - 2);

        TextField lastHealthTextField = enemyListHealthTextFields.get(enemyListHealthTextFields.size() - 1);

        int yIncrease = (int)(lastTextField.getLayoutY() - secondLastTextField.getLayoutY());
        int xPosition = (int)lastTextField.getLayoutX();
        int yPosition = (int)(lastTextField.getLayoutY() + yIncrease);

        int xHealthPosition = (int)lastHealthTextField.getLayoutX();

        int prefWidth = (int)lastTextField.getPrefWidth();
        int prefHeight = (int)lastTextField.getPrefHeight();

        int healthPrefWidth = (int)lastHealthTextField.getPrefWidth();

        enemyListTextFields.add(new TextField());
        enemyListHealthTextFields.add(new TextField());

        enemyListTextFields.get(enemyListTextFields.size() - 1).setLayoutX(xPosition);
        enemyListTextFields.get(enemyListTextFields.size() - 1).setLayoutY(yPosition);
        enemyListTextFields.get(enemyListTextFields.size() - 1).setPrefSize(prefWidth, prefHeight);

        enemyListHealthTextFields.get(enemyListHealthTextFields.size() - 1).setLayoutX(xHealthPosition);
        enemyListHealthTextFields.get(enemyListHealthTextFields.size() - 1).setLayoutY(yPosition);
        enemyListHealthTextFields.get(enemyListHealthTextFields.size() - 1).setPrefSize(healthPrefWidth, prefHeight);

        mainPane.getChildren().add(enemyListTextFields.get(enemyListTextFields.size() - 1));
        mainPane.getChildren().add(enemyListHealthTextFields.get(enemyListHealthTextFields.size() - 1));

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
        for(int i = 0; i < allyListTextFields.size(); i++) {
            if(allyListTextFields.get(i).getText().matches(".*[a-zA-Z].*") && allyListTextFields.get(i).getText().matches(".*[0-9].*") && allyListTextFields.get(i).getText().contains(",")) {
                String allyInfo = allyListTextFields.get(i).getText();
                sortedActorList.add(new Ally(parseName(allyInfo), parseInitiative(allyInfo)));
            }
        }

        for(int i = 0; i < enemyListTextFields.size(); i++) {
            if(enemyListTextFields.get(i).getText().matches(".*[a-zA-Z].*") && enemyListTextFields.get(i).getText().matches(".*[0-9].*") && enemyListTextFields.get(i).getText().contains(",") && enemyListHealthTextFields.get(i).getText().matches(".*[0-9].*")) {
                String enemyInfo = enemyListTextFields.get(i).getText();
                int enemyHealth = Integer.parseInt(enemyListHealthTextFields.get(i).getText());
                sortedActorList.add(new Enemy(parseName(enemyInfo), parseInitiative(enemyInfo), enemyHealth));
            }
        }
    }

    /**
     * Receives textfield input and returns the name
     */
    private String parseName(String input) {
        return input.substring(0, input.indexOf(","));

    }

    /**
     * Receives textfield input and returns the initiative
     */
    private int parseInitiative(String input) {
        return Integer.parseInt(input.substring(input.indexOf(",") + 1));
        
    }


    /**
     * Simply reorders the tab order on the main pane (heavily inefficient!)
     */
    private void reorderTabOrder() {
        mainPane.getChildren().clear();
        mainPane.getChildren().addAll(allyListTextFields);

        for(int i = 0; i < enemyListTextFields.size(); i++) {
            mainPane.getChildren().add(enemyListTextFields.get(i));
            mainPane.getChildren().add(enemyListHealthTextFields.get(i));
        }
    }

}