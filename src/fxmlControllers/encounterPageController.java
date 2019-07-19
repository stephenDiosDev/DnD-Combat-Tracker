package fxmlControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.event.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.image.*;
import application.*;

public class encounterPageController implements Initializable{

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
    void endEncounter(ActionEvent event) throws IOException{
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
        

    }
}
