package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;


import Exceptions.*;

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
	private long identification;
	
	/**
	 * Variable where we can store all the id's of armors.
	 */
	private static ArrayList<Long> idListArmors = new ArrayList<Long>();
	
	/**
	 * Add the given identification to the list of identifications of armors.
	 * 
	 * @param 	identification
	 * 			The identification to add.
	 * @post	The given identification is added to the list.
	 * 			| idListArmors.add(identification)
	 * @post	The size of the list is increased by one.
	 * 			| new.idListArmors.size() = this.idListArmors.size() + 1
	 */
	protected void addArmor(long identification){
		idListArmors.add(identification);
	}
	
	/**
	 * Return the arraylist with the identifications of all armors.
	 */
	protected static ArrayList<Long> getArmors(){
		return new ArrayList<Long>(idListArmors);
	}
	
	/**
	 * Variable where we can store all the id's of weapons.
	 */
	private static ArrayList<Long> idListWeapons = new ArrayList<Long>();
	
	/**
	 * Add the given identification to the list of identifications of weapons.
	 * 
	 * @param 	identification
	 * 			The identification to add.
	 * @post	The given identification is added to the list.
	 * 			| idListWeapons.add(identification)
	 * @post	The size of the list is increased by one.
	 * 			| new.idListWeapons.size() = this.idListWeapons.size() + 1
	 */
	protected void addWeapon(long identification){
		idListWeapons.add(identification);
	}
	
	/**
	 * Return the arraylist with the identifications of all weapons.
	 */
	protected static ArrayList<Long> getWeapons(){
		return new ArrayList<Long>(idListWeapons);
	}
	
	/**
	 * Variable where we can store all the id's of backpacks.
	 */
	private static ArrayList<Long> idListBackpacks = new ArrayList<Long>();
	
	/**
	 * Add the given identification to the list of identifications of backpacks.
	 * 
	 * @param 	identification
	 * 			The identification to add.
	 * @post	The given identification is added to the list.
	 * 			| idListBackpacks.add(identification)
	 * @post	The size of the list is increased by one.
	 * 			| new.idListArmorBackpacks.size() = this.idListBackpacks.size() + 1
	 */
	protected void addBackpack(long identification){
		idListBackpacks.add(identification);
	}
	
	/**
	 * Return the arraylist with the identifications of all backpacks.
	 */
	protected static ArrayList<Long> getBackpacks(){
		return new ArrayList<Long>(idListBackpacks);
	}
	
	/**
	 * Variable where we can store all the id's of purses.
	 */
	private static ArrayList<Long> idListPurses = new ArrayList<Long>();
	
	/**
	 * Add the given identification to the list of identifications of purses.
	 * 
	 * @param 	identification
	 * 			The identification to add.
	 * @post	The given identification is added to the list.
	 * 			| idListPurses.add(identification)
	 * @post	The size of the list is increased by one.
	 * 			| new.idListPurses.size() = this.idListPurses.size() + 1
	 */
	protected void addPurse(long identification){
		idListPurses.add(identification);
	}
	
	/**
	 * Return the arraylist with the identifications of all purses.
	 */
	protected static ArrayList<Long> getPurses(){
		return new ArrayList<Long>(idListPurses);
	}
	
	/**
	 * Return the identification of this ownable.
	 * 
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Raw @Basic
	public long getIdentification() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return this.identification;
	}
	
	/**
	 * Set the identification to the given identification if possible.
	 * 
	 * @param 	identification
	 * 			The identification of this ownable.
	 * @post	The identification of this ownabke is set to the given identification.
	 * 			| new.getIdentification() == identification
	 * @effect	The ownable is added to the list of identifications (every ownable
	 * 			has its own list).
	 * 			| addClass(identification)
	 * @effect	The size of the list of identifications is increased by 1.
	 * 			| new.idListClass.size() = this.idListClass.size() + 1
	 */
	@Raw
	protected void setIdentification(long identification){
		this.identification = identification;
	}
	
	/********************************
	 * Weight
	 ********************************/
	
	/**
	 * Variable referencing the weight in kilogram of the ownable object.
	 */
	private double ownWeight = 0;
	
	/**
	 * Return the weight of the ownable object. 
	 * 
	 * @param  unit
	 * 		   The weight in unit in which the weight should be returned.
	 * @return The resulting number must be bigger than or equal to 0.
	 * 		   | result >= 0
	 */
	public double getOwnWeight(Unit unit){
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
		if (isValidOwnWeight(weight)){
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
	public static boolean isValidOwnWeight(double weight){
		return (weight >= 0);
	}	
		
	
	/********************************
	 * holder
	 ********************************/
	
	/**
	 * Variable referencing the holder of an ownable.
	 */
	private Object holder = null;
	
	/**
	 * Return the holder of this ownable.
	 * 
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Raw @Basic
	public Object getHolder() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return this.holder;
	}
	/**
	 * Return the ultimate owner of this ownable.
	 * 
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Raw
	public Object getUltimateHolder() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
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
	 * @return	True if and only if the holder is a living creature or a non-terminated
	 * 			backpack or there is no holder at all. 
	 * 			| result == (holder instanceof Creature && !((Creature) holder).getKilled())
	 *			|			|| (holder instanceof Backpack && !((Backpack) holder).getTerminated()) ||
	 *			|			holder == null
	 */
	@Raw
	public boolean canHaveAsHolder(Object holder){
		return ((holder instanceof Creature && !((Creature) holder).getKilled())
				|| (holder instanceof Backpack && !((Backpack) holder).getTerminated()) ||
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
	public boolean isValidValue(Ducat value){
		return (value.compareTo(new Ducat(0)) >= 0);
	}
	
	/**
	 * Calculates the value in ducats of the ownable object.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	public abstract Ducat getValue();
	
	/********************************
	 * Containers
	 ********************************/
	
	/**
	 * Set that references all the backpacks in which the ownable is located.
	 */
	private HashSet<Backpack> containersSet = new HashSet<Backpack>() ;
	
	/**
	 * Return a set with all the backpacks that contain this ownable object.
	 */
	protected HashSet<Backpack> getContainersSet(){
		return new HashSet<Backpack>(this.containersSet);
	}
	
	/**
	 * Add all the backpacks that contain this new container and this new container to the set of containers of this ownable.
	 * (Used when this ownable is added to a backpack)
	 * 
	 * @param container
	 * 		  The backpack that contains the ownable.
	 * @post  The new containersSet will contain the new container.
	 * 		  | new.getContainersSet().contains(container)
	 * @post  The new containersSet will contain all the containers of the container.
	 * 		  | new.getContainersSet().containsAll(container.getContainersSet())
	 */
	protected void addAllContainersToContainersSet(Backpack container){
		this.containersSet.add(container);
		Iterator<Backpack> iterator = container.getContainersSet().iterator();
		while (iterator.hasNext()){
			this.containersSet.add(iterator.next());
		}
	}
	
	/**
	 * Removes all the containers out of the containersSet of this ownable.
	 * (Used when this ownable is removed from a backpack)
	 * 
	 * @post The new containersSet will be empty.
	 * 		 | new.getContainersSet().isEmpty()
	 */
	protected void removeAllContainers(){
		this.getContainersSet().clear();
	}
	
	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Variable referencing if this ownable is terminated or not.
	 */
	private boolean terminated = false;
	
	/**
	 * Return if this ownable is terminated or not.
	 */
	public boolean getTerminated(){
		return this.terminated;
	}
	
	/**
	 * Terminate this ownable if possible.
	 */
	protected abstract void terminate();
	
	/**
	 * Set the flag of terminated to the given flag.
	 * 
	 * @param 	terminated
	 * 			The given flag of terminated.
	 * @post	The flag of terminated of this ownable is ste to the given flag.
	 * 			| this.terminated = terminated
	 */
	protected void setTerminate(boolean terminated){
		this.terminated = terminated;
	}
}
