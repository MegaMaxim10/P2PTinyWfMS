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
 **/
package p2pTinyWfMS;

import inria.smarttools.core.util.*;

/**
 **/
public class ReturnToEvent extends StEventImpl {
   //
   // Fields 
   //

   /**
    **/
   protected smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response;

   /**
    **/
   public void setResponse(smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse v){
      this.response = v;
   }

   public smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse getResponse(){
      return response;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   ReturnToEvent(smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response){
      setResponse(response);
   }

   /**
    * Constructor
    **/
   public   ReturnToEvent(String adressee, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response){
      super(adressee);
      setResponse(response);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the ReturnToEvent object.
    * @return a value of the type 'String' : a string representation of this ReturnToEvent
    **/
   public  String toString(){
      String res = "ReturnToEvent";
      return res;
   }


}
