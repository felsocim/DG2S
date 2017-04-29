package Consumers;

public class VectorRessourceImpl extends _VectorRessourceImplBase
{
    private int resWood;
    private int resMarble;
    private int resCrystal;

    public VectorRessourceImpl (int initWood, int initMarble, int initCrystal)
    {
        super();
        this.resWood = initWood;
        this.resMarble = initMarble;
        this.resCrystal = initCrystal;
    }

    public int resWood ()
    {
        return this.resWood;
    }

    public int resMarble ()
    {
        return this.resMarble;
    }

    public int resCrystal ()
    {
        return this.resCrystal;
    }

    public void setWood (int units)
    {
        this.resWood = units;
    }

    public void setMarble (int units)
    {
        this.resMarble = units;
    }

    public void setCrystal (int units)
    {
        this.resCrystal = units;
    }

    public void setAll (int uWood, int uMarble, int uCrystal)
    {
        this.resWood = uWood;
        this.resMarble = uMarble;
        this.resCrystal = uCrystal;
    }

    public boolean compare (VectorRessource toBeCompared)
    {
        return ( (toBeCompared.resWood() >= this.resWood) && (toBeCompared.resMarble() >= this.resMarble) && (toBeCompared.resCrystal() >= this.resCrystal) );
    }

    public void add (VectorRessource operand)
    {
        this.resWood += operand.resWood();
        this.resMarble += operand.resMarble();
        this.resCrystal += operand.resCrystal();
    }

    public void subtract(VectorRessource operand)
    {
        System.out.println("Te be added: W = " + operand.resWood() + ", M = " + operand.resMarble() + ", C = " + operand.resCrystal());

        this.resWood -= operand.resWood();
        this.resMarble -= operand.resMarble();
        this.resCrystal -= operand.resCrystal();
    }

    public void addExplicit (int uWood, int uMarble, int uCrystal)
    {
        this.resWood += uWood;
        this.resMarble += uMarble;
        this.resCrystal += uCrystal;
    }

    public String _toString ()
    {
        return ("<Wood: " + this.resWood + ", Marble: " + this.resMarble + ", Crystal: " + this.resCrystal + ">");
    }
}
