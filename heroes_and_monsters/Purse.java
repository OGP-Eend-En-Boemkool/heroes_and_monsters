package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

/**
 * A class of purses.
 * 
 * @invar	Every purse must have a correct identification.
 * 			| canHaveAsIdentification(getIdentification())
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Purse extends Storage {

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new purse with an own weight, unit and maximum capacity.
	 * 
	 * @param	ownWeight
	 * 			The weight of this purse.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @param	maxCapacity
	 * 			The maximum capacity of this purse.
	 * @effect	This purse is initialized as a storage with a calculated identification
	 * 			and the given ownWeight and unit.
	 * 			| super(calculateValidIdentification(), ownWeight, unit)
	 * @effect	The maximum capacity is set to the given maxCapacity in the given unit.
	 * 			| setMaxCapacity(maxCapacity, unit)
	 */
	@Raw
	public Purse(double ownWeight, Unit unit, double maxCapacity){
		super(calculateValidIdentification(), ownWeight, unit);
		setMaxCapacity(maxCapacity, unit);
	}
	
	/*******************************
	 * identification
	 *******************************
	
	/**
	 *  Set the identification of this purse to the given identification. The first
	 *  thousand weapons have a unique number.
	 * 
	 * @param 	identification
	 * 			The identification of this purse.
	 * @effect	The identification is added to the list of identifications. The size of
	 * 			this list is increased by 1.
	 * 			| addPurse(identification)
	 * @effect	The identification of this purse is set to identification.
	 * 			| super.setIdentification(identification)
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		super.setIdentification(identification);
		addPurse(identification);
	}
	

	/**
	 * Check whether a number is a perfect square.
	 * 
	 * @param 	number
	 * 			The number to check.
	 * @return	True if and only if the square root from this number is multiplied with
	 * 			itself and that is equal to the original number.
	 * 			| result == (sqrt(number) * sqrt(number) == number)
	 */
	private static boolean isPerfectSquare(long number){
		double s = Math.sqrt(number);
		return (s*s == number);
	}
	
	/**
	 * Check whether a number is a Fibonacci number.
	 * 
	 * @param 	number
	 * 			The number to check.
	 * @return	True if and only if (5* number * number + 4) or (5* number * number - 4)
	 * 			is a perfect square.
	 * 			| result == isPerfectSquare(5* number * number + 4) ||
				|			isPerfectSquare(5* number * number - 4)
	 */
	private static boolean isFibonacci(long number){
		return (isPerfectSquare(5* number * number + 4) ||
				isPerfectSquare(5* number * number - 4));
	}
	
	/**
	 * Check whether a purse can have this identification.
	 * 
	 * @param 	identification
	 * 			The identification to check.
	 * @return	False if identification is not a Fibonacci number. Also false when there
	 * 			have not been made 1000 purses yet and the identification already exists
	 * 			for another purse. True otherwise.
	 * 			| result == isFibonacci(identification) && ( idListPurses.size() >= 1000 ||
	 * 			|				!idListPurses.contains(identification) )
	 */
	public static boolean canHaveAsIdentification(long identification){
		return (isFibonacci(identification) && (getPurses().size() >= 1000 ||
	 				!getPurses().contains(identification) ));
	}
	
	/**
	 * Return a valid identification for a purse.
	 */
	private static long calculateValidIdentification(){
		long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		while (!canHaveAsIdentification(id)){
			id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
		}
		return id;
	}
	

	
	/********************************
	 * Capacity - totaal
	 ********************************/
	
	/**
	 * Variable registering the maximum value of the capacity of the purse.
	 */
	private double maximumCapacity;
	
	/**
	 * Set the maximum capacity to the given maxCapacity in the given unit.
	 * 
	 * @param 	maxCapacity
	 * 			The maximum capacity of this purse.
	 * @param 	unit
	 * 			The unit the capacity is given in.
	 * @post	The maximum capacity is set to the given value converted to kilogram.
	 * 			| new.getMaximumCapacity == unit.convertToKilogram(maxCapacity)
	 */
	@Raw
	private void setMaxCapacity(double maxCapacity, Unit unit){
		this.maximumCapacity = unit.convertToKilogram(maxCapacity);
	}
	
	/**
	 * Return the maximum capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 */
	@Override @Raw
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
		return (this.getTotalWeight(unit)-this.getOwnWeight(unit));
	}


	/******************************
	 * Value
	 ******************************/
	 
	/**
	 * Calculates the value in ducats of the purse.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	@Override
	public Ducat getValue() {
		return new Ducat(this.content.getValue());
	}
	
	/*********************************
	 * content
	 *********************************

	/**
	 * Check whether the given object can be added to this purse.
	 * 
	 * @return	True if the object can be added to any storage and is a ducat, the purse
	 * 			isn't broken, all the backpacks in which it's located have enough capacity
	 * 			to store the extra weight and the capacity of the ultimate holder is big
	 * 			enough to support the extra weight. False otherwise
	 * 			| result == super.canAddToStorage(object) && (object instanceof Ducat)
	 * 			|			&& (!(this.getBroken())) &&
	 * 			|			(for all holders of this {
	 * 			|					holder.getUsedCapacity(Unit.KG) + weight <=
	 * 			|					holder.getMaximumCapacity(Unit.KG) } )
	 */
	@Override
	public boolean canAddToStorage(Object object){
		if (!super.canAddToStorage(object)){
			return false;
		}
		if ((object instanceof Ducat)&&(!(this.getBroken()))){
			Ducat ducat = (Ducat) object;
			double weight = ducat.getWeight(Unit.KG);
			Iterator<Backpack> iterator = this.getContainersSet().iterator();
			while (iterator.hasNext()){
				Backpack backpack = iterator.next();
				if ((backpack.getUsedCapacity(Unit.KG) + weight) > backpack.getMaximumCapacity(Unit.KG)){
					return false;
				}
			}
			if (this.getUltimateHolder() instanceof Creature){
				Creature creature = (Creature) this.getUltimateHolder();
				if ((creature.getUsedCapacity(Unit.KG) + weight) > creature.getMaximumCapacity(Unit.KG)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Variable referencing the content of this purse.
	 */
	private Ducat content = new Ducat(0);
	
	/**
	 * Return the content of this purse.
	 */
	public Ducat getContent(){
		return new Ducat(content.getValue());
	}
	
	/**
	 * Set the content of this purse to the given ducat.
	 * 
	 * @param 	ducat
	 * 			The ducat to set the content of this purse to.
	 * @post	The content of this purse is set to content.
	 * 			| new.getContent() == ducat
	 */
	private void setContent(Ducat ducat){
		this.content = ducat;
	}
	
	/**
	 * Variable referencing if this purse is broken or not.
	 */
	private boolean broken = false;
	
	/**
	 * Return if this purse is broken or not.
	 */
	public boolean getBroken(){
		return this.broken;
	}
	
	/**
	 * Set broken to the given flag.
	 * 
	 * @param 	broken
	 * 			The given flag.
	 * @post	Broken is set to the given flag.
	 * 			| new.getBroken() = broken
	 */
	private void setBroken(boolean broken){
		this.broken = broken;
	}
	
	/**
	 * Add the given object to this purse.
	 * 
	 * @param 	object
	 * 			The object to add.
	 * @post	The given object is added to this purse.
	 * 			| content.add(object)
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added to this purse.
	 * 			| !canAddToStorage(object)
	 */
	@Override
	public void addToStorage(Object object) throws IllegalArgumentException {
		if (!canAddToStorage(object)){
			throw new IllegalArgumentException("The given object can't be added to this purse.");
		}
		Ducat ducat = (Ducat) object;
		setContent(getContent().add(ducat));
		((Ducat) object).subtract(ducat);
		if (getContent().getWeight(Unit.KG) > this.getMaximumCapacity(Unit.KG)){
			if (this.getHolder() instanceof Backpack){
				Backpack backpack = (Backpack) this.getHolder();
				backpack.addToStorage(getContent());
			}
			else {
				((Ducat) object).add(this.getContent());	
			}
			setContent(getContent().subtract(this));
			setBroken(true);
		}
	}
	
	/**
	 * Check whether the given object can be taken out of this purse.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @return	True if and only if the given object can be taken out of any storage and
	 * 			is a ducat and its value is smaller than or equal to the value of the
	 * 			content of this purse.
	 * 			| result == super.canTakeOutOfStorage(object) && object instanceof Ducat &&
	 * 			|			object.getValue() <= content.getValue()
	 */
	@Override
	public boolean canTakeOutOfStorage(Object object){
		if (!super.canTakeOutOfStorage(object)){
			return false;
		}
		if (object instanceof Ducat){
			Ducat ducat = (Ducat) object;
			return ducat.getValue() <= content.getValue();
		}
		else {
			return false;
		}
	}
	
	/**
	 * Take the given object out of this purse.
	 * 
	 * @param 	object
	 * 			The object to take out.
	 * @post	The value of the given object (that must be a ducat) is subtracted from
	 * 			the value of this purse.
	 * 			| this.content.subtract(object)
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this purse.
	 * 			| !canTakeOutOfStorage(object)
	 */
	@Override
	protected void takeOutOfStorage(Object object) throws IllegalArgumentException {
		if (!canTakeOutOfStorage(object)){
			throw new IllegalArgumentException("The given object can't be taken out of this purse.");
		}
		else {
			Ducat ducat = (Ducat) object;
			this.content.subtract(ducat);
		}
	}
	
	/**
	 * Transfer an object from this purse to the given storage.
	 * 
	 * @param 	other
	 * 			The other storage to transfer to.
	 * @param 	object
	 * 			The object to transfer.
	 * @effect	The given object is taken out of this storage and added to the other storage.
	 * 			| super.transferToStorage(other, object)
	 * @post	If this purse is empty after the transfer and it was transferred to another purse, this
	 * 			purse is taken out of the backpack it's in or dropped from the anchor it's on (if it is any
	 * 			of those).
	 * 			| if (this.getContent().getValue() == 0 && other instanceof Purse)
	 * 			| then:		if (this.getHolder() instanceof Backpack)
	 * 			|				then (getHolder()).takeOutOfStorage(this))
	 * 			|			else if (this.getHolder() instanceof Creature)
	 * 			|				then (getHolder()).dropFromAnchor(this))
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this storage.
	 * 			| !this.canTakeOutOfStorage(object)
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added to the other storage.
	 * 			| !other.canAddToStorage(object)
	 */
	@Override
	public void transferToStorage(Storage other, Object object)
			throws IllegalArgumentException {
		super.transferToStorage(other, object);
		if (this.getContent().getValue() == 0 && other instanceof Purse){
			if (this.getHolder() instanceof Backpack){
				((Backpack) this.getHolder()).takeOutOfStorage(this);
			}
			else if (this.getHolder() instanceof Creature){
				((Creature) this.getHolder()).dropFromAnchor(this);
			}
		}
	}
	
	/**
	 * The content of this purse is emptied.
	 * 
	 * @effect	The content of this purse is taken out of this storage.
	 * 			| takeOutOfStorage(getContent())
	 * @post	The value of this purse is 0.
	 * 			| new.getValue() == 0
	 */
	@Override
	protected void emptyStorage() throws IllegalArgumentException {
		this.takeOutOfStorage(getContent());
	}

	
	/******************************
	 * Weight - totaal
	 ******************************/
	 
	/**
	 * Calculates the weight in the given weight unit of the purse.
	 * 
	 * @return The resulting number must be a valid weight.
	 * 		   | canHaveAsTotalWeight(result)
	 */
	@Override
	public double getTotalWeight(Unit unit) {
		return this.getContent().getWeight(unit) + this.getOwnWeight(Unit.KG);
	}

	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Terminating a purse is impossible.
	 */
	protected void terminate(){
		setTerminate(false);
	}
}
