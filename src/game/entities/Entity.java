package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gfx.Animation;
import game.gfx.Sprite;
import game.utils.AABB;
import game.utils.TileCollision;
import game.utils.Vector2f;

public abstract class Entity {

	protected final int RIGHT = 0;
	protected final int LEFT = 1;	
	protected final int DOWN = 2;
	protected final int UP = 3;	
	protected final int FALLEN = 4;
	protected final int HIT = 5;
	protected final int ATTACK = 6;
	protected final int DEAD = 7;
	protected int currentAnimation;

	protected Animation ani;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int size;

	protected boolean up;
	protected boolean down;
	protected boolean left;
	protected boolean right;
	protected boolean fallen;
	protected boolean hit;
	protected boolean attack;
	protected boolean dead;
	
	public boolean xCol = false;
	public boolean yCol = false;
	
	protected int attackSpeed;
	protected int attackDuration;

	protected float dx;
	protected float dy;

	protected float maxSpeed = 4f;
	protected float acc = 2f;
	protected float deacc = 0.5f;
	
	protected int health = 10;
	protected int damage = 2;
	protected boolean active = true;

	protected AABB hitBounds;
	protected AABB bounds;
	
	protected TileCollision tc;


	public Entity(Sprite sprite, Vector2f orgin, int size) {
		this.sprite = sprite;
		pos = orgin;
		this.size = size;

		bounds = new AABB(orgin, size, size);
		hitBounds = new AABB(orgin, size, size);
		hitBounds.setXOffset(size/2);

		ani = new Animation();
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
		
		tc = new TileCollision(this);
	}
	
	public float getDx() { return dx; }
	public float getDy() { return dy; }
	
	public boolean isFallen() { return fallen; }
	public void setFallen(boolean fallen) {	this.fallen = fallen; }

	public void setSprite(Sprite sprite) { this.sprite = sprite; }
	
	public float getMaxSpeed() { return maxSpeed; }
	public void setMaxSpeed(float maxSpeed) { this.maxSpeed = maxSpeed; }
	
	public void setAcc(float acc) { this.acc = acc; }
	public float getAcc() { return acc; }

	public void setDeacc(float deacc) { this.deacc = deacc; }
	public float getDeacc() { return deacc; }
	
	public AABB getBounds() { return bounds; }

	public void setSize(int size) { this.size = size; }
	public int getSize() { return size; }
	
	public Animation getAnimation() { return ani; }

	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		ani.setFrames(frames);
		ani.setDelay(delay);
	}

	public void animate() {
		if (up) {
			if (currentAnimation != UP || ani.getDelay() == -1) {
				setAnimation(UP, sprite.getSpriteArray(UP), 5);
			}
		} else if (down) {
			if (currentAnimation != DOWN || ani.getDelay() == -1) {
				setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
			}
		} else if (left) {
			if (currentAnimation != LEFT || ani.getDelay() == -1) {
				setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
			}
		} else if (right) {
			if (currentAnimation != RIGHT || ani.getDelay() == -1) {
				setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
			}
		} else if (fallen) {
			if (currentAnimation != FALLEN || ani.getDelay() == -1) {
				setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
			}
		} else if (attack) {
			if (currentAnimation != ATTACK || ani.getDelay() == -1) {
				setAnimation(ATTACK, sprite.getSpriteArray(ATTACK), 5);
			}
		} else if (dead) {
			if (currentAnimation != DEAD || ani.getDelay() == -1) {
				setAnimation(DEAD, sprite.getSpriteArray(DEAD), 20);
			}
		} else if (hit) {
			if (currentAnimation != HIT || ani.getDelay() == -1) {
				setAnimation(HIT, sprite.getSpriteArray(HIT), 5);
			}
		} else {
			setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), 5);
		}
	}

	private void setHitBoxDirection() {
		if (up) {
			hitBounds.setYOffset((-size / 2) + 20);
			hitBounds.setXOffset(0);
		}
		else if (down) {
			hitBounds.setYOffset((size / 2) + 20);
			hitBounds.setXOffset(0);
			
		}
		else if (left) {
			hitBounds.setYOffset(0);
			hitBounds.setXOffset((-size / 2) - 5);
		}
		else if (right) {
			hitBounds.setYOffset(0);
			hitBounds.setXOffset((size / 2) + 5);
		}
	}

	public void update() {
		animate();
		setHitBoxDirection();
		if(health <= 0) { die(); }
		ani.update();
		
	}
	
	protected abstract void die();
	protected void hurt(Entity e) {
		e.health -= damage;
		
		
		System.out.println(e.health);
	}
	public boolean isDead() {return dead; }

	public void setDead(boolean dead) {	this.dead = dead; }

	public abstract void render(Graphics2D g);


	


	
	
}
