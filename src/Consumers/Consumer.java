package Consumers;

import Producers.RemoteProducer;
import Producers.RemoteProducerHelper;
import Producers.RessourceType;
import org.apache.commons.cli.*;
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
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = null;
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Producer -h", options, false);

        options.addOption(OptionBuilder.withLongOpt("identificator").withArgName("string").hasArg().withDescription("consumer unique indentifier (must be different from any other simultaneously running consumer)").create("i"));
        options.addOption(OptionBuilder.withLongOpt("target-wood").withArgName("count").hasArg().withDescription("consumer's wood ressource target").create("tw"));
        options.addOption(OptionBuilder.withLongOpt("target-marble").withArgName("count").hasArg().withDescription("consumer's marble ressource target").create("tm"));
        options.addOption("c", "cooperative-personality", false, "sets consumer's personality to cooperative");
        options.addOption("I", "individuous-personality", false, "sets consumer's personality to individuous");
        options.addOption("o", "observation-mode", false, "launches consumer in the observation mode");
        options.addOption("H", "human-agent", false, "enables human interaction");
        options.addOption("t", "turn-by-turn", false, "makes the agent wait for its turn to play");
        options.addOption("h", "help", false, "shows help");

        try
        {
            commandLine = parser.parse(options, args);
        }
        catch (ParseException e)
        {
            System.err.println("Failed to parse given options! Enter 'java Consumer -h' for help.");
            System.exit(-1);
        }

        String id = "(default consumer)";
        int tgWood = 10;
        int tgMarble = 10;
        char personality = 'i';
        boolean inObservation = false;
        boolean manualMode = false;
        boolean humanGamer = false;

        if(commandLine.hasOption("i"))
        {
            id = commandLine.getOptionValue("i");
        }
        else
        {
            System.out.println("Incorrect argument(s)! Usage: java Consumer <CONSUMER ID> <TARGET WOOD> <TARGET MARBLE> <PERSONALITY> <OBSERVE> <MANUAL MODE> <HUMAN GAMER>");
            System.exit(-1);
        }

        if(commandLine.hasOption("tw"))
        {
            tgWood = Integer.parseInt(commandLine.getOptionValue("tw"));
        }

        if(commandLine.hasOption("tm"))
        {
            tgWood = Integer.parseInt(commandLine.getOptionValue("tm"));
        }

        if(commandLine.hasOption("c"))
        {
            personality = 'c';
        }

        if(commandLine.hasOption("I"))
        {
            personality = 'i';
        }

        if(commandLine.hasOption("o"))
        {
            inObservation = true;
        }

        if(commandLine.hasOption("H"))
        {
            humanGamer = true;
            manualMode = true;
            personality = 'i';
        }

        if(commandLine.hasOption("t"))
        {
            manualMode = true;
        }

        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
        String userResponse;

        RemoteConsumerImpl consumer = new RemoteConsumerImpl(id, inObservation, manualMode, new VectorRessourceImpl(tgWood, tgMarble), personality);

        LinkedList<String> prodWoodIOR = loadProducers(RessourceType.WOOD);
        LinkedList<String> prodMarbleIOR = loadProducers(RessourceType.MARBLE);
        LinkedList<String> consumersIOR = new LinkedList<String>();
        String consumerIOR;

        FortuneTeller fortuneTeller = new FortuneTeller(personality, humanGamer);

        if(prodWoodIOR.size() < 1 || prodMarbleIOR.size() < 1)
        {
            System.out.println("Please, make sure there is at least one producer initialized per raw material!");
            System.exit(12);
        }

        try
        {
            ORB corba = ORB.init(args, null);

            consumerIOR = corba.object_to_string(consumer);

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
                ioex.printStackTrace();
            }

            RemoteProducer[] prodWood = new RemoteProducer[prodWoodIOR.size()];
            RemoteProducer[] prodMarble = new RemoteProducer[prodMarbleIOR.size()];
            RemoteConsumer[] consumers = null;

            for (int i = 0; i < prodWood.length; i++)
            {
                prodWood[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodWoodIOR.get(i)));
            }

            for (int i = 0; i < prodMarble.length; i++)
            {
                prodMarble[i] = RemoteProducerHelper.narrow(corba.string_to_object(prodMarbleIOR.get(i)));
            }

            System.out.println("Consumer " + consumer.idConsumer() + " initialized. Waiting for signal...");

            boolean loadOthers = true;

            while(!consumer.finished())
            {
                if(consumer.readyToGo() && loadOthers)
                {
                    try
                    {
                        FileReader fileReader = new FileReader("consumers.drg");
                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        String current;

                        while((current = bufferedReader.readLine()) != null)
                        {
                            if(current.compareTo(consumerIOR) != 0)
                            {
                                consumersIOR.add(current);
                            }
                        }

                        fileReader.close();
                    }
                    catch (IOException inputOutputException)
                    {
                        System.out.println("Unable to read consumers list file!");
                        System.exit(12);
                    }

                    consumers = new RemoteConsumer[consumersIOR.size()];

                    for (int i = 0; i < consumersIOR.size(); i++)
                    {
                        consumers[i] = RemoteConsumerHelper.narrow(corba.string_to_object(consumersIOR.get(i)));
                    }

                    loadOthers = false;
                }

                if(consumer.readyToGo() && consumer.myTurn())
                {
                    int userWood = 0, userMarble = 0;

                    if(humanGamer)
                    {
                        VectorRessourceImpl remaining = new VectorRessourceImpl(consumer.resTarget().resWood(), consumer.resTarget().resMarble());
                        remaining.subtract(consumer.resCurrent());

                        System.out.println("Remaining: \n" + remaining._toString());

                        System.out.println("\nWood amount to acquire : ");

                        try
                        {
                            userResponse = userInputReader.readLine();
                            userWood = Integer.parseInt(userResponse);
                        }
                        catch (IOException e)
                        {
                            System.out.println("User input exception!");
                            System.exit(-1);
                        }

                        System.out.println("\nMarble amount to acquire : ");

                        try
                        {
                            userResponse = userInputReader.readLine();
                            userMarble = Integer.parseInt(userResponse);
                        }
                        catch (IOException e)
                        {
                            System.out.println("User input exception!");
                            System.exit(-1);
                        }
                    }

                    fortuneTeller.getFortune(consumer, consumers, prodWood, prodMarble, new VectorRessourceImpl(userWood, userMarble));

                    //System.out.println(consumer._toString());

                    if(consumer.manualMode())
                    {
                        consumer.setMyTurn(false);
                    }
                }
            }

            System.out.println("Consumer achieved the target!");

            corba.run();
        }
        catch (SystemException sys_e)
        {
            sys_e.printStackTrace();
        }
    }
}
