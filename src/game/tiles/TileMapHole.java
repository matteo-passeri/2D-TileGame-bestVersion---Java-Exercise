package game.tiles;

import java.awt.Graphics2D;

import game.gfx.Sprite;
import game.tiles.Blocks.Block;
import game.tiles.Blocks.HoleBlock;
import game.utils.AABB;
import game.utils.Vector2f;

public class TileMapHole extends TileMap {

	public static Block[] event_blocks;

	private int tileWidth;
	private int tileHeight;

	public static int width;
	public static int height;

	public TileMapHole(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight,
			int tileColumns) {
		Block tempBlock;
		event_blocks = new Block[width * height];

		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;

		TileMapHole.width = width;
		TileMapHole.height = height;

		String[] block = data.split(",");
		for (int i = 0; i < (width * height); i++) {
			int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));

			if (temp != 0) {
				tempBlock = new HoleBlock(
						sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)),
						new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth,
						tileHeight);

				event_blocks[i] = tempBlock;
			}
		}
	}

	public void render(Graphics2D g, AABB cam) {
		int x = (int) ((cam.getPos().getCamVar().x) / tileWidth);
		int y = (int) ((cam.getPos().getCamVar().y) / tileHeight);

		for (int i = x; i < x + (cam.getWidth() / tileWidth); i++) {
			for (int j = y; j < y + (cam.getHeight() / tileHeight); j++) {
				
				if (event_blocks[i + (j * height)] != null) {
					event_blocks[i + (j * height)].render(g);
				}

			}
		}
	}

}