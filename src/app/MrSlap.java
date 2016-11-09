package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.GameSurface;

public class MrSlap extends JFrame {

	private GameSurface gs;
	
	public MrSlap() {
		gs = new GameSurface();
		getContentPane().add(gs, "Center");
		
		pack();
	}
	
	public void initGUI() {
		setSize(GameSurface.WIDTH, GameSurface.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MrSlap");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void start() {
		gs = new GameSurface();
		getContentPane().add(gs, "Center");
		setVisible(true);
		this.revalidate();
		gs.repaint();
		gs.prepareGameSurface();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MrSlap app = new MrSlap();
				app.setVisible(true);
			}
		});
	}

}
