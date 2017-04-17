package Ressources;

import java.util.Timer;
import org.omg.CORBA.*;

public class RessourceImpl extends _RessourceImplBase
{
    private RessourceType type;
    private int count;
    private int step;
    private Timer generator;

    public RessourceImpl(RessourceType type, int count, int step)
    {
        
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

    public void pretype (RessourceType type)
    {
        this.type = type;
    }

    public void generate (int count)
    {

    }

    public synchronized boolean acquire (int count)
    {
        if (count <= this.step && count <= this.count)
        {
            this.count -= count;
            return true;
        }

        return false;
    }
}