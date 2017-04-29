package Consumers;

import Producers.RemoteProducer;
import Producers.RemoteProducerHelper;
import Producers.RessourceType;
import org.omg.CORBA.*;

import java.io.*;
import java.util.LinkedList;

public class Consumer
{
    private synchronized static LinkedList<String> loadProducers(RessourceType ressourceType)
    {
        LinkedList<String> producers = new LinkedList<String>();

        try
        {
            FileReader fileReader = new FileReader(ressourceType.toString().toLowerCase() + "_producers.drg");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String current;

            while((current = bufferedReader.readLine()) != null)
            {
                producers.add(current);
            }

            fileReader.close();
        }
        catch (IOException ioException)
        {
            System.out.println("Unable to discover " + ressourceType.toString().toLowerCase() + " producers!");
            System.exit(12);
        }

        return producers;
    }

    public static void main(String[] args)
    {
        if(args.length != 7)
        {
            System.out.println("Incorrect argument(s)! Usage: java Consumer <CONSUMER ID> <TARGET WOOD> <TARGET MARBLE> <TARGET CRYSTAL> <PERSONALITY> <OBSERVE> <MANUAL MODE>");
            System.exit(-1);
        }

        String id = args[0];
        int tgWood = Integer.parseInt(args[1]);
        int tgMarble = Integer.parseInt(args[2]);
        int tgCrystal = Integer.parseInt(args[3]);
        char personality = args[4].charAt(0);
        boolean inObservation = (args[5].charAt(0) == 'o');
        boolean manualMode = (args[6].charAt(0) == 'm');

        RemoteConsumerImpl consumer = new RemoteConsumerImpl(id, inObservation, manualMode, new VectorRessourceImpl(tgWood, tgMarble, tgCrystal), personality);

        LinkedList<String> prodWoodIOR = loadProducers(RessourceType.WOOD);
        LinkedList<String> prodMarbleIOR = loadProducers(RessourceType.MARBLE);
        LinkedList<String> prodCrystalIOR = loadProducers(RessourceType.CRYSTAL);


        if(prodWoodIOR.size() < 1 || prodMarbleIOR.size() < 1 || prodCrystalIOR.size() < 1 )
        {
            System.out.println("Please, make sure there is at least one producer initialized per raw material!");
            System.exit(12);
        }

        try
        {
            ORB corba = ORB.init(args, null);

            String consumerIOR = corba.object_to_string(consumer);

            try
            {
                FileWriter fileWriter = new FileWriter("consumers.drg", true);
                PrintWriter consWriter = new PrintWriter(fileWriter);
                consWriter.println(consumerIOR);
                consWriter.close();
            }
            catch (FileNotFoundException error404)
            {
                System.out.println("Error creating consumer list file!");
                error404.printStackTrace();
            }
            catch (IOException ioex)
            {
                ioex.printStackTrace();;
            }


            RemoteProducer[] prodWood = new RemoteProducer[prodWoodIOR.size()];
            RemoteProducer[] prodMarble = new RemoteProducer[prodMarbleIOR.size()];
            RemoteProducer[] prodCrystal = new RemoteProducer[prodCrystalIOR.size()];

            for (int i = 0; i < prodWood.length; i++)
            {
                prodWood[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodWoodIOR.get(i)));
            }

            for (int i = 0; i < prodMarble.length; i++)
            {
                prodMarble[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodMarbleIOR.get(i)));
            }

            for (int i = 0; i < prodCrystal.length; i++)
            {
                prodCrystal[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodCrystalIOR.get(i)));
            }

            System.out.println("Consumer " + consumer.idConsumer() + " initialized. Waiting for signal...");

            while (!consumer.finished())
            {
                if(consumer.readyToGo() && consumer.myTurn())
                {
                    // WTF????
                    VectorRessourceImpl acquired = new VectorRessourceImpl(0,0,0);

                    for (RemoteProducer woodProducer : prodWood)
                    {
                        int units = tgWood;

                        if(consumer.inObservation())
                        {
                            units = tgWood - consumer.resCurrent().resWood();
                        }

                        acquired.setWood(acquired.resWood() + woodProducer.acquire(units, consumer.personality()));
                    }

                    for (RemoteProducer marbleProducer : prodMarble)
                    {
                        int units = tgMarble;

                        if(consumer.inObservation())
                        {
                            units = tgMarble - consumer.resCurrent().resMarble();
                        }

                        acquired.setMarble(acquired.resMarble() + marbleProducer.acquire(units, consumer.personality()));
                    }

                    for (RemoteProducer crystalProducer : prodCrystal)
                    {
                        int units = tgCrystal;

                        if(consumer.inObservation())
                        {
                            units = tgCrystal - consumer.resCurrent().resCrystal();
                        }

                        acquired.setCrystal(acquired.resCrystal() + crystalProducer.acquire(units, consumer.personality()));
                    }

                    consumer.updateRessources(acquired);

                    System.out.println(consumer._toString());
                }
            }

            System.out.println("Consumer achieved the target!");
        }
        catch (SystemException sys_e)
        {
            sys_e.printStackTrace();
        }
    }
}
