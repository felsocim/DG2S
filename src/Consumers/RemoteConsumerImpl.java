package Consumers;

public class RemoteConsumerImpl extends _RemoteConsumerImplBase
{
    private String idConsumer;
    private boolean readyToGo;
    private boolean myTurn;
    private boolean inObservation;
    private boolean manualMode;
    private VectorRessource resCurrent;
    private VectorRessource resTarget;
    private char personality;
    private boolean gameOver;

    public RemoteConsumerImpl(String id, boolean inObservation, boolean manual, VectorRessource target, char personality)
    {
        this.idConsumer = id;
        this.readyToGo = false;
        this.myTurn = !manual;
        this.inObservation = inObservation;
        this.manualMode = manual;
        this.resCurrent = new VectorRessourceImpl(0, 0, 0);
        this.resTarget = target;
        this.personality = personality;
        this.gameOver = false;
    }

    public synchronized String idConsumer ()
    {
        return this.idConsumer;
    }

    public synchronized boolean readyToGo ()
    {
        return this.readyToGo;
    }

    public synchronized boolean myTurn ()
    {
        return this.myTurn;
    }

    public synchronized boolean inObservation ()
    {
        return this.inObservation;
    }

    public synchronized boolean manualMode ()
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

    public synchronized char personality()
    {
        return this.personality;
    }

    public synchronized boolean gameOver ()
    {
        return this.gameOver;
    }

    public synchronized void setIdConsumer (String id)
    {
        this.idConsumer = id;
    }

    public synchronized void setReadyToGo (boolean status)
    {
        this.readyToGo = status;
    }

    public synchronized void setMyTurn (boolean go)
    {
        this.myTurn = go;
    }

    public synchronized void setInObservation (boolean observation)
    {
        this.inObservation = observation;
    }

    public synchronized void setManualMode (boolean mode)
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

    public synchronized void setPersonality(char personality)
    {
        this.personality = personality;
    }

    public synchronized void setGameOver (boolean over)
    {
        this.gameOver = over;
    }

    public synchronized void updateRessources (VectorRessource acquired)
    {
        this.resCurrent.add(acquired);
    }

    public synchronized boolean finished()
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
        return (this.idConsumer + "\nCurrent ressources status: " + this.resCurrent._toString() + "\nTarget ressources status: " + this.resTarget._toString() );
    }
}
