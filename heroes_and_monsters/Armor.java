package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

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
public class Armor extends Ownable implements Protection, Comparable<Armor>{
	
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
	 * @post  	If the given value is valid, the maxValue of this armor is set to the given maxValue. 
	 * @post  	If the given value isn't valid, maxValue is set to the default.
	 * @post	If the given value is valid, the maxProtection of this armor is set to the given maxProtection.
	 * @post	If the given value isn't valid, maxProtecion is set to the default.
	 */
	@Raw
	public Armor(long identification, int maxProtection, Ducat maxValue,
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
	 */
	@Immutable
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
	 * @return The resulting number will be bigger than or equal to 1 and smaller than the maximum protection.
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
	@Basic @Immutable @Raw
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
	@Raw
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
	 * 		   | (decrease <= 0)||(decrease >= this.getCurrentProtection())
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
	 *  	   | (increase <= 0)||(increase >= (this.getMaxProtection() - this.getCurrentProtection()))
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
	@Override @Raw
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
	@Raw
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
		return (identification >= 2 && (Ownable.getArmors().size() >= 1000 ||
				!Ownable.getArmors().contains(identification)));
	}
	
	/**
	 * Set the identification to the given identification if it is valid, otherwise it will
	 * be set to the next valid value. The first thousand armors have a unique number.
	 * 
	 * @param 	identification
	 * 			The identification of this armor.
	 * @effect	The identification is added to the list of identifications. The size of
	 * 			this list is increased by one.
	 * @effect	If the given identification is unvalid, the identification is set to the
	 * 			next valid identification. If it is valid, it is set to the given
	 * 			identification.
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		while (!canHaveAsIdentification(identification)){
			identification++;
		}
		super.setIdentification(identification);
		super.addArmor(identification);
	}

	/******************************
	 * value
	 ******************************/
	
	/**
	 * Variable registering the maximum value in ducats of this armor.
	 */
	private Ducat maxValue = new Ducat(2);
	
	/**
	 * Sets the maximum value of the armor to the given ducat 'maxValue'.
	 *
	 * @param maxValue
	 * 		  The ducat to which the maxValue needs to be set.
	 * @post  maxValue will be set to the given ducat.
	 * 		  | new.getMaxValue().equals(maxValue)
	 * @post  If the given value isn't valid, maxValue is set to the default.
	 * 		  | if !this.isValidValue(maxValue) then this.setMaxValue(new Ducat(2))
	 */
	@Raw
	private void setMaxValue(Ducat maxValue){
		if (this.isValidValue(maxValue)){
			this.maxValue = maxValue;
		}
		else this.setMaxValue(new Ducat(2));
	}
	
	/**
	 * Returns the maximum value in ducats of the armor.
	 * 
	 * @return The resulting number must be a valid value.
	 * 		   | this.isValidValue(result)
	 */
	@Basic @Raw @Immutable
	public Ducat getMaxValue(){
		return new Ducat(this.maxValue.getValue());
	}
	
	/**
	 * Checks whether or not the given integer is a valid value.
	 * 
	 * @param  	value
	 * 		   	The integer that needs to be checked.
	 * @return 	True if the integer 'value' is smaller than or equal to 1000 and bigger than or equal to 1 and the value is even.
	 * 		   	| result == (super.isValidValue(value)&&(value.getValue()>=1)&&
	 * 			|			(value.getValue()<=1000)&&(value.getValue() % 2 == 0)
	 */
	@Override
	public boolean isValidValue(Ducat value){
		return (super.isValidValue(value) && (value.getValue()>=1) &&
				(value.getValue()<=1000) && (value.getValue() % 2 == 0));
	}
	
	/**
	 * Calculates the value in ducats of the armor.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	@Override
	public Ducat getValue() {
		Ducat value = new Ducat((int)Math.floor(this.getMaxValue().getValue()*(this.getCurrentProtection()/this.getMaxProtection())));
		if (value.compareTo(new Ducat(0)) == 0){
			return new Ducat(2);
		}
		if (this.isValidValue(value)){
			return value;
		}
		else{
			return value.add(new Ducat(1));
		}
	}
	
	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Terminate this armor.
	 * 
	 * @effect	Terminated is set to true.
	 * 			| setTerminate(true)
	 */
	protected void terminate(){
		setTerminate(true);
	}

	/**********************************
	 * comparable
	 **********************************/
	
	/**
	 * Compare the protections of this armor and another.
	 * 
	 * @return The subtraction of the current protection of this armor and the current protection of the other armor.
	 * 		   | result == (this.getCurrentProtection() - other.getCurrentProtection())
	 */
	@Override
	public int compareTo(Armor other) {
		return (this.getCurrentProtection() - other.getCurrentProtection());
	}
}
