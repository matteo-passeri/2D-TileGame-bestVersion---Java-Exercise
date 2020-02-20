package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import game.GamePanel;
import game.gfx.Sprite;
import game.states.PlayState;
import game.utils.KeyManager;
import game.utils.MouseManager;
import game.utils.Vector2f;

public class Player extends Entity {
	
	private boolean alreadyAttacked = false;

	public Player(Sprite sprite, Vector2f orgin, int size) {
		super(sprite, orgin, size);
		maxSpeed = 3f;
		acc = 1.5f;
		bounds.setWidth(15);
		bounds.setHeight(15);
		bounds.setXOffset(30);
		bounds.setYOffset(40);

	}

	private void move() {
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

	private void resetPosition() {
		System.out.println("Resetting player...");
		pos.x = GamePanel.width / 2 - size;
		PlayState.map.x = 0;

		pos.y = GamePanel.height / 2 - size;
		PlayState.map.y = 0;

		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
	}

	public void update(Enemy enemy) {
		super.update();

//		if ((attack) || bounds.collides(enemy.getBounds())) {
			if (attack) {
				if (ani.hasPlayedOnce()) {
//					if (bounds.collides(enemy.getBounds())) {
//						System.out.println("Ive just hit!!!!!!!");
//						hurt(enemy);
						attack = false;
						alreadyAttacked = false;
//					} else {
//						attack = false;
//						alreadyAttacked = false;
//
//					}
				} else {
					if (bounds.collides(enemy.getBounds()) && attack && !alreadyAttacked) {
				
					System.out.println("Ive just hit!!!!!!!");
//					attack = false;
					alreadyAttacked = true;
					hurt(enemy);
					
					} 
//					else {
////						attack = false;
//						alreadyAttacked = false;
//					}
				}
			}

//		} 
//		else {
////			attack = false;
////			alreadyAttacked = false;
//
//		}

		
		if (!fallen) {
			move();
			if (!tc.collisionTile(dx, 0)) {
				pos.x += dx;
				xCol = false;
			} else {
				xCol = true;
			}
			if (!tc.collisionTile(0, dy)) {
				pos.y += dy;
				yCol = false;
			} else {
				yCol = true;
			}
		} else {
			xCol = true;
			yCol = true;

			if (ani.hasPlayedOnce()) {
				resetPosition();
				dx = 0;
				dy = 0;
				fallen = false;
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect((int) ((pos.getWorldVar().x) + bounds.getXOffset()),
				(int) ((pos.getWorldVar().y) + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

		if (attack) {
			g.setColor(Color.RED);
			g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()),
					(int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(),
					(int) hitBounds.getHeight());
		}
		g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
	}

	public void input(MouseManager mouse, KeyManager key) {

		if (mouse.getButton() == 1) {
			System.out.println("Player: " + pos.x + ", " + pos.y);
		}
//		if (!attack) {
		if (!fallen) {
			if (key.up.down) {
				bounds.setWidth(15);
				bounds.setHeight(15);
				bounds.setXOffset(20);
				bounds.setYOffset(40);
				if (!attack) {
					up = true;
				}
			} else {
				up = false;
			}
			if (key.down.down) {
				if (!attack) {
					down = true;
				}
				bounds.setWidth(15);
				bounds.setHeight(15);
				bounds.setXOffset(20);
				bounds.setYOffset(40);
			} else {
				down = false;
			}
			if (key.left.down) {
				bounds.setWidth(15);
				bounds.setHeight(15);
				bounds.setXOffset(20);
				bounds.setYOffset(40);
				if (!attack) {
					left = true;
				}
			} else {
				left = false;
			}
			if (key.right.down) {
				bounds.setWidth(15);
				bounds.setHeight(15);
				bounds.setXOffset(30);
				bounds.setYOffset(40);
				if (!attack) {
					right = true;
				}
			} else {
				right = false;
			}
			if (!attack) {
				if (key.attack.down) {
					attack = true;
				} else {
					attack = false;
				}
			}

			if (up && down) {
				up = false;
				down = false;
			}
			if (right && left) {
				left = false;
				right = false;
			}
		} else {
			if (!attack) {
				up = false;
				down = false;
				left = false;
				right = false;
			}
		}
//		}

	}

	@Override
	protected void die() {
		resetPosition();
		System.out.println("GAMEOVER");
	}

}
