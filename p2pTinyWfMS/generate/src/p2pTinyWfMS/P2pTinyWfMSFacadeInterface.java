/**
 **/
package p2pTinyWfMS;


/**
 **/
public interface P2pTinyWfMSFacadeInterface {
   //
   // Methods 
   //

   /**
    * returnTo
    * null
    * @param expeditor is the component name who sent this message
    **/
   public  void inReturnTo(String expeditor, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response);

   /**
    * disconnect
    * disconnect
    * @param expeditor is the component name who sent this message
    **/
   public  void disconnectInput(String expeditor);

   /**
    * quit
    * quit
    * @param expeditor is the component name who sent this message
    **/
   public  void quit(String expeditor);

   /**
    * forwardTo
    * null
    * @param expeditor is the component name who sent this message
    **/
   public  void inForwardTo(String expeditor, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request);

   /**
    * shutdown
    * shutdown
    * @param expeditor is the component name who sent this message
    **/
   public  void shutdown(String expeditor);

   /**
    * requestInitData
    * 
    * @param expeditor is the component name who sent this message
    **/
   public  Object requestTree(String expeditor);

   /**
    * exit
    * 
    **/
   public  void addExitListener(ExitListener data);

   /**
    * exit
    * 
    **/
   public  void removeExitListener(ExitListener data);

   /**
    * disconnect
    * 
    **/
   public  void addDisconnectListener(DisconnectListener data);

   /**
    * disconnect
    * 
    **/
   public  void removeDisconnectListener(DisconnectListener data);

   /**
    * initData
    * 
    **/
   public  void addInitDataListener(InitDataListener data);

   /**
    * initData
    * 
    **/
   public  void removeInitDataListener(InitDataListener data);

   /**
    * undo
    * 
    **/
   public  void addUndoListener(UndoListener data);

   /**
    * undo
    * 
    **/
   public  void removeUndoListener(UndoListener data);

   /**
    * log
    * 
    **/
   public  void addLogListener(LogListener data);

   /**
    * log
    * 
    **/
   public  void removeLogListener(LogListener data);

   /**
    * logUndo
    * 
    **/
   public  void addLogUndoListener(LogUndoListener data);

   /**
    * logUndo
    * 
    **/
   public  void removeLogUndoListener(LogUndoListener data);

   /**
    * returnTo
    * null
    **/
   public  void addReturnToListener(ReturnToListener data);

   /**
    * returnTo
    * null
    **/
   public  void removeReturnToListener(ReturnToListener data);

   /**
    * forwardTo
    * null
    **/
   public  void addForwardToListener(ForwardToListener data);

   /**
    * forwardTo
    * null
    **/
   public  void removeForwardToListener(ForwardToListener data);

   /**
    * connectTo
    * 
    **/
   public  void addConnectToListener(ConnectToListener data);

   /**
    * connectTo
    * 
    **/
   public  void removeConnectToListener(ConnectToListener data);

   /**
    * send
    * 
    **/
   public  void addSendListener(SendListener data);

   /**
    * send
    * 
    **/
   public  void removeSendListener(SendListener data);


}
