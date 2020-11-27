/**
 **/
package p2pTinyWfMS;

import java.util.*;

import p2pTinyWfMS.P2pTinyWfMS;
import inria.smarttools.core.component.*;

/**
 **/
public class P2pTinyWfMSFacade extends P2pTinyWfMS implements P2pTinyWfMSFacadeInterface {
   //
   // Fields 
   //

   /**
    * exit
    * 
    **/
   protected Vector<ExitListener> exitListeners = new Vector<ExitListener>();
   /**
    * disconnect
    * 
    **/
   protected Vector<DisconnectListener> disconnectListeners = new Vector<DisconnectListener>();
   /**
    * initData
    * 
    **/
   protected Vector<InitDataListener> initDataListeners = new Vector<InitDataListener>();
   /**
    * undo
    * 
    **/
   protected Vector<UndoListener> undoListeners = new Vector<UndoListener>();
   /**
    * log
    * 
    **/
   protected Vector<LogListener> logListeners = new Vector<LogListener>();
   /**
    * logUndo
    * 
    **/
   protected Vector<LogUndoListener> logUndoListeners = new Vector<LogUndoListener>();
   /**
    * returnTo
    * null
    **/
   protected Vector<ReturnToListener> returnToListeners = new Vector<ReturnToListener>();
   /**
    * forwardTo
    * null
    **/
   protected Vector<ForwardToListener> forwardToListeners = new Vector<ForwardToListener>();
   /**
    * connectTo
    * 
    **/
   protected Vector<ConnectToListener> connectToListeners = new Vector<ConnectToListener>();
   /**
    * send
    * 
    **/
   protected Vector<SendListener> sendListeners = new Vector<SendListener>();
   /**
    **/
   private String idName;

   /**
    **/
   public void setIdName(String v){
      this.idName = v;
   }

   public String getIdName(){
      return idName;
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   P2pTinyWfMSFacade(String idName){
      setIdName(idName);
   }

   /**
    * Constructor
    **/
   public   P2pTinyWfMSFacade(){
   }


   //
   // Methods 
   //

   /**
    *  request init data 
    **/
   public  Object requestInitData(String expeditor){
      return "";
   }

   /**
    * returnTo
    * null
    * @param expeditor is the component name who sent this message
    **/
   public  void inReturnTo(String expeditor, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response){
      super.inReturnTo(expeditor, response);
   }

   /**
    * disconnect
    * disconnect
    * @param expeditor is the component name who sent this message
    **/
   public  void disconnectInput(String expeditor){
      super.disconnectInput(expeditor);
   }

   /**
    * quit
    * quit
    * @param expeditor is the component name who sent this message
    **/
   public  void quit(String expeditor){
      super.quit(expeditor);
   }

   /**
    * forwardTo
    * null
    * @param expeditor is the component name who sent this message
    **/
   public  void inForwardTo(String expeditor, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request){
      super.inForwardTo(expeditor, request);
   }

   /**
    * shutdown
    * shutdown
    * @param expeditor is the component name who sent this message
    **/
   public  void shutdown(String expeditor){
      super.shutdown(expeditor);
   }

   /**
    * requestInitData
    * 
    * @param expeditor is the component name who sent this message
    **/
   public  Object requestTree(String expeditor){
      return super.requestTree(expeditor);
   }

   /**
    * exit
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void exit(){
      exit(null);
   }

   /**
    * exit
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void exit(String adressee){
      PropertyMap args = new PropertyMap();
      ExitEvent ev =  new ExitEvent(adressee);
      ev.setAttributes(args);
      for(int i = 0 ; i < exitListeners.size() ; i++)
      (( ExitListener ) exitListeners.elementAt(i)).exit(ev);
   }

   /**
    * disconnect
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void disconnectOut(){
      disconnectOut(null);
   }

   /**
    * disconnect
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void disconnectOut(String adressee){
      PropertyMap args = new PropertyMap();
      DisconnectEvent ev =  new DisconnectEvent(adressee);
      ev.setAttributes(args);
      for(int i = 0 ; i < disconnectListeners.size() ; i++)
      (( DisconnectListener ) disconnectListeners.elementAt(i)).disconnectOut(ev);
   }

   /**
    * initData
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void initData(inria.smarttools.core.component.PropertyMap inits){
      initData(null, inits);
   }

   /**
    * initData
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void initData(String adressee, inria.smarttools.core.component.PropertyMap inits){
      PropertyMap args = new PropertyMap();
      args.put("inits",inits);
      InitDataEvent ev =  new InitDataEvent(adressee, inits);
      ev.setAttributes(args);
      for(int i = 0 ; i < initDataListeners.size() ; i++)
      (( InitDataListener ) initDataListeners.elementAt(i)).initData(ev);
   }

   /**
    * undo
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void undo(java.lang.String message, java.lang.String receiver){
      undo(null, message, receiver);
   }

   /**
    * undo
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void undo(String adressee, java.lang.String message, java.lang.String receiver){
      PropertyMap args = new PropertyMap();
      args.put("message",message);
      args.put("receiver",receiver);
      UndoEvent ev =  new UndoEvent(adressee, message, receiver);
      ev.setAttributes(args);
      for(int i = 0 ; i < undoListeners.size() ; i++)
      (( UndoListener ) undoListeners.elementAt(i)).undo(ev);
   }

   /**
    * log
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void log(java.lang.String info){
      log(null, info);
   }

   /**
    * log
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void log(String adressee, java.lang.String info){
      PropertyMap args = new PropertyMap();
      args.put("info",info);
      LogEvent ev =  new LogEvent(adressee, info);
      ev.setAttributes(args);
      for(int i = 0 ; i < logListeners.size() ; i++)
      (( LogListener ) logListeners.elementAt(i)).log(ev);
   }

   /**
    * logUndo
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void logUndo(java.lang.String message, java.lang.String receiver){
      logUndo(null, message, receiver);
   }

   /**
    * logUndo
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void logUndo(String adressee, java.lang.String message, java.lang.String receiver){
      PropertyMap args = new PropertyMap();
      args.put("message",message);
      args.put("receiver",receiver);
      LogUndoEvent ev =  new LogUndoEvent(adressee, message, receiver);
      ev.setAttributes(args);
      for(int i = 0 ; i < logUndoListeners.size() ; i++)
      (( LogUndoListener ) logUndoListeners.elementAt(i)).logUndo(ev);
   }

   /**
    * returnTo
    * null
    * @param ev a <code>Object</code> value : data
    **/
   public  void outReturnTo(smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response){
      outReturnTo(null, response);
   }

   /**
    * returnTo
    * null
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void outReturnTo(String adressee, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowResponse response){
      PropertyMap args = new PropertyMap();
      args.put("response",response);
      ReturnToEvent ev =  new ReturnToEvent(adressee, response);
      ev.setAttributes(args);
      for(int i = 0 ; i < returnToListeners.size() ; i++)
      (( ReturnToListener ) returnToListeners.elementAt(i)).outReturnTo(ev);
   }

   /**
    * forwardTo
    * null
    * @param ev a <code>Object</code> value : data
    **/
   public  void outForwardTo(smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request){
      outForwardTo(null, request);
   }

   /**
    * forwardTo
    * null
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void outForwardTo(String adressee, smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflowRequest request){
      PropertyMap args = new PropertyMap();
      args.put("request",request);
      ForwardToEvent ev =  new ForwardToEvent(adressee, request);
      ev.setAttributes(args);
      for(int i = 0 ; i < forwardToListeners.size() ; i++)
      (( ForwardToListener ) forwardToListeners.elementAt(i)).outForwardTo(ev);
   }

   /**
    * connectTo
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void connectTo(java.lang.String id_src, java.lang.String type_dest, java.lang.String id_dest, java.lang.String dc, java.lang.String tc, java.lang.String sc, inria.smarttools.core.component.PropertyMap actions){
      connectTo(null, id_src, type_dest, id_dest, dc, tc, sc, actions);
   }

   /**
    * connectTo
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void connectTo(String adressee, java.lang.String id_src, java.lang.String type_dest, java.lang.String id_dest, java.lang.String dc, java.lang.String tc, java.lang.String sc, inria.smarttools.core.component.PropertyMap actions){
      PropertyMap args = new PropertyMap();
      args.put("id_src",id_src);
      args.put("type_dest",type_dest);
      args.put("id_dest",id_dest);
      args.put("dc",dc);
      args.put("tc",tc);
      args.put("sc",sc);
      args.put("actions",actions);
      ConnectToEvent ev =  new ConnectToEvent(adressee, id_src, type_dest, id_dest, dc, tc, sc, actions);
      ev.setAttributes(args);
      for(int i = 0 ; i < connectToListeners.size() ; i++)
      (( ConnectToListener ) connectToListeners.elementAt(i)).connectTo(ev);
   }

   /**
    * send
    * 
    * @param ev a <code>Object</code> value : data
    **/
   public  void send(java.lang.String messageName, java.lang.String messageExpeditor){
      send(null, messageName, messageExpeditor);
   }

   /**
    * send
    * 
    * @param adressee component name, which will receive this message
    * @param ev a <code>Object</code> value : data
    **/
   public  void send(String adressee, java.lang.String messageName, java.lang.String messageExpeditor){
      PropertyMap args = new PropertyMap();
      args.put("messageName",messageName);
      args.put("messageExpeditor",messageExpeditor);
      SendEvent ev =  new SendEvent(adressee, messageName, messageExpeditor);
      ev.setAttributes(args);
      for(int i = 0 ; i < sendListeners.size() ; i++)
      (( SendListener ) sendListeners.elementAt(i)).send(ev);
   }

   /**
    * exit
    * 
    **/
   public  void addExitListener(ExitListener data){
      exitListeners.add(data);
   }

   /**
    * exit
    * 
    **/
   public  void removeExitListener(ExitListener data){
      exitListeners.remove(data);
   }

   /**
    * disconnect
    * 
    **/
   public  void addDisconnectListener(DisconnectListener data){
      disconnectListeners.add(data);
   }

   /**
    * disconnect
    * 
    **/
   public  void removeDisconnectListener(DisconnectListener data){
      disconnectListeners.remove(data);
   }

   /**
    * initData
    * 
    **/
   public  void addInitDataListener(InitDataListener data){
      initDataListeners.add(data);
   }

   /**
    * initData
    * 
    **/
   public  void removeInitDataListener(InitDataListener data){
      initDataListeners.remove(data);
   }

   /**
    * undo
    * 
    **/
   public  void addUndoListener(UndoListener data){
      undoListeners.add(data);
   }

   /**
    * undo
    * 
    **/
   public  void removeUndoListener(UndoListener data){
      undoListeners.remove(data);
   }

   /**
    * log
    * 
    **/
   public  void addLogListener(LogListener data){
      logListeners.add(data);
   }

   /**
    * log
    * 
    **/
   public  void removeLogListener(LogListener data){
      logListeners.remove(data);
   }

   /**
    * logUndo
    * 
    **/
   public  void addLogUndoListener(LogUndoListener data){
      logUndoListeners.add(data);
   }

   /**
    * logUndo
    * 
    **/
   public  void removeLogUndoListener(LogUndoListener data){
      logUndoListeners.remove(data);
   }

   /**
    * returnTo
    * null
    **/
   public  void addReturnToListener(ReturnToListener data){
      returnToListeners.add(data);
   }

   /**
    * returnTo
    * null
    **/
   public  void removeReturnToListener(ReturnToListener data){
      returnToListeners.remove(data);
   }

   /**
    * forwardTo
    * null
    **/
   public  void addForwardToListener(ForwardToListener data){
      forwardToListeners.add(data);
   }

   /**
    * forwardTo
    * null
    **/
   public  void removeForwardToListener(ForwardToListener data){
      forwardToListeners.remove(data);
   }

   /**
    * connectTo
    * 
    **/
   public  void addConnectToListener(ConnectToListener data){
      connectToListeners.add(data);
   }

   /**
    * connectTo
    * 
    **/
   public  void removeConnectToListener(ConnectToListener data){
      connectToListeners.remove(data);
   }

   /**
    * send
    * 
    **/
   public  void addSendListener(SendListener data){
      sendListeners.add(data);
   }

   /**
    * send
    * 
    **/
   public  void removeSendListener(SendListener data){
      sendListeners.remove(data);
   }


}
