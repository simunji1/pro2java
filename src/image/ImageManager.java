package image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
	private Map<String, BufferedImage> imgMap;
	private ImageSource source;
	
	public ImageManager(ImageSource source) {
		imgMap = new HashMap<>();
		this.source = source;
		this.source.fillMap();
	}

	public void getImagesReady() {
		getImageReady(Image.PLAYER);
		getImageReady(Image.BACKGROUND);
		getImageReady(Image.WALL);
	}
	
	private void getImageReady(Image img) {
		source.setSource(img.getKey());
		imgMap.put(img.getKey(), loadImage(img));
	}
	
	private BufferedImage loadImage(Image img) {
		BufferedImage image;
		try {
			image = source.getImage();
			if (image != null) {
				if (!isImageRight(image, img.getWidth(), img.getHeight())) {
					image = makeImageRight(image, img.getWidth(), img.getHeight());
				}
			} else {
				image = makeImage(img.getWidth(), img.getHeight(), img.getColor());
			}
		} catch(IOException e) {
			image = makeImage(img.getWidth(), img.getHeight(), img.getColor());
		}
		return image;
	}
	
	private BufferedImage makeImageRight(BufferedImage image, int width, int height) {
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return newImage;
	}

	private boolean isImageRight(BufferedImage image, int width, int height) {
		return image.getHeight() == height && image.getWidth() == width;
	}

	private BufferedImage makeImage(int width, int height, Color color) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.dispose();
		return newImage;
	}

	public BufferedImage getImage(Image img) {
		return imgMap.get(img.getKey());
	}
}
