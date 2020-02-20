package game.states;

import java.awt.Graphics2D;

import game.utils.KeyManager;
import game.utils.MouseManager;

public class PauseState extends GameState {

	public PauseState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void update() {
		System.out.println("PAUSED");
	}

	@Override
	public void input(MouseManager mouse, KeyManager key) {
		
	}

	@Override
	public void render(Graphics2D g) {

	}

}
