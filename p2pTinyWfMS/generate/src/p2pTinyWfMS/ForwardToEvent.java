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
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class ForwardToEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request;

   /**
    **/
   public void setRequest(smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest v){
      this.request = v;
   }

   public smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest getRequest(){
      return request;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   ForwardToEvent(smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request){
      setRequest(request);
   }

   /**
    * Constructor
    **/
   public   ForwardToEvent(String adressee, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request){
      super(adressee);
      setRequest(request);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the ForwardToEvent object.
    * @return a value of the type 'String' : a string representation of this ForwardToEvent
    **/
   public  String toString(){
      String res = "ForwardToEvent";
      return res;
   }


}
