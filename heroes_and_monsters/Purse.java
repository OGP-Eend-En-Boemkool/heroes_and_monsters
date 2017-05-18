package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of purses.
 * 
 * @invar	Every purse must have a correct identification.
 * 			| canHaveAsIdentification(getIdentification())
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Purse extends Storage {

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new purse with an identification.
	 * 
	 * @effect	This purse is initialized as an ownable with a calculated identification.
	 * 			| super(calculateValidIdentification())
	 */
	public Purse(){
		super(calculateValidIdentification());
	}
	
	/*******************************
	 * identification
	 *******************************
	
	/**
	 *  Set the identification of this purse to the given identification.
	 * 
	 * @param 	identification
	 * 			The identification of this purse.
	 * @effect	The identification is added to the list of identifications of purses.
	 *  		| idListPurses.add(identification)
	 * @post	The identification of this purse is set to identification.
	 * 			| new.getIdentification() = identification
	 */
	protected void setIdentification(long identification){
		this.identification = identification;
		idListPurses.add(identification);
	}
	

	/**
	 * Check whether a number is a perfect square.
	 * 
	 * @param 	number
	 * 			The number to check.
	 * @return	True if and only if the square root from this number is multiplied with
	 * 			itself and that is equal to the original number.
	 * 			| result == (sqrt(number) * sqrt(number) == number)
	 */
	private static boolean isPerfectSquare(long number){
		double s = Math.sqrt(number);
		return (s*s == number);
	}
	
	/**
	 * Check whether a number is a Fibonacci number.
	 * 
	 * @param 	number
	 * 			The number to check.
	 * @return	True if and only if (5* number * number + 4) or (5* number * number - 4)
	 * 			is a perfect square.
	 * 			| result == isPerfectSquare(5* number * number + 4) ||
				|			isPerfectSquare(5* number * number - 4)
	 */
	private static boolean isFibonacci(long number){
		return (isPerfectSquare(5* number * number + 4) ||
				isPerfectSquare(5* number * number - 4));
	}
	
	/**
	 * Check whether a purse can have this identification.
	 * 
	 * @param 	identification
	 * 			The identification to check.
	 * @return	False if identification is not a Fibonacci number. Also false when there
	 * 			have not been made 1000 purses yet and the identification already exists
	 * 			for another purse. True otherwise.
	 * 			| result == isFibonacci(identification) && ( idListPurses.size() >= 1000 ||
	 * 			|				!idListPurses.contains(identification) )
	 */
	public static boolean canHaveAsIdentification(long identification){
		return (isFibonacci(identification) && ( idListPurses.size() >= 1000 ||
	 				!idListPurses.contains(identification) ));
	}
	
	/**
	 * Return a valid identification for a purse.
	 */
	private static long calculateValidIdentification(){
		long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		while (!canHaveAsIdentification(id)){
			id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		}
		return id;
	}
	

	
	/********************************
	 * Capacity - totaal
	 ********************************/
	
	/**
	 * Variable registering the maximum value of the capacity of the purse.
	 */
	private double maximumCapacity;
	
	/**
	 * Return the maximum capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 */
	@Override
	public double getMaximumCapacity(Unit unit) {
		return unit.convertFromKilogram(maximumCapacity);
	}

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
		// TODO Auto-generated method stub
		return 0;
	}
	
	/******************************
	 * holder
	 ******************************
	
	/**
	 * 
	 */
	@Raw @Override
	protected void setHolder(Object holder){
		
	}
}
