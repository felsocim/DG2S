package Producers;

import org.omg.CORBA.*;

import java.util.Random;
import java.util.Timer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TimerTask;

public class Producer
{
    public static void main(String[] args)
    {
        if(args.length != 4)
        {
            System.out.println("Invalid argument entry! (Usage: java Producer <RESSOURCE TYPE> <INIT RES COUNT> <MAX ACQUIRE STEP> <REFILL FREQUENCE>");
            System.exit(-1);
        }

        Random random = new Random();
        int id = random.nextInt(1000);

        String strResType = args[0].replaceAll(" ", "").toUpperCase();
        RessourceType resType;

        switch(strResType)
        {
            case "WOOD":
                resType = RessourceType.WOOD;
                break;
            case "MARBLE":
                resType = RessourceType.MARBLE;
                break;
            case "WINE":
                resType = RessourceType.WINE;
                break;
            case "CRYSTAL":
                resType = RessourceType.CRYSTAL;
                break;
            case "BRIMSTONE":
                resType = RessourceType.BRIMSTONE;
                break;
            default:
                resType = RessourceType.WOOD;
                break;
        }

        int initCount = Integer.parseInt(args[1]);
        int maxStep = Integer.parseInt(args[2]);
        int refillFreq = Integer.parseInt(args[3]);

        try
        {
            ORB corba = ORB.init(args, null);

            RemoteProducerImpl ressource = new RemoteProducerImpl(resType, id, initCount, maxStep, refillFreq);

            String ior = corba.object_to_string(ressource);

            PrintWriter ior_writer = new PrintWriter("Producers_of_" + resType.toString() + ".res");
            ior_writer.println(ior);
            ior_writer.close();

            Timer refiller = new Timer();
            refiller.schedule(new TimerTask() {
                @Override
                public void run() {
                    ressource.generate();
                }
            }, ressource.refillFrequence(), ressource.refillFrequence());

            System.out.println(resType.toString() + " producer initialized.");
        }
        catch (SystemException sys_e)
        {
            sys_e.printStackTrace();
        }
        catch (FileNotFoundException fnf_e)
        {
            System.out.println("Unable to write down IOR!");
            System.exit(-1);
        }
    }
}
