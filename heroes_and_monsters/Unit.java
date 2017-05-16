package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;


/**
 * 	An enumeration of weight units.
 *  In its current definition, the class only distinguishes between
 *  kilogram, gram and pound as weight units. With each weight unit there's a constant associated
 *  in order to be able to convert the weight unit to kilogram.
 *  
 *  @invar	Each type must have a legal constant.
 * 			| isValidConstant(getConstant())
 *    
 * @author 	Lotte en Linde
 * @version	1.0
 */
@Value

public enum Unit {
	
	KG(1), GRAM(0.001), POUND(0.45359237);
	
	
	/**
	 * Initialize a new Unit with an associated converting constant.
	 * 
	 * @param constant
	 * 		  the constant needed when converting the unit to kilograms
	 * @post  the constant of this new type is set to the given constant.
	 * 		  | new.getConstant().equals(constant)
	 */
	private Unit (double constant){
		this.constant = constant;
	}
	
	/**
	 * Return the constant of this unit.
	 */
	@Raw @Basic @Immutable
	public double getConstant(){
		return this.constant;
	}
	
	/**
     * Check whether the given constant is a legal constant
     * for a unit.
     *
     * @return	True if the given double is a positive number.
     * 			| result == constant > 0
     */
	public static boolean isValidConstant(double constant){
      return (constant>0) ;
	}
	
	/**
	 * Return the corresponding amount of kilograms for the given amount of this weight unit.
	 *  
	 * @param  amount
	 * 		   the given amount of this weight unit
	 * @post   the converted amount is correct
	 * 		   | result = amount*this.getConstant()
	 * @throws IllegalArgumentException
	 * 		   when the given amount is negative
	 * 		   | if (amount < 0) throw new IllegalArgumentException
	 */
	public double convertToKilogram (double amount) throws IllegalArgumentException{
		if (amount < 0){
			throw new IllegalArgumentException("You can't have a weight less than zero.");
		}
		else
			return amount*this.getConstant();
	}
	
	/**
     * Variable registering the converting constant.
     */
	private final double constant;
	
}
