package propellerjoch;

import java.util.ArrayList;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.CenterFollowingViewport;
import nl.han.ica.oopg.view.EdgeFollowingViewport;
import nl.han.ica.oopg.view.View;
import processing.core.PApplet;
import propellerjoch.tiles.FloorTile;
import propellerjoch.tiles.PlatformTile;
import propellerjoch.tiles.SpikesTile;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class Propellerjoch extends GameEngine {

	private Player player;
	private Prinses prinses;
	private Monster zombie;
	private Monster spin;
	private ArrayList<Monster> monsters = new ArrayList<>();
	private TextObject text;
	private Checkpoint cp;
	private Dashboard db;

	public static String MEDIA_URL = "Propellerjoch/propellerjoch/media/";

	public static void main(String[] args) {
		Propellerjoch pj = new Propellerjoch();

		pj.runSketch();
	}

	@Override
	public void setupGame() {
		int worldWidth = 2880;
		int worldHeight = 1200;

		createObjects();
		initializeTileMap();

		View view = new View(worldWidth, worldHeight);

		setView(view);
		size(worldWidth, worldHeight);
		createViewWithViewport(worldWidth, worldHeight, 1200, 800, 1.2f);
	}

	private void createObjects() {
		cp = new Checkpoint(this);
		addGameObject(cp, 1600, 500);
		prinses = new Prinses(this);
		addGameObject(prinses, 2600, 700);
		player = new Player(this, cp);
		addGameObject(player, 200, 700);
		
		// Voor monsters : type(null, this, beginPunt, eindPunt, speed, player)
		zombie = new Zombie(null, this, 750, 1100, 1.5f, player);
		spin = new Spin(null, this, 250, 600, 1f, player);

		monsters.add(spin);
		monsters.add(spin);
		monsters.add(spin);
		monsters.add(zombie);
		addGameObject(zombie, 730, 650);
		addGameObject(spin, 800, 200);
		spin = new Spin(null, this, 500, 900, 1f, player);
		addGameObject(spin, 1200, 450);
	}

	@Override
	public void update() {
		for (Monster m : monsters) {
			m.update();
		}
	}

	public void gameOver() {
		createDashboard(1200, 800);
		dashboardText();
	}

	private void createDashboard(int dashboardWidth, int dashboardHeight) {
		final int tekstGrootte = 10;
		Dashboard dashboard = new Dashboard(0, 0, dashboardWidth, dashboardHeight);
		text = new TextObject("", tekstGrootte);
		dashboard.addGameObject(text);
		addDashboard(dashboard);
	}
	
	private void dashboardText() {
		text.setText("Game Over");
	}

	private void createViewWithViewport(int worldWidth, int worldHeight, int screenWidth, int screenHeight,
			float zoomFactor) {
		CenterFollowingViewport viewPort = new CenterFollowingViewport(player,
				(int) Math.ceil(screenWidth / zoomFactor), (int) Math.ceil(screenHeight / zoomFactor), 0, 0);
		viewPort.setTolerance(50, 50, 50, 50);
		View view = new View(viewPort, worldWidth, worldHeight);
		setView(view);
		size(screenWidth, screenHeight);
		view.setBackground(loadImage(Propellerjoch.MEDIA_URL.concat("backgroundBergen.png")));
	}

	private void initializeTileMap() {
		Sprite floorSprite = new Sprite(Propellerjoch.MEDIA_URL.concat("platformPack_tile001.png"));
		Sprite platformSprite = new Sprite(Propellerjoch.MEDIA_URL.concat("platform.png"));
		Sprite spikesSprite = new Sprite(Propellerjoch.MEDIA_URL.concat("spikes.png"));
		TileType<FloorTile> floorTileType = new TileType<>(FloorTile.class, floorSprite);
		TileType<PlatformTile> platformTileType = new TileType<>(PlatformTile.class, platformSprite);
		TileType<SpikesTile> spikesTileType = new TileType<>(SpikesTile.class, spikesSprite);

		TileType[] tileTypes = { floorTileType, platformTileType, spikesTileType };
		int tileSize = 64;
		int tilesMap[][] = {
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1,
						1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, -1, -1, 0, 0, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
						-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, } };
		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
	}
}
