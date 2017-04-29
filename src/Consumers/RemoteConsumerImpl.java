package Consumers;

import java.util.Random;

public class RemoteConsumerImpl extends _RemoteConsumerImplBase
{
    private String idConsumer;
    private boolean readyToGo;
    private boolean manualMode;
    private VectorRessource resCurrent;
    private VectorRessource resTarget;
    private char personality;
    private boolean gameOver;

    public RemoteConsumerImpl(String id, boolean manual, VectorRessource target, char personality)
    {
        this.idConsumer = id;
        this.readyToGo = false;
        this.manualMode = manual;
        this.resCurrent = new VectorRessourceImpl(0, 0, 0, 0, 0);
        this.resTarget = target;
        this.personality = personality;
        this.gameOver = false;
    }

    public String idConsumer ()
    {
        return this.idConsumer;
    }

    public synchronized boolean readyToGo ()
    {
        return this.readyToGo;
    }

    public boolean manualMode ()
    {
        return this.manualMode;
    }

    public synchronized VectorRessource resCurrent ()
    {
        return this.resCurrent;
    }

    public synchronized VectorRessource resTarget ()
    {
        return this.resTarget;
    }

    public char personality()
    {
        return this.personality;
    }

    public boolean gameOver ()
    {
        return this.gameOver;
    }

    public void setIdConsumer (String id)
    {
        this.idConsumer = id;
    }

    public synchronized void setReadyToGo (boolean status)
    {
        this.readyToGo = status;
    }

    public void setManualMode (boolean mode)
    {
        this.manualMode = mode;
    }

    public synchronized void setResCurrent (VectorRessource current)
    {
        this.resCurrent = current;
    }

    public synchronized void setResTarget (VectorRessource target)
    {
        this.resTarget = target;
    }

    public void setPersonality(char personality)
    {
        this.personality = personality;
    }

    public void setGameOver (boolean over)
    {
        this.gameOver = over;
    }

    public synchronized void updateRessources (VectorRessource acquired)
    {
        this.resCurrent.add(acquired);
    }

    public boolean finished()
    {
        if(this.resTarget().compare(this.resCurrent))
        {
            this.gameOver = true;
            return true;
        }

        this.gameOver = false;
        return false;
    }

    public synchronized String _toString ()
    {
        return ("CLIENT:\nIdentificator: " + this.idConsumer + "\nCurrent ressources status: " + this.resCurrent._toString() + "\nTarget ressources status: " + this.resTarget._toString() );
    }
}
