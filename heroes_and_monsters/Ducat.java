package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;


/**
 * A class of ducats with a value and a weight.
 * 
 * @invar	The value of each Ducat must be a valid value.
 * 			| isValidValue(getValue())
 * 
 * @author	Linde en Lotte
 * @version	1.0
 */
@Value
public class Ducat implements Comparable<Ducat>{

	/**
	 * Initialize this new Ducat with the given value as amount.
	 * @param 	value 
	 * 			The value for this new Ducat.
	 * @post	The value of this new Ducat is equal to the given value.
	 * 			| new.getValue() == value
	 * @throws 	IllegalArgumentException
	 * 			The given value is not valid.
	 * 			| !isValidValue(value)
	 */
	@Raw
	public Ducat(int value) throws IllegalArgumentException {
		if (!isValidValue(value)){
			throw new IllegalArgumentException("Value cannot be negative");
		}
		this.value = value;
	}
	
	/**
	 * Variable referencing the value of this ducat.
	 */
	private int value;
	
	/**
	 * Variable referencing one ducat
	 * 
	 * @return	The ducat ONE_DUCAT is equal to a ducat initialized with value 1.
	 * 			| ONE_DUCAT == new Ducat(1)
	 */
	public final static Ducat ONE_DUCAT = new Ducat(1);
	
	/**
	 * Return the value of this ducat.
	 */
	@Basic @Raw @Immutable
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Check whether the given value is a valid value for any amount of ducats.
	 * 
	 * @param	value
	 * 			The value to check.
	 * @return	True if and only if the given value is greater than or equal to 0.
	 * 			| result == (value >= 0)
	 */
	public static boolean isValidValue(int value){
		return (value >= 0);
	}
	
	/**
	 * Return the weight of this amount of ducats in the chosen weight unit.
	 * @return	The weight in the chosen weight unit.
	 * 			| result == (unit.convertFromKilogram(this.getValue()*0.050))
	 */
	public double getWeight(Unit unit){
		double weight = (double)Math.round(this.getValue()*0.050 * 100000d) / 100000d;
		return (unit.convertFromKilogram(weight));
	}
	
	/**
	 * Compute the sum of two amounts of ducats.
	 * 
	 * @param 	other
	 * 			The other amount of ducats to add.
	 * @return	The sum of the two amounts of ducats.
	 * 			| new.getValue().equals(this.getValue() + other.getValue())
	 * @throws 	IllegalArgumentException
	 * 			The other ducat is not effective.
	 * 			| other == null
	 */
	public Ducat add(Ducat other) throws IllegalArgumentException {
		if (other == null){
			throw new IllegalArgumentException("Other Ducat is not effective");
		}
		this.value = (this.getValue() + other.getValue());
		return this;
	}
	
	/**
	 * Compute the sum of a ducat and the content of a purse.
	 * 
	 * @param 	purse
	 * 			The purse of the content to add.
	 * @return	The sum of the value of the ducat and the purse.
	 * 			| new.getValue().equals(this.getValue() + purse.getValue())
	 * @throws 	IllegalArgumentException
	 * 			The purse is not effective.
	 * 			| purse == null
	 */
	public Ducat add(Purse purse) throws IllegalArgumentException {
		if (purse == null){
			throw new IllegalArgumentException("Other Ducat is not effective");
		}
		this.value = (this.getValue() + purse.getValue().getValue());
		return this;
	}
	
	/**
	 * Compute the subtraction of the other amount of ducats from this amount.
	 * 
	 * @param	other
	 * 			The other amount of ducats to subtract.
	 * @return	The subtraction of the two amounts of ducats.
	 * 			| new.getValue().equals(this.getValue() - other.getValue())
	 * @throws	IllegalArgumentException
	 * 			The other ducat is not effective.
	 * 			| other == null
	 * @throws	IllegalArgumentException
	 * 			The amount to subtract is greater than the amount to subtract from.
	 * 			| other.getValue() > this.getValue()
	 */
	public Ducat subtract(Ducat other) throws IllegalArgumentException {
		if (other == null){
			throw new IllegalArgumentException("Other Ducat is not effective");
		}
		if (other.getValue() > this.getValue()){
			throw new IllegalArgumentException("The result would be negative, which is invalid");
		}
		this.value = (this.getValue() - other.getValue());
		return this;
	}
	
	/**
	 * Compute the subtraction of the content of the purse from this ducat.
	 * 
	 * @param	purse
	 * 			The purse of the content to subtract.
	 * @return	The subtraction of the value of the ducat and the purse.
	 * 			| new.getValue().equals(this.getValue() - purse.getValue())
	 * @throws	IllegalArgumentException
	 * 			The purse is not effective.
	 * 			| purse == null
	 * @throws	IllegalArgumentException
	 * 			The amount to subtract is greater than the amount to subtract from.
	 * 			| purse.getValue() > this.getValue()
	 */
	public Ducat subtract(Purse purse) throws IllegalArgumentException {
		if (purse == null){
			throw new IllegalArgumentException("Purse is not effective");
		}
		if (purse.getValue().getValue() > this.getValue()){
			throw new IllegalArgumentException("The result would be negative, which is invalid");
		}
		this.value = (this.getValue() - purse.getValue().getValue());
		return this;
	}
	
	
	/**
 	 * Check whether or not this amount of ducats is the same as the given object.
 	 * 
 	 * @return True if and only if the given object is effective, if the given object and this object
 	 * 		   belong to the same class and this amount of ducats is the same as the amount of ducats of the given object.
 	 * 		   | result == 
 	 * 		   |	( (other != null)
 	 * 		   |	&& (this.getClass() == other.getClass())
 	 * 		   |	&& (this.getValue() == other.getValue()) )
 	 */
 	@Override
 	public boolean equals(Object other){
 		if ((other == null)||(this.getClass() != other.getClass())){
 			return false;
 		}
 		Ducat otherDucat = (Ducat) other;
 		return (this.getValue() == otherDucat.getValue());		
 	}
 	
 	/**
 	 * Return the value of this ducat as a string.
 	 */
 	@Override
 	public String toString(){
 		return String.valueOf(getValue());
 	}
 	
 	/**********************************
	 * comparable
	 **********************************/
	
	/**
	 * Compare this ducat to another by their value.
	 * 
	 * @return The subtraction of the value of this ducat and the value of the other ducat.
	 * 		   | result == (this.getValue() - other.getValue())
	 */
	@Override
	public int compareTo(Ducat other) {
		return (this.getValue() - other.getValue());
	}
}
