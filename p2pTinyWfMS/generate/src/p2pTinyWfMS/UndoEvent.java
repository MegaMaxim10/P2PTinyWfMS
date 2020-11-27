/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class UndoEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected java.lang.String message;
   /**
    **/
   protected java.lang.String receiver;

   /**
    **/
   public void setMessage(java.lang.String v){
      this.message = v;
   }

   public java.lang.String getMessage(){
      return message;
   }
   /**
    **/
   public void setReceiver(java.lang.String v){
      this.receiver = v;
   }

   public java.lang.String getReceiver(){
      return receiver;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   UndoEvent(java.lang.String message, java.lang.String receiver){
      setMessage(message);
      setReceiver(receiver);
   }

   /**
    * Constructor
    **/
   public   UndoEvent(String adressee, java.lang.String message, java.lang.String receiver){
      super(adressee);
      setMessage(message);
      setReceiver(receiver);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the UndoEvent object.
    * @return a value of the type 'String' : a string representation of this UndoEvent
    **/
   public  String toString(){
      String res = "UndoEvent";
      return res;
   }


}
