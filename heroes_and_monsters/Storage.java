package heroes_and_monsters;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of storages.
 * 
 * @invar	Every storage must have a correct total weight.
 * 			| canHaveAsTotalWeight(getTotalWeight(Unit.KG))
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public abstract class Storage extends Ownable implements Capacity {

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new storage with an identification, own weight and unit.
	 * 
	 * @param 	identification
	 * 			The identification of this storage.
	 * @param	ownWeight
	 * 			The weight of this storage.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @effect	This storage is initialized as an ownable with the given identification.
	 * 			| super(identification, ownWeight, unit)
	 */
	@Raw @Model
	protected Storage(long identification, double ownWeight, Unit unit){
		super(identification, ownWeight, unit);
	}
	
	/*****************************************
	 * identification
	 *****************************************/
	
	/**
	 * Set the identification to the given identification.
	 * 
	 * @effect	The identification is added to the list of identifications (each storage
	 * 			has its own list). The sizes of the lists are increased by one.
	 * 			| super.setIdentification(identification)
	 */
	@Raw @Override
	protected abstract void setIdentification(long identification);
	
	
	/*****************************************
	 * Weight
	 *****************************************/
	
	/**
	 * Checks whether or not the given weight is a legal value for the total weight.
	 * 
	 * @param  weight
	 * 		   The value that needs to be checked.
	 * @return True if the given value is bigger than or equal to the own weight of the storage object.
	 */
	protected boolean canHaveAsTotalWeight(double weight){
		return ((weight>=this.getOwnWeight(Unit.KG)));
	}
	
	/**
	 * Calculates the new total weight of the storage object. Used after adding or removing an item out of the backpack.
	 * 
	 * @return The new total weight must be a valid value for total weight.
	 * 		   | canHaveAsTotalWeight(result)
	 */
	protected abstract double getTotalWeight(Unit unit);
	
	/******************************************
	 * storage
	 ******************************************/
	
	/**
	 * Add the given object to this storage.
	 * 
	 * @param 	object
	 * 			The object to add.
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added to this storage.
	 * 			| !canAddToStorage(object)
	 */
	public abstract void addToStorage(Object object) throws IllegalArgumentException;
	
	/**
	 * Check whether the given object can be added to this storage.
	 * 
	 * @param 	object
	 * 			The object to check.
	 */
	public abstract boolean canAddToStorage(Object object);
	
	/**
	 * Take the given object out of this storage.
	 * 
	 * @param 	object
	 * 			The object to take out.
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this storage.
	 * 			| !canTakeOutOfStorage(object)
	 */
	public abstract void takeOutOfStorage(Object object) throws IllegalArgumentException;
	
	/**
	 * Check whether the given object can be taken out of this storage.
	 * 
	 * @param 	object
	 * 			The object to check.
	 */
	public abstract boolean canTakeOutOfStorage(Object object);
	
	/**
	 * Transfer an object from this storage to the given storage.
	 * 
	 * @param 	other
	 * 			The other storage to transfer to.
	 * @param 	object
	 * 			The object to transfer.
	 * @effect	The given object is taken out of this storage.
	 * 			| this.takeOutOfStorage(object)
	 * @effect	The given object is added to the other storage.
	 * 			| other.addToStorage(object)
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this storage.
	 * 			| !this.canTakeOutOfStorage(object)
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added to the other storage.
	 * 			| !other.canAddToStorage(object)
	 */
	public void transferToStorage(Storage other, Object object)
			throws IllegalArgumentException {
		this.takeOutOfStorage(object);
		other.addToStorage(object);
	}
	
	/**
	 * The content of this storage is emptied.
	 * 
	 * @throws 	IllegalArgumentException
	 * 			One of the objects can't be taken out of this storage. This can't happen.
	 * 			| !canTakeOutOfStorage(object)
	 */
	public abstract void emptyStorage() throws IllegalArgumentException;
}

