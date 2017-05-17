package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Backpack extends Storage {

	public Backpack(){
		
	}
	
	/********************************
	 * identification
	 ********************************
	
	/**
	 * Set the identification of this backpack to the given identification.
	 * 
	 * @param 	identification
	 * 			The identification of this backpack
	 * @post	The identification of this backpack is set to identification.
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
	@Raw
	private long calculateValidIdentification(){
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
	private long calculateBinomial(long n, long i){
		long coefficient;
	    if (i==0 | i==n){
	    	coefficient = 1;
	    }  
	    else{
	    	coefficient = calculateBinomial(n - 1, i) + calculateBinomial(n - 1, i - 1);
	    }
	    return coefficient;
	}
}
