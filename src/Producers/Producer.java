package Producers;

import org.omg.CORBA.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TimerTask;

public class Producer
{
    public static void main(String[] args)
    {
        if(args.length != 6)
        {
            System.out.println("Invalid argument entry! (Usage: java Producer <PRODUCER ID> <RESSOURCE TYPE> <INIT RES COUNT> <MAX ACQUIRE STEP> <REFILL FREQUENCE> <INFINITE RESSOURCES>");
            System.exit(-1);
        }

        int id = Integer.parseInt(args[0]);
        String strResType = args[1].replaceAll(" ", "").toUpperCase();
        int initCount = Integer.parseInt(args[2]);
        int maxStep = Integer.parseInt(args[3]);
        int refillFreq = Integer.parseInt(args[4]);
        boolean infiniteRessources = (args[5].charAt(0) == 'i');

        RessourceType resType;

        switch(strResType)
        {
            case "WOOD":
                resType = RessourceType.WOOD;
                break;
            case "MARBLE":
                resType = RessourceType.MARBLE;
                break;
            case "CRYSTAL":
                resType = RessourceType.CRYSTAL;
                break;
            default:
                resType = RessourceType.WOOD;
                break;
        }

        try
        {
            ORB corba = ORB.init(args, null);

            RemoteProducerImpl ressource = new RemoteProducerImpl(resType, id, initCount, maxStep, refillFreq, infiniteRessources);

            String ior = corba.object_to_string(ressource);

            try
            {
                FileWriter fileWriter = new FileWriter(resType.toString().toLowerCase() + "_producers.drg", true);
                PrintWriter ior_writer = new PrintWriter(fileWriter);
                ior_writer.println(ior);
                ior_writer.close();
            }
            catch (FileNotFoundException error404)
            {
                System.out.println("Unable to write down producer " + ressource.id() + " IOR!");
                System.exit(-1);
            }
            catch (IOException inputOutputException)
            {
                System.out.println("I/O error encountered while writing down producer " + ressource.id() + " IOR!");
                System.exit(-1);
            }

            if(!ressource.infiniteRessources())
            {
                Timer refiller = new Timer();
                refiller.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ressource.generate();
                    }
                }, 30000, ressource.refillFrequence());
            }

            System.out.println(resType.toString() + " producer " + ressource.id() + " initialized.");

            corba.run();
        }
        catch (SystemException sys_e)
        {
            sys_e.printStackTrace();
        }
    }
}
