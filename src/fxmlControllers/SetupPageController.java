package fxmlControllers;

import application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import entities.*;

import java.nio.Buffer;
import java.util.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.image.*;
import javafx.scene.text.Font;


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
    private VBox mainVbox;

    @FXML
    private AnchorPane buttonBar;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private Button addAllyBtn;

    @FXML
    private Label allyLabel;

    @FXML
    private Label enemyLabel;

    @FXML
    private Label allyNameLabel;

    @FXML
    private Label allyInitiativeLabel;

    @FXML
    private Label enemyNameLabel;

    @FXML
    private Label enemyInitiativeLabel;

    @FXML
    private Label enemyHealthLabel;

    @FXML
    private MenuItem backgroundBlack;

    @FXML
    private MenuItem backgroundGrey;

    @FXML
    private MenuItem backgroundBlue;

    @FXML
    private MenuItem backgroundRed;

    @FXML
    private MenuItem backgroundGreen;

    @FXML
    private MenuItem backgroundPurple;

    @FXML
    private MenuItem backgroundLightBlue;

    @FXML
    private MenuItem backgroundDefault;

    @FXML
    private MenuItem saveAllies;

    @FXML
    private MenuItem loadAllies;

    @FXML
    private CheckMenuItem loadPartyOnStartup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPartyOnStartup.setSelected(DndCombatTracker.getLoadPartyOnStartup());

        //if we want to load party on startup, set initial ally amount to
        //the correct value. Otherwise, we go with the default 5
        if(loadPartyOnStartup.isSelected())
            initialAllyAmount = DndCombatTracker.numberOfSavedAllies;

        mainPane.setMinHeight(377);

        setBackgroundColourAndLabelText();


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

        enemyList.trimToSize();
        allyList.trimToSize();

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

        loadSavedAlliesOnStartup();

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

    //sets the background of the setup page to be the colour chosen in DndCombatTracker. Also sets the label text to
    //be white if background is black, and black otherwise
    private void setBackgroundColourAndLabelText() {
        mainPane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        mainVbox.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonBar.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        scrollpane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));
        backgroundPane.setBackground(new Background(new BackgroundFill(Color.web(DndCombatTracker.getColour()), CornerRadii.EMPTY, Insets.EMPTY)));

        if(DndCombatTracker.needHighContrast()) {
            allyNameLabel.setTextFill(Color.web("#FFFFFF"));   //white text
            allyInitiativeLabel.setTextFill(Color.web("#FFFFFF"));
            enemyNameLabel.setTextFill(Color.web("#FFFFFF"));
            enemyInitiativeLabel.setTextFill(Color.web("#FFFFFF"));
            enemyHealthLabel.setTextFill(Color.web("#FFFFFF"));
            allyLabel.setTextFill(Color.web("#FFFFFF"));
            enemyLabel.setTextFill(Color.web("#FFFFFF"));
        } else {
            allyNameLabel.setTextFill(Color.web("#000000"));   //black text
            allyInitiativeLabel.setTextFill(Color.web("#000000"));
            enemyNameLabel.setTextFill(Color.web("#000000"));
            enemyInitiativeLabel.setTextFill(Color.web("#000000"));
            enemyHealthLabel.setTextFill(Color.web("#000000"));
            allyLabel.setTextFill(Color.web("#000000"));
            enemyLabel.setTextFill(Color.web("#000000"));
        }
    }

    /**
     * Grabs ally names from fields and saves it to /partyList/party.txt
     * @param event
     */
    @FXML
    private void saveCurrentAllies(ActionEvent event) {
        //open party.txt and grab every ally name from the list
        //and write them to the file

        //overwrite file
        try(BufferedWriter write = new BufferedWriter(new FileWriter(DndCombatTracker.partyFileURL, false))) {
            allyList.trimToSize();

            int numberOfAllies = 0;

            for(Ally a: allyList) {
                if(!a.getNameBox().getText().isEmpty())
                    numberOfAllies++;
            }

            write.write(numberOfAllies + "\n");   //write # of allies to top of file

            for (Ally a: allyList) {
                //ensure the name box is non-empty, otherwise file would be
                //full of empty lines!
                if(!a.getNameBox().getText().isEmpty()) {
                    write.write(a.getNameBox().getText() + "\n");
                }
            }

            write.close();

        } catch (IOException e) {
            //handle general IO exception
            System.err.println("Error: IOException occurred when writing ally name to file!");
            e.printStackTrace();
        }

    }

    @FXML
    private void savedAlliesSetting(ActionEvent event) {
        DndCombatTracker.setLoadPartyOnStartup(loadPartyOnStartup.isSelected());
    }

    /**
     * If the setting is enabled, we want to load. If not, does nothing
     */
    private void loadSavedAlliesOnStartup() {
        if(DndCombatTracker.getLoadPartyOnStartup()) {  //if load setting is enabled
            loadSavedAllies(new ActionEvent());
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void loadSavedAllies(ActionEvent event) {
        //open file, if doesn't exist make it
        //if file is empty, do popup that says empty
        //otherwise load the names one by one into the ally message boxes

        try(BufferedReader reader = new BufferedReader(new FileReader(DndCombatTracker.partyFileURL))) {
            //first line is number of allies
            String line = reader.readLine();
            int numOfSavedAllies = Integer.parseInt(line);

            ArrayList<String> tempList = new ArrayList<>();

            //read in next line which will be the first actual ally name
            line = reader.readLine();

            //while line is non empty, add the name to an ally namebox
            while(line != null) {
                tempList.add(line);
                line = reader.readLine();
            }

            reader.close();


            //allyList.size says how many textfield rows there are on the page
            if(numOfSavedAllies > allyList.size()) {    //we need more boxes!
                int numberOfBoxesNeeded = numOfSavedAllies - allyList.size();

                for(int i = 0; i < numberOfBoxesNeeded; i++) {
                    this.addAlly(new ActionEvent());    //add a new box
                }
            }

            //we now have enough boxes, fill them up!
            for(int i = 0; i < tempList.size(); i++) {
                allyList.get(i).getNameBox().setText(tempList.get(i));
            }

        } catch (FileNotFoundException e) {
            //file wasn't found. Create it, declare empty popup, fill nothing
            File partyFile = new File(DndCombatTracker.partyFileURL);

            System.err.println("Party file did not exist! File is created");
        } catch (IOException e) {

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

    @FXML
    private void changeBackgroundColour(ActionEvent event) {
        String menuItemName = event.getSource().toString().substring(event.getSource().toString().indexOf("=") + 1, event.getSource().toString().indexOf(","));

        switch (menuItemName) {
            case "backgroundBlack":
                DndCombatTracker.setColour(DndCombatTracker.BLACK);
                DndCombatTracker.setHighContrast(true);
                break;
            case "backgroundBlue":
                DndCombatTracker.setColour(DndCombatTracker.BLUE);
                DndCombatTracker.setHighContrast(false);
                break;
            case "backgroundGrey":
                DndCombatTracker.setColour(DndCombatTracker.GREY);
                DndCombatTracker.setHighContrast(false);
                break;
            case "backgroundLightBlue":
                DndCombatTracker.setColour(DndCombatTracker.LIGHT_BLUE);
                DndCombatTracker.setHighContrast(false);
                break;
            case "backgroundRed":
                DndCombatTracker.setColour(DndCombatTracker.RED);
                DndCombatTracker.setHighContrast(false);
                break;
            case "backgroundGreen":
                DndCombatTracker.setColour(DndCombatTracker.GREEN);
                DndCombatTracker.setHighContrast(false);
                break;
            case "backgroundPurple":
                DndCombatTracker.setColour(DndCombatTracker.PURPLE);
                DndCombatTracker.setHighContrast(false);
                break;
            default:
                DndCombatTracker.setColour(DndCombatTracker.DEFAULT);
                DndCombatTracker.setHighContrast(false);
                break;
        }

        setBackgroundColourAndLabelText();
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