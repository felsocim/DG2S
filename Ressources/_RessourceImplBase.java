package Ressources;


/**
* Ressources/_RessourceImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Ressource.idl
* Pondelok, 2017, apríla 17 17:54:20 CEST
*/

public abstract class _RessourceImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements Ressources.Ressource, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _RessourceImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_type", new java.lang.Integer (0));
    _methods.put ("_get_count", new java.lang.Integer (1));
    _methods.put ("_get_step", new java.lang.Integer (2));
    _methods.put ("pretype", new java.lang.Integer (3));
    _methods.put ("generate", new java.lang.Integer (4));
    _methods.put ("acquire", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Ressources/Ressource/_get_type
       {
         Ressources.RessourceType $result = null;
         $result = this.type ();
         out = $rh.createReply();
         Ressources.RessourceTypeHelper.write (out, $result);
         break;
       }

       case 1:  // Ressources/Ressource/_get_count
       {
         int $result = (int)0;
         $result = this.count ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 2:  // Ressources/Ressource/_get_step
       {
         int $result = (int)0;
         $result = this.step ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 3:  // Ressources/Ressource/pretype
       {
         Ressources.RessourceType type = Ressources.RessourceTypeHelper.read (in);
         this.pretype (type);
         out = $rh.createReply();
         break;
       }

       case 4:  // Ressources/Ressource/generate
       {
         int count = in.read_long ();
         this.generate (count);
         out = $rh.createReply();
         break;
       }

       case 5:  // Ressources/Ressource/acquire
       {
         int count = in.read_long ();
         boolean $result = false;
         $result = this.acquire (count);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Ressources/Ressource:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _RessourceImplBase
