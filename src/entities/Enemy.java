package entities;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

            this.healthBox.setOnKeyReleased(event -> {  //fires event when enter key is pushed and released
                System.out.println("NEWTEXT = " + newText);
                if(event.getCode() == KeyCode.ENTER) {  //if enter is pushed after text change
                    //check if a - or + is present
                    if(newText.contains("-") || newText.contains("+")) {    //health change based on operator
                        applyHealthChangeWithOperator(newText);

                        if(getIsDead()) {   //if dead
                            this.setLabelsDead();
                        } else {    //if still alive
                            this.setLabelsAlive();
                        }
                    } else {    //change current health, no fancy adding or subtracting
                        if(Integer.parseInt(newText) <= 0) {    //if new health entered is 0 or lower
                            this.setCurrentHealth(0);  //actor is dead, method sets alive status automatically
            
                            //set labels to be RED
                            this.setLabelsDead();
                        } else {
                            this.setIsDead(false); //otherwise "revive" enemy if their health goes above 0
            
                            this.setCurrentHealth(Integer.parseInt(newText));
            
                            //set labels to be BLACK
                            this.setLabelsAlive();
                        }
                    }

                    System.out.println("NEW CURRENT HEALTH = " + this.getCurrentHealth());
                    /*
                    Sets the healthbox to hold current health, so we can see the updated health
                    otherwise we will see the user input of "-X" or "+X" or if the user entered a
                    health amount above the total health, it will set it to total health
                    */
                    this.healthBox.setText(Integer.toString(this.getCurrentHealth()));

                    this.getInitiativeLabel().requestFocus();   //moves focus from textfield to label
                    
                }
            });

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

    @Override
    public void setIsDead(boolean isDead) {
        super.setIsDead(isDead);

        if(isDead) {    //if dead
            this.setLabelsDead();
        } else {
            this.setLabelsAlive();  //if alive
        }
    }

    /**
     * When the enemy dies, the labels will be turned red
     */
    private void setLabelsDead() {
        this.getInitiativeLabel().setTextFill(Color.RED);
        this.getNameLabel().setTextFill(Color.RED);
    }

    /**
     * When the enemy is alive, the labels will be black
     */
    private void setLabelsAlive() {
        this.getInitiativeLabel().setTextFill(Color.BLACK);
        this.getNameLabel().setTextFill(Color.BLACK);
    }

    /**
     * This method takes a damage amount and does currentHealth - damage. This is performed through the
     * setCurrentHealth method which takes care of number formats and updates isDead if required.
     */
    public void takeDamage(int damage) {
        setCurrentHealth(getCurrentHealth() - damage);  //deals damage to the enemy
    }

    /**
     * This method takes a heal amount and does currentHealth + heal amount. This is performed through the
     * setCurrentHealth method which takes care of number formats and updates isDead if required.
     */
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
     * This method takes a string, input of the form -X or +X where X is an int, 
     * and will parse either a - sign or + sign. Based on the operator it then either
     * does current health - X or current health + X. 
     */
    private void applyHealthChangeWithOperator(String input) {
        if(input.substring(0, 1).equals("-") || input.substring(0, 1).equals("+")) {    //good format
            String operator = input.substring(0, 1);    //grabs the operator
            String healthChangeAmount = input.substring(1); //grabs the actual number

            if(operator.equals("-")) {
                this.takeDamage(Integer.parseInt(healthChangeAmount));
            } else if(operator.equals("+")) {
                this.heal(Integer.parseInt(healthChangeAmount));
            }

        } else {    //format not good, clear textfield, set its text to unchanged current health
            this.healthBox.setText(Integer.toString(this.getCurrentHealth()));
        }
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
            setIsDead(false);
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