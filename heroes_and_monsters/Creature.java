package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import Exceptions.*;

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
	 * 
	 * @throws	CreatureIsDeadException
	 * 			This creature is dead.
	 * 			| getKilled()
	 */
	@Raw @Basic
	public String getName() throws CreatureIsDeadException {
		if (getKilled()){
			throw new CreatureIsDeadException(this);
		}
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
		for (int i=2; i <= Math.round(Math.sqrt(hitpoints)); i++){
			if (hitpoints % i == 0){
				return false;
			}
		}
		return true;
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
		this.maxHitpoints = maxHitpoints;
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
	 * 
	 * @throws	CreatureIsDeadException
	 * 			This creature is dead.
	 * 			| getKilled()
	 */
	@Raw @Basic
	public BigDecimal getStrength() throws CreatureIsDeadException {
		if (getKilled()){
			throw new CreatureIsDeadException(this);
		}
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
		return (!(strength == null || strength.signum() == -1));
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
		return new HashMap<String, Object>(this.anchors);
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
	 * 			already have another object, this creature is still alive and the object
	 * 			is an ownable or a ducat. But if it's a ducat and its value is not equal
	 * 			to one, it is false 'though. And if the object is terminated, it's also
	 * 			false.
	 * 			| result == this.getAnchors().keySet().contains(anchor) &&
	 *			|			this.getAnchors().get(anchor) == null && !getKilled() &&
	 *			|			((object instanceof Ownable) || (object instanceof Ducat)) &&
	 *			|			(if (object instanceof Ducat) {
	 *			|					object.getValue() == 1 } ) &&
	 *			|			(if (object instanceof Ownable) {
	 *			|					!object.getTerminated() } )
	 */
	@Raw
	public boolean canAddToAnchor(Object object, String anchor){
		if (object instanceof Ducat){
			if (((Ducat) object).getValue() != 1){
				return false;
			}
			else if ((((Ducat) object).getWeight(Unit.KG)) + this.getUsedCapacity(Unit.KG) > this.getMaximumCapacity(Unit.KG)){
				return false;
			}
		}
		else if (object instanceof Ownable) {
			if (((Ownable) object).getTerminated()){
				return false;
			}
		}
		if (object instanceof Storage){
			if ((((Storage) object).getTotalWeight(Unit.KG)) + this.getUsedCapacity(Unit.KG) > this.getMaximumCapacity(Unit.KG)){
				return false;
			}
		}
		else if (object instanceof Ownable){
			if ((((Ownable) object).getOwnWeight(Unit.KG)) + this.getUsedCapacity(Unit.KG) > this.getMaximumCapacity(Unit.KG)){
				return false;
			}
		}
		return ((object == null) || (this.getAnchors().keySet().contains(anchor) &&
				this.getAnchors().get(anchor) == null && !getKilled()) &&
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
	 * 			Anchor cannot be emptied.
	 * 			| !canEmptyAnchor(anchor)
	 */
	protected void emptyAnchor(String anchor) throws IllegalArgumentException {
		if (!canEmptyAnchor(anchor)){
			throw new IllegalArgumentException("Anchor cannot be emptied.");
		}
		Object object = this.anchors.put(anchor, null);
		if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			ownable.setHolder();
		}
	}
	
	/**
	 * Check whether the given anchor can be emptied.
	 * 
	 * @param 	anchor
	 * 			The anchor to check.
	 * @return 	True if and only if this creature has such anchor and it is still alive.
	 * 			| result == getAnchors().keySet().contains(anchor) && !getKilled()
	 */
	public boolean canEmptyAnchor(String anchor){
		return (getAnchors().keySet().contains(anchor) && !getKilled());
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
	protected void dropFromAnchor(Object object) throws IllegalArgumentException {
		if (!canDropFromAnchor(object)){
			throw new IllegalArgumentException("Object cannot be dropped.");
		}
		Iterator<String> iterator = this.getAnchors().keySet().iterator();
		while (iterator.hasNext()){
			String anchor = iterator.next();
			if ( this.getAnchors().get(anchor) == object){
				this.emptyAnchor(anchor);
			}
		}
	}
	
	/**
	 * Check whether the given object can be dropped from its anchor.
	 * 
	 * @param	object
	 * 			The object to check.
	 * @return	True if and only if this creature carries this object and the creature is
	 * 			not dead and if the object is an ownable, it's not terminated.
	 * 			| getAnchors().containsValue(object) && !getKilled()
	 */
	@Raw
	public boolean canDropFromAnchor(Object object){
		return (getAnchors().containsValue(object) && !getKilled() &&
				(!(object instanceof Ownable) || !((Ownable) object).getTerminated()));
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
	 * @throws 	IllegalArgumentException
	 * 			The given object cannot be dropped.
	 * 			| !canDropFromAnchor(object)
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be added to the given anchor.
	 * 			| !canAddToAnchor(object, anchor)
	 */
	public void passAlong(Object object, Creature creature, String anchor)
			throws IllegalArgumentException {
		this.dropFromAnchor(object);
		creature.addToAnchor(object, anchor);
	}
	
	/**
	 * Pass the given object from this creature to the given storage.
	 * 
	 * @param 	object
	 * 			The object to pass along.
	 * @param 	storage
	 * 			The storage to pass it to.
	 * @effect	Drop the given object from its anchor.
	 * 			| this.dropFromAnchor(object)
	 * @effect	Add the given object to the given storage.
	 * 			| storage.addToStorage(object)
	 * @throws 	IllegalArgumentException
	 * 			The given object cannot be dropped.
	 * 			| !canDropFromAnchor(object)
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added to the given storage.
	 * 			| !canAddToStorage(object)
	 */
	public void passToStorage(Object object, Storage storage)
			throws IllegalArgumentException {
		this.dropFromAnchor(object);
		storage.addToStorage(object);
	}
	
	/**
	 * Empty all the anchors from this creature.
	 * 
	 * @effect	All the anchors are emptied.
	 * 			| for all anchors from this {
	 * 			|		emptyAnchor(anchor) }
	 */
	protected void emptyAllAnchors() throws IllegalArgumentException {
		Iterator<String> iterator = this.getAnchors().keySet().iterator();
		while (iterator.hasNext()){
			this.emptyAnchor(iterator.next());
		}
	}
	
	/**
	 * Empty this anchor and terminate the object that was in it if needed.
	 * 
	 * @param 	anchor
	 * 			The anchor to empty.
	 * @effect	The given anchor is emptied.
	 * 			| emptyAnchor(anchor)
	 * @effect	If the object that was in the given anchor is an ownable, it is terminated
	 * 			if possible (only armors and weapons get terminated).
	 * 			| if (previous instanceof Ownable){
	 *			|		((Ownable) previous).terminate() }
	 *@throws 	IllegalArgumentException
	 * 			This creature has no such anchor.
	 * 			| !getAnchors().keySet().contains(anchor)
	 */
	public void emptyAnchorAndTerminate(String anchor) throws IllegalArgumentException {
		Object previous = this.getAnchors().get(anchor);
		this.emptyAnchor(anchor);
		if (previous instanceof Ownable){
			((Ownable) previous).terminate();
		}
	}
	
	/**
	 * The given object is dropped from its anchor and terminated if needed.
	 * 
	 * @param	object
	 * 			The object to drop.
	 * @effect	The given object is dropped from its anchor.
	 * 			| dropFromAnchor(object)
	 * @effect	If the object that was in the given anchor is an ownable, it is terminated
	 * 			if possible (only armors and weapons get terminated).
	 * 			| if (previous instanceof Ownable){
	 *			|		((Ownable) previous).terminate() }
	 * @throws 	IllegalArgumentException
	 * 			The given object cannot be dropped.
	 * 			| !canDropFromAnchor(object)
	 */
	public void dropFromAnchorAndTerminate(Object object) throws IllegalArgumentException {
		this.dropFromAnchor(object);
		if (object instanceof Ownable){
			((Ownable) object).terminate();
		}
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
	
	/**
	 * This creature can have the advantages from killing another creature.
	 * 
	 * @param 	opponent
	 * 			The opponent that this creature has killed.
	 */
	protected abstract void deathblow(Creature opponent);
	
	/**
	 * Try to hit the given other creature.
	 * 
	 * @param 	other
	 * 		  	The creature that is hit.
	 * @post	If it is an effective hit with the generated random number, the hitpoints
	 * 			of the other creature decreased by the resulting damage. If that's not a
	 * 			correct number for hitpoints and it's still positive, it is decreased until
	 * 			we reach a correct value or 0. If after that the hitpoints are negative or
	 * 			0, the hitpoints are set to zero.
	 * 			| if (effectiveHit(randy)){
	 *			|		newHitpointsOther = other.getHitpoints() - this.getResultingDamage()
	 *			|		while (!canHaveAsHitpointsNotFighting(newHitpointsOther) &&
	 *			|			newHitpointsOther > 0){
	 *			|				newHitpointsOther-- }
	 *			|	if (newHitpointsOther <= 0){
	 *			|	newHitpointsOther = 0 }
	 *			|	other.setHitpoints(newHitpointsOther)
	 *			|	}
	 * @effect	If the other creature has no hitpoints left, this creature can have the
	 * 			benefits of deathblow.
	 * 			| if (newHitpointsOther <= 0) {
	 * 			|		deathblow(other) }
	 * @throws	CreatureIsDeadException
	 * 			This creature is dead.
	 * 			| getKilled()
	 * @throws	IllegalArgumentException
	 * 			This creature can't hit the given other creature.
	 * 			| !canHitCreature(other)
	 */
	public void hit(Creature other)
			throws CreatureIsDeadException, IllegalArgumentException {
		if (getKilled()){
			throw new CreatureIsDeadException(this);
		}
		if (!canHitCreature(other)){
			throw new IllegalArgumentException("This creature can't hit the given creature.");
		}
		int randy = Creature.randomNumber();
		if (effectiveHit(randy)){
			int newHitpointsOther = other.getHitpoints() - this.getResultingDamage();
			while (!canHaveAsHitpointsNotFighting(newHitpointsOther) &&
					newHitpointsOther > 0){
				newHitpointsOther--;
			}
			if (newHitpointsOther <= 0){
				newHitpointsOther = 0;
				this.deathblow(other);
			}
			other.setHitpoints(newHitpointsOther);
		}
	}
	
	/**
	 * Check whether this creature can hit the given other creature.
	 * 
	 * @param 	other
	 * 			The other creature to check.
	 */
	public abstract boolean canHitCreature(Creature other);
	
	/*************************************
	 * collect treasures
	 *************************************/

	/**
	 * Adds one of the or a few of the possessions of the opponent to its own possessions.
	 * 
	 * @param object
	 * 		  The treasure that this creature steals from its opponent.
	 * @param opponent
	 * 		  The creature that was the opponent from which this creature steals.
	 * @post  The creature will be the new ultimate holder of this object.
	 * 		  | object.getUltimateHolder().equals(this)
	 */
	protected abstract void addTreasure(Object object, Creature opponent);


	/**
	 * Return a hashmap with all the possessions of the opponent in the battle with their classnames as keys and an arraylist of all 
	 * objects of that class as value.
	 * 
	 * @param  opponent
	 * 		   The creature that was beaten in the battle.
	 * @post   All the objects that have the opponent as ultimate holder will be in the arraylist associated with their classname.
	 * 		   Therefore their classname will be one of the keys in this hashmap.
	 * 		   | for all objects{
	 * 		   |  	if object.getUltimateHolder().equals(opponent){
	 * 		   | 		 this.getOpponentsPossessions(opponent).containsKey(object.getClass().toString())
	 * 		   |    	 && this.getOpponentsPossessions(opponent).get(object.getClass().toString()).contains(object)
	 * 		   |	}
	 * 		   | }
	 */
	protected HashMap<String, ArrayList<Object>> getOpponentsPossessions(Creature opponent){
		HashMap<String, ArrayList<Object>> opponentsPossessions = new HashMap<String, ArrayList<Object>>();
		Iterator<Object> iterator = this.getAnchors().values().iterator();
		while (iterator.hasNext()){
			Object object = iterator.next();
			this.addToPossessions(object, opponentsPossessions);
			if (object instanceof Backpack){
				Enumeration<Object> enumeration = ((Backpack)object).getBackpackIterator();
				while (enumeration.hasMoreElements()){
					Object objectInBackpack = enumeration.nextElement();
					this.addToPossessions(objectInBackpack, opponentsPossessions);
				}
			}
		}
		return opponentsPossessions;
	}
		
	/**
	 * Adds a certain object to the hashmaps with all the possessions of the opponent.
	 * 
	 * @param 	object
	 * 		  	The object that is added to the hashmap with the possessions.
	 * @param 	opponentsPossessions
	 * 		  	The hashmap with the possessions to which the object should be added.
	 * @post   	The object will be in the arraylist associated with its classname.
	 * 		   	Therefore its classname will be one of the keys in this hashmap.
	 * 			| if object.getUltimateHolder().equals(opponent){
	 * 		   	| 		 this.getOpponentsPossessions(opponent).containsKey(object.getClass().toString())
	 * 		   	|    	 && this.getOpponentsPossessions(opponent).get(object.getClass().toString()).contains(object)
	 * 		   	| }	
	 */
	protected void addToPossessions(Object object, HashMap<String, ArrayList<Object>> opponentsPossessions){
		if (object != null){
			String classObject = object.getClass().getSimpleName();
			if (opponentsPossessions.containsKey(classObject)){
				opponentsPossessions.get(classObject).add(object);
			}
			else {
				ArrayList<Object> arraylist = new ArrayList<Object>();
				arraylist.add(object);
				opponentsPossessions.put(classObject, arraylist);
			}
		}
	}
	
	/**
	 * Terminate all the remaining objects from a certain class (weapon or armor), the ones that the creature doesn't take with him.
	 * 
	 * @param 	className
	 * 			The name of the class, from which all the remaining objects should be destroyed
	 * @param 	allPossessions
	 * 			The hashmap that contains all the remaining objects.
	 * @pre		The className should always be "Armor" or "Weapon"
	 * 			| (className == "Armor") || (className == "Weapon")
	 * @post	All the objects of the class in this hashmap will be terminated
	 * 			| for all object in allPossessions.get(className){
	 * 			|	object.isTerminated()
	 * 			| }
	 */
	protected void terminateRemainingObjectsFromClass(String className, HashMap<String, ArrayList<Object>> allPossessions){
		Iterator<Object> iterator = allPossessions.get(className).iterator();
		while (iterator.hasNext()){
			((Ownable) iterator.next()).terminate();
		}
	}
	
	/**********************************
	 * Protection 
	 **********************************/
	
	/**
	 * Return the value for the current protection.
	 */
	public abstract int getCurrentProtection();
	
	/**********************************
	 * Capacity
	 **********************************/
	
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
		double weight = 0;
		Iterator<Object> iterator = this.getAnchors().values().iterator();
		while (iterator.hasNext()){
			Object object = iterator.next();
			if (object instanceof Ownable){
				if (object instanceof Storage){
					Storage storage = (Storage) object;
					weight = weight + storage.getTotalWeight(unit);
				}
				else {
					Ownable ownable = (Ownable) object;
					weight = weight + ownable.getOwnWeight(unit);
				}
			}
			else if (object instanceof Ducat){
				Ducat ducat = (Ducat) object;
				weight = weight + ducat.getWeight(unit);
			}
		}
		return weight;
	}
	
	/************************************
	 * killed
	 ************************************/
	
	/**
	 * Variable referencing if this creature is dead or not.
	 */
	private boolean killed = false;
	
	/**
	 * Return if this creature is killed or still alive.
	 */
	public boolean getKilled(){
		return this.killed;
	}
	
	/**
	 * Kill this creature.
	 * 
	 * @post	Killed is set to true.
	 * 			| this.killed = true
	 */
	protected void kill(){
		this.killed = true;
	}
	
}
