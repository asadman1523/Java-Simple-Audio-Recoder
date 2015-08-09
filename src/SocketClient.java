import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.IOException;
 
public class SocketClient {
    private String address = "192.168.1.18";// 連線的ip
    private int port = 3702;// 連線的port
    Socket client;
    InetSocketAddress isa;
    BufferedOutputStream out;
    public SocketClient() {
 
        client = new Socket();
        isa = new InetSocketAddress(this.address, this.port);
        try {
			client.connect(isa, 10000);
	         out = new BufferedOutputStream(client
	                 .getOutputStream());
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public void sendRecord(byte[] bytes,int length)
    {
    	if( client.isConnected()){
            try {
                // 送出字串
                //out.write("Send From Client ".getBytes());
                out.write(bytes,0,length);
                out.flush();
     
            } catch (java.io.IOException e) {
                System.out.println("Socket連線有問題 !");
                System.out.println("IOException :" + e.toString());
            }
    	}

    }
    
    public void close()
    {

        try {
			client.close();
			 client = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
}