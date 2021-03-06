package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Wall {

	public static final int WIDTH = 65;
	public static final int SPEED = -6;
	public static final int GAP = 200;
	public static final int POINTS_FOR_WALL = 1;
	private Random random;
	
	//TODO
	
	private static BufferedImage img = null;
	private static int lastWallPosition;
	
	private int x;
	private int y;
	private int height;
	
	public Wall(int startingPosition) {
		this.x = startingPosition;
		
		random = new Random();
		generateGap();
	}

	private void generateGap() {
		y = random.nextInt(GameSurface.HEIGHT-400) + GAP;
		height = GameSurface.HEIGHT - y;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
		g.drawImage(img, x, y - (GAP + GameSurface.HEIGHT), null);
		
		if (GameSurface.DEBUG) {
			g.setColor(Color.RED);
			g.drawString("[x=" + x + ", y=" + y + ", height=" + height + "]", x, y - 5);
		}
	}
	
	public void move() {
		x += SPEED;
		if (x <= -Wall.WIDTH) {
			x = Wall.lastWallPosition;
			// TODO
		}
	}

	public static int getLastWallPosition() {
		return lastWallPosition;
	}

	public static void setLastWallPosition(int lastWallPosition) {
		Wall.lastWallPosition = lastWallPosition;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public static void setImg(BufferedImage img) {
		Wall.img = img;
	}
	
	public Rectangle getLowerRect() {
		return new Rectangle(x, y, WIDTH, height);
	}
	
	public Rectangle getUpperRect() {
		return new Rectangle(x, 0, WIDTH, GameSurface.HEIGHT - height - GAP);
	}
	
	public void reset() {
		generateGap();
	}
}
