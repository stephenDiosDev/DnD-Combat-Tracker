package entities;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import javafx.scene.control.TextField;

public class Enemy extends Actor {
    private int totalHealth, currentHealth;

    private TextField healthBox = new TextField();

    public Enemy() {
        super();
        setupHealth(1);
    }

    public Enemy(String name, int initiativeTotal, int totalHealth) {
        super(name, initiativeTotal);
        setupHealth(totalHealth);
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

    public void setHealthBoxLayout(double x, double y, double prefWidth, double prefHeight) {
        this.healthBox.setLayoutX(x);
        this.healthBox.setLayoutY(y);
        this.healthBox.setPrefSize(prefWidth, prefHeight);
    }

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