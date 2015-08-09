import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.IOException;
 
public class SocketClient {
    private String address = "192.168.1.18";// �s�u��ip
    private int port = 3702;// �s�u��port
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
                // �e�X�r��
                //out.write("Send From Client ".getBytes());
                out.write(bytes,0,length);
                out.flush();
     
            } catch (java.io.IOException e) {
                System.out.println("Socket�s�u�����D !");
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