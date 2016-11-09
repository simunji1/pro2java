package image;

import java.awt.Color;

import game.GameSurface;
import game.Player;

public enum Image {
	PLAYER("player", Player.WIDTH, Player.HEIGHT, new Color(0, 0, 255)),
	BACKGROUND("background", GameSurface.WIDTH * 2, GameSurface.HEIGHT, new Color(0, 255, 0));
	
	private static final int size = Image.values().length;
	
	private static final Image[] images = Image.values();
	
	private final String key;
	private final int width;
	private final int height;
	private final Color color;
	
	private Image(String key, int width, int height, Color color) {
		this.key = key;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public static int getSize() {
		return size;
	}

	public static Image[] getImages() {
		return images;
	}

	public String getKey() {
		return key;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}
}
