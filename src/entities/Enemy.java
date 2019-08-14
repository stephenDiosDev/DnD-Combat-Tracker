package entities;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * This class represents the enemies and any actors that you want to track the HP of. Contains everything
 * from actor, plus a health textfield.
 */
public class Enemy extends Actor {
    private int totalHealth, currentHealth;

    private TextField healthBox = new TextField();

    /**
     * Basic constructor. Sets the enemy to have 1 HP. Avoid using when possible.
     */
    public Enemy() {
        super();
        setupHealth(1);
        addHealthBoxListener();
    }

    /**
     * Proper constructor for Enemy. Assigns the name and initiative similarly to actor, and assigns the
     * health and adds a listener to the health textfield.
     * @param name The name of the Enemy.
     * @param initiativeTotal The initiative total of the Enemy.
     * @param totalHealth The total "maximum" health of the enemy.
     */
    public Enemy(String name, int initiativeTotal, int totalHealth) {
        super(name, initiativeTotal);
        setupHealth(totalHealth);
        addHealthBoxListener();
    }

    /**
     * Adds a listener to the textfield. This watches for changes to the current health textfield (in encounter page)
     * and updates the enemy for certain changes. When the current health in the textfield hits 0 (or lower), the
     * labels for the enemy on the encounter page turn red, and the enemy is assigned to be DEAD. When the
     * current health is changed to be above 0, the labels for the enemy turn black and the enemy is assigned to ALIVE.
     */
    private void addHealthBoxListener() {
        //listener for the health box
        this.healthBox.textProperty().addListener((obs, oldText, newText) -> {
            if(Integer.parseInt(newText) <= 0) {    //if new health entered is 0 or lower
                this.setCurrentHealth(0);  //actor is dead, method sets alive status automatically

                //set labels to be RED
                this.getInitiativeLabel().setTextFill(Color.RED);
                this.getNameLabel().setTextFill(Color.RED);
            } else {
                this.setIsDead(false); //otherwise "revive" enemy if their health goes above 0

                this.setCurrentHealth(Integer.parseInt(newText));

                //set labels to be BLACK
                this.getInitiativeLabel().setTextFill(Color.BLACK);
                this.getNameLabel().setTextFill(Color.BLACK);
            }
        });
    }
    /**
     * This method is only used to initialize the health of the enemy, and sets
     * both the total and current health to the given totalHealth.
     * @param totalHealth The maximum and current health of the enemy
     */
    private void setupHealth(int totalHealth) {
        if(totalHealth > 0) {
            this.totalHealth = totalHealth;
            this.currentHealth = totalHealth;
        }
    }

    public void takeDamage(int damage) {
        setCurrentHealth(getCurrentHealth() - damage);  //deals damage to the enemy
    }

    public void heal(int amount) {
        if(amount > 0) {
            setCurrentHealth(getCurrentHealth() + amount);  //heals an amount of health for the enemy
        }
    }

    public int getTotalHealth() {   //Gets the total health
        return this.totalHealth;
    }

    public int getCurrentHealth() {     //Gets the current health
        return this.currentHealth;
    }

    public TextField getHealthBox() {
        return this.healthBox;
    }

    /**
     * Applies the layout provided by the parameters to the health textfield.
     * @param x The X position of the textfield.
     * @param y The Y position of the textfield.
     * @param prefWidth The prefWidth of the textfield. This is what the box width will be under ideal conditions.
     * @param prefHeight The prefHeight of the textfield. This is what the box height will be under ideal conditions.
     */
    public void setHealthBoxLayout(double x, double y, double prefWidth, double prefHeight) {
        this.healthBox.setLayoutX(x);
        this.healthBox.setLayoutY(y);
        this.healthBox.setPrefSize(prefWidth, prefHeight);
    }

    /**
     * Calls the actor method to apply the info for the name and initiative. Also applies it
     * similarly for the health box.
     */
    @Override
    public void applyTextFieldInfo() {
        super.applyTextFieldInfo();
        this.setupHealth(Integer.parseInt(this.getHealthBox().getText()));
    }

    /**
     * This method sets the current health to the given health, and does checks to ensure the new health is not negative
     * and that it does not exceed the total health. It also handles the chance that the enemy dies.
     * @param health The new current Health amount.
     */
    private void setCurrentHealth(int health) {
        if(health > 0) {
            if(health < getTotalHealth()) {
                this.currentHealth = health;        //if new current health is above 0 and does not exceed total health, set to that value
            } else {
                this.currentHealth = getTotalHealth();  //if new current health is above 0 but meets or exceeds total health, set to total health
            }
        } else {
            this.currentHealth = 0;     //otherwise, health is at 0, and this enemy is labelled as dead
            setIsDead(true);
        }
    }

    public void changeCurrentHealth(int health) {
        if(health > 0 && health <= getTotalHealth()) {
            this.currentHealth = health;
        } else if(health == 0) {
            this.currentHealth = health;
            setIsDead(true);
        }
    }


    public String toString() {
        return "Enemy: [Name = " + super.getName() + "] [Initiative Total = " + super.getInitiativeTotal() +
         "] [Total Health = " + getTotalHealth() + "] [Current Health = " + getCurrentHealth() + "]";
    }

    public static void main(String[] args) {
        Enemy a = new Enemy("Sbeve", 2, 55);
        Enemy b = new Enemy();

        a.takeDamage(10000);
        a.heal(200);

        System.out.println(a.toString());
        System.out.println(b.toString());
    }
}