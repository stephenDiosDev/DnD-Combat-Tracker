package entities;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * Acts as a base class for all other entities. Contains a name and an
 * initiative total.
 */
public abstract class Actor {
    private String name = "";
    private int initiativeTotal = 0;
    private boolean isDead = false;

    private TextField nameBox = new TextField();
    private TextField initiativeBox = new TextField();

    private Label nameLabel = new Label();
    private Label initiativeLabel = new Label();

    public Actor () {

    }

    public Actor (String name, int initiativeTotal) {
        setName(name);
        setInitiativeTotal(initiativeTotal);

        setNameLabel();
        setInitiativeLabel();
    }


    public String getName() {
        return this.name;
    }

    public int getInitiativeTotal() {
        return this.initiativeTotal;
    }

    public boolean getIsDead() {
        return this.isDead;
    }

    public Label getNameLabel() {
        return this.nameLabel;
    }

    public Label getInitiativeLabel() {
        return this.initiativeLabel;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void setName(String name) {
        if(!(name.isEmpty())) { //if name is not empty
            this.name = name;
        }
    }

    public void setInitiativeTotal(int initiativeTotal) {
        if(initiativeTotal >= 0) {
            this.initiativeTotal = initiativeTotal;
        }
    }

    public void setNameLabel() {
        this.nameLabel.setText(getName());
    }

    public void setInitiativeLabel() {
        this.initiativeLabel.setText(Integer.toString(getInitiativeTotal()));
    }

    public TextField getNameBox() {
        return this.nameBox;
    }

    public TextField getInitiativeBox() {
        return this.initiativeBox;
    }

    public void applyTextFieldInfo() {
        this.setName(this.getNameBox().getText());
        this.setInitiativeTotal(Integer.parseInt(this.getInitiativeBox().getText()));
    }

    public void setNameBoxLayout(double x, double y, double prefWidth, double prefHeight) {
        this.nameBox.setLayoutX(x);
        this.nameBox.setLayoutY(y);
        this.nameBox.setPrefSize(prefWidth, prefHeight);
    }

    public void setInitiativeBoxLayout(double x, double y, double prefWidth, double prefHeight) {
        this.initiativeBox.setLayoutX(x);
        this.initiativeBox.setLayoutY(y);
        this.initiativeBox.setPrefSize(prefWidth, prefHeight);
    }

    @Override
    public abstract String toString();

}