package entities;

/**
 * This class represents the player characters and any actor's that you wish to use
 * without tracking the HP. Contains a name and initiative total (Everything from actor)
 */
public class Ally extends Actor{

    public Ally() {
        super();
    }

    /**
     * The proper "full" ally constructor. Use this.
     * @param name The name of the Ally
     * @param initiativeTotal The initiative total of the Ally
     */
    public Ally(String name, int initiativeTotal) {
        super(name, initiativeTotal);
    }


    /**
     * Simple toString method that prints the name and initiative total. Meant primarily for debugging.
     * @return The string containing the formatted name and initiative total.
     */
    public String toString() {
        return "Ally: [Name = " + super.getName() + "] [Initiative Total = " + super.getInitiativeTotal() + "]";
    }

    /**
     * Meant for quick and dirty testing of the Ally class.
     * @param args
     */
    public static void main(String[] args) {
        Ally a = new Ally("Flint ", 14);
        Ally b = new Ally();

        System.out.println(a.toString());
        System.out.println(b.toString());

    }
}