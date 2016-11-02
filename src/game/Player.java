package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player {
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	private static final int COEF_ACCELERATION = 1;
	private static final int COEF_SPEED = 2;
	private BufferedImage img = null;
	private int x;
	private int y;
	private int speed;
	
	public Player(BufferedImage img) {
		this.img = img;
		y = GameSurface.HEIGHT / 2 - this.HEIGHT / 2;
		x = GameSurface.WIDTH / 3 - this.WIDTH / 2;
		speed = COEF_SPEED;
	}
	
	public void reset() {
		y = GameSurface.HEIGHT / 2 - this.HEIGHT / 2;
		speed = COEF_SPEED;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void kick() {
		speed = -20;
	}
	
	public void move() {
		speed += COEF_ACCELERATION;
		y += speed;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
}