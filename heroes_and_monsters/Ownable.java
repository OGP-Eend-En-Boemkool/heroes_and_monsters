package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of ownables.
 * 
 * @invar	Every ownable must have a valid weight.
 * 			| isValidOwnWeight(getOwnWeight(Unit.KG))
 * @invar	Every ownable must have a valid value.
 * 			| isValidValue(getValue())
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
	 * @param	weight
	 * 			The weight of this ownable.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @post	The identification of this ownable is set to identification.
	 * 			| new.getIdentification() == identification
	 * @post	The weight of this ownable is set to the given weight in the given unit.
	 * 			| new.getOwnWeight(unit) == weight
	 */
	@Raw @Model
	protected Ownable(long identification, double weight, Unit unit){
		setIdentification(identification);
		setOwnWeight(weight, unit);
	}
	
	/*******************************
	 * identification
	 *******************************/
	
	/**
	 * Variable referencing the identification of an ownable.
	 */
	protected long identification;
	
	/**
	 * Variable where we can store all the armors.
	 */
	protected static ArrayList<Armor> listArmors;
	
	/**
	 * Variable where we can store all the id's of armors.
	 */
	protected static ArrayList<Long> idListArmors;
	
	/**
	 * Variable where we can store all the weapons.
	 */
	protected static ArrayList<Weapon> listWeapons;
	
	/**
	 * Variable where we can store all the id's of weapons.
	 */
	protected static ArrayList<Long> idListWeapons;
	
	/**
	 * Variable where we can store all the backpacks.
	 */
	protected static ArrayList<Backpack> listBackpacks;
	
	/**
	 * Variable where we can store all the id's of backpacks.
	 */
	protected static ArrayList<Long> idListBackpacks;
	
	/**
	 * Variable where we can store all the purses.
	 */
	protected static ArrayList<Purse> listPurses;
	
	/**
	 * Variable where we can store all the id's of purses.
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
	 * @post	The ownable is added to the list of ownables (every ownable
	 * 			has its own lists).
	 * 			| listClass.add(this)
	 * @post	The size of the list of ownables is increased by 1.
	 * 			| new.listClass.size() = this.listClass.size() + 1
	 * @post	The ownable is added to the list of identifications (every ownable
	 * 			has its own lists).
	 * 			| idListClass.add(this)
	 * @post	The size of the list of identifications is increased by 1.
	 * 			| new.idListClass.size() = this.idListClass.size() + 1
	 */
	@Raw
	protected abstract void setIdentification(long identification);
	
	/********************************
	 * Weight
	 ********************************/
	
	/**
	 * Variable referencing the weight in kilogram of the ownable object.
	 */
	protected double ownWeight = 0;
	
	/**
	 * Return the weight of the ownable object. 
	 * 
	 * @param  unit
	 * 		   The weight in unit in which the weight should be returned.
	 * @return The resulting number must be bigger than or equal to 0.
	 * 		   | result >= 0
	 */
	protected double getOwnWeight(Unit unit){
		return (unit.convertFromKilogram(this.ownWeight));
	}
	
	/**
	 * Sets the weight of the ownable object to the given value.
	 *
	 * @param weight
	 * 		  The integer to which the weight needs to be set.
	 * @param unit
	 * 		  The unit in which the parameter weight is given.
	 * @post  If the given value is valid, weight will be set to the given integer.
	 * 		  | if isValidOwnWeight(weight)
	 * 		  | then new.getOwnWeight().equals(weight)
	 * @post  If the given value isn't valid, weight is set to the default.
	 * 		  | if !this.isValidOwnWeight(weight) 
	 * 		  | then new.getOwnWeight().equals(0)
	 */
	protected void setOwnWeight(double weight, Unit unit){
		if (this.isValidOwnWeight(weight)){
			this.ownWeight = unit.convertToKilogram(weight);
		}
	}
	
	/**
	 * Checks whether or not the given integer is a valid weight.
	 * 
	 * @param  weight
	 * 		   The integer that needs to be checked.
	 * @return False if the given integer is negative, true otherwise.
	 * 		   | result == (weight >= 0)
	 */
	protected boolean isValidOwnWeight(double weight){
		return (weight >= 0);
	}	
		
	
	/********************************
	 * holder
	 ********************************/
	
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
	
	/**
	 * Set the holder of this ownable to the given creature.
	 * 
	 * @param 	holder
	 * 			The holder of this ownable.
	 * @post	The holder of this ownable is set to the given holder.
	 * 			| this.getHolder() = holder
	 */
	@Raw
	protected void setHolder(Creature holder){
		this.holder = holder;
	}
	
	/**
	 * Set the holder of this ownable to the given backpack.
	 * 
	 * @param 	holder
	 * 			The holder of this ownable.
	 * @post	The holder of this ownable is set to the given holder.
	 * 			| this.getHolder() = holder 
	 */
	@Raw
	protected void setHolder(Backpack holder){
		this.holder = holder;
	}
	
	/**
	 * Set the holder of this ownable to null.
	 * 
	 * @post	The holder of this ownable is set to null.
	 * 			| this.getHolder() = null 
	 */
	@Raw
	protected void setHolder(){
		this.holder = null;
	}
	
	/**
	 * Check whether this ownable can have the given holder as its holder.
	 * 
	 * @param 	holder
	 * 			The holder to check.
	 * @return	True if and only if the holder is a creature or a backpack or there is
	 * 			no holder at all.
	 * 			| result == ((holder instanceof Creature) || (holder instanceof Backpack) ||
	 *			|				holder == null)
	 */
	@Raw
	public boolean canHaveAsHolder(Object holder){
		return ((holder instanceof Creature) || (holder instanceof Backpack) ||
				holder == null);
	}
	
	/********************************
	 * Value
	 ********************************/
	
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
	protected abstract int getValue();
	
	/********************************
	 * Containers
	 ********************************/
	
	/**
	 * Set that references all the backpacks in which the ownable is located.
	 */
	protected HashSet<Backpack> containersSet = new HashSet<Backpack>() ;
	
	/**
	 * Add all the backpacks that contain this new container and this new container to the set of containers of this ownable.
	 * (Used when this ownable is added to a backpack)
	 * 
	 * @param container
	 * 		  The backpack that contains the ownable.
	 * @post  The new containersSet will contain the new container.
	 * 		  | new.containersSet.contains(container)
	 * @post  The new containersSet will contain all the containers of the container.
	 * 		  | new.containersSet.containsAll(container.containersSet)
	 */
	protected void addAllContainersToContainersSet(Backpack container){
		this.containersSet.add(container);
		while (container.containersSet.iterator().hasNext()){
			this.containersSet.add(container.containersSet.iterator().next());
		}
	}
	
	/**
	 * Removes all the containers out of the containersSet of this ownable.
	 * (Used when this ownable is removed from a backpack)
	 * 
	 * @post The new containersSet will be empty.
	 * 		 | new.containersSet.isEmpty()
	 */
	protected void removeAllContainers(){
		this.containersSet.clear();
	}
}
