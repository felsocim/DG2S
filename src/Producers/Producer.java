package Producers;

import org.apache.commons.cli.*;
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
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = null;
        HelpFormatter helpFormatter = new HelpFormatter();

        options.addOption(OptionBuilder.withLongOpt("identificator").hasArg().withDescription("producer unique indentifier (must be different from any other simultaneously running producer)").create("i"));
        options.addOption(OptionBuilder.withLongOpt("type").withArgName("RessourceType").hasArg().withDescription("producing ressource type (WOOD or MARBLE)").create("t"));
        options.addOption(OptionBuilder.withLongOpt("initial-ressource-count").withArgName("count").hasArg().withDescription("initial ressource count").create("c"));
        options.addOption(OptionBuilder.withLongOpt("acquire-max").withArgName("step").hasArg().withDescription("max ressource amount which can be delivered at once to a consumer").create("max"));
        options.addOption(OptionBuilder.withLongOpt("refill-frequence").withArgName("miliseconds").hasArg().withDescription("producer refill frequence in miliseconds").create("f"));
        options.addOption("I", "infinite-res", false, "launch producer with an infinite amount of its ressource");
        options.addOption("h", "help", false, "shows help");

        try
        {
            commandLine = parser.parse(options, args);
        }
        catch (ParseException e)
        {
            System.err.println("Failed to parse given options! Enter 'java Manager -h' for help.");
            System.exit(-1);
        }

        int id = 0;
        String strResType = "WOOD";
        int initCount = 10;
        int maxStep = 5;
        int refillFreq = 5000;
        boolean infiniteRessources = false;

        if(commandLine.hasOption("i"))
        {
            id = Integer.parseInt(commandLine.getOptionValue("i"));
        }
        else
        {
            System.out.println("Invalid argument entry! (Usage: java Producer <PRODUCER ID> <RESSOURCE TYPE> <INIT RES COUNT> <MAX ACQUIRE STEP> <REFILL FREQUENCE> <INFINITE RESSOURCES>");
            System.exit(-1);
        }

        if(commandLine.hasOption("t"))
        {
            strResType = commandLine.getOptionValue("t").replaceAll(" ", "").toUpperCase();
        }

        if(commandLine.hasOption("c"))
        {
            initCount = Integer.parseInt(commandLine.getOptionValue("c"));
        }

        if(commandLine.hasOption("i"))
        {
            id = Integer.parseInt(commandLine.getOptionValue("i"));
        }

        if(commandLine.hasOption("max"))
        {
            maxStep = Integer.parseInt(commandLine.getOptionValue("max"));
        }

        if(commandLine.hasOption("f"))
        {
            refillFreq = Integer.parseInt(commandLine.getOptionValue("f"));
        }

        if(commandLine.hasOption("I"))
        {
            infiniteRessources = true;
        }

        if(commandLine.hasOption("h"))
        {
            helpFormatter.printHelp("Producer -h", options, false);
        }

        RessourceType resType;

        switch(strResType)
        {
            case "WOOD":
                resType = RessourceType.WOOD;
                break;
            case "MARBLE":
                resType = RessourceType.MARBLE;
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

            if(ressource.infiniteRessources())
            {
                Timer refiller = new Timer();
                refiller.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ressource.generate();
                    }
                }, ressource.refillFrequence(), ressource.refillFrequence());
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
