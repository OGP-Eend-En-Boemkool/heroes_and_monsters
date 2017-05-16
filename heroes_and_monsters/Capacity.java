package heroes_and_monsters;


import java.math.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * The capacity interface is an interface that we use to implement the capacity of certain objects. 
 * The interface is able to return, calculate and set the maximum capacity. It's also able to determine the
 * used amount of capacity. And whether or not the given capacity can be valid.
 * 
 * @author 	Lotte en Linde
 * @version	1.0
 */

public interface Capacity {
	
	/**
	 * Return the maximumcapacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result >= 0
	 */
	float getMaximumCapacity();
	
	/**
	 * Return the used part of the total capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result >= 0
	 * @return the resulting number cannot be larger than the maximum capacity of the object.
	 * 		   | result <= this.getMaximumCapacity()
	 */
	float getUsedCapacity();
	
	/**
	 * Checks whether or not a certain given capacity is a valid capacity.
	 * 
	 * @return returns true if the capacity is not negative, false otherwise.
	 * 		   | result == (capacity >= 0)
	 */
	boolean isValidCapacity(float capacity);

}
