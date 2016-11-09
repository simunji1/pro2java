package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileImage extends ImageSource {
	private static final String FILEPATH = "img/";
	
	@Override
	public void fillMap() {
		getMap().put(Image.BACKGROUND.getKey(), "background.png");		
	}

	@Override
	public BufferedImage getImage() throws IOException {
		// TODO Auto-generated method stub
		return ImageIO.read(new File(FILEPATH + getSource()));
	}

}
