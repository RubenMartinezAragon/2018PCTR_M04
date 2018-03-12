
public class hiloPintor implements Runnable{

	private final Board board;
	
	public hiloPintor(Board b ) {
		board=b;
		
	}
	@Override
	public void run() {
		while(true) {
			try {
				board.repaint();
				Thread.sleep(10);//mejor no pasar de 10
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
		
	}

}
