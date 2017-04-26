package Consumers;

import Producers.RemoteProducer;
import Producers.RemoteProducerHelper;
import Producers.RessourceType;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Consumer
{
    public static void main(String[] args)
    {
        int tgWood = Integer.parseInt(args[0]);
        int tgMarble = Integer.parseInt(args[1]);
        int tgWine = Integer.parseInt(args[2]);
        int tgCrystal = Integer.parseInt(args[3]);
        int tgBrimstone = Integer.parseInt(args[4]);

        try
        {
            ORB corba = ORB.init(args, null);

            LinkedList<RemoteProducer> wood_producers = new LinkedList<RemoteProducer>();
            LinkedList<RemoteProducer> marble_producers = new LinkedList<RemoteProducer>();
            LinkedList<RemoteProducer> wine_producers = new LinkedList<RemoteProducer>();
            LinkedList<RemoteProducer> crystal_producers = new LinkedList<RemoteProducer>();
            LinkedList<RemoteProducer> brimstone_producers = new LinkedList<RemoteProducer>();

            try
            {
                for(RessourceType ressourceType : RessourceType.values())
                {
                    FileReader fileReader = new FileReader("Producers_of_" + ressourceType.toString() + ".res");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String current;

                    while((current = bufferedReader.readLine()) != null)
                    {
                        Object obj = corba.string_to_object(current);

                        switch(ressourceType.toString())
                        {
                            case "WOOD":
                                wood_producers.add(RemoteProducerHelper.narrow(obj));
                                break;
                            case "MARBLE":
                                marble_producers.add(RemoteProducerHelper.narrow(obj));
                                break;
                            case "WINE":
                                wine_producers.add(RemoteProducerHelper.narrow(obj));
                                break;
                            case "CRYSTAL":
                                crystal_producers.add(RemoteProducerHelper.narrow(obj));;
                                break;
                            case "BRIMSTONE":
                                brimstone_producers.add(RemoteProducerHelper.narrow(obj));
                                break;
                        }
                    }

                    fileReader.close();
                }
            }
            catch (FileNotFoundException fnf_e)
            {
                System.out.println("Unable to access producers list!");
            }
            catch (IOException io_e)
            {
                io_e.printStackTrace();
            }

            int clWood = 0, clMarble = 0, clWine = 0, clCrystal = 0, clBrimstone = 0;

            while ((clWood < tgWood) || (clMarble < tgMarble) || (clWine < tgWine) || (clCrystal < tgCrystal) || (clBrimstone < tgBrimstone))
            {
                for(RemoteProducer wr : wood_producers)
                {
                    if(wr.acquire(1))
                    {
                        clWood += 1;
                        System.out.println("Consumer acquired 1 wood unit.");
                    }
                }

                for(RemoteProducer mr : marble_producers)
                {
                    if(mr.acquire(1))
                    {
                        clMarble += 1;
                        System.out.println("Consumer acquired 1 marble unit.");
                    }
                }

                for(RemoteProducer wnr : wine_producers)
                {
                    if(wnr.acquire(1))
                    {
                        clWine += 1;
                        System.out.println("Consumer acquired 1 wine unit.");
                    }
                }

                for(RemoteProducer cr : crystal_producers)
                {
                    if(cr.acquire(1))
                    {
                        clCrystal += 1;
                        System.out.println("Consumer acquired 1 crystal unit.");
                    }
                }

                for(RemoteProducer br : brimstone_producers)
                {
                    if(br.acquire(1))
                    {
                        clBrimstone += 1;
                        System.out.println("Consumer acquired 1 brimstone unit.");
                    }
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
