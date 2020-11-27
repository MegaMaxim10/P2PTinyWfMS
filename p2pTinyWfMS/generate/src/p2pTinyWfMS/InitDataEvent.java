/**
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
public class InitDataEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected inria.smarttools.core.component.PropertyMap inits;

   /**
    **/
   public void setInits(inria.smarttools.core.component.PropertyMap v){
      this.inits = v;
   }

   public inria.smarttools.core.component.PropertyMap getInits(){
      return inits;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   InitDataEvent(inria.smarttools.core.component.PropertyMap inits){
      setInits(inits);
   }

   /**
    * Constructor
    **/
   public   InitDataEvent(String adressee, inria.smarttools.core.component.PropertyMap inits){
      super(adressee);
      setInits(inits);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the InitDataEvent object.
    * @return a value of the type 'String' : a string representation of this InitDataEvent
    **/
   public  String toString(){
      String res = "InitDataEvent";
      return res;
   }


}
