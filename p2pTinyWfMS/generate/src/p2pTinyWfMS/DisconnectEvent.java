/**
 * 
 * 
 * 
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class DisconnectEvent extends StEventImpl {
   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   DisconnectEvent(){
   }

   /**
    * Constructor
    **/
   public   DisconnectEvent(String adressee){
      super(adressee);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the DisconnectEvent object.
    * @return a value of the type 'String' : a string representation of this DisconnectEvent
    **/
   public  String toString(){
      String res = "DisconnectEvent";
      return res;
   }


}
