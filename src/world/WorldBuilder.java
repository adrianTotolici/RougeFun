package world;

import entities.Creature;
import entities.Tile;
import utils.Constants;

import java.util.*;

public class WorldBuilder {
	private int width;
	private int height;
	private Tile[][] tiles;
	private Map<String, Map<String, String>> tileData;
	private Map<String, Map<String, String>> creatureData;
	private Set<Creature> creatures;

	public WorldBuilder(Map<String, Map<String, String>> tileData, Map<String, Map<String, String>> creatureData, int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		this.tileData = tileData;
		this.creatureData = creatureData;
		this.creatures = new HashSet<Creature>();
	}

	public WorldBuilder load(String file) {
		// Loads map from file
		return this;
	}
	
	public Tile createTile(String type, int x, int y) {
		return new Tile(tileData.get(type), x, y);
	}
	
	public Creature createCreature(String type, int x, int y) {
		return new Creature(creatureData.get(type), x, y);
	}
	
	public WorldBuilder fill(String tileMain) {
		for (int x=0; x < width; x++) {
			for (int y=0; y < height; y++) {
				tiles[x][y] = new Tile(tileData.get(tileMain), x, y);
			}
		}
		return this;
	}
	
	public WorldBuilder addBorders(String borderTile) {
		for (int x=0; x<width-3; x++) {
			tiles[x][0] = createTile(borderTile, x, 0);
			tiles[x][height-1] = createTile(borderTile, x, height-1);
		}
		
		for (int y=0; y<height; y++) {
			tiles[0][y] = createTile(borderTile, 0, y);
			tiles[width-3][y] = createTile(borderTile, width-4, y);
		}
		return this;
	}
	
	public WorldBuilder populateWorld(int nrOfCreatures) {
		Random rnd = new Random();
		int rndX;
		int rndY;
		
		for (int i=0; i < nrOfCreatures; i++) {
			
			do {
				rndX = rnd.nextInt(width-2);
				rndY = rnd.nextInt(height);
			} while (tiles[rndX][rndY].isBlocked());
			
			List<String> creatureTypes = new ArrayList<String>(creatureData.keySet());
			creatureTypes.remove(Constants.PLAYER_ENTITY);
			String creatureType = creatureTypes.get(rnd.nextInt(creatureTypes.size()));
			
			creatures.add(createCreature(creatureType, rndX, rndY));
			
		}
		
		return this;
	}

	public WorldBuilder addForests(String treeTile){
		Random rnd = new Random();

		int rndNrForest = rnd.nextInt(20);
		for (int i=0; i<rndNrForest; i++) {
			int forestPosX = rnd.nextInt(width - 2);
			int forestPosY = rnd.nextInt(height);
			int rndForestSize = rnd.nextInt(3)+1;

			for (int j=rndForestSize; j>0; j--) {
				int treeNumber = rnd.nextInt((10+(j*5))-(5+(j*5)))+(5+(j*5));
				for (int z=0; z<treeNumber; z++){
					boolean placeTree = true;
					while (placeTree) {
						int trePosX = rnd.nextInt((j*2)+2)-2;
						int trePosY = rnd.nextInt((j*2)+2)-2;
						if ((forestPosY+trePosY>0) && (forestPosX+trePosX>0) && (forestPosY+trePosY<Constants.WINDOW_SIZE.width-4) && (forestPosX+trePosX<Constants.WINDOW_SIZE.height-4)) {
							System.out.println("trePosX  = "+forestPosY+trePosX+" trePosY  = "+forestPosY+trePosY);
							if (!tiles[forestPosY + trePosX][forestPosY + trePosY].isBlocked()) {
								tiles[forestPosX + trePosX][forestPosY + trePosY] = createTile(treeTile, forestPosX + trePosX, forestPosY + trePosY);

							}

						}
						placeTree = false;
					}
				}
			}
		}
		return this;
	}
	
	public World build() {
		return new World(tiles, creatures);
	}

}
