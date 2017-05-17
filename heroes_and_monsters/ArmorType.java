package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

/**
 * 	An enumeration of armor types.
 *  In its current definition, the class only has one distinct type. 
 *  With each armor type there's an integer associated which gives us the maximum protection an armor 
 *  of this armor type can offer.
 *  
 *  @invar	Each type must have a legal maxProtection.
 * 			| isValidMaxProtection(getMaxProtection())
 *    
 * @author 	Lotte en Linde
 * @version	1.0
 */
@Value

public enum ArmorType {
	
	STANDARD(100);
	
	/**
	 * Initialize a new Armor type with an associated maximum protection.
	 * 
	 * @param maxProtection
	 * 		  the maximum of protection an armor of this type can offer
	 * @post  the maxProtection of this new type is set to the given integer.
	 * 		  | new.getMaxProtection().equals(maxProtection)
	 */
	private ArmorType(int maxProtection){
		this.maxProtection = maxProtection;
	}
	
	/**
	 * Return the maximum protection of this armor type.
	 */
	@Raw @Basic @Immutable
	public double getMaxProtection(){
		return this.maxProtection;
	}
	
	/**
     * Check whether the given integer is a legal number for the maximum protection.
     *
     * @return	True if the given integer is a positive number.
     * 			| result == (maxProtection > 0)
     */
	public static boolean isValidMaxProtection(int maxProtection){
      return (maxProtection > 0);
	}
	
	/**
     * Variable registering the maximum protection an armor of this armortype can offer.
     */
	private final int maxProtection;

}
