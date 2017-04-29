package Manager;

import Consumers.RemoteConsumer;
import Consumers.RemoteConsumerHelper;
import Producers.RemoteProducer;
import org.omg.CORBA.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Remote;
import java.util.LinkedList;
import java.util.ListIterator;

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

    public static void main(String[] args)
    {
        try
        {
            ORB corba = ORB.init(args, null);

            LinkedList<String> remoteConsumersIOR = new LinkedList<String>();

            try
            {
                FileReader fileReader = new FileReader("Consumers.cns");
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

            System.out.println("Discovered consumers: ");

            RemoteConsumer[] remoteConsumers = new RemoteConsumer[remoteConsumersIOR.size()];

            for (int i = 0; i < remoteConsumersIOR.size(); i++)
            {
                remoteConsumers[i] = RemoteConsumerHelper.narrow(corba.string_to_object(remoteConsumersIOR.get(i)));
            }

            for (RemoteConsumer remoteConsumer : remoteConsumers)
            {
                remoteConsumer.setReadyToGo(true);
                System.out.println(remoteConsumer._toString());
            }

        }
        catch (SystemException systemException)
        {
            System.out.println("CORBA system exception!");
            systemException.printStackTrace();
        }
    }
}
