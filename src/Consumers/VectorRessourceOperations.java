package Consumers;


/**
* Consumers/VectorRessourceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RemoteConsumer.idl
* Sobota, 2017, apr�la 29 10:59:02 CEST
*/

public interface VectorRessourceOperations 
{
  int resWood ();
  int resMarble ();
  int resCrystal ();
  void setWood (int units);
  void setMarble (int units);
  void setCrystal (int units);
  void setAll (int uWood, int uMarble, int uCrystal);
  boolean compare (Consumers.VectorRessource toBeCompared);
  void add (Consumers.VectorRessource operand);
  void subtract (Consumers.VectorRessource operand);
  void addExplicit (int uWood, int uMarble, int uCrystal);
  String _toString ();
} // interface VectorRessourceOperations