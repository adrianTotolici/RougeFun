import entities.Creature;
import ui.Interface;
import utils.Constants;
import world.World;
import world.WorldBuilder;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class StartGame {

	private boolean isRunning;
	private int timePerLoop = 1000000000 / Constants.FRAME_PER_SECOND;

	private static StartGame INSTANCE;
	
	private World world;
	private Creature player;
	
	private Map<String,Map<String, String>> creatureData;
	private Map<String,Map<String, String>> tileData;
	private Map<String,Map<String, String>> itemData;
	
	private int screenWidth;
	private int screenHeight;
	
	private Rectangle gameViewArea;
	
	private Interface ui;

	public static StartGame getInstance(Rectangle screen){
		if ( INSTANCE == null ) {
			INSTANCE = new StartGame(screen);
		}
		return INSTANCE;
	}

	public StartGame(Rectangle screen) {
		this.screenWidth = screen.width;
		this.screenHeight = screen.height;
		
		gameViewArea = new Rectangle(screenWidth, screenHeight-4);
		
		ui = new Interface(screenWidth, screenHeight, screen);

		creatureData = loadData(Constants.CREATURE_GRAPHICS_PATH);
		tileData = loadData(Constants.TILES_GRAPHICS_PATH);
		itemData = loadData(Constants.ITEM_GRAPHICS_PATH);
		
		createWorld();
	}
	
	public Map<String, Map<String, String>> loadData(String file) {
		Map<String, Map<String, String>> entityMap = new HashMap<>();
		String line = "";
		String[] attributeNames = new String[10];
		
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        	line = br.readLine();
        	
        	if (line != null) {
        		attributeNames = line.split(", ");
        	}
        	
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                Map<String, String> entityData = new HashMap<>();
                
                for (int i=0; i<attributeNames.length; i++) {
                	entityData.put(attributeNames[i], data[i]);
                }
                
                String name = data[1];
                entityMap.put(name, entityData);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entityMap;
	}
	
	private void createWorld(){
		player = new Creature(creatureData.get(Constants.PLAYER_ENTITY), 10, 10);

		WorldBuilder worldBuilder = new WorldBuilder(tileData, creatureData,screenWidth, screenHeight);
		worldBuilder.fill(Constants.GROUND_ROCK_TILE_ENTITY);
		worldBuilder.addBorders(Constants.WALL_TILE_ENTITY);
		worldBuilder.addForests(Constants.TREE_TILE_ENTITY);
		worldBuilder.addForests(Constants.WATER_TILE_ENTITY);
		worldBuilder.populateWorld(50);

		world = worldBuilder.build();
		world.player = player;
		world.addEntity(player);
	}
	
	public void processInput() {
	    InputEvent event = ui.getNextInput();
	    if (event instanceof KeyEvent) {
	    	KeyEvent keypress = (KeyEvent)event;
	    	switch (keypress.getKeyCode()){
				case KeyEvent.VK_LEFT: 
					player.move(world, -1, 0); 
					break;
				case KeyEvent.VK_RIGHT: 
					player.move(world, 1, 0);
					break;
				case KeyEvent.VK_UP: 
					player.move(world, 0, -1); 
					break;
				case KeyEvent.VK_DOWN: 
					player.move(world, 0, 1); 
					break;
			}
	    }
	}
	
	public void render(){
		ui.pointCameraAt(world, player.getX(), player.getY());
		ui.drawDynamicLegend(gameViewArea, world, tileData, creatureData);
		ui.refresh();
	}
	
	public void update() {
		world.update();
	}
	
	public void run() {
		isRunning = true;

		while(isRunning) {
			long startTime = System.nanoTime();
			
			processInput();
			update();
			render();
			
			long endTime = System.nanoTime();
			
			long sleepTime = timePerLoop - (endTime-startTime);
			
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime/1000000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
