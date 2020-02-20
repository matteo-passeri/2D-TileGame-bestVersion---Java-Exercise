package game.tiles;

import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import game.gfx.Sprite;
import game.utils.Camera;

public class TileManager {

	public static ArrayList<TileMap> tm;
	public Camera cam;
	
	// Size of the tile - you have to change just this var
	public static int TILE_SIZE = 48;

	public TileManager() {
		tm = new ArrayList<TileMap>();
	}

	public TileManager(String path, Camera cam) {
		tm = new ArrayList<TileMap>();
		addTileMap(path, TILE_SIZE, TILE_SIZE, cam);
	}

	public TileManager(String path, int blockWidth, int blockHeight, Camera cam) {
		tm = new ArrayList<TileMap>();
		addTileMap(path, blockWidth, blockHeight, cam);
	}

	private void addTileMap(String path, int blockWidth, int blockHeight, Camera cam) {
		this.cam = cam;

		String imagePath;
		String imagePathTSX;

		int width = 0;
		int height = 0;
		int tileWidth;
		int tileHeight;
		int tileCount;
		int tileColumns;
		int layers = 0;
		Sprite sprite;

		String[] data = new String[10];

		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("tileset");
			Node node = list.item(0);
			Element eElement = (Element) node;

			imagePathTSX = eElement.getAttribute("source");
			imagePath = imagePathTSX.substring(0, imagePathTSX.length() - 4);

			list = doc.getElementsByTagName("map");
			node = list.item(0);
			eElement = (Element) node;

			tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
			tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));

			sprite = new Sprite("tiles/" + imagePath + ".png", tileWidth, tileHeight);

			tileColumns = sprite.getSpriteSheetWidth() / tileWidth;
			tileCount = tileColumns * (sprite.getSpriteSheetHeight() / tileHeight);

			list = doc.getElementsByTagName("layer");
			layers = list.getLength();

			for (int i = 0; i < layers; i++) {
				node = list.item(i);
				eElement = (Element) node;
				if (i <= 0) {
					width = Integer.parseInt(eElement.getAttribute("width"));
					height = Integer.parseInt(eElement.getAttribute("height"));
				}

				data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

				if (i == 1) {
					tm.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
				} else if (i == 0) {
					tm.add(new TileMapHole(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
				} else {
					tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
				}

				cam.setLimit(width * blockWidth, height * blockHeight);

			}

		} catch (Exception e) {
			System.out.println("ERROR - TileManager: could not read tilemap: " + e);

		}
	}

	public void render(Graphics2D g) {
		if (cam == null) { return; }

		for (int i = 0; i < tm.size(); i++) {
			tm.get(i).render(g, cam.getBounds());
		}
	}
}
