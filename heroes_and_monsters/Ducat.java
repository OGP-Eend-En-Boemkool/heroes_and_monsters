package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of ducats with a numeral and a weight.
 * 
 * @invar	The numeral of each Ducat must be a valid numeral.
 * 			| isValidNumeral(getNumeral())
 * 
 * @author	Linde en Lotte
 * @version	1.0
 */
@Value
public class Ducat {

	/**
	 * Initialize this new Ducat with the given numeral as amount.
	 * @param 	numeral
	 * 			The numeral for this new Ducat.
	 * @post	The numeral of this new Ducat is equal to the given numeral.
	 * 			| new.getNumeral() == numeral
	 * @throws 	IllegalArgumentException
	 * 			The given numeral is not valid.
	 * 			| !isValidNumeral(numeral)
	 */
	@Raw
	public Ducat(int numeral) throws IllegalArgumentException {
		if (!isValidNumeral(numeral)){
			throw new IllegalArgumentException("Numeral cannot be negative");
		}
		this.numeral = numeral;
	}
	
	/**
	 * Variable referencing the numeral of this ducat.
	 */
	private final int numeral;
	
	/**
	 * Variable referencing one ducat
	 * 
	 * @return	The ducat ONE_DUCAT is equal to a ducat initialized with numeral 1.
	 * 			| ONE_DUCAT == new Ducat(1)
	 */
	public final static Ducat ONE_DUCAT = new Ducat(1);
	
	/**
	 * Return the numeral of this ducat.
	 */
	@Basic @Raw @Immutable
	public int getNumeral(){
		return this.numeral;
	}
	
	/**
	 * Chack whether the given numeral is a valid numeral for any amount of ducats.
	 * 
	 * @param	numeral
	 * 			The numeral to check.
	 * @return	True if and only if the given numeral is greater than or equal to 0.
	 * 			| result == (numeral >= 0)
	 */
	public static boolean isValidNumeral(int numeral){
		return (numeral >= 0);
	}
	
	/**
	 * Return the weight of this amount of ducats in gramm.
	 * @return	The weight in gramm.
	 * 			| this.getNumeral()*50
	 */
	public long getWeight(){
		return (this.getNumeral()*50);
	}
	
	/**
	 * Compute the sum of two amounts of ducats.
	 * 
	 * @param 	other
	 * 			The other amount of ducats to add.
	 * @return	The sum of the two amounts of ducats.
	 * 			| result.getNumeral().equals(this.getNumeral() + other.getNumeral())
	 * @throws 	IllegalArgumentException
	 * 			The other ducat is not effective.
	 * 			| other == null
	 */
	public Ducat add(Ducat other) throws IllegalArgumentException {
		if (other == null){
			throw new IllegalArgumentException("Other Ducat is not effective");
		}
		return new Ducat(this.getNumeral() + other.getNumeral());
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
	 * 			| other.getNumeral() > this.getNumeral()
	 */
	public Ducat subtract(Ducat other) throws IllegalArgumentException {
		if (other == null){
			throw new IllegalArgumentException("Other Ducat is not effective");
		}
		if (other.getNumeral() > this.getNumeral()){
			throw new IllegalArgumentException("The result would be negative, which is invalid");
		}
		return new Ducat(this.getNumeral() - other.getNumeral());
	}
	
	
}
