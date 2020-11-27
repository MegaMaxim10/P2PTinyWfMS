/**
 * 
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class ExitEvent extends StEventImpl {
   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   ExitEvent(){
   }

   /**
    * Constructor
    **/
   public   ExitEvent(String adressee){
      super(adressee);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the ExitEvent object.
    * @return a value of the type 'String' : a string representation of this ExitEvent
    **/
   public  String toString(){
      String res = "ExitEvent";
      return res;
   }


}
