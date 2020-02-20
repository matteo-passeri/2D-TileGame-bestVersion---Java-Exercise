package game.utils;

import game.entities.Entity;
import game.tiles.TileManager;
import game.tiles.TileMapHole;
import game.tiles.TileMapObj;
import game.tiles.Blocks.Block;
import game.tiles.Blocks.HoleBlock;

public class TileCollision {
	private Entity e;
	private Block block;

	public TileCollision(Entity e) {
		this.e = e;
	}

	public boolean collisionTile(float ax, float ay) {
		for (int c = 0; c < 4; c++) {

			int xt = (int) ((e.getBounds().getPos().x + ax) + (c % 2) * e.getBounds().getWidth()
					+ e.getBounds().getXOffset()) / TileManager.TILE_SIZE;
			int yt = (int) ((e.getBounds().getPos().y + ay) + ((int) (c / 2)) * e.getBounds().getHeight()
					+ e.getBounds().getYOffset()) / TileManager.TILE_SIZE;

			if (TileMapObj.event_blocks[xt + (yt * TileMapObj.height)] instanceof Block) {

				Block block = TileMapObj.event_blocks[xt + (yt * TileMapObj.height)];

				return block.update(e.getBounds());
			}
			if (TileMapHole.event_blocks[xt + (yt * TileMapHole.height)] instanceof HoleBlock) {
				
				Block block = TileMapHole.event_blocks[xt + (yt * TileMapHole.height)];
				
				if (block instanceof HoleBlock) {
					return collisionHole(ax, ay, xt, yt, block);
				}
				return block.update(e.getBounds());
			}
		}
		return false;
	}

	private boolean collisionHole(float ax, float ay, float xt, float yt, Block block) {
		int nextXt = (int) ((((e.getBounds().getPos().x + ax) + e.getBounds().getXOffset()) / 64)
				+ e.getBounds().getWidth() / 64);
		int nextYt = (int) ((((e.getBounds().getPos().y + ay) + e.getBounds().getYOffset()) / 64)
				+ e.getBounds().getHeight() / 64);

		if (block.isInside(e.getBounds())) {
			e.setFallen(true);
			return false;
		} else if ((nextXt == yt + 1) || (nextXt == xt + 1) || (nextXt == yt - 1) || (nextXt == xt - 1)) {
			if (TileMapHole.event_blocks[nextXt + (nextYt * TileMapHole.height)] instanceof HoleBlock) {
				if (e.getBounds().getPos().x > block.getPos().x) {
					e.setFallen(true);
				}
				return false;
			}
		}
		return false;
	}
}
