package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Purse extends Storage {

	public Purse(){
		
	}
	
	/*******************************
	 * identification
	 *******************************
	
	/**
	 *  Set the identification of this purse to the given identification.
	 * 
	 * @param 	identification
	 * 			The identification of this purse.
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
	public boolean canHaveAsIdentification(long identification){
		return (isFibonacci(identification) && ( idListPurses.size() >= 1000 ||
	 				!idListPurses.contains(identification) ));
	}
	
	/**
	 * Return a valid identification for a purse.
	 */
	private long calculateValidIdentification(){
		long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		while (!canHaveAsIdentification(id)){
			id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		}
		return id;
	}
	
}
