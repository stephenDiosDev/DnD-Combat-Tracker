package fxmlControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Ally;
import javafx.event.ActionEvent;

public class addNewCharacterPageController implements Initializable {

    private final String allyMenuText = "Ally";
    private final String enemyMenuText = "Enemy";

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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

    @FXML
    private void addToEncounter(ActionEvent event) {
        if(typeMenu.getText().equalsIgnoreCase(allyMenuText)) { //ally
            Ally newAlly = new Ally(nameBox.getText(), Integer.parseInt(initiativeBox.getText()));
            
        } else if (typeMenu.getText().equalsIgnoreCase(enemyMenuText)) {    //enemy

        }
    }

}