/*
 * ������ ä�� ����
 * author PYO IN SOO
 */

import java.io.*;
import java.net.*;

public class ChatServer {
    private static boolean serverReady = false;
	public static void main (String args[]) throws IOException {
    ServerSocket server = new ServerSocket (10001);
    System.out.println(" ä�� ���� �غ� �Ϸ�!");
    while (!serverReady ) {
      Socket client = server.accept ();
      System.out.println ("���ӵ� ��ġ��  " + client.getInetAddress ());
      ChatClientRequestHandler clientHandler = 
    		    new ChatClientRequestHandler(client);
      clientHandler.clientRequestInitStart();
    }
  }
}
