import java.io.*;
import java.net.*;
class ThreadEchoServer{
	public static void main( String[] args ) throws IOException{
        
	ServerSocket serverSock = new ServerSocket(10000);
        System.out.println(" Server Socket Start !");
        while(true){		
            Socket clientSocket = serverSock.accept();
		    new AndroidConnection(clientSocket);
        }
    }
    private static class AndroidConnection extends Thread{
        private Socket clientSocket;
        private InputStream fromClient;
        private OutputStream toClient;
		private int bufSize = 256;
        public AndroidConnection( Socket clientSocket ) throws IOException{
            System.out.println(clientSocket + ": 연결됨! ");
            this.clientSocket = clientSocket;
			fromClient = clientSocket.getInputStream();
			toClient   = clientSocket.getOutputStream();
			this.start();
        }
        public void run(){
            try{
            	byte [] buf = new byte[bufSize];
				int data = 0 ;
                while((data = fromClient.read(buf)) != -1){
                   toClient.write(buf,0,data);
				   toClient.flush();
				   System.out.println(clientSocket.getInetAddress().toString());
				 //  System.out.print(" Android Message => ");
				  // System.out.write(buf,0,data);
				   System.out.flush();
				   
                }
                System.out.println(clientSocket + ": 정상 연결 종료");
            }catch(IOException ex ){
                System.err.println(clientSocket + ": 연결 종료 (" + ex + ")");
            }finally{
            	if( fromClient != null){
            		try{
            			fromClient.close();
            		}catch(IOException ioe){}
            	}
            	if( toClient != null){
            		try{
            			toClient.close();
            		}catch(IOException ioe){}
            	}
                try{
                   clientSocket.close();
                }catch(IOException ex ) {}
            }
        }
    }
}