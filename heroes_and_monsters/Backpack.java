package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of backpacks.
 * 
 * @invar	Every backpack must have the next correct identification number.
 * 			| getIdentification == calculateValidIdentification()
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Backpack extends Storage {

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new backpack with an identification.
	 * 
	 * @effect	This backpack is initialized as an ownable with a calculated identification.
	 * 			| super(calculateValidIdentification())
	 */
	public Backpack(){
		super(calculateValidIdentification());
	}
	
	/********************************
	 * identification
	 ********************************
	
	/**
	 * Set the identification of this backpack to the given identification.
	 * 
	 * @param 	identification
	 * 			The identification of this backpack.
	 * @post	The identification of this backpack is set to identification.
	 * 			| new.getIdentification() = identification
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		this.identification = identification;
		idListBackpacks.add(identification);
	}
	
	/**
	 * Calculate a valid identification for a backpack. This is the sum of all the binomial
	 * coefficients with at the top the amount of backpacks that already exist plus one and
	 * at the bottom an increasing number from zero to that same number (increased with
	 * every new term of the sum).
	 */
	private static long calculateValidIdentification(){
		long n = idListBackpacks.size() + 1;
		long id = 0;
		for (int i = 0; i <= n; i++){
			id += calculateBinomial(n,i);
		}
		return id;
	}
	
	/**
	 * Calculate the binomial coefficient.
	 * 
	 * @param 	n
	 * 			The top of the binomial coefficient.
	 * @param 	i
	 * 			The bottom of the binomial coefficient.
	 * @return	The binomial coefficient of n and i.
	 * 			| if (i==0 | i==n){
	    		|		return 1 }  
	    		| else {
	    		|	return (calculateBinomial(n - 1, i) + calculateBinomial(n - 1, i - 1))
	 */
	private static long calculateBinomial(long n, long i){
		long coefficient;
	    if (i==0 | i==n){
	    	coefficient = 1;
	    }  
	    else{
	    	coefficient = calculateBinomial(n - 1, i) + calculateBinomial(n - 1, i - 1);
	    }
	    return coefficient;
	}

	/********************************
	 * Capacity - totaal
	 ********************************/
	
	/**
	 * Variable registering the maximum capacity of the backpack.
	 */
	private final double maximumCapacity;
	
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
	
}
