package fxmlControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.image.*;
import application.*;
import entities.*;

public class encounterPageController implements Initializable{
    //this list contains the actual actor objects
    private ArrayList<Actor> encounterActorList = new ArrayList<>();

    private ArrayList<Label> initiativeLabels = new ArrayList<>();
    private ArrayList<Label> nameLabels = new ArrayList<>();

    private ArrayList<TextField> enemyHealthTextFields = new ArrayList<>();

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
    private AnchorPane mainPane;


    @FXML
    void nextTurn(ActionEvent event) {

    }

    @FXML
    void endEncounter(ActionEvent event) throws IOException{    //switches fxml page back to setup page and ends current encounter
        //switch FXML page to encounter page
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPages/setupPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = DndCombatTracker.mainStage;

        stage.setTitle(DndCombatTracker.getStageTitle());
        stage.getIcons().add(new Image(DndCombatTracker.getWindowIconURL()));

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        encounterActorList = setupPageController.sortedActorList;
        Enemy tempEnemy;

        for (Actor actor : encounterActorList) {
            Label newInitiativeLabel = new Label(Integer.toString(actor.getInitiativeTotal()));
            Label newNameLabel = new Label(actor.getName());
            
            newInitiativeLabel.setLayoutX(initiativeXPosition);
            newInitiativeLabel.setLayoutY(yPosition);
            newInitiativeLabel.setPrefSize(initiativePrefWidth, prefHeight);
            newInitiativeLabel.setFont(new Font(24.0));
            initiativeLabels.add(newInitiativeLabel);


            newNameLabel.setLayoutX(nameXPosition);
            newNameLabel.setLayoutY(yPosition);
            newNameLabel.setPrefSize(namePrefWidth, prefHeight);
            newNameLabel.setFont(new Font(24.0));
            nameLabels.add(newNameLabel);

            if(actor instanceof Enemy) {
                tempEnemy = (Enemy)actor;

                TextField newHealthField = new TextField(Integer.toString(tempEnemy.getCurrentHealth()));

                newHealthField.setLayoutX(healthXPosition);
                newHealthField.setLayoutY(yHealthPosition);
                newHealthField.setPrefSize(healthPrefWidth, healthPrefHeight);
                newHealthField.setFont(new Font(16.0));
                enemyHealthTextFields.add(newHealthField);
            }

            yPosition += yIncrease;
            yHealthPosition += yIncrease;
        }

        ImageView turnIcon = new ImageView("/application/initiative_icon.png");
        turnIcon.setLayoutX(iconXPosition);
        turnIcon.setLayoutY(iconYPosition);


        if(nameLabels.isEmpty()){
            turnIcon.setOpacity(0.0);   //makes picture invisible
        }

        mainPane.getChildren().addAll(initiativeLabels);
        mainPane.getChildren().addAll(nameLabels);
        mainPane.getChildren().addAll(enemyHealthTextFields);
        mainPane.getChildren().add(turnIcon);

    }
}
