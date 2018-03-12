import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop,b_rei;

	private Board board;
	
	private Thread[] hiloballs;
	private Thread hilopintor;
	private String estado="Iniciado";//evitar sobrecarga de hilos

	// TODO update with number of group label. See practice statement. HECHO
	private final int N_BALL = 4;
	private Ball[] balls;

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());
		b_rei = new JButton("Reiniciar");
		b_rei.addActionListener(new ReiListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);
		p_Botton.add(b_rei);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}

	private void initBalls() {
		// TODO init balls HECHO
		balls=new Ball[N_BALL+3];
		for(int i=0;i<balls.length;i++) {
			balls[i]=new Ball();
			
			
		}
		board.setBalls(balls);
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when start button is pushed HECHO
			if(estado.equals("Parado")||estado.equals("Iniciado")) {
				hilopintor= new Thread(new hiloPintor(board));
				hilopintor.start();
				hiloballs= new Thread[N_BALL+3];
				for(int i=0;i<balls.length;i++) {
					hiloballs[i]=new Thread(new hiloBall(balls[i]));
					hiloballs[i].start();
				}
				estado="Movimiento";
			}

		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when stop button is pushed HECHO
			if( estado.equals("Movimiento")) {
				hilopintor.interrupt();
				for(int i=0;i<balls.length;i++) {
					hiloballs[i].interrupt();
					System.out.println(hiloballs[i].getState());
				}
				estado="Parado";
			}

		}
	}
	
	private class ReiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if( !estado.equals("Iniciado")) {
				initBalls();
				hilopintor.interrupt();
				board.setBalls(balls);
				hilopintor= new Thread(new hiloPintor(board));
				hilopintor.start();
				for(int i=0;i<balls.length;i++) {
					hiloballs[i].interrupt();//interrumpe el anterior
					hiloballs[i]=new Thread(new hiloBall(balls[i]));//crea uno nuevo
					hiloballs[i].start();
				}
				//System.out.println(Thread.currentThread());
				estado="Movimiento";
			}
		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}