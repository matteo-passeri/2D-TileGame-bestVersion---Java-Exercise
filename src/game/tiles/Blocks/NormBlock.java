package game.tiles.Blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.utils.AABB;
import game.utils.Vector2f;

public class NormBlock extends Block{

	public NormBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
	}

	@Override
	public boolean update(AABB p) { return false; }
	public boolean isInside(AABB p) { return false; }

	public void render(Graphics2D g) { super.render(g); }

}
