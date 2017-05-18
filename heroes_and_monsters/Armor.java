package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Armor extends Ownable implements Protection{
	
	/******************************************
	 * Constructors
	 ******************************************/
	
	public Armor(){
		super();
		}
	
	/******************************************
	 * Armor Type
	 ******************************************/
	
	private ArmorType type = ArmorType.STANDARD;
	

	/******************************************
	 * Protection
	 ******************************************/

	/**
	 * Variable registering the value of the current damage.
	 */
	public int protection = 1;
	
	/**
	 * Returns the current value for the protection of an armor.
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
		if (canHaveAsProtection(protection)){
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
	 *  	   | if ((increase <= 0)||(increase >= (this.getMaxProtection() - this.getCurrentProtection())))
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
	 * 		   | result == ((protection >= 1) && (protection <= type.getMaxProtection()))
	 */
	@Override
	public boolean canHaveAsProtection(int protection) {
		return ((protection >= 1)&&(protection <= type.getMaxProtection()));
	}
	
	/*******************************
	 * identification
	 *******************************/
	
	/**
	 * Check whether the given identification is valid.
	 *  
	 * @param 	identification
	 * 			The identification to check.
	 * @return	True if and only if the identification is positive, prime and if it is one
	 * 			of the first 1000 armors, it must also be unique.
	 */
	@Raw
	public boolean canHaveAsIdentification(long identification){
		boolean prime = true;
		for (int i=1; i <= Math.round(Math.sqrt(identification)); i++){
			if (identification % i == 0){
				prime = false;
				break;
			}
		}
		return (identification >= 0 && prime && (idListArmors.size() >= 1000 ||
				!idListArmors.contains(identification)));
	}
	
	/**
	 * Set the identification to the given identification if it is valid, otherwise it will
	 * be set to a random valid value.
	 * 
	 * @param 	identification
	 * 			The identification of this armor.
	 * @post	If the given identification is unvalid, the identification is set to a
	 * 			random valid identification. If it is valid, it is set to the given
	 * 			identification.
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		if (!canHaveAsIdentification(identification)){
			identification = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
			while (!canHaveAsIdentification(identification)){
				identification = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
			}
		}
		this.identification = identification;
		this.idListArmors.add(identification);
	}
}
