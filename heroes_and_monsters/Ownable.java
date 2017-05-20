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
	@Raw @Model
	protected Ownable(long identification){
		setIdentification(identification);
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
	protected abstract void setHolder(Hero holder, String anchor);
	
	@Raw
	public boolean canHaveAsHolder(Object holder){
		return ((holder instanceof Creature) || (holder instanceof Backpack) ||
				holder == null);
	}
}
