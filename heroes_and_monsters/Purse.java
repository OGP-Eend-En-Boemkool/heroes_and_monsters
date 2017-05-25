package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.util.concurrent.ThreadLocalRandom;

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
	 * Initialize this new purse with an identification, own weight and unit.
	 * 
	 * @param	ownWeight
	 * 			The weight of this purse.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @effect	This purse is initialized as a storage with a calculated identification
	 * 			and the given ownWeight and unit.
	 * 			| super(calculateValidIdentification(), ownWeight, unit)
	 */
	@Raw
	public Purse(double ownWeight, Unit unit){
		super(calculateValidIdentification(), ownWeight, unit);
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
	 * 			| super.setIdentification(identification)
	 * @post	The identification of this purse is set to identification.
	 * 			| new.getIdentification() = identification
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		this.identification = identification;
		idListPurses.add(identification);
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
		return (isFibonacci(identification) && ( idListPurses.size() >= 1000 ||
	 				!idListPurses.contains(identification) ));
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
	 * Return the maximum capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 */
	@Override
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
	 * @effect 
	 */
	@Override
	public double getUsedCapacity(Unit unit) {
		return this.getTotalWeight(unit);
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
	protected int getValue() {
		return this.content.getValue();
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
			while (this.getContainersSet().iterator().hasNext()){
				Backpack backpack = this.getContainersSet().iterator().next();
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
		content.add(ducat);
		if (content.getWeight(Unit.KG) > this.getMaximumCapacity(Unit.KG)){
			if (this.getHolder() instanceof Backpack){
				Backpack backpack = (Backpack) this.getHolder();
				backpack.addToStorage(content);
			}
			this.content.subtract(this);
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
	 * 			| result == super.canAddToStorage(object) && object instanceof Ducat &&
	 * 			|			object.getValue() <= content.getValue()
	 */
	@Override
	public boolean canTakeOutOfStorage(Object object){
		if (!super.canAddToStorage(object)){
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
	protected double getTotalWeight(Unit unit) {
		return this.content.getWeight(unit);
	}

	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Terminating a purse is impossible.
	 */
	protected void terminate(){
		this.terminated = false;
	}
}
