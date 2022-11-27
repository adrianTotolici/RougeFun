package entities;

import world.World;

import java.util.Map;
import java.util.Random;

public class Creature extends Entity {

    private int hitpoints = 0;
    private String behaviour;
    private int power = 0;
    
    public Creature(Map<String, String> creatureData, int x, int y)
    {
        super(creatureData, x, y);
        behaviour = creatureData.get("behaviour");
        hitpoints = Integer.parseInt(creatureData.get("life"));
        power = Integer.parseInt(creatureData.get("power"));
    }
    
    public int getHitpoints() {
    	return hitpoints;
    }

    public int getPower() {
        return power;
    }

    private void setHitpoints(int amount) {
		hitpoints += amount;
	}
 
    public void move(World world, int dx, int dy)
    {
        if ((y + dy < world.height()-1) && (x + dx < world.width())) {
            x += dx;
            y += dy;
        }
    }
    
    public void useItem(Item item) {
    	if (item.getEffect() == "health" && hitpoints <= 90) {
    		hitpoints += 10;
    	}
    }
    
    public void attackCreature(Creature creature, int attackPower) {
    	creature.setHitpoints(-attackPower);
    }

	public void moveTo(World world, int x, int y)
    {
        //
    }

    public void update(World world)
    {
    	Random rnd = new Random();
    	int performAction = rnd.nextInt(100);
    	if (behaviour.equals("docile") && performAction > 98) {
    		// walk around and flee if attacked
    		
    		int rndNr = rnd.nextInt(4);
    		    		
    		if (rndNr == 0) {
    			move(world, 1, 0);
    		} else if (rndNr == 1) {
    			move(world, -1, 0);
    		} else if (rndNr == 2) {
    			move(world, 0, 1);
    		} else if (rndNr == 3) {
    			move(world, 0, -1);
    		}

    	} else if (behaviour.equals("aggressive") && performAction > 98) {
    		// look for other npcs and hunt them
    	}

    }


}
