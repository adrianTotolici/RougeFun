package utils;

import java.awt.*;
import java.nio.file.Paths;

public class Constants {

    //Game Settings
    public static final int FRAME_PER_SECOND = 60;
    public static final int MAX_FOREST_NUMBER = 20;
    public static final int MAX_LAKE_NUMBER = 5;

    // Map size
    public static final Rectangle WINDOW_SIZE = new Rectangle(200,60);

    // entity list
    public static final String CREATURE_GRAPHICS_PATH = Paths.get("res", "creatures.txt").toString();
    public static final String TILES_GRAPHICS_PATH = Paths.get("res", "tiles.txt").toString();
    public static final String ITEM_GRAPHICS_PATH = Paths.get("res", "items.txt").toString();

    // entity names
    public static final String PLAYER_ENTITY = "player";
    public static final String SHEEP_ENTITY = "sheep";
    public static final String ZOMBIE_ENTITY = "zombie";
    public static final String COW_ENTITY = "cow";

    // entity tile names
    public static final String WALL_TILE_ENTITY = "wall";
    public static final String GROUND_ROCK_TILE_ENTITY = "ground";
    public static final String TREE_TILE_ENTITY = "tree";
    public static final String WATER_TILE_ENTITY = "water";
}
