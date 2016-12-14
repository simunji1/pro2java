package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player {
	public static final int WIDTH = 55;
	public static final int HEIGHT = 55;
	private static final int COEF_ACCELERATION = 1;
	private static final int COEF_SPEED = 2;
	private BufferedImage img = null;
	private int x;
	private int y;
	private int speed;
	
	public Player(BufferedImage img) {
		this.img = img;
		y = GameSurface.HEIGHT / 2 - HEIGHT / 2;
		x = GameSurface.WIDTH / 3 - WIDTH / 2;
		speed = COEF_SPEED;
	}
	
	public void reset() {
		y = GameSurface.HEIGHT / 2 - HEIGHT / 2;
		speed = COEF_SPEED;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void kick() {
		speed = -17;
	}
	
	public void move() {
		speed += COEF_ACCELERATION;
		y += speed;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
		
		if (GameSurface.DEBUG) {
			g.setColor(Color.RED);
			g.drawString("[x=" + x + ", y=" + y + ", speed=" + speed + "]", x, y - 5);
		}
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
}