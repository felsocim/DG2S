package Consumer;

import Ressources.Ressource;
import Ressources.RessourceHelper;
import Ressources.RessourceImpl;
import Ressources.RessourceType;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class Consumer
{
    public static void main(String[] args)
    {
        try
        {
            ORB corba = ORB.init(args, null);

            LinkedList<Ressource> wood_producers = new LinkedList<Ressource>();

            try
            {
                FileReader wp_ior = new FileReader("WoodProducers.list");
                BufferedReader wp_br = new BufferedReader(wp_ior);

                String current;

                while((current = wp_br.readLine()) != null)
                {
                    Object obj = corba.string_to_object(current);
                    wood_producers.add(RessourceHelper.narrow(obj));
                }

                wp_ior.close();
            }
            catch (FileNotFoundException fnf_e)
            {
                System.out.println("Unable to access wood producers list!");
            }
            catch (IOException io_e)
            {
                io_e.printStackTrace();
            }

            int target = 30;
            int collected = 0;

            while (collected < target)
            {
                ListIterator<Ressource> wr = wood_producers.listIterator();

                while(wr.hasNext())
                {
                    if(wr.next().acquire(3))
                    {
                        collected += 3;

                        System.out.println("Consumer acquired 3 woods.");
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
