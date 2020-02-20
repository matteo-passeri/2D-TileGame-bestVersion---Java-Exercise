package game.states;

import java.awt.Graphics2D;

import game.utils.KeyManager;
import game.utils.MouseManager;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		

	}
	public abstract void update();
	public abstract void input(MouseManager mouse, KeyManager key);
	public abstract void render(Graphics2D g);


}
