/*
 *  간단한 소켓 채팅 클라이언트
 *  author PYO IN SOO
 */
package com.pyo.tcp.simple.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChattingClientActivity extends Activity{
	
	private static final String CHAT_LOG = "CHATTING_LOG";
	private ArrayAdapter<String> messageItems;
	private ListView messagePanel;
	private Button sendBtn;
	private Button stopBtn;
	private Button connectBtn;
	private EditText chatMessage;
	private ConnectionThread connThread;
	
	private Handler messageHandler = new Handler();
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_main);
        
        messageItems = new ArrayAdapter<String>(this, R.layout.message);
        messagePanel = (ListView)findViewById(R.id.message_panel);
        messagePanel.setAdapter(messageItems);
        
        chatMessage = (EditText)findViewById(R.id.chat_message);
        
        connectBtn = (Button)findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new View.OnClickListener(){		
  		  @Override
  		  public void onClick(View v) {
  			
  			  connThread =   					  
  					  new ConnectionThread("192.168.201.119", 10000);
  			  connThread.start();
  			  stopBtn.setEnabled(true);
  			  sendBtn.setEnabled(true);
  			  connectBtn.setEnabled(false);
  		  }
  		});
        sendBtn = (Button)findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener(){		
  		  @Override
  		  public void onClick(View v) {
              String message = chatMessage.getText().toString();
              if(message != null && message.length() > 0){
            	  connThread.sendChatMessage(message);
                  chatMessage.setText("");
              }else{
            	  Toast.makeText(getApplicationContext(), "메세지를 입력하세요",
            			     Toast.LENGTH_SHORT).show();       	  
              }
              chatMessage.requestFocus();
  		  }
  		});
        stopBtn = (Button)findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener(){		
			@Override
			public void onClick(View v) {
	           if( connThread != null && connThread.isAlive()){
	        	   connThread.interrupt();
	        	   connThread.releaseThread();
	           }
	           connectBtn.setEnabled(true);
	           sendBtn.setEnabled(false);
	           stopBtn.setEnabled(false);
			}
		});
        stopBtn.setEnabled(false);
        sendBtn.setEnabled(false);   
    }
	private class ConnectionThread extends Thread{
		private DataInputStream fromServer;
		private DataOutputStream toServer;
		private Socket socket;
		private String chatName;		

		private String ipAddress;
		private int portNumber;		
	    public ConnectionThread(String ipAddress, int portNumber){
	    	  chatName = "맥스:";
	    	 this.ipAddress = ipAddress;
	    	 this.portNumber = portNumber;   	
	    }
	    public void run(){
	      try{
	    	     socket = new Socket(ipAddress, portNumber);
		    	 socket.setTcpNoDelay(true);
		    	 fromServer = new DataInputStream(socket.getInputStream());
		         toServer = new DataOutputStream(socket.getOutputStream());
		         toServer.writeUTF(chatName + " 입장했네요!");
		         toServer.flush();
	    	while(!isInterrupted()){
	    		final String chatMessage = fromServer.readUTF();
	    		messageHandler.post(new Runnable(){
	    		  public void run(){
	    			  messageItems.add(chatMessage);
	    		  }
	    		});
	    	}
	      }catch(IOException ioe){
	    	  handleException(ioe);
	      }finally{
	    	  releaseThread();
	      }
	    }
	    public void sendChatMessage(String message){
	      try{
	    	toServer.writeUTF(chatName + message);
	    	toServer.flush();
	      }catch(IOException ioe){
	    	Log.e(CHAT_LOG," 메세지 보낼 때 문제발생!" + ioe.toString());
	      }
	    }
	    private synchronized void handleException(IOException ioe){
	    	if (this != Thread.currentThread()){
	            this.interrupt ();
	            Log.e(CHAT_LOG, " run() 실행중 문제발생! " + ioe.toString());
	            releaseThread();
	    	}
	    }
	    public synchronized void releaseThread(){
	    	 if( fromServer != null){
	    		  try{
	    			  fromServer.close();
	    		  }catch(IOException e){}
	    	  }
	    	  if( toServer != null){
	    		  try{
	    			  toServer.close();
	    		  }catch(IOException e){}
	    	  }
	    	  if( socket != null){
	    		  try{
	    			  socket.close();
	    		  }catch(IOException e){}
	    	  }
	    }
	}
	public void onPause(){
		super.onPause();
		if( connThread != null && connThread.isAlive()){
			connThread.interrupt();
			connThread.releaseThread();
		}
	}
}