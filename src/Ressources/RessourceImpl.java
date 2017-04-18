package Ressources;

import java.util.Timer;
import java.util.TimerTask;

public class RessourceImpl extends _RessourceImplBase
{
    private RessourceType type;
    private int count;
    private int step;
    private int refillFrequence;

    public RessourceImpl(RessourceType type, int count, int step, int refillFrequence)
    {
        super();
        this.type = type;
        this.count = count;
        this.step = step;
        this.refillFrequence = refillFrequence;
    }

    public RessourceType type ()
    {
        return this.type;
    }

    public int count ()
    {
        return this.count;
    }

    public int step ()
    {
        return this.step;
    }

    public int refillFrequence()
    {
        return this.refillFrequence;
    }

    public void pretype (RessourceType type)
    {
        this.type = type;
    }

    public void generate ()
    {
        count += (count / 2) + 5;
        System.out.println("New ressource generated");
    }

    public synchronized boolean acquire (int count)
    {
        System.out.println("count: " + count + " step: " + this.step + " pcount: " + this.count);
        if (count <= this.step && count <= this.count)
        {
            this.count -= count;
            return true;
        }

        return false;
    }
}