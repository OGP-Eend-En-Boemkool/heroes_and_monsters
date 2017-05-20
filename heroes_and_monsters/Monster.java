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
public class Monster extends Creature implements Damage, Protection {
	
	/******************************************
	 * Constructors
	 ******************************************/

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
	 ******************************************/
	
	/**
	 * Set the name of this monster to the given name.
	 * 
	 * @effect	The name of this monster is set to the given name.
	 */
	@Raw @Override
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
	 * @effect	The anchor objects are added to a random anchor.
	 * @throws	IllegalArgumentException
	 * 			There can't be more objects than anchors.
	 * @throws 	IllegalArgumentException
	 * 			One of the objects can't be added to an anchor.
	 */
	@Raw @Override
	protected void setAnchorObjects(ArrayList<Object> anchorObjects)
			throws IllegalArgumentException{
		if (anchorObjects.size() > anchors.size()){
			throw new IllegalArgumentException("There can't be more objects than anchors.");
		}
		if (anchorObjects != null && anchorObjects.size() != 0){
			Set<String> set = anchors.keySet();
			ArrayList<String> list = (ArrayList)set;
			ArrayList<Integer> full = new ArrayList<>();
			for (int j = 0; j < anchorObjects.size(); j++){
				Integer random = ThreadLocalRandom.current().nextInt(0, anchors.size());
				while (full.contains(random)){
					random = ThreadLocalRandom.current().nextInt(0, anchors.size());
				}
				addToAnchor(anchorObjects.get(j), list.get(random));
				full.add(random);
			}
		}
		
	}
	
	/**************************************
	 * Damage - nominaal
	 **************************************/
	
	/**
	 * Variable registering the value of the current damage.
	 */
	public int damage = 7;
	
	/**
	 * Variable registering the maximum value of the monster.
	 */
	public static int maxDamage = 100;
	
	/**
	 * Returns the current value for the damage of the monster.
	 * 
	 * @return The resulting number must be bigger than 1 and smaller than the maximum damage.
	 * 		   | result >= 1 && result <= maximumDamage
	 * @return The resulting number must be a multiple of 7.
	 * 		   | result % 7 = 0
	 */
	@Basic @Override
	public int getCurrentDamage() {
		return this.damage;
	}

	/**
	 * Returns the maximum integer allowed for the damage of the monster.
	 * 
	 * @return The resulting number must be bigger than 1.
	 * 		   | result >= 1
	 */
	@Override
	public int getMaximumDamage() {
		return Monster.maxDamage;
	}

	/**
	 * Sets the current damage to the given damage.
	 * 
	 * @param damage
	 * 		  The number of damage to which the current damage needs to be set.
	 * @pre   the given damage must be a legal number.
	 * 		  | this.canHaveAsDamage(damage)
	 * @post  the number to which the damage is set is equal to the given number.
	 * 		  | new.getCurrentDamage().equals(damage) 
	 */
	@Override
	public void setCurrentDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Sets the maximum damage to the given number.
	 * 
	 * @param damage
	 * 		  The number of damage to which the maximum damage needs to be set.
	 * @pre   the given damage must be a valid number.
	 * 		  | this.isValidMaximumDamage(damage)
	 * @post  the number to which the maximum damage is set is equal to the given number.
	 * 		  | new.getMaximumDamage().equals(damage)
	 */
	@Override
	public void setMaximumDamage(int damage) {
		Monster.maxDamage = damage;	
	}

	/**
	 * Checks whether or not the given damage is a legal number.
	 * 
	 * @param  damage
	 * 		   the number that needs to be checked.
	 * @return true if the integer is bigger than 1 and smaller than the maximum damage and it's a 
	 * 		   multiple of 7, false otherwise.
	 * 		   | result == ((damage >= 1) && (damage <= maximumDamage) && (damage % 7 == 0))
	 */
	@Override
	public boolean canHaveAsDamage(int damage) {
		return ((damage >= 1)&&(damage <= Monster.maxDamage)&&(damage % 7 == 0));
	}

	/**
	 * Checks whether or not the given damage is a valid damage.
	 * 
	 * @param  damage
	 * 		   the number that needs to be checked.
	 * @return true if the integer is bigger than 1 
	 * 		   | result == (damage >= 1)
	 */
	@Override
	public boolean isValidMaximumDamage(int damage) {
		return (damage >= 1);
	}

	/**************************************
	 * Protection
	 **************************************/
	
	/**
	 * Variable registering the value of the current damage.
	 */
	public int protection = 1;
	
	/**
	 * Variable registering the maximum value of the damage.
	 */
	public final int maxProtection = 100;
	
	/**
	 * Returns the current value for the protection of a monster.
	 * 
	 * @return The resulting number must be bigger than or equal to 1 and smaller than the maximum protection.
	 * 		   | (result >= 1) && (result <= maxProtection)
	 */
	@Override
	public int getCurrentProtection() {
		return this.protection;
	}

	/**
	 * Sets the current protection to the given number.
	 * 
	 * @param protection
	 * 		  The number to which the protection needs to be set.
	 * @post  The given number must be a legal number.
	 * 		  | canHaveAsProtection(protection)
	 * @post  The number to which the protection is set is equal to the given number.
	 * 		  | new.getCurrentProtection().equals(protection) 
	 */
	@Override
	public void setCurrentProtection(int protection) {
		if (this.canHaveAsProtection(protection)){
			this.protection = protection;
		}
	}

	/**
	 * Decreases the current protection with a given integer 'decrease'.
	 * 
	 * @param  decrease
	 * 		   The integer we use to decrease the current protection with.
	 * @post   The new protection is the original protection decreased with an integer 'decrease'.
	 * 		   | new.getCurrentProtection().equals(this.getCurrentProtection() - decrease)
	 * @post   The new protection is smaller than the original protection.
	 * 		   | new.getCurrentProtection() < this.getCurrentProtection()
	 * @post   The new protection must be a legal case
	 * 	       | canHaveAsProtection(new.getCurrentprotection())
	 * @throws IllegalArgumentException
	 * 		   Throws an IllegalArgumentException when the integer 'decrease' is not positive or when 
	 * 		   'decrease' is bigger or equal to the original protection.
	 * 		   | if ((decrease <= 0)||(decrease >= this.getCurrentProtection()))
	 * 		   | 	throw new IllegalArgumentException()
	 * @effect The new protection of this object is set to the original protection decreased with 'decrease'
	 * 		   | setCurrentProtection(this.getCurrentProtection() - decrease)
	 */
	@Override
	public void decreaseProtection(int decrease) throws IllegalArgumentException {
		if (decrease > 0){
			int decreasedProtection = this.protection - decrease;
			if (canHaveAsProtection(decreasedProtection)){
				this.setCurrentProtection(decreasedProtection);
			}
			else {
				throw new IllegalArgumentException("Invalid protection reached");
			}
		}
		else {
			throw new IllegalArgumentException("Use a positive number to decrease.");
		}
	}

	/**
	 * Increases the current protection with a given integer 'increase'.
	 * 
	 * @param  increase
	 * 		   The integer we use to increase the current protection with.
	 * @post   The new protection is the original protection increased with an integer 'increase'.
	 * 		   | new.getCurrentProtection().equals(this.getCurrentProtection() + increase)
	 * @post   The new protection is bigger than the original protection.
	 * 	  	   | new.getCurrentProtection() > this.getCurrentProtection()
	 * @post   The new protection must be a legal case
	 * 	       | canHaveAsProtection(new.getCurrentprotection())
	 * @throws IllegalArgumentException
	 * 		   Throws an IllegalArgumentException when the integer 'increase' is not positive
	 *  	   or when 'increase' is bigger than the maximum protection decreased with the original 
	 *  	   protection.
	 *  	   | if ((increase <= 0)||(increase >= (this.maxProtection() - this.getCurrentProtection())))
	 * @effect The new protection of this object is set to the original protection increased with 'increase'
	 * 		   | setCurrentProtection(this.getCurrentProtection() + increase)
	 */
	@Override
	public void increaseProtection(int increase) throws IllegalArgumentException {
		if (increase > 0){
			int increasedProtection = this.protection + increase;
			if (canHaveAsProtection(increasedProtection)){
				this.setCurrentProtection(increasedProtection);
			}
			else {
				throw new IllegalArgumentException("Invalid protection reached.");
			}
		}
		else {
			throw new IllegalArgumentException("Use a positive number to increase.");
		}
	}

	/**
	 * Checks whether or not the given protection is a legal case.
	 * 
	 * @param  protection
	 * 		   The integer that needs to be checked.
	 * @return True if the given integer is bigger than 0 and smaller than the maximum Protection, 
	 * 		   false otherwise.
	 * 		   | result == ((protection >= 1) && (protection <= this.maxProtection))
	 */
	@Override
	public boolean canHaveAsProtection(int protection) {
		return ((protection >= 1)&&(protection <= this.maxProtection));
	}

	
	/**************************************
	 * Capacity - totaal
	 **************************************/
	
	/**
	 * Return the maximum capacity of the monster.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @return the resulting number must be the strength multiplied by 9.
	 * 		   | result == (this.getStrength.floatValue() * 9)
	 */
	@Override
	public double getMaximumCapacity(Unit unit) {
		float strength = this.getStrength().floatValue();
		return (unit.convertFromKilogram(strength * 9));
	}

	/**
	 * Return the used part of the total capacity of the monster.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @return the resulting number cannot be bigger than the maximum capacity of the object.
	 * 		   | result <= this.getMaximumCapacity()
	 */
	@Override
	public double getUsedCapacity(Unit unit) {
		// TODO
		return 0;
	}

}
