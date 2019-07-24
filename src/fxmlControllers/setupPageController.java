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
    protected static ArrayList<Actor> sortedActorList;

    //holds the (unsorted) list of all ally name textfields
    protected ArrayList<TextField> allyNames;

    //holds the (unsorted) list of all ally initiative textfields
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
        //these 6 lines "clear" out these arraylists to be brand new
        sortedActorList = new ArrayList<>();
        allyNames = new ArrayList<>(initialAllyAmount);
        allyInitiatives = new ArrayList<>(initialAllyAmount);
        enemyNames = new ArrayList<>(initialEnemyAmount);
        enemyInitiatives = new ArrayList<>(initialEnemyAmount);
        enemyHealths = new ArrayList<>(initialEnemyAmount);


        //fill ally arrayLists with empty textfields
        for(int i = 0; i < initialAllyAmount; i++) {
            allyNames.add(new TextField());
            allyInitiatives.add(new TextField());
        }

        //fill enemy arrayLists with empty textfields
        for(int i = 0; i < initialEnemyAmount; i++) {
            enemyNames.add(new TextField());
            enemyInitiatives.add(new TextField());
            enemyHealths.add(new TextField());
        }

        //variables hold the layout of the textfield components
        int namePrefWidth = 110;
        int prefHeight = 25;
        int initiativePrefWidth = 45;
        int healthPrefWidth = 61;

        int xCord = 14; //these 3 specify the layout of the textfields
        int yCord = 14;
        int xIncrease = 10;
        int yIncrease = 33;

        //Add ally textfields to the main pane in correct layout
        for(int i = 0; i < allyNames.size(); i++) {
            allyNames.get(i).setLayoutX(xCord);
            allyNames.get(i).setLayoutY(yCord);
            allyNames.get(i).setPrefSize(namePrefWidth, prefHeight);

            allyInitiatives.get(i).setLayoutX(xCord + namePrefWidth + xIncrease);
            allyInitiatives.get(i).setLayoutY(yCord);
            allyInitiatives.get(i).setPrefSize(initiativePrefWidth, prefHeight);

            yCord += yIncrease;

            mainPane.getChildren().add(allyNames.get(i));
            mainPane.getChildren().add(allyInitiatives.get(i));
        }

        xCord = 348;   //these 2 are for the specific layout of the enemy name/initiative textfields
        yCord = 14;

        //this loop adds all enemy textfields and health textfields with correct position and layout
        //and adds them to the main pane in the correct tab order (main textfield -> health textField)
        for(int i = 0; i < enemyNames.size(); i++) {
            enemyNames.get(i).setLayoutX(xCord);
            enemyNames.get(i).setLayoutY(yCord);
            enemyNames.get(i).setPrefSize(namePrefWidth, prefHeight);

            enemyInitiatives.get(i).setLayoutX(xCord + namePrefWidth + xIncrease);
            enemyInitiatives.get(i).setLayoutY(yCord);
            enemyInitiatives.get(i).setPrefSize(initiativePrefWidth, prefHeight);

            enemyHealths.get(i).setLayoutX(xCord + namePrefWidth + xIncrease + initiativePrefWidth + xIncrease);
            enemyHealths.get(i).setLayoutY(yCord);
            enemyHealths.get(i).setPrefSize(healthPrefWidth, prefHeight);

            yCord += yIncrease;

            mainPane.getChildren().add(enemyNames.get(i));
            mainPane.getChildren().add(enemyInitiatives.get(i));
            mainPane.getChildren().add(enemyHealths.get(i));
        }
        
    }

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

    @FXML
    private void addEnemy(ActionEvent event) {
        TextField lastTextField = enemyNames.get(enemyNames.size() - 1);
        TextField secondLastTextField = enemyNames.get(enemyNames.size() - 2);

        TextField lastHealthTextField = enemyHealths.get(enemyHealths.size() - 1);

        int yIncrease = (int)(lastTextField.getLayoutY() - secondLastTextField.getLayoutY());
        int xPosition = (int)lastTextField.getLayoutX();
        int yPosition = (int)(lastTextField.getLayoutY() + yIncrease);

        int xHealthPosition = (int)lastHealthTextField.getLayoutX();

        int prefWidth = (int)lastTextField.getPrefWidth();
        int prefHeight = (int)lastTextField.getPrefHeight();

        int healthPrefWidth = (int)lastHealthTextField.getPrefWidth();

        enemyNames.add(new TextField());
        enemyHealths.add(new TextField());

        enemyNames.get(enemyNames.size() - 1).setLayoutX(xPosition);
        enemyNames.get(enemyNames.size() - 1).setLayoutY(yPosition);
        enemyNames.get(enemyNames.size() - 1).setPrefSize(prefWidth, prefHeight);

        enemyHealths.get(enemyHealths.size() - 1).setLayoutX(xHealthPosition);
        enemyHealths.get(enemyHealths.size() - 1).setLayoutY(yPosition);
        enemyHealths.get(enemyHealths.size() - 1).setPrefSize(healthPrefWidth, prefHeight);

        mainPane.getChildren().add(enemyNames.get(enemyNames.size() - 1));
        mainPane.getChildren().add(enemyHealths.get(enemyHealths.size() - 1));

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
        for(int i = 0; i < allyNames.size(); i++) {
            if(allyNames.get(i).getText().matches(".*[a-zA-Z].*") && allyNames.get(i).getText().matches(".*[0-9].*") && allyNames.get(i).getText().contains(",")) {
                String allyInfo = allyNames.get(i).getText();
                sortedActorList.add(new Ally(parseName(allyInfo), parseInitiative(allyInfo)));
            }
        }

        for(int i = 0; i < enemyNames.size(); i++) {
            if(enemyNames.get(i).getText().matches(".*[a-zA-Z].*") && enemyNames.get(i).getText().matches(".*[0-9].*") && enemyNames.get(i).getText().contains(",") && enemyHealths.get(i).getText().matches(".*[0-9].*")) {
                String enemyInfo = enemyNames.get(i).getText();
                int enemyHealth = Integer.parseInt(enemyHealths.get(i).getText());
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
        mainPane.getChildren().addAll(allyNames);

        for(int i = 0; i < enemyNames.size(); i++) {
            mainPane.getChildren().add(enemyNames.get(i));
            mainPane.getChildren().add(enemyHealths.get(i));
        }
    }

}