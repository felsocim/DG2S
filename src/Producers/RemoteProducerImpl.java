package Producers;

public class RemoteProducerImpl extends _RemoteProducerImplBase {
    private RessourceType type;
    private int id;
    private int count;
    private int step;
    private int refillFrequence;
    private boolean readyToGo;

    public RemoteProducerImpl(RessourceType type, int id, int count, int step, int refillFrequence) {
        super();
        this.id = id;
        this.type = type;
        this.count = count;
        this.step = step;
        this.refillFrequence = refillFrequence;
        this.readyToGo = true;
    }

    public RessourceType type() {
        return this.type;
    }

    public int id() {
        return this.id;
    }

    public synchronized int count() {
        return this.count;
    }

    public int step() {
        return this.step;
    }

    public int refillFrequence() {
        return this.refillFrequence;
    }

    public boolean readyToGo() {
        return this.readyToGo;
    }

    public void setReadyToGo(boolean status) {
        this.readyToGo = status;
    }

    public void pretype(RessourceType type) {
        this.type = type;
    }

    public synchronized void generate() {
        if (this.readyToGo) {
            this.count += (this.count / 2) + 1;
            System.out.println("New " + this.type.toString() + " generated for producer " + this.id + " (now " + this.type.toString() + ": " + this.count + " )");

            return;
        }

        System.out.println(type.toString() + " producer " + id + " is not ready yet.");
    }

    public synchronized int acquire(int units, char personality)
    {
        int toBeAcquired;
        int init = this.count;

        switch(personality)
        {
            case 'c':
                if(units == 0)
                {
                    toBeAcquired = 0;
                }
                else
                {
                    toBeAcquired = ( (units > 5 ) ? (units / 5) : 1 );
                }
                break;
            default:
                toBeAcquired = units;
        }

        System.out.println("TBA1: " + toBeAcquired);

        if (toBeAcquired > this.count)
        {
            toBeAcquired = this.count;
        }

        System.out.println("TBA2: " + toBeAcquired);
        //System.out.println("init2: " + init);
        this.count -= toBeAcquired;
        int actuallyAcquired = init - this.count;
        System.out.println("acquire: " + units + " wanted, "  + actuallyAcquired + " actually acquired, " + this.count + " remaining");

        return actuallyAcquired;
    }
}