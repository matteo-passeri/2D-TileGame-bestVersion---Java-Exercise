package game.tiles;

import java.awt.Graphics2D;

import game.utils.AABB;

public abstract class TileMap {
	
	public abstract void render(Graphics2D g, AABB cam);

}
