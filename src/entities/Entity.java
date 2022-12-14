package entities;

import java.awt.*;
import java.util.Map;

import utils.Utils;

public class Entity {
	
	protected int x;
	protected int y;
	
	protected String type;
	protected char glyph;
	protected Color color;
	
	public int getX() {return x;}
	public int getY() {return y;}
    public char getGlyph() {return this.glyph;}
    public String getType() {return type;}
    public Color getColor() {return this.color;}
    
    public Entity(Map<String, String> entityData, int xPos, int yPos) {
    	x = xPos;
    	y = yPos;
        type = entityData.get("name");
        glyph = entityData.get("glyph").charAt(0);
        color = Utils.stringRgbToColor(entityData.get("color"));
    }
}
