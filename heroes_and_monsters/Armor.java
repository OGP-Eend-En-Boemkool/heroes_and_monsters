package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import Exceptions.OwnableIsTerminatedException;

/**
 * A class of armors.
 * 
 * @invar	Every armor must have a correct identification.
 * @invar	Every armor must have a correct protection.
 * @invar	Every armor must have a valid maxValue.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Armor extends Ownable implements Protection{
	
	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new armor with an identification, maximum protection, maximum value,
	 * weight and unit.
	 * 
	 * @param 	identification
	 * 			The identification of this armor.
	 * @param	maxProtection
	 * 			The maximum protection of this armor.
	 * @param	maxValue
	 * 			The maximum value of this armor.
	 * @param	weight
	 * 			The weight of this armor.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @effect	This armor is initialized as an ownable with the given identification, 
	 * 			weight and unit. If the given identification is not a correct
	 * 			identification, the identification is set to a correct identification.
	 * @post  	The maxValue of this armor is set to the given maxValue.
	 * @post  	If the given value isn't valid, maxValue is set to the default.
	 */
	@Raw
	public Armor(long identification, int maxProtection, int maxValue,
			double weight, Unit unit){
		super(identification, weight, unit);
		this.setMaxValue(maxValue);
		this.setMaxProtection(maxProtection);
		}
	
	/******************************************
	 * Armor Type
	 ******************************************/
	
	/**
	 * Variable registering the type of the armor.
	 */
	private ArmorType type = ArmorType.STANDARD;
	
	/**
	 * Return the type of the armor.
	 * 
	 * @return The resulting type must be a valid type.
	 * 		   | (type instanceof ArmorType)
	 */
	private ArmorType getType(){
		return this.type;
	}
	

	/******************************************
	 * Protection
	 ******************************************/

	/**
	 * Variable registering the value of the current protection.
	 */
	private int protection = 1;
	
	/**
	 * Variable registering the maximum protection of this particular armor.
	 */
	private int maxProtection = 1;
	
	/**
	 * Returns the current value for the protection of an armor.
	 * 
	 * @return The resulting number must be bigger than or equal to 1 and smaller than the maximum protection.
	 * 		   | (result >= 1) && (result <= maxProtection)
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public int getCurrentProtection() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return this.protection;
	}
	
	/**
	 * Returns the maximum protection of this particular armor.
	 * 
	 * @return The resulting number must be a valid protection.
	 * 		   | this.canHaveAsMaxProtection(getMaxProtection())
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Basic
	public int getMaxProtection() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return this.maxProtection;
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
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public void setCurrentProtection(int protection) throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		if (canHaveAsProtection(protection)){
			this.protection = protection;
		}
	}
	
	/**
	 * Sets the maximum protection to the given number.
	 * 
	 * @param maxProtection
	 * 		  The number to which the maximum protection is set.
	 * @post  The given number must be a legal number.
	 * 		  | canHaveAsMaxProtection(maxProtection)
	 * @post  The number to which the maximum protection is set is equal to the given number.
	 * 		  | new.getMaxProtection().equals(maxProtection)
	 */
	private void setMaxProtection(int maxProtection) {
		if (canHaveAsMaxProtection(protection)){
			this.maxProtection = maxProtection;
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
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 * @effect The new protection of this object is set to the original protection decreased with 'decrease'
	 * 		   | setCurrentProtection(this.getCurrentProtection() - decrease)
	 */
	@Override
	public void decreaseProtection(int decrease)
			throws IllegalArgumentException, OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
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
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 * @effect The new protection of this object is set to the original protection increased with 'increase'
	 * 		   | setCurrentProtection(this.getCurrentProtection() + increase)
	 */
	@Override
	public void increaseProtection(int increase)
			throws IllegalArgumentException, OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
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
	 * 		   | result == ((protection >= 1) && (protection <= this.getMaxProtection()))
	 */
	@Override
	public boolean canHaveAsProtection(int protection) {
		return ((protection >= 1)&&(protection <= this.getMaxProtection()));
	}
	
	/**
	 * Checks whether or not the given maximum protection is a legal case.
	 * 
	 * @param  maxProtection
	 * 		   The integer that needs to be checked.
	 * @return True if the given integer is bigger than 0 and smaller than the maximum Protection, 
	 * 		   false otherwise.
	 * 		   | result == ((protection >= 1) && (protection <= this.getType().getMaxProtection()))
	 */
	public boolean canHaveAsMaxProtection(int maxProtection) {
		return ((maxProtection >= 1)&&(maxProtection <= this.getType().getMaxProtection()));
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
	public static boolean canHaveAsIdentification(long identification){
		for (int i=2; i <= Math.round(Math.sqrt(identification)); i++){
			if (identification % i == 0){
				return false;
			}
		}
		return (identification >= 2 && (idListArmors.size() >= 1000 ||
				!idListArmors.contains(identification)));
	}
	
	/**
	 * Set the identification to the given identification if it is valid, otherwise it will
	 * be set to a random valid value. The first thousand armors have a unique number.
	 * 
	 * @param 	identification
	 * 			The identification of this armor.
	 * @effect	The identification is added to the list of identifications. The size of
	 * 			this list is increased by one.
	 * @post	If the given identification is unvalid, the identification is set to a
	 * 			random valid identification. If it is valid, it is set to the given
	 * 			identification.
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		if (!canHaveAsIdentification(identification)){
			identification = 2;
			while (!canHaveAsIdentification(identification)){
				identification += 1;
			}
		}
		this.identification = identification;
		Ownable.idListArmors.add(identification);
	}

	/******************************
	 * value
	 ******************************/
	
	/**
	 * Variable registering the maximum value in ducats of this armor.
	 */
	private int maxValue = 2;
	
	/**
	 * Sets the maximum value of the armor to the given integer 'maxValue'.
	 *
	 * @param maxValue
	 * 		  The integer to which the maxValue needs to be set.
	 * @post  maxValue will be set to the given integer.
	 * 		  | new.getMaxValue().equals(maxValue)
	 * @post  If the given value isn't valid, maxValue is set to the default.
	 * 		  | if !this.isValidValue(maxValue) then this.setMaxValue(2)
	 */
	private void setMaxValue(int maxValue){
		if (this.isValidValue(maxValue)){
			this.maxValue = maxValue;
		}
		else this.setMaxValue(2);
	}
	
	/**
	 * Returns the maximum value in ducats of the armor.
	 * 
	 * @return The resulting number must be a valid value.
	 * 		   | this.isValidValue(result)
	 */
	@Basic
	public int getMaxValue(){
		return this.maxValue;
	}
	
	/**
	 * Checks whether or not the given integer is a valid value.
	 * 
	 * @param  value
	 * 		   The integer that needs to be checked.
	 * @return True if the integer 'value' is smaller than or equal to 1000 and bigger than or equal to 1 and the value is even.
	 * 		   | result == (super.isValidValue(value)&&(value>=1)&&(value<=1000)&&(value % 2 == 0)
	 */
	@Override
	public boolean isValidValue(int value){
		return (super.isValidValue(value)&&(value>=1)&&(value<=1000)&&(value % 2 == 0));
	}
	
	/**
	 * Calculates the value in the ducats of the armor.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	@Override
	public int getValue() {
		int value = (int)Math.floor(this.getMaxValue()*(this.getCurrentProtection()/this.getMaxProtection()));
		if (value == 0){
			return 2;
		}
		if (this.isValidValue(value)){
			return value;
		}
		else{
			return value + 1;
		}
	}
	
	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Terminate this armor.
	 * 
	 * @post	Terminated is set to true.
	 * 			| this.terminated = true
	 */
	protected void terminate(){
		this.terminated = true;
	}

}
