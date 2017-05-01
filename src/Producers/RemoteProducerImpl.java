package Producers;

public class RemoteProducerImpl extends _RemoteProducerImplBase {
    private RessourceType type;
    private int id;
    private int count;
    private int step;
    private int refillFrequence;
    private boolean readyToGo;
    private boolean infiniteRessources;
    private boolean empty;

    public RemoteProducerImpl(RessourceType type, int id, int count, int step, int refillFrequence, boolean infiniteRessources) {
        super();
        this.id = id;
        this.type = type;
        this.count = count;
        this.step = step;
        this.refillFrequence = refillFrequence;
        this.readyToGo = false;
        this.infiniteRessources = infiniteRessources;
        this.empty = false;
    }

    public synchronized RessourceType type() {
        return this.type;
    }

    public synchronized int id() {
        return this.id;
    }

    public synchronized int count() {
        return this.count;
    }

    public synchronized int step() {
        return this.step;
    }

    public synchronized int refillFrequence() {
        return this.refillFrequence;
    }

    public synchronized boolean readyToGo() {
        return this.readyToGo;
    }

    public synchronized boolean infiniteRessources ()
    {
        return this.infiniteRessources;
    }

    public synchronized boolean empty()
    {
        return this.empty;
    }

    public synchronized void setInfiniteRessources (boolean property)
    {
        this.infiniteRessources = property;
    }

    public synchronized void setReadyToGo(boolean status) {
        this.readyToGo = status;
    }

    public synchronized void pretype(RessourceType type) {
        this.type = type;
    }

    public synchronized void generate()
    {
        if (this.readyToGo)
        {
            if(this.infiniteRessources)
            {
                this.count += (this.count / 2) + 1;
                System.out.println("New " + this.type.toString() + " generated for producer " + this.id + " (now " + this.type.toString() + ": " + this.count + " )");

                return;
            }
        }

        System.out.println(type.toString() + " producer " + id + " is not ready yet.");
    }

    public synchronized int acquire(int units, char personality)
    {
        int toBeAcquired = units;
        int init = this.count;

        int actuallyAcquired;

        if(toBeAcquired > this.step)
        {
            toBeAcquired = this.step;
        }

        if (toBeAcquired > this.count)
        {
            toBeAcquired = this.count;
        }

        this.count -= toBeAcquired;

        actuallyAcquired = init - this.count;

        System.out.println("acquire: " + units + " wanted, "  + actuallyAcquired + " actually acquired, " + this.count + " remaining");

        if(this.count < 1 && !this.infiniteRessources)
        {
            this.empty = true;
        }

        return actuallyAcquired;
    }

    public synchronized String _toString()
    {
        return (this.type.toString() + " producer " + this.id + " with " + this.count + " remaining ressource units. Infinite ressources " + this.infiniteRessources);
    }
}