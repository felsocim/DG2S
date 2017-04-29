package Consumers;

import Producers.RemoteProducer;
import Producers.RemoteProducerHelper;
import Producers.RessourceType;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class Consumer
{
    private synchronized static LinkedList<String> loadProducers(RessourceType ressourceType)
    {
        LinkedList<String> producers = new LinkedList<String>();

        try
        {
            FileReader fileReader = new FileReader("Producers_of_" + ressourceType.toString() + ".res");
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
            System.out.println("Unable to discover " + ressourceType.toString() + " producers!");
        }

        return producers;
    }

    public static void main(String[] args)
    {
        if(args.length != 7)
        {
            System.out.println("Incorrect argument(s)! Usage: java Consumer <CONSUMER ID> <TARGET WOOD> <TARGET MARBLE> <TARGET WINE> <TARGET CRYSTAL> <TARGET BRIMSTONE> <PERSONALITY>");
        }

        String id = args[0];
        int tgWood = Integer.parseInt(args[1]);
        int tgMarble = Integer.parseInt(args[2]);
        int tgWine = Integer.parseInt(args[3]);
        int tgCrystal = Integer.parseInt(args[4]);
        int tgBrimstone = Integer.parseInt(args[5]);
        char personality = args[6].charAt(0);

        RemoteConsumerImpl consumer = new RemoteConsumerImpl(id, false, new VectorRessourceImpl(tgWood, tgMarble, tgWine, tgCrystal, tgBrimstone), personality);

        LinkedList<String> prodWoodIOR = loadProducers(RessourceType.WOOD);
        LinkedList<String> prodMarbleIOR = loadProducers(RessourceType.MARBLE);
        LinkedList<String> prodWineIOR = loadProducers(RessourceType.WINE);
        LinkedList<String> prodCrystalIOR = loadProducers(RessourceType.CRYSTAL);
        LinkedList<String> prodBrimstoneIOR = loadProducers(RessourceType.BRIMSTONE);

        if(prodWoodIOR.size() < 1 || prodMarbleIOR.size() < 1 || prodWineIOR.size() < 1 || prodCrystalIOR.size() < 1 || prodBrimstoneIOR.size() < 1)
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
                FileWriter fileWriter = new FileWriter("Consumers.cns", true);
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
            RemoteProducer[] prodWine = new RemoteProducer[prodWineIOR.size()];
            RemoteProducer[] prodCrystal = new RemoteProducer[prodCrystalIOR.size()];
            RemoteProducer[] prodBrimstone = new RemoteProducer[prodBrimstoneIOR.size()];

            for (int i = 0; i < prodWood.length; i++)
            {
                prodWood[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodWoodIOR.get(i)));
            }

            for (int i = 0; i < prodMarble.length; i++)
            {
                prodMarble[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodMarbleIOR.get(i)));
            }

            for (int i = 0; i < prodWine.length; i++)
            {
                prodWine[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodWineIOR.get(i)));
            }

            for (int i = 0; i < prodCrystal.length; i++)
            {
                prodCrystal[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodCrystalIOR.get(i)));
            }

            for (int i = 0; i < prodBrimstone.length; i++)
            {
                prodBrimstone[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodBrimstoneIOR.get(i)));
            }

            System.out.println("Consumer " + consumer.idConsumer() + " initialized. Waiting for signal...");

            while (!consumer.finished())
            {
                if(consumer.readyToGo())
                {
                    // WTF????
                    VectorRessourceImpl acquired = new VectorRessourceImpl(0,0,0,0,0);

                    for (RemoteProducer woodProducer : prodWood)
                    {
                        acquired.setWood(acquired.resWood() + woodProducer.acquire(tgWood - consumer.resCurrent().resWood(), consumer.personality()));
                    }

                    for (RemoteProducer marbleProducer : prodMarble)
                    {
                        acquired.setMarble(acquired.resMarble() + marbleProducer.acquire(tgMarble - consumer.resCurrent().resMarble(), consumer.personality()));
                    }

                    for (RemoteProducer wineProducer : prodWine)
                    {
                        acquired.setWine(acquired.resWine() + wineProducer.acquire(tgWine - consumer.resCurrent().resWine(), consumer.personality()));
                    }

                    for (RemoteProducer crystalProducer : prodCrystal)
                    {
                        acquired.setCrystal(acquired.resCrystal() + crystalProducer.acquire(tgCrystal - consumer.resCurrent().resCrystal(), consumer.personality()));
                    }

                    for (RemoteProducer brimstoneProducer : prodBrimstone)
                    {
                        acquired.setBrimstone(acquired.resBrimstone() + brimstoneProducer.acquire(tgBrimstone - consumer.resCurrent().resBrimstone(), consumer.personality()));
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
