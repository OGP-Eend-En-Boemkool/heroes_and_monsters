package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * A class of heroes.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Hero extends Creature {

	/**
	 * Initialize this new hero with a name, a strength, a maximum hitpoints a
	 * current hitpoints, anchors and anchorObjects.
	 * 
	 * @param 	name
	 * 			The name of this hero.
	 * @param	strength
	 * 			The strength of this hero.
	 * @param	maxHitpoints
	 * 			The maximum and current hitpoints of this hero.
	 * @param	anchorObjects
	 * 			The objects that must go in the anchors.
	 * @effect	This hero is initialized as a creature with the given name, strength, 
	 * 			current hitpoints, maximum hitpoints and anchorObjects.
	 * 			| super(name, strength, maxHitpoints, anchorsStandard, anchorObjects)
	 * @throws 	IllegalArgumentException
	 * 			The list anchorObjects must have 5 objects.
	 * 			| anchorObjects.size() != anchorsStandard.size()
	 * @throws	IllegalArgumentException
	 * 			The object on the fourth position must be effective and it must be
	 * 			an armor.
	 * 			| anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor
	 * @throws	IllegalArgumentException
	 * 			The object on the fifth position can only be a purse.
	 * 			| anchorObjects.get(4) != null && !(anchorObjects.get(4) instanceof Purse)		
	 */
	@Raw
	public Hero(String name, BigDecimal strength, int maxHitpoints,
			ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		super(name, strength, maxHitpoints, anchorsStandard, anchorObjects);
	}
	
	/****************************************
	 * name
	 ****************************************
	
	/**
	 * Set the name of this hero to the given name.
	 * 
	 * @effect	The name of this hero is set to the given name.
	 * 			| new.getName() == name
	 */
	@Raw
	protected void setName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name)){
			throw new IllegalArgumentException("This is not a valid name for a hero");
		}
		this.name = name;
	}
	
	/**
	 * Checks whether this hero can have the given name as name.
	 * 
	 * @param	name
	 * 			The name to check
	 * @return	False if the given name can't be a name for any creature or if it has
	 * 			a character that is not valid for a name or if it has more than two
	 * 			apostrophes or if a colon is not followed by a space.
	 * 			True otherwise.
	 * 			| result == ((super.canHaveAsName(name)) &&
	 * 			|				( for all char in name:
	 * 			|						char in "a-zA-Z': " ) &&
	 * 			|				( name.length() - name.replaceAll("'", "").length() <= 2 ) &&
	 * 			|				( for char in name:
	 * 			|						if (char == ':'){
	 * 			|							next char == ' ' }) )
	 */
	@Override @Raw
	public boolean canHaveAsName(String name){
		if (!super.canHaveAsName(name)){
			return false;
		}
		else if (!name.matches("[a-zA-Z': ]+")){
			return false;
		}
		else if (name.length() - name.replaceAll("'", "").length() > 2){
			return false;
		}
		else {
			for (int i = 0; i < name.length(); i++){
				if (name.charAt(i) == ':'){
					if (i == name.length() - 1){
						return false;
					}
					else if (name.charAt(i+1) != ' '){
						return false;
					}
				}
			}
			return true;
		}
	}
	
	/****************************************
	 * anchors
	 ****************************************
	
	/**
	 * The anchors of a hero.
	 */
	private static ArrayList<String> anchorsStandard = new ArrayList<String>(Arrays.asList("Left hand", "Right hand", "Back", "Body", "Belt")) ;
	
	/**
	 * The anchors of this hero are set to the objects in the given list anchorObjects.
	 * 
	 * @param 	anchorObjects
	 * 			The list with all the objects that must go in an anchor.
	 * @effect	The positions of the anchor are filled with the given objects in the list.
	 * 			| new.getAnchors() = super.setAnchorObjects(anchorObjects)
	 * @throws 	IllegalArgumentException
	 * 			The list anchorObjects must have 5 objects.
	 * 			| anchorObjects.size() != anchorsStandard.size()
	 * @throws	IllegalArgumentException
	 * 			There can only be one object in an anchor.
	 * 			| anchorObjects.get(i) instanceof List ||
	 * 			|		anchorObjects.get(i) instanceof Map ||
				|		anchorObjects.get(i) instanceof Set
	 * @throws	IllegalArgumentException
	 * 			The object on the fourth position must be effective and it must be
	 * 			an armor.
	 * 			| anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor
	 * @throws	IllegalArgumentException
	 * 			The object on the fifth position can only be a purse.
	 * 			| anchorObjects.get(4) != null && !(anchorObjects.get(4) instanceof Purse)
	 */
	@Raw
	protected void setAnchorObjects(ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		if (anchorObjects.size() != anchorsStandard.size()){
			throw new IllegalArgumentException("Not the right amount of items");
		}
		for (int i = 0; i < anchorsStandard.size(); i++){
			if (anchorObjects.get(i) instanceof List || anchorObjects.get(i) instanceof Map
					|| anchorObjects.get(i) instanceof Set){
				throw new IllegalArgumentException("There can only be 1 object in an anchor.");
			}
		}
		if (anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor)){
			throw new IllegalArgumentException("At birth, the object on the fourth position "
					+ "must be effective and its class must be Armor.");
		}
		if (anchorObjects.get(4) != null && !(anchorObjects.get(4) instanceof Purse)){
			throw new IllegalArgumentException("The object on the fifth position can only "
					+ "be a purse.");
		}
		this.anchors.put("Left hand", anchorObjects.get(0));
		this.anchors.put("Right hand", anchorObjects.get(1));
		this.anchors.put("Back", anchorObjects.get(2));		
		this.anchors.put("Body", anchorObjects.get(3));
		this.anchors.put("Belt", anchorObjects.get(4));
	}
}
