package Producer;

import Ressources.RessourceImpl;
import Ressources.RessourceType;
import org.omg.CORBA.*;
import java.util.Timer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TimerTask;

public class ProducerWood
{
    public static void main(String[] args)
    {
        try
        {
            ORB corba = ORB.init(args, null);

            RessourceImpl wood = new RessourceImpl(RessourceType.WOOD, 10, 3, 10000);

            String ior = corba.object_to_string(wood);

            PrintWriter ior_writer = new PrintWriter("WoodProducers.list");
            ior_writer.println(ior);
            ior_writer.close();

            System.out.println(ior);

            Timer refiller = new Timer();
            refiller.schedule(new TimerTask() {
                @Override
                public void run() {
                    wood.generate();
                }
            }, wood.refillFrequence(), wood.refillFrequence());
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
