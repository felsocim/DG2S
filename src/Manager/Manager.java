package Manager;

import Consumers.RemoteConsumer;
import Consumers.RemoteConsumerHelper;
import Producers.RemoteProducer;
import Producers.RemoteProducerHelper;
import Producers.RessourceType;
import org.omg.CORBA.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Manager
{
    private static boolean allFinished(RemoteConsumer[] consumers)
    {
        for (RemoteConsumer remoteConsumer : consumers)
        {
            if(!remoteConsumer.finished())
                return false;
        }

        return true;
    }

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
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
        String userResponse;

        try
        {
            ORB corba = ORB.init(args, null);

            LinkedList<String> remoteConsumersIOR = new LinkedList<String>();
            LinkedList<String> remoteWoodProducersIOR = loadProducers(RessourceType.WOOD);
            LinkedList<String> remoteMarbleProducersIOR = loadProducers(RessourceType.MARBLE);
            LinkedList<String> remoteCrystalProducersIOR = loadProducers(RessourceType.CRYSTAL);

            try
            {
                FileReader fileReader = new FileReader("consumers.drg");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String current;

                while((current = bufferedReader.readLine()) != null)
                {
                    remoteConsumersIOR.add(current);
                }

                fileReader.close();
            }
            catch (IOException inputOutputException)
            {
                System.out.println("Unable to read consumers list file!");
                System.exit(12);
            }

            RemoteConsumer[] remoteConsumers = new RemoteConsumer[remoteConsumersIOR.size()];
            RemoteProducer[] remoteWoodProducers = new RemoteProducer[remoteWoodProducersIOR.size()];
            RemoteProducer[] remoteMarbleProducers = new RemoteProducer[remoteMarbleProducersIOR.size()];
            RemoteProducer[] remoteCrystalProducers = new RemoteProducer[remoteCrystalProducersIOR.size()];

            System.out.println("Discovering producers...");

            for (int i = 0; i < remoteWoodProducersIOR.size(); i++)
            {
                remoteWoodProducers[i] = RemoteProducerHelper.narrow(corba.string_to_object(remoteWoodProducersIOR.get(i)));
            }

            for (int i = 0; i < remoteMarbleProducersIOR.size(); i++)
            {
                remoteMarbleProducers[i] = RemoteProducerHelper.narrow(corba.string_to_object(remoteMarbleProducersIOR.get(i)));
            }

            for (int i = 0; i < remoteCrystalProducersIOR.size(); i++)
            {
                remoteCrystalProducers[i] = RemoteProducerHelper.narrow(corba.string_to_object(remoteCrystalProducersIOR.get(i)));
            }

            System.out.println("Successfully discovered " + remoteWoodProducers.length + " wood producers, " + remoteMarbleProducers.length + " marble producers and " + remoteCrystalProducers.length + " crystal producers.");
            System.out.println("Discovering consumers...");

            for (int i = 0; i < remoteConsumersIOR.size(); i++)
            {
                remoteConsumers[i] = RemoteConsumerHelper.narrow(corba.string_to_object(remoteConsumersIOR.get(i)));
            }

            System.out.println("Successfully discovered " + remoteConsumers.length + " consumers.");
            System.out.println("DRG is ready to start the game. Would you like to make producers and consumers ready to go? [YyNn]");
            userResponse = userInputReader.readLine();

            if(!userResponse.matches("[Yy]"))
            {
                System.out.println("Negative or unknown response was recognized. System exiting...");
                System.exit(0);
            }

            System.out.println("Getting producers ready...");

            for (RemoteProducer remoteWoodProducer : remoteWoodProducers)
            {
                remoteWoodProducer.setReadyToGo(true);
                System.out.println(remoteWoodProducer._toString());
            }

            for (RemoteProducer remoteMarbleProducer : remoteMarbleProducers)
            {
                remoteMarbleProducer.setReadyToGo(true);
                System.out.println(remoteMarbleProducer._toString());
            }

            for (RemoteProducer remoteCrystalProducer : remoteCrystalProducers)
            {
                remoteCrystalProducer.setReadyToGo(true);
                System.out.println(remoteCrystalProducer._toString());
            }

            System.out.println("All discovered producers joined the game.");
            System.out.println("Getting consumers ready...");

            for (RemoteConsumer remoteConsumer : remoteConsumers)
            {
                remoteConsumer.setReadyToGo(true);
                System.out.println(remoteConsumer._toString());
            }

            System.out.println("All discovered consumers joined the game.");
            System.out.println("Game is in progress...");

            while(!allFinished(remoteConsumers)) {}

            System.out.println("Game ended.");
        }
        catch (SystemException systemException)
        {
            System.out.println("CORBA system is down!");
            System.exit(-1);
        }
        catch (IOException inputOutputException)
        {
            System.out.println("I/O exception thrown by user input!");
            System.exit(-1);
        }
    }
}
