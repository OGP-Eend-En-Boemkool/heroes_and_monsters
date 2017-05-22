package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of ownables.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public abstract class Ownable{
 
	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize an ownable with an identification.
	 * 
	 * @param 	identification
	 * 			The identification of this ownable.
	 * @post	The identification of this ownable is set to identification.
	 * 			| new.getIdentification() == identification
	 */
	@Raw
	protected Ownable(long identification){
		setIdentification(identification);
		setValue(this.calculateValue());
	}
	
	/*******************************
	 * identification
	 *******************************/
	
	/**
	 * Variable referencing the identification of an ownable.
	 */
	protected long identification;
	
	/**
	 * Variable where we can store the first 1000 identifications of armors.
	 */
	protected static ArrayList<Long> idListArmors;
	
	/**
	 * Variable where we can store the first 1000 identifications of weapons.
	 */
	protected static ArrayList<Long> idListWeapons;
	
	/**
	 * Variable where we can store the first 1000 identifications of backpacks.
	 */
	protected static ArrayList<Long> idListBackpacks;
	
	/**
	 * Variable where we can store the first 1000 identifications of purses.
	 */
	protected static ArrayList<Long> idListPurses;
	
	/**
	 * Return the identification of this ownable.
	 */
	@Raw @Basic
	public long getIdentification(){
		return this.identification;
	}
	
	/**
	 * Set the identification to the given identification if possible.
	 * 
	 * @param 	identification
	 * 			The identification of this ownable.
	 * @post	The identification is added to the list of identifications (every ownable
	 * 			has its own list).
	 * 			| idListClass.add(identification)
	 */
	@Raw
	protected abstract void setIdentification(long identification);
	
	/********************************
	 * holder
	 ********************************
	
	/**
	 * Variable referencing the holder of an ownable.
	 */
	protected Object holder = null;
	
	/**
	 * Return the holder of this ownable.
	 */
	@Raw @Basic
	public Object getHolder(){
		return this.holder;
	}
	/**
	 * Return the ultimate owner of this ownable.
	 */
	@Raw
	public Object getUltimateHolder(){
		Object obj = this.getHolder();
		if (obj == null || obj instanceof Creature){
			return obj;
		}
		else {
			Ownable own = (Ownable)obj;
			while (own.getHolder() != null && !(own.getHolder() instanceof Creature)){
				obj = own.getHolder();
				own = (Ownable)obj;
			}
			if (own.getHolder() == null){
				return own;
			}
			else {
				return own.getHolder();
			}		
		}
	}
	
	@Raw
	protected abstract void setHolder(Object holder);
	
	@Raw
	public boolean canHaveAsHolder(Object holder){
		return ((holder instanceof Creature) || (holder instanceof Backpack));
	}
	
	/********************************
	 * Value
	 ********************************/
	
	protected int value = 1;	
	
	/**
	 * Return the number of ducats that represents the value of the ownable object. 
	 * 
	 * @return The resulting number must be bigger than or equal to 0.
	 * 		   | result >= 0
	 */
	protected int getValue(){
		return this.value;
	}
	
	/**
	 * Sets the value of the ownable object to the given integer 'value'.
	 *
	 * @param value
	 * 		  The integer to which the value needs to be set.
	 * @post  Value will be set to the given integer.
	 * 		  | new.getValue().equals(value)
	 * @post  If the given value isn't valid, value is set to the default.
	 * 		  | if !this.isValidValue(value) then this.setValue(1)
	 */
	protected void setValue(int value){
		if (this.isValidValue(value)){
			this.value = value;
		}
	}
	
	/**
	 * Checks whether or not the given integer is a valid value.
	 * 
	 * @param  value
	 * 		   The integer that needs to be checked.
	 * @return False if the given integer is negative.
	 * 		   | if (value < 0) return False
	 * @note   This return must be left open in order for the subclasses to be able to change it.
	 */
	protected boolean isValidValue(int value){
		return (value >= 0);
	}
	
	/**
	 * Calculates the value in ducats of the ownable object.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	protected abstract int calculateValue();
	
	
}
