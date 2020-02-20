package game.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.utils.Vector2f;

public class Sprite {

	private final BufferedImage SPRITE_SHEET;
	private BufferedImage[][] spriteArray;
	private final int TILE_SIZE = 32;
	public int w;
	public int h;
	private int wSprite;
	private int hSprite;
	
	public static Font currentFont;

	public Sprite(String file) {
		w = TILE_SIZE;
		h = TILE_SIZE;

		System.out.println("Loading: " + file + "...");
		SPRITE_SHEET = loadSprite(file);

		wSprite = SPRITE_SHEET.getWidth() / w;
		hSprite = SPRITE_SHEET.getHeight() / h;
		loadSpriteArray();
	}

	public Sprite(String file, int w, int h) {
		this.w = w;
		this.h = h;

		System.out.println("Loading: " + file + "...");
		SPRITE_SHEET = loadSprite(file);

		wSprite = SPRITE_SHEET.getWidth() / w;
		hSprite = SPRITE_SHEET.getHeight() / h;
		loadSpriteArray();

	}

	public void setSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	public void setWidth(int i) {
		w = i;
		wSprite = SPRITE_SHEET.getWidth() / w;
	}

	public void setHeight(int i) {
		h = i;
		hSprite = SPRITE_SHEET.getHeight() / h;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	private BufferedImage loadSprite(String file) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getSystemResourceAsStream(file));
		} catch (Exception e) {
			System.out.println("ERROR: could not load file:" + file);
		}
		return sprite;
	}

	public void loadSpriteArray() {
		spriteArray = new BufferedImage[hSprite][wSprite];

		for (int y = 0; y < hSprite; y++) {
			for (int x = 0; x < wSprite; x++) {
				spriteArray[y][x] = getSprite(x, y);
			}
		}
	}

	public int getSpriteSheetWidth() {
		return SPRITE_SHEET.getWidth();
	}

	public int getSpriteSheetHeight() {
		return SPRITE_SHEET.getHeight();
	}

	public BufferedImage getSpriteSheet() {
		return SPRITE_SHEET;
	}

	public BufferedImage getSprite(int x, int y) {
		return SPRITE_SHEET.getSubimage(x * w, y * h, w, h);
	}

	public BufferedImage[] getSpriteArray(int i) {
		return spriteArray[i];
	}

	public BufferedImage[][] getSpriteArray2(int i) {
		return spriteArray;
	}

	public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int width, int height,
			int xOffset, int yOffset) {
		float x = pos.x;
		float y = pos.y;

		for (int i = 0; i < img.size(); i++) {
			if (img.get(i) != null) {
				g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
			}

			x += xOffset;
			y += yOffset;
		}
	}

	public static void drawArray(Graphics2D g, String word, Vector2f pos, int size) {
		drawArray(g, currentFont, word, pos, size, size, size, 0);
		
	}

	public static void drawArray(Graphics2D g, String word, Vector2f pos, int size, int xOffset) {
		drawArray(g, currentFont, word, pos, size, size, xOffset, 0);
		
	}

	public static void drawArray(Graphics2D g, String word, Vector2f pos, int width, int height, int xOffset) {
		drawArray(g, currentFont, word, pos, width, height, xOffset, 0);
		
	}
	
	public static void drawArray(Graphics2D g, Font font, String word, Vector2f pos, int size, int xOffset) {
		drawArray(g, currentFont, word, pos, size, size, xOffset, 0);
		
	}

	public static void drawArray(Graphics2D g, Font font, String word, Vector2f pos, int width, int height, int xOffset,
			int yOffset) {
		float x = pos.x;
		float y = pos.y;
		
		currentFont = font;

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != 32) {
				g.drawImage(font.getFont(word.charAt(i)), (int) x + (32 * i), (int) y, (int) (width), (int) (height),
						null);

			}

		}

	}

}
