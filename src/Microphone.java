

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Microphone {
	TargetDataLine line;
	AudioFormat format;
	private String errorMessage;
	public boolean stopped;
	private String time;
	SocketClient socketClient;
	public Microphone() {
		// TODO Auto-generated constructor stub
		format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				16000.0F, 16, 2, 4, 16000.0F, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine
				.class, format); // format is an AudioFormat object
		
		if (!AudioSystem.isLineSupported(info)) {
		    // Handle the error ... 
			errorMessage = "System not supports specified Line.Info object.";
		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    line.open(format);
		} catch (LineUnavailableException ex) {
		    // Handle the error ... 
			errorMessage=ex.toString();
		}
		

		stopped=true;
	    //新增資料夾
	    String filePath = "C:\\asd";
	    File folder = new File(filePath);
	    if(!folder.exists()){
	    	folder.mkdirs();
	    }
	    
	    socketClient = new SocketClient();
	}
	
	public void capture(String IP)
	{
		//  準備輸出的格式，如：星期四 2009/01/01
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		//  利用 DateFormat 來parse 日期的字串
		Calendar calendar = Calendar.getInstance();
	    time=sdf.format(calendar.getTime());
	    
		stopped=false;
		// Assume that the TargetDataLine, line, has already
		// been obtained and opened.
		ByteArrayOutputStream out  = new ByteArrayOutputStream();
		
		int numBytesRead;
		byte[] data = new byte[line.getBufferSize() / 5];

		// Begin audio capture.
		line.start();

		
		
		// Here, stopped is a global boolean set by another thread.
		while (!stopped) {
		   // Read the next chunk of data from the TargetDataLine.
		   numBytesRead =  line.read(data, 0, data.length);
		   // Save this chunk of data.
		   out.write(data, 0, numBytesRead);
		   
		   socketClient.sendRecord(data,numBytesRead);
		}     
		
		String filePath = "C:\\asd\\"+time+"_to_"+IP+".pcm";

		try {
			OutputStream outputStream = new FileOutputStream (filePath); 
			out.writeTo(outputStream);
			out.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		line.drain();
		return;
	}
	public void stop()
	{
		stopped=true;
	}
}
