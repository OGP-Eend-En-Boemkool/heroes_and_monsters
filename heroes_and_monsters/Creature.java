package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of creatures with a name and a strength.
 * 
 * @invar	Every creature must have a valid name.
 * 			| canHaveAsName(getName())
 * @invar	Every creature must have a valid strength.
 * 			| isValidStrength(getStrength())
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public abstract class Creature implements Protection{

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new Creature with the given name as name and the
	 * given strength as strength.
	 * 
	 * @param	name
	 * 			The name of this creature.
	 * @param	strength
	 * 			The strength of this creature.
	 * @param	maxHitpoints
	 * 			The maximum and current hitpoints of this creature.
	 * @param	anchors
	 * 			The anchors of a creature.
	 * @param	anchorObjects
	 * 			The objects that must go in the anchors.
	 * @pre 	MaxHitpoints must be a valid value for hitpoints when the creature is
	 * 			not fighting.
	 * 			| canHaveAsHitpointsNotFighting(maxHitpoints)
	 * @post	The name of this creature is set to the given name.
	 * 			| new.getName() == name
	 * @post	The strength of this creature is set to the given strength.
	 * 			| new.getStrength() == strength
	 * @post	The current hitpoints of this creature is set to the given maxHitpoints.
	 * 			| new.getHitpoints() == maxHitpoints
	 * @post	The maximum hitpoints of this creature is set to the given maxHitpoints.
	 * 			| new.getMaxHitpoints() == maxHitpoints
	 * @post	The anchors and its objects are set to the given anchors and anchorObjects.
	 * 			| new.getAnchors == (anchors, anchorObjects)
	 * @throws	IllegalArgumentException()
	 * 			This creature can't have this name.
	 * 			| !canHaveAsName(name)
	 * @throws 	IllegalArgumentException
	 * 			The size of the given arraylist anchors can't be zero.
	 * 			| anchors.size() == 0
	 * @throws	IllegalArgumentException
	 * 			There can only be one object in an anchor.
	 * 			| anchorObjects.get(i) instanceof List ||
	 * 			|		anchorObjects.get(i) instanceof Map ||
				|		anchorObjects.get(i) instanceof Set
	 */
	@Raw
	protected Creature(String name, BigDecimal strength, int maxHitpoints,
			ArrayList<String> anchors, ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		setName(name);
		setStrength(strength);
		setHitpoints(maxHitpoints);
		setMaxHitpoints(maxHitpoints);
		setAnchors(anchors);
		setAnchorObjects(anchorObjects);
	}
	
	/**********************************
	 * name
	 **********************************
	
	/**
	 * Variable referencing the name of a creature.
	 */
	protected String name;
	
	/**
	 * Return the name of this creature.
	 */
	@Raw @Basic
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the name of this creature to the given name.
	 * 
	 * @post	The name of this creature is equal to name.
	 * 			| new.getName() == name
	 * @throws	IllegalArgumentException()
	 * 			This creature can't have this name.
	 * 			| !canHaveAsName(name)
	 */
	@Raw
	protected abstract void setName(String name) throws IllegalArgumentException;
	
	/**
	 * Checks whether this creature can have the given name as name.
	 * 
	 * @param 	name
	 * 			The name to check.
	 * @return	True if and only if the given name is effective and the name starts with
	 * 			an uppercase letter.
	 * 			| result == ((name != null) && (Character.isUpperCase(name.charAt(0))))
	 */
	@Raw
	public boolean canHaveAsName(String name){
		if (name == null){
			return false;
		}
		else if (!Character.isUpperCase(name.charAt(0))){
			return false;
		}
		else {
			return true;
		}
	}
	
	/************************************
	 * hitpoints
	 ************************************/
	
	/**
	 * Variable referencing the current hitpoints of a creature.
	 */
	protected int hitpoints;
	
	/**
	 * Variable referencing the maximum hitpoints of this creature.
	 */
	protected int maxHitpoints;
	
	/**
	 * Return the current hitpoints of this creature.
	 */
	@Basic @Raw
	public int getHitpoints(){
		return this.hitpoints;
	}
	
	/**
	 * Return the maximum hitpoints of this creature.
	 */
	@Basic @Raw
	public int getMaxHitpoints(){
		return this.maxHitpoints;
	}
	
	/**
	 * Set the hitpoints to the given hitpoints.
	 * 
	 * @param 	hitpoints
	 * 			The hitpoints of this creature.
	 * @pre		The hitpoints must be valid, if the creature is fighting or not.
	 * 			| if (isFighting()){
	 * 			|		canHaveAsHitpointsFighting(hitpoints) }
	 * 			| if (!isFighting()){
	 * 			|		canHaveAsHitpointsNotFighting(hitpoints) }
	 * @post	The hitpoints are set to the given hitpoints.
	 * 			| new.getHitpoints() == hitpoints
	 */
	@Raw
	protected void setHitpoints(int hitpoints){
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Check whether the hitpoints are valid when the creature is fighting.
	 * 
	 * @param 	hitpoints
	 * 			The hitpoints to check.
	 * @return	True if and only if the hitpoints are not negative.
	 * 			| result == hitpoints >= 0
	 */
	@Raw
	public boolean canHaveAsHitpointsFighting(int hitpoints){
		return (hitpoints >= 0);
	}
	
	/**
	 * Check whether the hitpoints are valid when the creature is not fighting.
	 * 
	 * @param 	hitpoints
	 * 			The hitpoints to check.
	 * @return	True if and only if the hitpoints is a prime number.
	 * 			| result == (hitpoints is prime)
	 */
	@Raw
	public boolean canHaveAsHitpointsNotFighting(int hitpoints){
		if (hitpoints < 0){
			return false;
		}
		boolean prime = true;
		for (int i=1; i <= Math.round(Math.sqrt(hitpoints)); i++){
			if (hitpoints % i == 0){
				prime = false;
				break;
			}
		}
		return prime;
	}
	
	/**
	 * Set the maximum hitpoints to the given maxHitpoints.
	 * 
	 * @param	maxHitpoints
	 * 			The maximum hitpoints of this creature.
	 * @pre		The maxHitpoints must be valid for a creature who is not fighting.
	 * 			| canHaveAsHitpointsNotFighting(maxHitpoints)
	 * @post	The maxHitpoints is set to maxHitpoints
	 * 			| new.getMaxHitpoints() == maxHitpoints
	 */
	@Raw
	protected void setMaxHitpoints(int maxHitpoints){
		this.maxHitpoints = maxHitpoints;
	}
	
	/**
	 * Change the maximum hitpoints to the given maxHitpoints.
	 * 
	 * @param	maxHitpoints
	 * 			The new maximum hitpoints of this creature.
	 * @pre		The maxHitpoints must be valid for a creature who is fighting or not fighting
	 * 			| canHaveAsHitpointsFighting(maxHitpoints) ||
	 * 			|		canHaveAsHitpointsNotFighting(maxHitpoints)
	 * @post	The maxHitpoints is set to maxHitpoints
	 * 			| new.getMaxHitpoints() == maxHitpoints
	 */
	protected void changeMaxHitpoints(int maxHitpoints){
		
	}
	
	/************************************
	 * strength
	 ************************************
	
	/**
	 * Variable referencing the strength of a creature.
	 */
	protected BigDecimal strength;
	
	/**
	 * Constant with the default value of strength.
	 */
	private static final BigDecimal DEFAULT_STRENGTH = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	/**
	 * Return the default value of strength.
	 */
	@Raw @Basic @Immutable
	public final static BigDecimal getDefaultStrength(){
		return DEFAULT_STRENGTH;
	}
	
	/**
	 * Return the strength of this creature.
	 */
	@Raw @Basic
	public BigDecimal getStrength(){
		return this.strength;
	}
	
	/**
	 * Set strength of this creature to the given strength.
	 * 
	 * @param 	strength
	 * 			The strength of this creature.
	 * @post	If the given strength is not valid, the strength is set
	 * 			to the default value.
	 * 			| if (!isValidStrength(strength)){
	 * 			| 		strength = getDefaultStrength() }
	 * @post	The strength of this creature is equal to the given strength rounded
	 * 			(using HALF_DOWN) to a decimal number with 2 fractional digits.
	 * 			| if (numeral.scale() != 2){
	 * 			|		strength = strength.setScale(2, RoundingMode.HALF_UP) }
	 * @post	The strength for this creature is the same as strength.
	 * 			| new.getStrength() == strength
	 */
	@Raw
	protected void setStrength(BigDecimal strength){
		if (!isValidStrength(strength)){
			strength = getDefaultStrength();
		}
		if (strength.scale() != 2){
			strength = strength.setScale(2, RoundingMode.HALF_UP);
		}
		this.strength = strength;
	}
	
	/**
	 * Check whether the given strength is valid.
	 * 
	 * @param 	strength
	 * 			The strength to check.
	 * @return	True if and only if the given strength is effective and not negative.
	 * 			| result == (strength != null && strength.signum() != -1)
	 */
	@Raw
	public static boolean isValidStrength(BigDecimal strength){
		if (strength == null || strength.signum() == -1){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Multiply the strength with the given number.
	 * 
	 * @param 	number
	 * 			The number to multiply the strength from this creature with.
	 * @post	The strength of this creature is multiplied by the given number.
	 * 			| new.getStrength() == this.getStrength().multiply(new BigDecimal(number))
	 */
	protected void multiplyStrength(int number){
		setStrength(this.getStrength().multiply(new BigDecimal(number)));
	}
	
	/**
	 * Divide the strength with the given number.
	 * 
	 * @param 	number
	 * 			The number to divide the strength from this creature by.
	 * @post	If the given number is zero, it is changed to one, because division by zero
	 * 			is impossible.
	 * 			| if (number == 0){
	 * 			|		number = 1 }
	 * @post	The strength of this creature is divided by number.
	 * 			| new.getStrength() == this.getStrength().divide(new BigDecimal(number), RoundingMode.HALF_UP)
	 */
	protected void divideStrength(int number){
		if (number == 0){
			number = 1;
		}
		setStrength(this.getStrength().divide(new BigDecimal(number), RoundingMode.HALF_UP));
	}
	
	/**************************************
	 * anchors
	 **************************************/
	
	/**
	 * Variable referencing the anchors with their object of a creature.
	 */
	protected HashMap<String, Object> anchors = new HashMap<String, Object>();
	
	/**
	 * Return the anchors and what's in the anchor of this creature.
	 */
	@Raw @Basic
	public HashMap<String, Object> getAnchors(){
		return this.anchors;
	}
	
	/**
	 * Set the anchors for this creature.
	 * 
	 * @param 	anchors
	 * 			The anchors of this creature.
	 * @throws 	IllegalArgumentException
	 * 			The size of the given arraylist can't be zero.
	 * 			| anchors.size() == 0
	 */
	@Raw
	protected void setAnchors(ArrayList<String> anchors)
			throws IllegalArgumentException{
		if (anchors.size() == 0){
			throw new IllegalArgumentException("A creature must have at least 1 anchor.");
		}
		for (int i = 0; i < anchors.size(); i++){
			this.anchors.put(anchors.get(i), null);
		}
	}
	
	
	/**
	 * The anchors of this creature are set to the objects in the given list anchorObjects.
	 * 
	 * @param 	anchorObjects
	 * 			The list with all the objects that must go in an anchor.
	 * @post	The positions of the anchor are filled with the given objects in the list.
	 * 			| new.getAnchors() ==	for all keys {
	 * 			|								(key, value of key) in anchor }
	 */
	@Raw
	protected abstract void setAnchorObjects(ArrayList<Object> anchorObjects)
			throws IllegalArgumentException;
	
	/*************************************
	 * hit
	 *************************************/
	
	/**
	 * Return random number between 0 and 100.
	 */
	protected static int randomNumber(){
		return ThreadLocalRandom.current().nextInt(0, 101);
	}
	
	/**
	 * Check whether the punch that was given is effective or not.
	 * @return
	 */
	public abstract boolean effectiveHit();
	
	/*************************************
	 * collect treasures
	 *************************************/
	
	protected abstract void collectTreasures();
	
}
