package game.states;

import java.awt.Graphics2D;

import game.GamePanel;
import game.entities.Enemy;
import game.entities.Player;
import game.gfx.Font;
import game.gfx.Sprite;
import game.tiles.TileManager;
import game.utils.AABB;
import game.utils.Camera;
import game.utils.KeyManager;
import game.utils.MouseManager;
import game.utils.Vector2f;

public class PlayState extends GameState {

	private Font font;
	private Player player;
	private Enemy enemy;
	private TileManager tm;
	private Camera cam;

	public static Vector2f map;

	public PlayState(GameStateManager gsm) {
		super(gsm);

		map = new Vector2f();
		Vector2f.setWorldVar(map.x, map.y);

		cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64, GamePanel.height + 64));

		tm = new TileManager("maps/01.xml", cam);

		enemy = new Enemy(cam, new Sprite("entities/enemies/orcWarrior.png"),
				new Vector2f(150 + (GamePanel.width / 2) - 32, 200 + (GamePanel.height / 2) - 32), 80);
		player = new Player(new Sprite("entities/player/player_sheet.png"),
				new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32), 64);

		cam.target(player);
	}

	@Override
	public void update() {
		Vector2f.setWorldVar(map.x, map.y);
		player.update(enemy);
		enemy.update(player);
		cam.update();
	}

	@Override
	public void input(MouseManager mouse, KeyManager key) {
		key.escape.tick();

		player.input(mouse, key);
		cam.input(mouse, key);

		// clicked = pushed and release
		if (key.escape.clicked) {
			if (gsm.getState(GameStateManager.PAUSE)) {
				gsm.pop(GameStateManager.PAUSE);
			} else {
				gsm.add(GameStateManager.PAUSE);

			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		tm.render(g);

		String fps = GamePanel.oldFrameCount + " FPS";

		player.render(g);
		enemy.render(g);
		cam.render(g);

	}

}
