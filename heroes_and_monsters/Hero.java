package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

import java.math.*;
import java.util.*;

/**
 * A class of heroes.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Hero extends Creature {
	
	/******************************************
	 * Constructors
	 ******************************************

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
	@Raw @Override
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
	 * The anchors of a hero: "Left hand", "Right hand", "Back", "Body", "Belt".
	 */
	private static ArrayList<String> anchorsStandard = new ArrayList<String>(Arrays.asList("Left hand", "Right hand", "Back", "Body", "Belt")) ;
	
	/**
	 * The anchors of this hero are set to the objects in the given list anchorObjects.
	 * 
	 * @param 	anchorObjects
	 * 			The list with all the objects that must go in an anchor.
	 * @effect	The positions of the anchor are filled with the given objects in the list.
	 * 			| new.getAnchors() = super.setAnchorObjects(anchorObjects)
	 * @effect	The objects are added to their associated anchors.
	 * 			| for all objects in anchorObjects {
	 * 			|		addToAnchor(object, associated anchor)
	 * @throws 	IllegalArgumentException
	 * 			The list anchorObjects must have an equal amount of objects as anchors.
	 * 			| anchorObjects.size() != anchorsStandard.size()
	 * @throws	IllegalArgumentException
	 * 			The object on the fourth position must be effective and it must be
	 * 			an armor.
	 * 			| anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor
	 * @throws	IllegalArgumentException
	 * 			One of the objects can't be added to its associated anchor.
	 * 			| !canAddToAnchor(object, anchor)
	 */
	@Raw @Override
	protected void setAnchorObjects(ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		if (anchorObjects.size() != anchorsStandard.size()){
			throw new IllegalArgumentException("Not the right amount of items");
		}
		if (anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor)){
			throw new IllegalArgumentException("At birth, the object on the fourth position "
					+ "must be effective and its class must be Armor.");
		}
		for (int i = 0; i < anchorObjects.size(); i++){
			this.addToAnchor(anchorObjects.get(i), anchorsStandard.get(i));
		}
	}
	
	/**
	 * Check whether the given object can be added to the given anchor.
	 * 
	 * @return	True if and only if the given object can be added to the given anchor
	 * 			for any creature and if when the anchor is a belt, the object is a purse
	 * 			and if when the anchor is a body, the object is an armor.
	 * 			| result == super.canAddToAnchor(object, anchor) &&
	 *			|				(anchor != "Belt" || (object instanceof Purse)) &&
	 *			|				(anchor != "Body" || (object instanceof Armor))
	 */
	@Raw @Override
	public boolean canAddToAnchor(Object object, String anchor){
		return (super.canAddToAnchor(object, anchor) &&
				(anchor != "Belt" || (object instanceof Purse)) &&
				(anchor != "Body" || (object instanceof Armor)));
	}
	
	
	/**********************************
	 * Heal
	 **********************************/
	
	/**
	 * A hero can heal and get more hitpoints.
	 * 
	 * @post	The hitpoints are increased with a random percentage of the difference
	 * 			between the maximum hitpoints and the current hitpoints.
	 * 			| new.getHitpoints() == this.getHitpoints + 
	 * 			|						Math.round(randomNumber() * 0.01 *
	 * 			|						(this.getMaxHitpoints() - this.getHitpoints()))
	 */
	private void heal(){
		int difference = this.getMaxHitpoints() - this.getHitpoints();
		int extra = Math.round(randomNumber() * (float)0.01 * difference);
		if (this.getHitpoints() + extra > this.getMaxHitpoints()){
			this.setHitpoints(this.getMaxHitpoints());
		}
		else {
			this.setHitpoints(this.getHitpoints() + extra);
		}
		
	}
	
	/**********************************
	 * Protection 
	 **********************************/
	
	/**
	 * Variable registering the value of the standard protection of a hero.
	 */
	public final int standardProtection = 10;
	
	/**
	 * Returns the current value for the protection of a hero.
	 * 
	 * @return The resulting number must be bigger than or equal to 1 and smaller than the maximum protection.
	 * 		   | (result >= 1) && (result <= maxProtection)
	 * @return The resulting number must be the sum of the standardProtection and the currentProtection of the armor.
	 * 		   | result == (armor.getCurrentProtection() + this.standardProtection))
	 */
	public int getCurrentProtection() {
		Object object = getAnchors().get("Body");
		if ((object != null)&&(object instanceof Armor)){
			Armor armor = (Armor)object;
			return (armor.getCurrentProtection() + this.standardProtection);
		}
		else {
			return this.standardProtection;
		}
	}


	/**********************************
	 * Capacity -  totaal
	 **********************************/
	
	/**
	 * The list of capacities between 10 and 20 of a hero.
	 */
	private static ArrayList<Integer> capacities = new ArrayList<Integer>(Arrays.asList(115, 130, 150, 175, 200, 230, 260, 300, 350, 400));
	
	/**
	 * Return the maximum capacity of the hero.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 */
	@Override
	public double getMaximumCapacity(Unit unit) {
		float strength = this.getStrength().floatValue();
		int constant = 1;
		float capacity = 0;
		while (strength > 20){
			constant = constant * 4;
			strength = strength - 10;
		}
		if (strength < 1.00){
			capacity =  0;
		}
		else if (strength <= 10){
			capacity = (strength * 10);
		}
		else if (strength <= 20){
			int i = (int)Math.floor(strength);
			capacity =  capacities.get(i);			
		}
		return (unit.convertFromKilogram(constant*capacity));
	}

	/**
	 * Return the used part of the total capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @return the resulting number cannot be larger than the maximum capacity of the object.
	 * 		   | result <= this.getMaximumCapacity()
	 */
	@Override
	public double getUsedCapacity(Unit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

		
}
