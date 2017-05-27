package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

import java.util.concurrent.ThreadLocalRandom;

import Exceptions.OwnableIsTerminatedException;


/**
 * A class of weapons.
 * 
 * @invar	Every weapon must have a correct identification.
 * @invar	Every weapon must have a correct damage.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Weapon extends Ownable implements Damage, Comparable<Weapon>{

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new weapon with an identification, weight, unit and damage.
	 * 
	 * @param	weight
	 * 			The weight of this weapon.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @param	damage
	 * 			The damage of this weapon
	 * @pre   	the given damage must be a legal number.
	 * @effect	This weapon is initialized as an ownable with a calculated identification 
	 * 			and the given weight and unit.
	 * @post	The damage of this weapon is set to damage.
	 */
	@Raw
	public Weapon(double weight, Unit unit, int damage){
		super(calculateValidIdentification(), weight, unit);
		setCurrentDamage(damage);
	}
	
	/**********************************************************
	 * Damage - nominaal
	 **********************************************************/
	
	/**
	 * Variable registering the value of the current damage.
	 */
	private int damage = 7;
	
	/**
	 * Variable registering the maximum value of the weapon.
	 */
	private static int maxDamage = 100;
	
	/**
	 * Returns the current value for the damage of a weapon.
	 * 
	 * @return The resulting number must be bigger than 1 and smaller than the maximum damage.
	 * 		   | result >= 1 && result <= maximumDamage
	 * @return The resulting number must be a multiple of 7.
	 * 		   | result % 7 = 0
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override @Basic
	public int getCurrentDamage() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return this.damage;		
	}

	/**
	 * Returns the maximum integer allowed for the damage of the weapon.
	 * 
	 * @return The resulting number must be bigger than 1.
	 * 		   | result >= 1
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public int getMaximumDamage() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return Weapon.maxDamage;
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
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public void setCurrentDamage(int damage) throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		this.damage=damage;	
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
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public void setMaximumDamage(int damage) throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		if (this.isValidMaximumDamage(damage)){
			Weapon.maxDamage=damage;
		}		
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
		return ((damage >= 1)&&(damage <= Weapon.maxDamage)&&(damage % 7 == 0));
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
	
	/******************************
	 * identification
	 ******************************
	
	/**
	 * Return a valid identification for a weapon.
	 */
	private static long calculateValidIdentification(){
		long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		while (!canHaveAsIdentification(id)){
			id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		}
		return id;
	}
	
	/**
	 * Check whether the identification is correct.
	 * 
	 * @param 	identification
	 * 			The identification to check.
	 * @return	False if the number cannot be divided by 6. Also false if there are not
	 * 			made 1000 weapons yet and the identification already exists. True otherwise.
	 */
	public static boolean canHaveAsIdentification(long identification){
		return (identification%6 == 0 && (idListWeapons.size() >= 1000 ||
				!idListWeapons.contains(identification)));
	}
	
	/**
	 * Set the identification of a weapon to an even positive number that can be divided
	 * by 3. The first thousand weapons have a unique number.
	 * 
	 * @param 	identification
	 * 			The identification of this weapon.
	 * @effect	The identification is added to the list of identifications. The size of
	 * 			this list is increased by one.
	 * @post	The identification is set to a positive even number that can be divided
	 * 			by 3.
	 */
	@Override @Raw
	protected void setIdentification(long identification){
		this.identification = identification;
		idListWeapons.add(identification);
	}

	/******************************
	 * value
	 ******************************/
	
	/**
	 * Checks whether or not the given integer is a valid value.
	 * 
	 * @param  value
	 * 		   The integer that needs to be checked.
	 * @return True if the integer 'value' is smaller than or equal to 200 and bigger than or equal to 1.
	 * 		   | result == (super.isValidValue(value)&&(value>=1)&&(value<=200)
	 */
	@Override
	public boolean isValidValue(Ducat value){
		return (super.isValidValue(value)&&(value.getValue()>=1)&&(value.getValue()<=2*this.getMaximumDamage()));
	}
	
	/**
	 * Calculates the value in the ducats of the weapon.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	@Override
	public Ducat getValue() {
		Ducat value = new Ducat(this.getCurrentDamage()*2);
		if (this.isValidValue(value)){
			return value;
		}
		else{
			return new Ducat(1);
		}
	}
	
	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Terminate this weapon.
	 * 
	 * @post	Terminated is set to true.
	 * 			| this.terminated = true
	 */
	protected void terminate(){
		this.terminated = true;
	}

	/**********************************
	 * comparable
	 **********************************/
	
	/**
	 * Compare the damages of this weapon and another.
	 * 
	 * @return The subtraction of the current damage of this weapon and the current damage of the other weapon.
	 * 		   | result == (this.getCurrentDamage() - other.getCurrentDamage())
	 */
	@Override
	public int compareTo(Weapon other) {
		return (this.getCurrentDamage() - other.getCurrentDamage());
	}
}
