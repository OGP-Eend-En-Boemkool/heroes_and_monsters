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
public class Ducat{

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
	private final int value;
	
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
	 * Chack whether the given value is a valid value for any amount of ducats.
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
		return (unit.convertFromKilogram(this.getValue()*0.050));
	}
	
	/**
	 * Compute the sum of two amounts of ducats.
	 * 
	 * @param 	other
	 * 			The other amount of ducats to add.
	 * @return	The sum of the two amounts of ducats.
	 * 			| result.getValue().equals(this.getValue() + other.getValue())
	 * @throws 	IllegalArgumentException
	 * 			The other ducat is not effective.
	 * 			| other == null
	 */
	public Ducat add(Ducat other) throws IllegalArgumentException {
		if (other == null){
			throw new IllegalArgumentException("Other Ducat is not effective");
		}
		return new Ducat(this.getValue() + other.getValue());
	}
	
	/**
	 * Compute the subtraction of the other amount of ducats from this amount.
	 * 
	 * @param	other
	 * 			The other amount of ducats to subtract.
	 * @return	The subtraction of the two amounts of ducats.
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
		return new Ducat(this.getValue() - other.getValue());
	}
	
	
}
