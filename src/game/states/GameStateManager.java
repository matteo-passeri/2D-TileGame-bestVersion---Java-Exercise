package game.states;

import java.awt.Graphics2D;

import game.GamePanel;
import game.gfx.Font;
import game.gfx.Sprite;
import game.utils.KeyManager;
import game.utils.MouseManager;
import game.utils.Vector2f;

public class GameStateManager {
	private GameState states[];

	public static Vector2f map;

	public static final int PLAY = 0;
	public static final int MENU = 1;
	public static final int PAUSE = 2;
	public static final int GAMEOVER = 3;

	public int ontTopState = 0;

	public static Font font;

	public GameStateManager() {
		map = new Vector2f(GamePanel.width, GamePanel.height);
		Vector2f.setWorldVar(map.x, map.y);

		states = new GameState[4];

		font = new Font("font/asciiFont_16x16.png", 10, 10);
		Sprite.currentFont = font;

		states[PLAY] = new PlayState(this);

	}
	
	public boolean getState(int state) {
		return states[state] != null;
	}

	public void pop(int state) {
		states[state] = null;
		
	}

	public void add(int state) {
		if (states[state] != null) {
			return;
		}

		if (state == PLAY) {
			states[PLAY] = new PlayState(this);
		}
		if (state == MENU) {
			states[MENU] = new MenuState(this);
		}
		if (state == PAUSE) {
			states[PAUSE] = new PauseState(this);
		}
		if (state == GAMEOVER) {
			states[GAMEOVER] = new GameOverState(this);
		}
	}

	public void addAndPop(int state) {
		addAndPop(state, 0);
	}

	public void addAndPop(int state, int remove) {
		pop(state);
		add(state);
	}

	public void update() {
		Vector2f.setWorldVar(map.x, map.y);
		
		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				states[i].update();
			}
		}
	}

	public void input(MouseManager mouse, KeyManager key) {
		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				states[i].input(mouse, key);
			}
		}

	}

	public void render(Graphics2D g) {
		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				states[i].render(g);
			}
		}
	}

}
