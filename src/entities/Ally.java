package entities;

/**
 * This class represents the player characters and only contains a name and 
 * initiative total.
 */
public class Ally extends Actor{

    public Ally() {
        super();
    }

    public Ally(String name, int initiativeTotal) {
        super(name, initiativeTotal);
    }


    public String toString() {
        return "Ally: [Name = " + super.getName() + "] [Initiative Total = " + super.getInitiativeTotal() + "]";
    }

    public static void main(String[] args) {
        Ally a = new Ally("Flint ", 14);
        Ally b = new Ally();

        System.out.println(a.toString());
        System.out.println(b.toString());

    }
}