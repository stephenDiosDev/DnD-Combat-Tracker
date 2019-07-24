package entities;

/**
 * Acts as a base class for all other entities. Contains a name and an
 * initiative total.
 */
public abstract class Actor {
    private String name = "PLACEHOLDER";
    private int initiativeTotal = 0;
    private boolean isDead = false;

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

    @Override
    public abstract String toString();

}