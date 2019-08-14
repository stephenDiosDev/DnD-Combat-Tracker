package entities;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * Acts as a base class for all other entities. Contains a name and an
 * initiative total. Also contains java FX labels and textboxes for
 * the names and initiatives.
 */
public abstract class Actor {
    private String name = "";
    private int initiativeTotal = 0;
    private boolean isDead = false;     //where false = ALIVE and true = DEAD

    private TextField nameBox = new TextField();
    private TextField initiativeBox = new TextField();

    private Label nameLabel = new Label();
    private Label initiativeLabel = new Label();

    /**
     * Default constructor. Creates actor with empty name, 0 initiative and is alive.
     * Avoid using.
     */
    public Actor () {

    }

    /**
     * Full actor constructor. Sets the name and initiative variables and fills
     * the labels. The proper "full" constructor to use.
     * @param name The name of the Actor
     * @param initiativeTotal The initiative total of the Actor
     */
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

    /**
     * Returns the isDead status of the Actor. True = DEAD and False = ALIVE.
     * @return The isDead status of the Actor.
     */
    public boolean getIsDead() {
        return this.isDead;
    }

    public Label getNameLabel() {
        return this.nameLabel;
    }

    public Label getInitiativeLabel() {
        return this.initiativeLabel;
    }

    /**
     * Sets the isDead status of the Actor provided by the parameter. True = DEAD and False = ALIVE.
     * @param isDead The new isDead status of the Actor.
     */
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    /**
     * Sets the name of the actor. Given name cannot be empty.
     * @param name New name of the actor
     */
    public void setName(String name) {
        if(!(name.isEmpty())) { //if name is not empty
            this.name = name;
        }
    }

    /**
     * Sets the new initiative total given by the parameter. Must be non-negative.
     * @param initiativeTotal The new initiative total
     */
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

    /**
     * Applies the name and initiative total stored in the actor's instance variables to the
     * name box and initiative box.
     */
    public void applyTextFieldInfo() {
        this.setName(this.getNameBox().getText());
        this.setInitiativeTotal(Integer.parseInt(this.getInitiativeBox().getText()));
    }

    /**
     * Applies the layout provided by the parameters to the name textfield.
     * @param x The X position of the textfield.
     * @param y The Y position of the textfield.
     * @param prefWidth The prefWidth of the textfield. This is what the box width will be under ideal conditions.
     * @param prefHeight The prefHeight of the textfield. This is what the box height will be under ideal conditions.
     */
    public void setNameBoxLayout(double x, double y, double prefWidth, double prefHeight) {
        this.nameBox.setLayoutX(x);
        this.nameBox.setLayoutY(y);
        this.nameBox.setPrefSize(prefWidth, prefHeight);
    }

    /**
     * Applies the layout provided by the parameters to the initiative textfield.
     * @param x The X position of the textfield.
     * @param y The Y position of the textfield.
     * @param prefWidth The prefWidth of the textfield. This is what the box width will be under ideal conditions.
     * @param prefHeight The prefHeight of the textfield. This is what the box height will be under ideal conditions.
     */
    public void setInitiativeBoxLayout(double x, double y, double prefWidth, double prefHeight) {
        this.initiativeBox.setLayoutX(x);
        this.initiativeBox.setLayoutY(y);
        this.initiativeBox.setPrefSize(prefWidth, prefHeight);
    }

    @Override
    public abstract String toString();

}