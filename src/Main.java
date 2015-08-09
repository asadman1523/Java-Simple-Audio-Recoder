
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Microphone microphone = new Microphone();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				microphone.capture("");
			}
		}).start();
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					//ten second record
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					microphone.stop();
				}
			}
		}).start();
		
	}

}
