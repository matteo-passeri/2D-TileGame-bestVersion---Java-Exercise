package game.utils;

import java.awt.Color;
import java.awt.Graphics;

import game.GamePanel;
import game.entities.Entity;
import game.states.PlayState;

public class Camera {

	private AABB collisionCam;
	private AABB bounds;

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	private float dx;
	private float dy;
	private float maxSpeed = 4f;
	private float acc = 1f;
	private float deacc = 0.3f;

	private int widthLimit;
	private int heightLimit;

	private Entity e;

	public Camera(AABB collisionCam) {
		this.collisionCam = collisionCam;
		float x = collisionCam.getPos().x;
		float y = collisionCam.getPos().y;
				
		this.bounds = new AABB(new Vector2f(x, y), (int) collisionCam.getWidth(), (int) collisionCam.getHeight());
	}

	public void setLimit(int widthLimit, int heightLimit) {
		this.widthLimit = widthLimit;
		this.heightLimit = heightLimit;
	}

	public AABB getBounds() {
		return collisionCam;
	}
	
	public AABB getBoundsOnScreen() {
		return bounds;
	}

	public void target(Entity e) {
		this.e = e;
		deacc = e.getDeacc();
		maxSpeed = e.getMaxSpeed();
	}

	public void update() {
		move();
		if (!e.xCol) {
			if ((e.getBounds().getPos().getWorldVar().x + e.getDx()) < Vector2f
					.getWorldVarX(widthLimit - collisionCam.getWidth() / 2) - 64
					&& (e.getBounds().getPos().getWorldVar().x + e.getDx()) > Vector2f
							.getWorldVarX(GamePanel.width / 2 - 64)) {
				PlayState.map.x += dx;
				bounds.getPos().x += dx;
			}
		}
		if (!e.yCol) {
			if ((e.getBounds().getPos().getWorldVar().y + e.getDy()) < Vector2f
					.getWorldVarY(heightLimit - collisionCam.getHeight() / 2) - 64
					&& (e.getBounds().getPos().getWorldVar().y + e.getDy()) > Vector2f
							.getWorldVarY(GamePanel.height / 2 - 64)) {
				PlayState.map.y += dy;
				bounds.getPos().y += dy;

			}
		}

	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect((int) collisionCam.getPos().x, (int) collisionCam.getPos().y, (int) collisionCam.getWidth(),
				(int) collisionCam.getHeight());
		
		g.setColor(Color.MAGENTA);
		g.drawLine(GamePanel.width / 2, 0, GamePanel.width / 2, GamePanel.height);
		g.drawLine(0, GamePanel.height / 2, GamePanel.width, GamePanel.height / 2);

	}

	public void move() {
		if (up) {
			dy -= acc;
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
		if (down) {
			dy += acc;
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
		if (left) {
			dx -= acc;
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
		if (right) {
			dx += acc;
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
	}

	public void input(MouseManager mouse, KeyManager key) {
		if (e == null) {
			if (key.up.down) {

				up = true;
			} else {
				up = false;
			}
			if (key.down.down) {
				down = true;

			} else {
				down = false;
			}
			if (key.left.down) {

				left = true;
			} else {
				left = false;
			}
			if (key.right.down) {

				right = true;
			} else {
				right = false;
			}
		} else {
			if (!e.yCol) {
				if (PlayState.map.y + GamePanel.height / 2 - e.getSize() / 2 + dy > e.getBounds().getPos().y + e.getDy() + 2) {
					up = true;
					down = false;
				} else if (PlayState.map.y + GamePanel.height / 2 - e.getSize() / 2 + dy < e.getBounds().getPos().y + e.getDy() - 2) {
					down = true;
					up = false;
				} else {
					dy = 0;
					up = false;
					down = false;
				}
			}

			if (!e.xCol) {
				if (PlayState.map.x + GamePanel.width / 2 - e.getSize() / 2 + dx > e.getBounds().getPos().x + e.getDx() + 2) {
					left = true;
					right = false;
				} else if (PlayState.map.x + GamePanel.width / 2 - e.getSize() / 2 + dx < e.getBounds().getPos().x + e.getDx() - 2) {
					right = true;
					left = false;
				} else {
					dx = 0;
					left = false;
					right = false;
				}
			}

		}

	}


}
