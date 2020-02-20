package game.gfx;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Font {

	private final BufferedImage FONT_SHEET;
	private BufferedImage[][] spriteArray;
	private final int TILE_SIZE = 32;
	public int w;
	public int h;
	private int wLetter;
	private int hLetter;

	public Font(String file) {
		w = TILE_SIZE;
		h = TILE_SIZE;

		System.out.println("Loading: " + file + "...");
		FONT_SHEET = loadFont(file);

		wLetter = FONT_SHEET.getWidth() / w;
		hLetter = FONT_SHEET.getHeight() / h;
		loadFontArray();
	}

	public Font(String file, int w, int h) {
		this.w = w;
		this.h = h;

		System.out.println("Loading: " + file + "...");
		FONT_SHEET = loadFont(file);

		wLetter = FONT_SHEET.getWidth() / w;
		hLetter = FONT_SHEET.getHeight() / h;
		loadFontArray();

	}

	public void setSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	public void setWidth(int i) {
		w = i;
		wLetter = FONT_SHEET.getWidth() / w;
	}

	public void setHeight(int i) {
		h = i;
		hLetter = FONT_SHEET.getHeight() / h;
	}

	public int getWidth() { return w; }
	public int getHeight() { return h; }

	private BufferedImage loadFont(String file) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getSystemResourceAsStream(file));
		} catch (Exception e) {
			System.out.println("ERROR: could not load file: " + file);
		}
		return sprite;
	}

	public void loadFontArray() {
		spriteArray = new BufferedImage[wLetter][hLetter];

		for (int x = 0; x < wLetter; x++) {
			for (int y = 0; y < hLetter; y++) {
				spriteArray[x][y] = getLetter(x, y);
			}
		}
	}

	public BufferedImage getFontSheet() { return FONT_SHEET; }

	public BufferedImage getLetter(int x, int y) { return FONT_SHEET.getSubimage(x * w, y * h, w, h); }
	
	public BufferedImage getFont(char letter) {
		int value = letter;
		
		int x = value % wLetter;
		int y = value / hLetter;
//		System.out.println(x + ", " + y);
		return getLetter(x, y);
	}


}
