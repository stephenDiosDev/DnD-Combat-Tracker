package fxmlControllers;

import application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import entities.*;
import java.util.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.image.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import managers.Scenes;


public class SetupPageController implements Initializable{

    private int initialAllyAmount = 5;  //this determines, on start up, how many ally/enemy fields to show
    private int initialEnemyAmount = 5;

    //holds the unsorted list of allies
    protected ArrayList<Ally> allyList;

    //holds the unsorted list of enemies
    protected ArrayList<Enemy> enemyList;

    //x and y spacing between adjacent textfield components
    private final int xIncrease = 10;
    private final int yIncrease = 33;

    Tooltip nameTooltip = new Tooltip("The name of the character.");
    Tooltip initiativeTooltip = new Tooltip("The initiative total of the character. Must be an integer!");
    Tooltip healthTooltip = new Tooltip("The total health of the character. Must be an integer!");

    Tooltip addAllyTooltip = new Tooltip("Adds a new ally to the encounter. All ally fields must be filled properly.");
    Tooltip addEnemyTooltip = new Tooltip("Adds a new enemy to the encounter. All enemy fields must be filled properly.");
    Tooltip runEncounterTooltip = new Tooltip("This runs the encounter with the above allies and enemies sorted in order.");

    Tooltip allyTooltip = new Tooltip("An ally is your players and any NPC that you don't want to track the health of.");
    Tooltip enemyTooltip = new Tooltip("An enemy is your monsters and any NPC that you do want to track the health of.");


    @FXML
    private Button runEncounterBtn;

    @FXML
    private Button addEnemyBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button addAllyBtn;

    @FXML
    private Label allyLabel;

    @FXML
    private Label enemyLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allyTooltip.setFont(new Font(18));
        enemyTooltip.setFont(new Font(18));

        runEncounterBtn.setTooltip(runEncounterTooltip);
        addEnemyBtn.setTooltip(addEnemyTooltip);
        addAllyBtn.setTooltip(addAllyTooltip);
        allyLabel.setTooltip(allyTooltip);
        enemyLabel.setTooltip(enemyTooltip);
        //these lines "clear" out these arraylists to be brand new
        //or fills them if needed
        DndCombatTracker.getControllerManager().setActorList(new ArrayList<>());
        enemyList = new ArrayList<>();
        allyList = new ArrayList<>();



        //fill enemy list with "empty" enemies
        for(int i = 0; i < initialEnemyAmount; i++) {
            enemyList.add(new Enemy());
        }

        //if encounter has not been run, set to empty as normal
        if(DndCombatTracker.getControllerManager().getAllyList().isEmpty() || DndCombatTracker.getControllerManager().getAllyList() == null) {
            //fill ally list with "empty" allies
            for(int i = 0; i < initialAllyAmount; i++) {
                allyList.add(new Ally());
            }
        } else {    //otherwise, refill with used ally names
            for(int i = 0; i < DndCombatTracker.getControllerManager().getAllyList().size(); i++) {
                Ally temp = DndCombatTracker.getControllerManager().getAllyList().get(i);
                allyList.add(temp);
            }
        }

        //variables hold the layout of the textfield components
        int namePrefWidth = 110;
        int prefHeight = 25;
        int initiativePrefWidth = 45;
        int healthPrefWidth = 61;

        int xCord = 14; //these 3 specify the layout of the textfields
        int yCord = 14;

        //Add ally textfields to the main pane in correct layout
        for (Ally ally : allyList) {
            ally.setNameBoxLayout(xCord, yCord, namePrefWidth, prefHeight);
            ally.setInitiativeBoxLayout(xCord + namePrefWidth + xIncrease, yCord, initiativePrefWidth, prefHeight);

            ally.getNameBox().setTooltip(nameTooltip);
            ally.getInitiativeBox().setTooltip(initiativeTooltip);

            yCord += yIncrease;

            ally.getNameBox().setText(ally.getName());  //sets the name to the name box

            mainPane.getChildren().add(ally.getNameBox());
            mainPane.getChildren().add(ally.getInitiativeBox());
        }

        xCord = 348;   //these 2 are for the specific layout of the enemy name/initiative textfields
        yCord = 14;

        //this loop adds all enemy textfields and health textfields with correct position and layout
        //and adds them to the main pane in the correct tab order (main textfield -> health textField)
        for (Enemy enemy : enemyList) {
            enemy.setNameBoxLayout(xCord, yCord, namePrefWidth, prefHeight);
            enemy.setInitiativeBoxLayout(xCord + namePrefWidth + xIncrease, yCord, initiativePrefWidth, prefHeight);
            enemy.setHealthBoxLayout(xCord + namePrefWidth + xIncrease + initiativePrefWidth + xIncrease, yCord, healthPrefWidth, prefHeight);

            enemy.getNameBox().setTooltip(nameTooltip);
            enemy.getInitiativeBox().setTooltip(initiativeTooltip);
            enemy.getHealthBox().setTooltip(healthTooltip);

            yCord += yIncrease;

            mainPane.getChildren().add(enemy.getNameBox());
            mainPane.getChildren().add(enemy.getInitiativeBox());
            mainPane.getChildren().add(enemy.getHealthBox());
        }
        
    }

    //adds a new ally
    @FXML
    private void addAlly(ActionEvent event) {
        Ally lastAlly = allyList.get(allyList.size() - 1);

        double xNamePosition = lastAlly.getNameBox().getLayoutX();   //x positions
        double xInitiativePosition = lastAlly.getInitiativeBox().getLayoutX();

        double yPosition = (lastAlly.getNameBox().getLayoutY() + yIncrease);     //y position

        double namePrefWidth = lastAlly.getNameBox().getPrefWidth(); //pref widths and heights
        double initiativePrefWidth = lastAlly.getInitiativeBox().getPrefWidth();
        double prefHeight = lastAlly.getNameBox().getPrefHeight();

        Ally newAlly = new Ally();

        //apply layout to both name and initiative box
        newAlly.setNameBoxLayout(xNamePosition, yPosition, namePrefWidth, prefHeight); 
        newAlly.setInitiativeBoxLayout(xInitiativePosition, yPosition, initiativePrefWidth, prefHeight);

        //add new ally to both the ally list and main pane
        allyList.add(newAlly);

        mainPane.getChildren().add(newAlly.getNameBox());
        mainPane.getChildren().add(newAlly.getInitiativeBox());

        reorderTabOrder();  //reorder tab order for newly added textfields
    }

    //adds a new enemy
    @FXML
    private void addEnemy(ActionEvent event) {
        Enemy lastEnemy = enemyList.get(enemyList.size() - 1);

        double yPosition = (lastEnemy.getNameBox().getLayoutY() + yIncrease);

        double xNamePosition = lastEnemy.getNameBox().getLayoutX();   //get x positions of all new textfields
        double xInitiativePosition = lastEnemy.getInitiativeBox().getLayoutX();
        double xHealthPosition = lastEnemy.getHealthBox().getLayoutX();

        double namePrefWidth = lastEnemy.getNameBox().getPrefWidth(); //get pref widths for all new textfields
        double initiativePrefWidth = lastEnemy.getInitiativeBox().getPrefWidth();
        double healthPrefWidth = lastEnemy.getHealthBox().getPrefWidth();

        double prefHeight = lastEnemy.getNameBox().getPrefHeight();   //get pref height for all new textfields

        Enemy newEnemy = new Enemy();

        newEnemy.setNameBoxLayout(xNamePosition, yPosition, namePrefWidth, prefHeight);
        newEnemy.setInitiativeBoxLayout(xInitiativePosition, yPosition, initiativePrefWidth, prefHeight);
        newEnemy.setHealthBoxLayout(xHealthPosition, yPosition, healthPrefWidth, prefHeight);

        enemyList.add(newEnemy);

        mainPane.getChildren().add(newEnemy.getNameBox());    //add new textfields to mainPane in order
        mainPane.getChildren().add(newEnemy.getInitiativeBox());  
        mainPane.getChildren().add(newEnemy.getHealthBox());

        reorderTabOrder();  //reorder the tab order for newly added textfields

    }

    //launches the encounter page
    @FXML
    private void runEncounter(ActionEvent event) throws IOException{  
        addGoodTextFieldsToActorList(); //remove bad entries and add the good ones to the actor list
        applyTextToActors();
        DndCombatTracker.getControllerManager().sortActorListDescending();

        //print for debug
        for (Actor actor : DndCombatTracker.getControllerManager().getActorList()) {
            System.out.println(actor.toString());
        } 

        //switch FXML page to encounter page
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.ENCOUNTER));
        loader.setController(DndCombatTracker.getControllerManager().getEncounterPageController());
        Parent root = loader.load();
        DndCombatTracker.getControllerManager().setRootEncounterScene(root);
        Stage stage = DndCombatTracker.getControllerManager().getMainStage();

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        DndCombatTracker.getControllerManager().setSceneToEncounterScene();
        stage.show();
    }

    /**
     * This removes any empty or otherwise "bad" textfields from the ally and enemy lists,
     * and adds the good entries to the actor list in controller manager
     */
    private void addGoodTextFieldsToActorList() {
        //adds all "well formatted" allies
        for(Ally ally : allyList) {
            //if ally name is non-empty AND ally initiative is just an int
            if(!ally.getNameBox().getText().isEmpty() && ally.getInitiativeBox().getText().matches(".*[0-9].*")) {
                //add ally to actor list
                DndCombatTracker.getControllerManager().getActorList().add(ally);
            }
        }

        //adds all "well formatted" enemies
        for (Enemy enemy : enemyList) {
            //if enemy name is non-empty AND enemy initiative is just an int AND enemy health is just an int
            if(!enemy.getNameBox().getText().isEmpty() && enemy.getInitiativeBox().getText().matches(".*[0-9].*") && enemy.getHealthBox().getText().matches(".*[0-9].*")) {
                //add enemy to actor list
                DndCombatTracker.getControllerManager().getActorList().add(enemy);
            }   
        }
    }

    //applies the textfield information in both ally and enemy lists
    private void applyTextToActors() {
        ArrayList<Actor> actorList = DndCombatTracker.getControllerManager().getActorList();
        for (Actor actor : actorList) {
            actor.applyTextFieldInfo();
        }

        DndCombatTracker.getControllerManager().setActorList(actorList);
    }
    /**
     * Simply reorders the tab order on the main pane (heavily inefficient!)
     */
    private void reorderTabOrder() {
        mainPane.getChildren().clear();
        
        //add ally textfields in order
        for (Ally ally : allyList) {
            mainPane.getChildren().add(ally.getNameBox());
            mainPane.getChildren().add(ally.getInitiativeBox());
        }

        //add enemy textfields in order
        for (Enemy enemy : enemyList) {
            mainPane.getChildren().add(enemy.getNameBox());
            mainPane.getChildren().add(enemy.getInitiativeBox());
            mainPane.getChildren().add(enemy.getHealthBox());
        }
    }
}