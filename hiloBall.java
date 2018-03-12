


public class hiloBall implements Runnable {
	
	private final Ball bola;
	
	public hiloBall(Ball bo) {
		bola=bo;
		
	}

	@Override
	public void run() {
		while(true) {
			System.out.println("muevo");
			bola.move();
			try {
					Thread.sleep(10);
			} catch (InterruptedException e) {
					break;
			}
		}
		
	}

}
