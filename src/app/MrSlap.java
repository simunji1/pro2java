package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.GameSurface;

public class MrSlap extends JFrame {

	public MrSlap() {
		GameSurface gs = new GameSurface();
		getContentPane().add(gs, "Center");
		
		pack();
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
