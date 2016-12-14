package app;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import game.GameSurface;
import image.FileImage;
import image.ImageManager;

public class MrSlap extends JFrame {
	private ImageManager im;
	
	public MrSlap() {
		im = new ImageManager(new FileImage());
	}
	
	public void initGUI() {
		setSize(GameSurface.WIDTH, GameSurface.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MrSlap");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void start() {
		class Thread extends SwingWorker<Object, Object> {
			private JFrame owner;
			private JLabel label;
			private GameSurface gs;
			
			public void setOwner(JFrame owner) {
				this.owner = owner;
			}
			
			public void doBaforeExecute() {
				label = new JLabel("Naèítám...");
				label.setFont(new Font("Courier New", Font.BOLD, 40));
				label.setForeground(Color.BLUE);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				owner.add(label);
				label.setVisible(true);
				owner.revalidate();
				owner.repaint();
			}
			
			@Override
			protected Object doInBackground() throws Exception {
				im.getImagesReady();
				gs = new GameSurface(im);
				gs.prepareGameSurface();
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				owner.remove(label);
				owner.revalidate();
				owner.add(gs);
				gs.setVisible(true);
				owner.revalidate();
				owner.repaint();
			}
		}
		
		Thread thread = new Thread();
		thread.setOwner(this);
		thread.doBaforeExecute();
		thread.execute();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MrSlap app = new MrSlap();
				app.initGUI();
				app.start();
			}
		});
	}

}
