/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * null
 * null
 * null
 * null
 * 
 * 
 * 
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class SendEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected java.lang.String messageName;
   /**
    **/
   protected java.lang.String messageExpeditor;

   /**
    **/
   public void setMessageName(java.lang.String v){
      this.messageName = v;
   }

   public java.lang.String getMessageName(){
      return messageName;
   }
   /**
    **/
   public void setMessageExpeditor(java.lang.String v){
      this.messageExpeditor = v;
   }

   public java.lang.String getMessageExpeditor(){
      return messageExpeditor;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   SendEvent(java.lang.String messageName, java.lang.String messageExpeditor){
      setMessageName(messageName);
      setMessageExpeditor(messageExpeditor);
   }

   /**
    * Constructor
    **/
   public   SendEvent(String adressee, java.lang.String messageName, java.lang.String messageExpeditor){
      super(adressee);
      setMessageName(messageName);
      setMessageExpeditor(messageExpeditor);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the SendEvent object.
    * @return a value of the type 'String' : a string representation of this SendEvent
    **/
   public  String toString(){
      String res = "SendEvent";
      return res;
   }


}
