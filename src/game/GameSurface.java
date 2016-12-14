package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import image.FileImage;
import image.Image;
import image.ImageManager;

public class GameSurface extends JPanel {
	public static final boolean DEBUG = false;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static final int SPEED = -4;
	public static final int WALLS_COUNT = 4;
	
	private Player player;
	private BufferedImage backgroundImage;
	private Timer animationTimer;
	private boolean paused = false;
	private boolean gameRunning = false;
	private int backgroundPositionX = 0;
	private ListWalls listWalls;
	private Wall actualWall;
	private Wall previousWall;
	private int score = 0;
	private JLabel lbScore;
	private JLabel lbMessage;
	private Font font;
	private Font messageFont;
	
	public GameSurface(ImageManager im) {
		backgroundImage = im.getImage(Image.BACKGROUND);
		player = new Player(im.getImage(Image.PLAYER));
		Wall.setImg(im.getImage(Image.WALL));
				
		listWalls = new ListWalls();
		makeLabels();
	}
	
	private void makeLabels() {
		font = new Font("Courier New", Font.BOLD, 40);
		messageFont = new Font("Courier New", Font.BOLD, 20);
		this.setLayout(new BorderLayout());
		
		lbMessage = new JLabel();
		lbMessage.setFont(messageFont);
		lbMessage.setForeground(Color.RED);
		lbMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		lbScore = new JLabel("0");
		lbScore.setFont(font);
		lbScore.setForeground(Color.BLUE);
		lbScore.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.add(lbScore, BorderLayout.NORTH);
		this.add(lbMessage, BorderLayout.CENTER);
	}
	
	private void makeWalls() {
		Wall wall;
		int position = GameSurface.WIDTH;
		for (int i = 0; i < WALLS_COUNT; i++) {
			wall = new Wall(position);
			listWalls.add(wall);
			position = position + (GameSurface.WIDTH / 2);
		}
		position = position - GameSurface.WIDTH - Wall.WIDTH;
		Wall.setLastWallPosition(position);
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
		
		for (Wall wall : listWalls) {
			wall.paint(g);
		}
		
		player.paint(g);
		
		lbScore.paint(g);
		lbMessage.paint(g);
	}
	
	private void move() {
		if (!paused && gameRunning) {
			actualWall = listWalls.getActualWall();
			previousWall = listWalls.getPreviousWall();
			
			if (isWallCollision(previousWall, player) || 
					isWallCollision(actualWall, player) ||
					isGameSurfaceCollision(player)) {
				endGameReset();
			} else {
				for (Wall wall : listWalls) {
					wall.move();
				}
				
				player.move();
				
				if (player.getX() >= actualWall.getX()) {
					listWalls.nextWall();
					incrementScoreWall();
					lbScore.setText(score + "");
				}

				backgroundPositionX += GameSurface.SPEED;
				if (backgroundPositionX == -backgroundImage.getWidth()) {
					backgroundPositionX = 0;
				}
			}
		}
	}
	
	private void endGameReset() {
		gameRunning = false;
		animationTimer.stop();
		animationTimer = null;
		resetGame();
		setMessageWallHit();
	}

	private boolean isWallCollision(Wall wall, Player player) {
		return wall.getLowerRect().intersects(player.getRect()) || wall.getUpperRect().intersects(player.getRect());
	}
	
	private boolean isGameSurfaceCollision(Player player) {
		return player.getY() < 0 || player.getY() >= GameSurface.HEIGHT; // - vyska hrace ??
	}
	
	private void startGame() {
		animationTimer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				move();
			}
		});
		setMessageEmpty();
		gameRunning = true;
		animationTimer.start();
	}
	
	public void prepareGameSurface() {
		setMessageControls();
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
							setMessageEmpty();
						} else {
							setMessagePaused();
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
		resetGame();
	}
	
	private void resetGame() {
		resetWalls();
		player.reset();
		lbScore.setText(score + "");
		resetScore();
	}

	private void resetScore() {
		score = 0;		
	}
	
	private void incrementScoreWall() {
		score += Wall.POINTS_FOR_WALL;
	}

	private void resetWalls() {
		listWalls.clear();
		makeWalls();
	}
	
	private void setMessageWallHit() {
		lbMessage.setFont(font);
		lbMessage.setText("Loser!");
	}
	
	private void setMessagePaused() {
		lbMessage.setFont(font);
		lbMessage.setText("PAUZA");
	}
	
	private void setMessageControls() {
		lbMessage.setFont(messageFont);
		lbMessage.setText("levé tl. myši = skok, právé tl. myši = pauza");
	}
	
	private void setMessageEmpty() {
		lbMessage.setText("");
	}
}
