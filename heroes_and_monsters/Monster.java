package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.util.concurrent.ThreadLocalRandom;

import java.math.BigDecimal;
import java.util.*;

/**
 * A class of monsters.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Monster extends Creature {

	/**
	 * Initialize this new monster with a name, a strength, a maximum hitpoints a
	 * current hitpoints, anchors and anchorObjects.
	 * 
	 * @param 	name
	 * 			The name of this monster.
	 * @param	strength
	 * 			The strength of this monster.
	 * @param	maxHitpoints
	 * 			The maximum and current hitpoints of this monster.
	 * @param	anchors
	 * 			The anchors of a monster.
	 * @param	anchorObjects
	 * 			The objects that must go in the anchors.
	 * @effect	This monster is initialized as a creature with the given name, strength,
	 * 			current hitpoints, maximum hitpoints, anchors and anchorObjects.
	 * @throws	IllegalArgumentException
	 * 			There can't be more anchorObjects than anchors.		
	 */
	@Raw
	public Monster(String name, BigDecimal strength, int maxHitpoints,
			ArrayList<String> anchors, ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		super(name, strength, maxHitpoints, anchors, anchorObjects);
	}
	
	/******************************************
	 * name
	 ******************************************
	
	/**
	 * Set the name of this monster to the given name.
	 * 
	 * @effect	The name of this monster is set to the given name.
	 */
	@Raw
	protected void setName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name)){
			throw new IllegalArgumentException("This is not a valid name for a monster");
		}
		this.name = name;
	}
	

	/**
	 * Checks whether this monster can have the given name as name.
	 * 
	 * @param	name
	 * 			The name to check
	 * @return	False if the given name can't be a name for any creature or if it has
	 * 			a character that is not valid for a name.
	 * 			True otherwise.
	 */
	@Override @Raw
	public boolean canHaveAsName(String name){
		if (!super.canHaveAsName(name)){
			return false;
		}
		else if (!name.matches("[a-zA-Z' ]+")){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**************************************
	 * anchors
	 **************************************/
	
	/**
	 * The anchors of this monster are set to the objects in the given list anchorObjects.
	 * 
	 * @param	anchorObjects
	 * 			The list with all the objects that must go in an anchor.
	 * @effect	The positions of the anchor are filled with the given objects in the list.
	 * @throws	IllegalArgumentException
	 * 			There can't be more objects than anchors.
	 * @throws	IllegalArgumentException
	 * 			There can only be one object in an anchor.
	 */
	@Raw
	protected void setAnchorObjects(ArrayList<Object> anchorObjects)
			throws IllegalArgumentException{
		if (anchorObjects.size() > anchors.size()){
			throw new IllegalArgumentException("There can't be more objects than anchors.");
		}
		for (int i = 0; i < anchors.size(); i++){
			if (anchorObjects.get(i) instanceof List || anchorObjects.get(i) instanceof Map
					|| anchorObjects.get(i) instanceof Set){
				throw new IllegalArgumentException("There can only be 1 object in an anchor.");
			}
		}
		if (anchorObjects != null && anchorObjects.size() != 0){
			Set<String> set = anchors.keySet();
			ArrayList<String> list = (ArrayList)set;
			for (int j = 0; j < anchorObjects.size(); j++){
				Integer random = ThreadLocalRandom.current().nextInt(0, anchors.size());
				ArrayList<Integer> full = new ArrayList<>();
				while (full.contains(random)){
					random = ThreadLocalRandom.current().nextInt(0, anchors.size());
				}
				full.add(random);
				anchors.put(list.get(random), anchorObjects.get(j));
			}
		}
		
	}
	
}
