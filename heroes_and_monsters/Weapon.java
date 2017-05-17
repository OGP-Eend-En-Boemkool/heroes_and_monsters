package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends Ownable implements Damage{

	
	public Weapon(){
		
	}
	
	/**********************************************************
	 * Damage - nominaal
	 **********************************************************/
	
	/**
	 * Variable registering the value of the current damage.
	 */
	public int damage = 7;
	
	/**
	 * Variable registering the maximum value of the weapon.
	 */
	public static int maxDamage = 100;
	
	/**
	 * Returns the current value for the damage of a weapon.
	 * 
	 * @return The resulting number must be bigger than 1 and smaller than the maximum damage.
	 * 		   | result >= 1 && result <= maximumDamage
	 * @return The resulting number must be a multiple of 7.
	 * 		   | result % 7 = 0
	 */
	@Override @Basic
	public int getCurrentDamage() {
		return this.damage;		
	}

	/**
	 * Returns the maximum integer allowed for the damage of the weapon.
	 * 
	 * @return The resulting number must be bigger than 1.
	 * 		   | result >= 1
	 */
	@Override
	public int getMaximumDamage() {
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
	 */
	@Override
	public void setCurrentDamage(int damage) {
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
	 */
	@Override
	public void setMaximumDamage(int damage) {
		Weapon.maxDamage=damage;		
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
	 * 
	 */
	@Raw
	private long calculateValidIdentification(){
		long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		if (idListWeapons.size() >= 1000){
			while (id%6 != 0){
				id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
			}
		}
		else {
			while (id%6 != 0 || idListWeapons.contains(id)){
				id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
			}
		}
		return id;
	}
	
	/**
	 * Set the identification of a weapon to an even positive number that can be divided
	 * by 3. The first thousand weapons have a unique number.
	 * 
	 * @param 	identification
	 * 			The identification of this weapon.
	 * @post	The identification is set to a positive even number that can be divided
	 * 			by 3.
	 */
	@Override @Raw
	protected void setIdentification(long identification){
		this.identification = identification;
		idListWeapons.add(identification);
	}

}
