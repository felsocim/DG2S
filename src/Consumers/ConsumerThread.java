package Consumers;

import Producers.RemoteProducer;

public class ConsumerThread extends Thread
{
    private Thread consumerThread;
    private RemoteConsumerImpl consumer;
    private RemoteProducer[] prodWood;
    private RemoteProducer[] prodMarble;
    private String threadName;

    public ConsumerThread(String threadName, RemoteConsumerImpl consumer, RemoteProducer[] prodWood, RemoteProducer[] prodMarble)
    {
        this.threadName = threadName;
        this.consumer = consumer;
        this.prodWood = prodWood;
        this.prodMarble = prodMarble;
    }

    public void run()
    {

    }

    public void start()
    {
        if(this.consumerThread == null)
        {
            this.consumerThread = new Thread(this, this.threadName);
            this.consumerThread.start();
        }
    }
}
