package Consumers;

public class VectorRessourceImpl extends _VectorRessourceImplBase
{
    private int resWood;
    private int resMarble;
    private int resWine;
    private int resCrystal;
    private int resBrimstone;

    public VectorRessourceImpl (int initWood, int initMarble, int initWine, int initCrystal, int initBrimstone)
    {
        super();
        this.resWood = initWood;
        this.resMarble = initMarble;
        this.resWine = initWine;
        this.resCrystal = initCrystal;
        this.resBrimstone = initBrimstone;
    }

    public int resWood ()
    {
        return this.resWood;
    }

    public int resMarble ()
    {
        return this.resMarble;
    }

    public int resWine ()
    {
        return this.resWine;
    }

    public int resCrystal ()
    {
        return this.resCrystal;
    }

    public int resBrimstone ()
    {
        return this.resBrimstone;
    }

    public void setWood (int units)
    {
        this.resWood = units;
    }

    public void setMarble (int units)
    {
        this.resMarble = units;
    }

    public void setWine (int units)
    {
        this.resWine = units;
    }

    public void setCrystal (int units)
    {
        this.resCrystal = units;
    }

    public void setBrimstone (int units)
    {
        this.resBrimstone = units;
    }

    public void setAll (int uWood, int uMarble, int uWine, int uCrystal, int uBrimstone)
    {
        this.resWood = uWood;
        this.resMarble = uMarble;
        this.resWine = uWine;
        this.resCrystal = uCrystal;
        this.resBrimstone = uBrimstone;
    }

    public boolean compare (VectorRessource toBeCompared)
    {
        return ( (toBeCompared.resWood() >= this.resWood) && (toBeCompared.resMarble() >= this.resMarble) && (toBeCompared.resWine() >= this.resWine) && (toBeCompared.resCrystal() >= this.resCrystal) && (toBeCompared.resBrimstone() >= this.resBrimstone) );
    }

    public void add (VectorRessource operand)
    {
        this.resWood += operand.resWood();
        this.resMarble += operand.resMarble();
        this.resWine += operand.resWine();
        this.resCrystal += operand.resCrystal();
        this.resBrimstone += operand.resBrimstone();
    }

    public void subtract(VectorRessource operand)
    {
        System.out.println("Te be added: W = " + operand.resWood() + ", M = " + operand.resMarble() + ", WI = " + operand.resWine() + ", C = " + operand.resCrystal() + ", B = " + operand.resBrimstone());

        this.resWood -= operand.resWood();
        this.resMarble -= operand.resMarble();
        this.resWine -= operand.resWine();
        this.resCrystal -= operand.resCrystal();
        this.resBrimstone -= operand.resBrimstone();
    }

    public void addExplicit (int uWood, int uMarble, int uWine, int uCrystal, int uBrimstone)
    {
        this.resWood += uWood;
        this.resMarble += uMarble;
        this.resWine += uWine;
        this.resCrystal += uCrystal;
        this.resBrimstone += uBrimstone;
    }

    public String _toString ()
    {
        return ("<Wood: " + this.resWood + ", Marble: " + this.resMarble + ", Wine: " + this.resWine + ", Crystal: " + this.resCrystal + ", Brimstone: " + this.resBrimstone + ">");
    }
}
