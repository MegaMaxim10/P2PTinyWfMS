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
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class LogEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected java.lang.String info;

   /**
    **/
   public void setInfo(java.lang.String v){
      this.info = v;
   }

   public java.lang.String getInfo(){
      return info;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   LogEvent(java.lang.String info){
      setInfo(info);
   }

   /**
    * Constructor
    **/
   public   LogEvent(String adressee, java.lang.String info){
      super(adressee);
      setInfo(info);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the LogEvent object.
    * @return a value of the type 'String' : a string representation of this LogEvent
    **/
   public  String toString(){
      String res = "LogEvent";
      return res;
   }


}
