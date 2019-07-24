package entities;

import javafx.scene.control.TextField;

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

    public Actor () {

    }

    public Actor (String name, int initiativeTotal) {
        setName(name);
        setInitiativeTotal(initiativeTotal);
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