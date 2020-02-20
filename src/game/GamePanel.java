package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.states.GameStateManager;
import game.utils.KeyManager;
import game.utils.MouseManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width, height;

	private Thread thread;
	private boolean running = false;

	private BufferedImage image;
	private Graphics2D g;

	private MouseManager mouse;
	private KeyManager key;

	private GameStateManager gsm;
	
	// loop engine
	final double GAME_HERTZ = 60.0;
	final double TBU = 1000000000 / GAME_HERTZ; // time before update

	final int MUBR = 5; // times must update before render
	
	final double TARGET_PFS = 60;
	final double TTBR = 1000000000 / TARGET_PFS; // Total time before render

	int frameCount = 0;
	public static int oldFrameCount = 0;
	public static int oldTickCount = 0;
	//...loop engine

	public GamePanel(int width, int height) {
		GamePanel.width = width;
		GamePanel.height = height;

		setPreferredSize(new Dimension(width, height));

		setFocusable(true);
		requestFocus();

	}

	public void init() {
		running = true;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) image.getGraphics();

		mouse = new MouseManager(this);
		key = new KeyManager(this);

		gsm = new GameStateManager();

	}

	public void update() {
		gsm.update();
	}

	public void input(MouseManager mouse, KeyManager key) {
		gsm.input(mouse, key);
	}

	public void render() {
		if (g != null) {
			g.setColor(new Color(66, 134, 244));
			g.fillRect(0, 0, width, height);
			gsm.render(g);
		}
	}

	public void draw() {
		Graphics g2 = (Graphics) this.getGraphics();
		g2.drawImage(image, 0, 0, width, height, null);
		g2.dispose();
	}

	public void run() {
		init();

		

		double lastUpdateTime = System.nanoTime();
		double lastRenderTime;

		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;

			while (((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
				update();
				input(mouse, key);
				lastUpdateTime += TBU;
				updateCount++;
			}
			oldTickCount = updateCount;


			if (now - lastUpdateTime > TBU) {
				lastUpdateTime = now - TBU;
			}

			input(mouse, key);
			render();
			draw();

			lastRenderTime = now;
			frameCount++;

			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime) {
				if (frameCount != oldFrameCount) {
					System.out.println("New second: " + thisSecond + " " + frameCount);
					oldFrameCount = frameCount;
				}
				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
				Thread.yield();
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					System.out.println("ERROR: yielding thread" + e);
				}
				now = System.nanoTime();
			}

		}

	}

	public void addNotify() {
		super.addNotify();

		if (thread == null) {
			thread = new Thread(this, "GameThread");
			thread.start();
		}
	}

}
