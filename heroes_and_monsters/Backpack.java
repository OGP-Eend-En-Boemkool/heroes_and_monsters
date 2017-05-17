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
}
