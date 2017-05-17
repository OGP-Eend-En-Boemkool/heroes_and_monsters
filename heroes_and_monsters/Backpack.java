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
	 * 
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		
	}
	
	/**
	 * 
	 */
	@Raw
	private long calculateValidIdentification(){
		long n = idListBackpacks.size() + 1;
		long id = 0;
		for (int i = 0; i <= n; i++){
			
		}
	}
	
	private long calculateBinomial(long n, long k){
		if (k>n-k){
			k=n-k;
		}
        long b = 1;
        for (long i=1, m=n; i<=k; i++, m--){
            b=b*m/i;
        }
        return b;
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

	/**
	 * Checks whether or not a certain given capacity is a valid capacity.
	 * 
	 * @return returns False if the capacity is negative or zero, True otherwise.
	 * 		   | (capacity <=  0)
	 */
	@Override
	public boolean isValidCapacity(double capacity) {
		return (capacity <= 0);
	}
	
}
