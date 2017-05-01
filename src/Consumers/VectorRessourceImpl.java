package Consumers;

public class VectorRessourceImpl extends _VectorRessourceImplBase
{
    private int resWood;
    private int resMarble;

    public VectorRessourceImpl (int initWood, int initMarble)
    {
        super();
        this.resWood = initWood;
        this.resMarble = initMarble;
    }

    public int resWood ()
    {
        return this.resWood;
    }

    public int resMarble ()
    {
        return this.resMarble;
    }

    public void setWood (int units)
    {
        this.resWood = units;
    }

    public void setMarble (int units)
    {
        this.resMarble = units;
    }

    public void setAll (int uWood, int uMarble)
    {
        this.resWood = uWood;
        this.resMarble = uMarble;
    }

    public boolean compare (VectorRessource toBeCompared)
    {
        return ( (toBeCompared.resWood() >= this.resWood) && (toBeCompared.resMarble() >= this.resMarble) );
    }

    public void add (VectorRessource operand)
    {
        this.resWood += operand.resWood();
        this.resMarble += operand.resMarble();
    }

    public void subtract(VectorRessource operand)
    {
        System.out.println("Te be added: W = " + operand.resWood() + ", M = " + operand.resMarble());

        this.resWood -= operand.resWood();
        this.resMarble -= operand.resMarble();
    }

    public void addExplicit (int uWood, int uMarble)
    {
        this.resWood += uWood;
        this.resMarble += uMarble;
    }

    public String _toString ()
    {
        return ("<Wood: " + this.resWood + ", Marble: " + this.resMarble + ">");
    }
}
