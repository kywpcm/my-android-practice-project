/*
 * 클라이언트의 채팅요청에 대한 서버쪽 구현
 * author PYO IN SOO
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClientRequestHandler implements Runnable{
  private Socket clientSocket;
  private static ArrayList<ChatClientRequestHandler> handlers = 
		             new ArrayList<ChatClientRequestHandler>(100);
  public ChatClientRequestHandler (Socket socket) {
    this.clientSocket = socket;
  }
  protected DataInputStream dataIn;
  protected DataOutputStream dataOut;
  protected Thread listener;
  
  public  void clientRequestInitStart() {
    if (listener == null) {
      try {
        dataIn = new DataInputStream
          (new BufferedInputStream (clientSocket.getInputStream ()));
        dataOut = new DataOutputStream
          (new BufferedOutputStream (clientSocket.getOutputStream ()));
        listener = new Thread (this);
        listener.start ();
      }catch (IOException ioe) {
        System.err.println(" 클라이 언트 요청 중 예외 발생!" + ioe);
        stop();
      }
    }
  }
  public  void stop () {
     if (listener != null) {
        if (listener != Thread.currentThread ()){
          listener.interrupt ();
        }
        listener = null;
    	if( dataIn != null){
           try{
        	   dataIn.close();
           }catch(IOException e){}
    	}
    	if( dataOut != null){
            try{
         	   dataOut.close();
            }catch(IOException e){}
     	}
    	if( clientSocket != null){
            try{
              clientSocket.close();
            }catch(IOException e){}
     	}
     }
  }
  public void run () {
    try {
      handlers.add (this);
      while (!Thread.interrupted ()) {
        String message = dataIn.readUTF ();
        broadcastChatMessage(message);
      }
    } catch (EOFException ignored) {
    } catch (IOException ex) {
      if (listener == Thread.currentThread ())
        ex.printStackTrace ();
    } finally {
      handlers.remove(this);
    }
    stop();
  }
  private void broadcastChatMessage (String message) {
    synchronized (handlers) {
      Iterator<ChatClientRequestHandler> chatClients = handlers.iterator();
      while (chatClients.hasNext ()) {
        ChatClientRequestHandler handler = chatClients.next ();
        try {
          handler.dataOut.writeUTF (message);
          handler.dataOut.flush ();
        }catch (IOException ex) {
        	handler.stop ();
        }
      }
    }
  }
}
