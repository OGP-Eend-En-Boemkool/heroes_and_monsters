package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of creatures.
 * 
 * @invar	Every creature must have a valid name.
 * 			| canHaveAsName(getName())
 * @invar	Every creature must have a valid strength.
 * 			| isValidStrength(getStrength())
 * @invar	Every creature must have a valid maxHitpoints.
 * 			| canHaveAsHitpointsNotFighting(getMaxHitpoints()) ||
 * 			|	canHaveAsHitpointsFighting(getMaxHitpoints())
 * @invar	Every creature must have a valid hitpoints.
 * 			| if this creature is fighting {
 * 			|		canHaveAsHitpointsFighting(getHitpoints()) }
 * 			| else {
 * 			|		canHaveAsHitpointsNotFighting(getHitpoints()) }
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public abstract class Creature implements Capacity{

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new Creature with a name, a strength, a maximum hitpoints a
	 * current hitpoints, anchors and anchorObjects.
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
	 * @throws 	IllegalArgumentException
	 * 			An object can't be added to its associated anchor.
	 * 			| !canAddToAnchor(object, anchor)
	 */
	@Raw @Model
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
		return new HashMap(this.anchors);
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
	
	/**
	 * The given object is added to the given anchor.
	 * 
	 * @param 	object
	 * 			The object to add.
	 * @param 	anchor
	 * 			The anchor to add it to.
	 * @post	The given object is added to the given anchor.
	 * 			| this.anchors.put(anchor, object)
	 * @effect	The holder of the given object is set to this (if it is a ownable).
	 * 			| if (object instanceof Ownable){
	 * 			|		object.setHolder(this) }
	 * @throws 	IllegalArgumentException
	 * 			This object can't be added to this anchor.
	 * 			| !canAddToAnchor(object, anchor)
	 */
	@Raw
	public void addToAnchor(Object object, String anchor)
			throws IllegalArgumentException {
		if (!canAddToAnchor(object, anchor)){
			throw new IllegalArgumentException("The object can't be added to this anchor.");
		}
		this.anchors.put(anchor, object);
		if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			ownable.setHolder(this);
		}
	}
	
	/**
	 * Check whether the given object can be added to the given anchor.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @param 	anchor
	 * 			The anchor to check.
	 * @return	True if and only if this creature has such anchor, that anchor doesn't
	 * 			already have another object and the object is an ownable or a ducat. But
	 * 			if it's a ducat and its value is not equal to one, it is false 'though.
	 * 			| result == this.getAnchors().keySet().contains(anchor) &&
	 *			|			this.getAnchors().get(anchor) == null &&
	 *			|			((object instanceof Ownable) || (object instanceof Ducat)) &&
	 *			|			(if (object instanceof Ducat) {
	 *			|					object.getValue() == 1 } )
	 */
	@Raw
	public boolean canAddToAnchor(Object object, String anchor){
		if (object instanceof Ducat){
			if (((Ducat) object).getValue() != 1){
				return false;
			}
		}
		return (this.getAnchors().keySet().contains(anchor) &&
				this.getAnchors().get(anchor) == null &&
				((object instanceof Ownable) || (object instanceof Ducat)));
	}
	
	/**
	 * Empty the given anchor.
	 * 
	 * @param 	anchor
	 * 			The anchor to empty.
	 * @post	The given anchor has no object
	 * 			| new.getAnchors().get(anchor) == null
	 * @effect	The holder of the object in this anchor is set to null.
	 * 			| if (object instanceof Ownable){
	 * 			| 		object.setHolder() }
	 * @throws 	IllegalArgumentException
	 * 			This creature has no such anchor.
	 * 			| !getAnchors().keySet().contains(anchor)
	 */
	public void emptyAnchor(String anchor) throws IllegalArgumentException {
		if (!getAnchors().keySet().contains(anchor)){
			throw new IllegalArgumentException("This creature has no such anchor.");
		}
		Object object = this.anchors.remove(anchor);
		if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			ownable.setHolder();
		}
	}
	
	/**
	 * Drop the given object from its anchor.
	 * 
	 * @param 	object
	 * 			The object to drop.
	 * @effect	The anchor from the given object is emptied.
	 * 			| for all anchors in getAnchors() {
	 * 			|		if (getAnchors().get(anchor) == object) {
	 * 			|				emptyAnchor(anchor) } }
	 * @throws 	IllegalArgumentException
	 * 			The given object cannot be dropped.
	 * 			| !canDropFromAnchor(object)
	 */
	public void dropFromAnchor(Object object) throws IllegalArgumentException {
		if (!canDropFromAnchor(object)){
			throw new IllegalArgumentException("Object cannot be dropped.");
		}
		ArrayList<String> anchors = (ArrayList) getAnchors().keySet();
		for (int i = 0; i < anchors.size(); i++){
			if (getAnchors().get(anchors.get(i)) == object){
				emptyAnchor(anchors.get(i));
			}
		}
	}
	
	/**
	 * Check whether the given object can be dropped from its anchor.
	 * 
	 * @param	object
	 * 			The object to check.
	 * @return	True if and only if this creature carries this object.
	 * 			| getAnchors().containsValue(object)
	 */
	@Raw
	public boolean canDropFromAnchor(Object object){
		return (getAnchors().containsValue(object));
	}
	
	/**
	 * Pass along an object from one creature to another.
	 * 
	 * @param 	object
	 * 			The object to pass along.
	 * @param 	creature
	 * 			The creature to pass it along to.
	 * @param 	anchor
	 * 			The anchor from the other creature to put it in.
	 * @effect	The object is dropped from this creature.
	 * 			| this.dropFromAnchor(object)
	 * @effect	The object is added to the given anchor from the given creature.
	 * 			| creature.addToAnchor(object, anchor)
	 */
	public void passAlong(Object object, Creature creature, String anchor){
		this.dropFromAnchor(object);
		creature.addToAnchor(object, anchor);	// methode ook nog schrijven voor rugzakken
	}
	
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
	 * 
	 * @return True if hit is bigger (or equal to) than the protection of the creature that has been hit, False otherwise.
	 * 		   | result == (hit >= this.getCurrentProtection())
	 */
	protected boolean effectiveHit(int hit){
		return (hit >= this.getCurrentProtection());
	}
	
	/**
	 * Return the resulting damage of a certain hit of this creature
	 * 
	 */
	protected abstract int getResultingDamage();
	
	protected abstract int deathblow();
	
	/**
	 * 
	 * @param other
	 * 		  the creature that is hit.
	 */
	public void hit(Creature other){
		int randy = Creature.randomNumber();
		if (effectiveHit(randy)){
			int newHitpointsOther = other.getHitpoints() - this.getResultingDamage();
			if (newHitpointsOther < 0){
				newHitpointsOther = 0;
				this.deathblow();
			}
			other.setHitpoints(newHitpointsOther);
		}
	}
	
	/*************************************
	 * collect treasures
	 *************************************/
	
	protected abstract void collectTreasures();
	
	
	/**********************************
	 * Protection 
	 **********************************/
	
	public abstract int getCurrentProtection();
}
