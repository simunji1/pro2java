package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

import image.FileImage;
import image.Image;

public class GameSurface extends JPanel {
	public static final boolean DEBUG = true;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	
	public static final int SPEED = -2;
	
	private Player player;
	private BufferedImage backgroundImage;
	private Timer animationTimer;
	private boolean paused = false;
	private boolean gameRunning = false;
	private int backgroundPositionX = 0;
	
	public GameSurface() {
		//TODO
		FileImage source = new FileImage();
		source.fillMap();
		source.setSource(Image.BACKGROUND.getKey());
		
		try {
			backgroundImage = source.getImage();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		source.setSource(Image.PLAYER.getKey());
		BufferedImage playerImage;
		//player = new Player(null);
		try {
			playerImage = source.getImage();
			player = new Player(playerImage);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		//2x background
		//1.
		g.drawImage(backgroundImage, backgroundPositionX, 0, null);
		//2.
		g.drawImage(backgroundImage, backgroundPositionX + backgroundImage.getWidth(), 0, null);
		
		if (GameSurface.DEBUG) {
			g.setColor(Color.RED);
			g.drawString("[backgroundPositionX=" + backgroundPositionX + "]", 0, 10);
		}
		
		player.paint(g);
	}
	
	private void move() {
		if (!paused && gameRunning) {
			//TODO
			player.move();
			//background
			backgroundPositionX += GameSurface.SPEED;
			if (backgroundPositionX == -backgroundImage.getWidth()) {
				backgroundPositionX = 0;
			}
		}
	}
	
	private void startGame() {
		animationTimer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				move();
			}
		});
		gameRunning = true;
		animationTimer.start();
	}
	
	public void prepareGameSurface() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					//TODO jump
					player.kick();
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					//TODO pause
					if (gameRunning) {
						if (paused) {
							
						} else {
							
						}
						paused = !paused;
					} else {
						prepareNewGame();
						startGame();
					}
				}
			}
		});
		setSize(WIDTH, HEIGHT);
	}

	protected void prepareNewGame() {
		// TODO Auto-generated method stub
		
	}
}
