package fxmlControllers;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.net.URL;

public class setupPageController implements Initializable{

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
        System.out.println("Initialized!");
    }

}