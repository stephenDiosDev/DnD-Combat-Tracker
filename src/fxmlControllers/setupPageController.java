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

    //holds the unsorted list of allies
    protected ArrayList<Ally> allyList;

    //holds the unsorted list of enemies
    protected ArrayList<Enemy> enemyList;

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

    //adds a new row of textfields for a new ally
    @FXML
    private void addAlly(ActionEvent event) {
        TextField lastName = allyNames.get(allyNames.size() - 1);   //get last and second last name for layout
        TextField secondLastName = allyNames.get(allyNames.size() - 2);

        TextField lastInitiative = allyInitiatives.get(allyInitiatives.size() - 1); //get last initiative for layout

        double xNamePosition = lastName.getLayoutX();   //x positions
        double xInitiativePosition = lastInitiative.getLayoutX();

        double yIncrease = (lastName.getLayoutY() - secondLastName.getLayoutY());   //amount to increase y
        double yPosition = (lastName.getLayoutY() + yIncrease);     //y position

        double namePrefWidth = lastName.getPrefWidth(); //pref widths and heights
        double initiativePrefWidth = lastInitiative.getPrefWidth();
        double prefHeight = lastName.getPrefHeight();

        TextField newName = new TextField();    //create new textfields to add
        TextField newInitiative = new TextField();

        newName.setLayoutX(xNamePosition);  //apply layout
        newName.setLayoutY(yPosition);
        newName.setPrefSize(namePrefWidth, prefHeight);

        newInitiative.setLayoutX(xInitiativePosition);  //apply layout
        newInitiative.setLayoutY(yPosition);
        newInitiative.setPrefSize(initiativePrefWidth, prefHeight);

        allyNames.add(newName);     //add new textfields to proper arraylist
        allyInitiatives.add(newInitiative);

        mainPane.getChildren().add(newName);        //add new textfields to mainPane
        mainPane.getChildren().add(newInitiative);

        reorderTabOrder();  //reorder tab order for newly added textfields
    }

    //adds a new row of textfields for a new enemy
    @FXML
    private void addEnemy(ActionEvent event) {
        TextField lastName = enemyNames.get(enemyNames.size() - 1); //get last and second last names for layout
        TextField secondLastName = enemyNames.get(enemyNames.size() - 2);

        TextField lastInitiative = enemyInitiatives.get(enemyInitiatives.size() - 1);   //get last initiative for layout

        TextField lastHealth = enemyHealths.get(enemyHealths.size() - 1);   //get last health for layout

        double yIncrease = (lastName.getLayoutY() - secondLastName.getLayoutY());   //find how much y changes and the new y position
        double yPosition = (lastName.getLayoutY() + yIncrease);

        double xNamePosition = lastName.getLayoutX();   //get x positions of all new textfields
        double xInitiativePosition = lastInitiative.getLayoutX();
        double xHealthPosition = lastHealth.getLayoutX();

        double namePrefWidth = lastName.getPrefWidth(); //get pref widths for all new textfields
        double initiativePrefWidth = lastInitiative.getPrefWidth();
        double healthPrefWidth = lastHealth.getPrefWidth();

        double prefHeight = lastName.getPrefHeight();   //get pref height for all new textfields

        TextField newName = new TextField();    //create new textfields to add
        TextField newInitiative = new TextField();
        TextField newHealth = new TextField();

        newName.setLayoutX(xNamePosition);  //set layout for new textfields
        newName.setLayoutY(yPosition);
        newName.setPrefSize(namePrefWidth, prefHeight);

        newInitiative.setLayoutX(xInitiativePosition);  //set layout for new textfields
        newInitiative.setLayoutY(yPosition);
        newInitiative.setPrefSize(initiativePrefWidth, prefHeight);

        newHealth.setLayoutX(xHealthPosition);  //set layout for new textfields
        newHealth.setLayoutY(yPosition);
        newHealth.setPrefSize(healthPrefWidth, prefHeight);

        enemyNames.add(newName);    //add new textfields to proper arraylist
        enemyInitiatives.add(newInitiative);
        enemyHealths.add(newHealth);

        mainPane.getChildren().add(newName);    //add new textfields to mainPane in order
        mainPane.getChildren().add(newInitiative);  
        mainPane.getChildren().add(newHealth);

        reorderTabOrder();  //reorder the tab order for newly added textfields

    }

    //launches the encounter page
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

    //sorts the full actor list into descending order based on initiative
    //highest initiative first, lowest last
    private void sortActorListDescending() {
        Collections.sort(sortedActorList, Comparator.comparingInt(Actor::getInitiativeTotal).reversed());
    }

    /**
     * This removes any empty or otherwise "bad" textfields from the ally and enemy lists
     */
    private void addGoodTextFieldsToActorList() {
        //add all allies
        for(int i = 0; i < allyNames.size(); i++) {
            //if ally name is non-empty AND ally initiative is just an int
            if(!allyNames.get(i).getText().isEmpty() && allyInitiatives.get(i).getText().matches(".*[0-9].*")) {
                //add new ally to actor list
                sortedActorList.add(new Ally(allyNames.get(i).getText(), Integer.parseInt(allyInitiatives.get(i).getText())));
            }
        }

        //add all enemies
        for(int i = 0; i < enemyNames.size(); i++) {
            //if enemy name is non-empty AND enemy initiative is just and int AND enemy health is just an int
            if(!enemyNames.get(i).getText().isEmpty() && enemyInitiatives.get(i).getText().matches(".*[0-9].*") && enemyHealths.get(i).getText().matches(".*[0-9].*")) {
                //add new enemy to actor list
                sortedActorList.add(new Enemy(enemyNames.get(i).getText(), Integer.parseInt(enemyInitiatives.get(i).getText()), Integer.parseInt(enemyHealths.get(i).getText())));
            }
        }
    }

    /**
     * Simply reorders the tab order on the main pane (heavily inefficient!)
     */
    private void reorderTabOrder() {
        mainPane.getChildren().clear();
        
        //add ally textfields in order
        for(int i = 0; i < allyNames.size(); i++) {
            mainPane.getChildren().add(allyNames.get(i));
            mainPane.getChildren().add(allyInitiatives.get(i));
        }

        //add enemy textfields in order
        for(int i = 0; i < enemyNames.size(); i++) {
            mainPane.getChildren().add(enemyNames.get(i));
            mainPane.getChildren().add(enemyInitiatives.get(i));
            mainPane.getChildren().add(enemyHealths.get(i));
        }
    }

}