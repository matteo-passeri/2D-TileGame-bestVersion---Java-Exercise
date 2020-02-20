package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import game.gfx.Sprite;
import game.utils.AABB;
import game.utils.Camera;
import game.utils.Vector2f;

public class Enemy extends Entity {

	private AABB sense;
	private int r;

	private Camera cam;

	public Enemy(Camera cam, Sprite sprite, Vector2f orgin, int size) {
		super(sprite, orgin, size);
		this.cam = cam;

		acc = 0.5f;
		maxSpeed = 2f;
		r = 135;

		bounds.setWidth(42);
		bounds.setHeight(22);
		bounds.setXOffset(20);
		bounds.setYOffset(63);

		sense = new AABB(new Vector2f(orgin.x - (bounds.getWidth() * 2) - ((bounds.getWidth() / 2) / 2),
				orgin.y - (bounds.getHeight() * 2) - (bounds.getHeight())), r * 2, this);

	}

	@Override
	protected void die() {
		up = false;
		down = false;
		left = false;
		right = false;
		attack = false;
		dead = true;
		if (ani.hasPlayedOnce()) {
			// TO CHANGE
			pos.x = -10;
			pos.y = -10;
			active = false;
			
			
			System.out.println("Im dead, cause var dead is " + dead);
		} 

	}

	public void update(Player player) {
		if (!active) { return; }
		
		if (cam.getBoundsOnScreen().collides(this.bounds)) {
			super.update();

			move(player);
			if (!fallen) {
				if (!tc.collisionTile(dx, 0)) {
					sense.getPos().x += dx;
					pos.x += dx;
				}
				if (!tc.collisionTile(0, dy)) {
					sense.getPos().y += dy;
					pos.y += dy;
				}
			} else {
				die();
			}
		}

	}

	private void move(Player player) {

		if (!dead) {
			if (bounds.collides(player.getBounds()) || attack) {
				up = false;
				down = false;
				left = false;
				right = false;
				attack = true;
				dx = 0;
				dy = 0;
				if (ani.hasPlayedOnce()) {
					if (bounds.collides(player.getBounds())) {
						System.out.println("ATTACK!!!!!!!!!!!!!!!!!!!!!!!!!");
//					hurt(player);
						attack = false;
					} else {
						attack = false;

					}
				}

			} else if (sense.colCircleBox(player.getBounds()) && (!bounds.collides(player.getBounds())) && !attack) {

				// up
				if (pos.y > player.pos.y - 7) {
					dy -= acc;
					left = false;
					right = false;
					down = false;
					up = true;
					if (dy < -maxSpeed) {
						dy = -maxSpeed;
					}
				} else {
					if (dy < 0) {
						dy += deacc;
						if (dy > 0) {
							dy = 0;
						}
					}
				}
				// down
				if (pos.y < player.pos.y - 45) {
					dy += acc;
					left = false;
					right = false;
					up = false;
					down = true;
					if (dy > maxSpeed) {
						dy = maxSpeed;
					}
				} else {
					if (dy > 0) {
						dy -= deacc;
						if (dy < 0) {
							dy = 0;
						}
					}
				}
				// left
				if (pos.x > player.pos.x + 16) {
					dx -= acc;
					up = false;
					down = false;
					right = false;
					left = true;
					if (dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				} else {
					if (dx < 0) {
						dx += deacc;
						if (dx > 0) {
							dx = 0;
						}
					}
				}
				// right
				if (pos.x < player.pos.x - 32) {
					dx += acc;
					up = false;
					down = false;
					left = false;
					right = true;
					if (dx > maxSpeed) {
						dx = maxSpeed;
					}
				} else {
					if (dx > 0) {
						dx -= deacc;
						if (dx < 0) {
							dx = 0;
						}
					}
				}
			} else {
				if (!attack) {
					up = false;
					down = false;
					left = false;
					right = false;
					attack = false;

					dx = 0;
					dy = 0;
				}
			}
		}
	}


	@Override
	public void render(Graphics2D g) {
		if (!active) { return; }
		if (cam.getBoundsOnScreen().collides(this.bounds)) {

			g.setColor(Color.GREEN);
			g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()),
					(int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(),
					(int) bounds.getHeight());

			g.setColor(Color.BLUE);
			g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r * 2, r * 2);

			g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
		}
	}

}
