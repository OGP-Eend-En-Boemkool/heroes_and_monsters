package heroes_and_monsters;

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
	 * Return the maximum capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 */
	public double getMaximumCapacity(Unit unit);
	
	/**
	 * Return the used part of the total capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @return the resulting number cannot be larger than the maximum capacity of the object.
	 * 		   | result <= this.getMaximumCapacity()
	 */
	public double getUsedCapacity(Unit unit);
	
	/**
	 * Checks whether or not a certain given capacity is a valid capacity.
	 * 
	 * @return returns false if the capacity is negative.
	 * 		   | if (capacity < 0) then 
	 * 		   |		return false
	 * @note   this return must be left open because some of the subclasses might change it.
	 */
	public boolean isValidCapacity(double capacity);

}
