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
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class ConnectToEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected java.lang.String id_src;
   /**
    **/
   protected java.lang.String type_dest;
   /**
    **/
   protected java.lang.String id_dest;
   /**
    **/
   protected java.lang.String dc;
   /**
    **/
   protected java.lang.String tc;
   /**
    **/
   protected java.lang.String sc;
   /**
    **/
   protected inria.smarttools.core.component.PropertyMap actions;

   /**
    **/
   public void setId_src(java.lang.String v){
      this.id_src = v;
   }

   public java.lang.String getId_src(){
      return id_src;
   }
   /**
    **/
   public void setType_dest(java.lang.String v){
      this.type_dest = v;
   }

   public java.lang.String getType_dest(){
      return type_dest;
   }
   /**
    **/
   public void setId_dest(java.lang.String v){
      this.id_dest = v;
   }

   public java.lang.String getId_dest(){
      return id_dest;
   }
   /**
    **/
   public void setDc(java.lang.String v){
      this.dc = v;
   }

   public java.lang.String getDc(){
      return dc;
   }
   /**
    **/
   public void setTc(java.lang.String v){
      this.tc = v;
   }

   public java.lang.String getTc(){
      return tc;
   }
   /**
    **/
   public void setSc(java.lang.String v){
      this.sc = v;
   }

   public java.lang.String getSc(){
      return sc;
   }
   /**
    **/
   public void setActions(inria.smarttools.core.component.PropertyMap v){
      this.actions = v;
   }

   public inria.smarttools.core.component.PropertyMap getActions(){
      return actions;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   ConnectToEvent(java.lang.String id_src, java.lang.String type_dest, java.lang.String id_dest, java.lang.String dc, java.lang.String tc, java.lang.String sc, inria.smarttools.core.component.PropertyMap actions){
      setId_src(id_src);
      setType_dest(type_dest);
      setId_dest(id_dest);
      setDc(dc);
      setTc(tc);
      setSc(sc);
      setActions(actions);
   }

   /**
    * Constructor
    **/
   public   ConnectToEvent(String adressee, java.lang.String id_src, java.lang.String type_dest, java.lang.String id_dest, java.lang.String dc, java.lang.String tc, java.lang.String sc, inria.smarttools.core.component.PropertyMap actions){
      super(adressee);
      setId_src(id_src);
      setType_dest(type_dest);
      setId_dest(id_dest);
      setDc(dc);
      setTc(tc);
      setSc(sc);
      setActions(actions);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the ConnectToEvent object.
    * @return a value of the type 'String' : a string representation of this ConnectToEvent
    **/
   public  String toString(){
      String res = "ConnectToEvent";
      return res;
   }


}
