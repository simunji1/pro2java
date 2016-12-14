package image;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class URLImage extends ImageSource {
	private static final String FILEPATH = "imgurl.csv";
	private static final String SPLITTER = ";";

	@Override
	public void fillMap() {
		loadCSV();
		
	}

	private void loadCSV() {
		try (BufferedReader input = new BufferedReader(new FileReader(FILEPATH))) {
			String row;
			
			for (int i = 0; i < Image.getSize(); i++) {
				if ((row = input.readLine()) != null) {
					parseRow(row);
				}
			}
		} catch (IOException e) {
			System.out.println("Error while reading CSV file: " + e.getMessage());
		}
	}

	private void parseRow(String row) {
		StringTokenizer st = new StringTokenizer(row, SPLITTER);
		if (st.countTokens() == 2) {
			String key = st.nextToken();
			String link = st.nextToken();
			
			if (isKey(key)) {
				getMap().put(key, link);
			} else {
				System.out.println("Key " + key + " is not defined");
			}
		}
	}

	private boolean isKey(String key) {
		for (Image img : Image.getImages()) {
			if (img.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BufferedImage getImage() throws IOException {
		URL url = new URL(getSource());
		URLConnection urlConnection = url.openConnection();
		
		urlConnection.setReadTimeout(10000);
		InputStream is = urlConnection.getInputStream();
		BufferedImage img = ImageIO.read(is);
		is.close();
		
		return img;
	}

}
