package Consumers;

import Producers.RemoteProducer;
import Producers.RessourceType;

import java.util.Random;

public class FortuneTeller
{
    private char personality;
    private boolean human;
    private RessourceType last;
    private Random random;

    public FortuneTeller(char personality, boolean human)
    {
        this.personality = personality;
        this.human = human;
        this.last = RessourceType.WOOD;
        this.random = new Random();
    }

    public void getFortune(RemoteConsumerImpl localConsumer, RemoteConsumer[] remoteConsumers, RemoteProducer[] woodProducers, RemoteProducer[] marbleProducers, VectorRessourceImpl humanRes)
    {
        if(localConsumer.inObservation() && remoteConsumers != null)
        {
            if(localConsumer.stolen())
            {
                localConsumer.unstole();
            }
        }

        int unitsWood = localConsumer.resTarget().resWood() - localConsumer.resCurrent().resWood();
        int unitsMarble = localConsumer.resTarget().resMarble() - localConsumer.resCurrent().resMarble();

        if(this.human)
        {
            unitsWood = humanRes.resWood();
            unitsMarble = humanRes.resMarble();
        }

        VectorRessourceImpl acquired = new VectorRessourceImpl(0,0);

        int woodUnitsPerProducer = ( (unitsWood > woodProducers.length) ? (unitsWood / woodProducers.length) : 1 );
        int marbleUnitsPerProducer = ( (unitsMarble > marbleProducers.length) ? (unitsMarble / marbleProducers.length) : 1 );

        if(unitsWood == 0)
            woodUnitsPerProducer = 0;

        if(unitsMarble == 0)
            marbleUnitsPerProducer = 0;

        if(this.human)
        {
            acquired.setWood(this.acquireWood(woodProducers, woodUnitsPerProducer, unitsWood));
            acquired.setMarble(this.acquireMarble(marbleProducers, marbleUnitsPerProducer, unitsMarble));
        }
        else
        {
            if(localConsumer.inObservation() && remoteConsumers != null)
            {
                int avgWood = 0, avgMarble = 0;

                for (RemoteConsumer remoteConsumer : remoteConsumers)
                {
                    avgWood += remoteConsumer.resCurrent().resWood();
                    avgMarble += remoteConsumer.resCurrent().resMarble();
                }

                avgWood /= remoteConsumers.length;
                avgMarble /= remoteConsumers.length;

                if((avgWood > localConsumer.resCurrent().resWood()) || (avgMarble > localConsumer.resCurrent().resMarble()))
                {
                    if(avgWood > localConsumer.resCurrent().resWood())
                    {
                        int local = this.acquireWood(woodProducers, woodUnitsPerProducer, unitsWood);

                        if(local < 1)
                        {
                            if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(unitsWood, 0), localConsumer))
                            {
                                local = unitsWood;
                            }
                        }

                        acquired.setWood(local);
                    }

                    if(avgMarble > localConsumer.resCurrent().resMarble())
                    {
                        int local = this.acquireMarble(marbleProducers, marbleUnitsPerProducer, unitsMarble);

                        if(local < 1)
                        {
                            if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(0, unitsMarble), localConsumer))
                            {
                                local = unitsMarble;
                            }
                        }

                        acquired.setMarble(local);
                    }
                }
                else
                {
                    if(this.personality == 'i')
                    {
                        if(unitsWood > 0)
                        {
                            int local = this.acquireWood(woodProducers, woodUnitsPerProducer, unitsWood);

                            if(local < 1)
                            {
                                if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(unitsWood, 0), localConsumer))
                                {
                                    local = unitsWood;
                                }
                            }

                            acquired.setWood(local);
                        }
                        else
                        {
                            int local = this.acquireMarble(marbleProducers, marbleUnitsPerProducer, unitsMarble);

                            if(local < 1)
                            {
                                if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(0, unitsMarble), localConsumer))
                                {
                                    local = unitsMarble;
                                }
                            }

                            acquired.setMarble(local);
                        }
                    }
                    else
                    {
                        if(this.last == RessourceType.WOOD && unitsMarble > 0)
                        {
                            int local = this.acquireMarble(marbleProducers, marbleUnitsPerProducer, unitsMarble);

                            if(local < 1)
                            {
                                if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(0, unitsMarble), localConsumer))
                                {
                                    local = unitsMarble;
                                }
                            }

                            acquired.setMarble(local);
                            this.last = RessourceType.MARBLE;
                        }
                        else
                        {
                            int local = this.acquireWood(woodProducers, woodUnitsPerProducer, unitsWood);

                            if(local < 1)
                            {
                                if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(unitsWood, 0), localConsumer))
                                {
                                    local = unitsWood;
                                }
                            }

                            acquired.setWood(local);

                            this.last = RessourceType.WOOD;
                        }
                    }
                }
            }
            else
            {
                if(this.personality == 'i')
                {
                    if(unitsWood > 0)
                    {
                        int local = this.acquireWood(woodProducers, woodUnitsPerProducer, unitsWood);

                        //System.out.println("Acquired: " + local);

                        if(local < 1 && remoteConsumers != null)
                        {


                            if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(unitsWood, 0), localConsumer))
                            {
                                local = unitsWood;
                            }
                        }

                        acquired.setWood(local);
                    }
                    else
                    {
                        int local = this.acquireMarble(marbleProducers, marbleUnitsPerProducer, unitsMarble);

                        if(local < 1 && remoteConsumers != null)
                        {
                            if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(0, unitsMarble), localConsumer))
                            {
                                local = unitsMarble;
                            }
                        }

                        acquired.setMarble(local);
                    }
                }
                else
                {
                    if(this.last == RessourceType.WOOD && unitsMarble > 0)
                    {
                        int local = this.acquireMarble(marbleProducers, marbleUnitsPerProducer, unitsMarble);

                        if(local < 1 && remoteConsumers != null)
                        {
                            if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(0, unitsMarble), localConsumer))
                            {
                                local = unitsMarble;
                            }
                        }

                        acquired.setMarble(local);
                        this.last = RessourceType.MARBLE;
                    }
                    else
                    {
                        int local = this.acquireWood(woodProducers, woodUnitsPerProducer, unitsWood);

                        if(local < 1 && remoteConsumers != null)
                        {
                            if(remoteConsumers[this.random.nextInt(remoteConsumers.length)].stole(new VectorRessourceImpl(unitsWood, 0), localConsumer))
                            {
                                local = unitsWood;
                            }
                        }

                        acquired.setWood(local);
                        this.last = RessourceType.WOOD;
                    }
                }
            }
        }

        localConsumer.updateRessources(acquired);
    }

    private int acquireWood(RemoteProducer[] woodProducers, int unitsPerProducer, int target)
    {
        int total = 0;

        for (RemoteProducer woodProducer : woodProducers)
        {
            if(total < target)
            {
                if(unitsPerProducer <= target)
                {
                    total += woodProducer.acquire(unitsPerProducer, this.personality);
                }
                else
                {
                    total += woodProducer.acquire(target, this.personality);
                }
            }
            
        }

        return total;
    }

    private int acquireMarble(RemoteProducer[] marbleProducers, int unitsPerProducer, int target)
    {
        int total = 0;

        for (RemoteProducer marbleProducer : marbleProducers)
        {
            if(total < target)
            {
                if(unitsPerProducer <= target)
                {
                    total += marbleProducer.acquire(unitsPerProducer, this.personality);
                }
                else
                {
                    total += marbleProducer.acquire(target, this.personality);
                }
            }

        }

        return total;
    }
}
