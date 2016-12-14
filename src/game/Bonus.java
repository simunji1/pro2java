package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bonus {
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	public static final int VALUE = 5;
	private Random random;
	private BufferedImage img;
	private ListWalls listWalls;
	private int x;
	private int y;
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
	
	public void move() {
		x += Wall.SPEED;
	}
}
