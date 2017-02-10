/*
 * 간단한 채팅 서버
 * author PYO IN SOO
 */

import java.io.*;
import java.net.*;

public class ChatServer {
    private static boolean serverReady = false;
	public static void main (String args[]) throws IOException {
    ServerSocket server = new ServerSocket (10001);
    System.out.println(" 채팅 서버 준비 완료!");
    while (!serverReady ) {
      Socket client = server.accept ();
      System.out.println ("접속된 위치는  " + client.getInetAddress ());
      ChatClientRequestHandler clientHandler = 
    		    new ChatClientRequestHandler(client);
      clientHandler.clientRequestInitStart();
    }
  }
}
