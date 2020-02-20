package game;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public Window() {
		setTitle("TileGame via Xml");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new GamePanel(1024, 640));
		pack();
		setResizable(false);
		setLocationRelativeTo(null);		
		setVisible(true);
	}

}
