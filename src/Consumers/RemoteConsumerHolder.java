package Consumers;

/**
* Consumers/RemoteConsumerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RemoteConsumer.idl
* Pondelok, 2017, m�ja 1 21:03:36 CEST
*/

public final class RemoteConsumerHolder implements org.omg.CORBA.portable.Streamable
{
  public Consumers.RemoteConsumer value = null;

  public RemoteConsumerHolder ()
  {
  }

  public RemoteConsumerHolder (Consumers.RemoteConsumer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Consumers.RemoteConsumerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Consumers.RemoteConsumerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Consumers.RemoteConsumerHelper.type ();
  }

}
