package Producers;

/**
* Producers/RemoteProducerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RemoteProducer.idl
* Streda, 2017, apríla 26 11:20:56 CEST
*/

public final class RemoteProducerHolder implements org.omg.CORBA.portable.Streamable
{
  public Producers.RemoteProducer value = null;

  public RemoteProducerHolder ()
  {
  }

  public RemoteProducerHolder (Producers.RemoteProducer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Producers.RemoteProducerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Producers.RemoteProducerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Producers.RemoteProducerHelper.type ();
  }

}
