package heroes_and_monsters;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

public abstract class Storage extends Ownable implements Capacity {

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new storage with an identification.
	 * 
	 * @param 	identification
	 * 			The identification of this storage.
	 * @effect	This storage is initialized as an ownable with the given identification.
	 * 			| super(identification)
	 */
	@Raw @Model
	protected Storage(long identification){
		super(identification);
	}
	
	/*****************************************
	 * identification
	 *****************************************
	
	/**
	 * Set the identification to the given identification.
	 * 
	 * @effect	The identification is added to the list of identifications and this storage
	 * 			is added to the list of storages (each storage has its own lists). Both 
	 * 			sizes are increased by 1.
	 * 			| super.setIdentification(identification)
	 */
	@Raw
	protected abstract void setIdentification(long identification);
	
	/******************************************
	 * storage
	 ******************************************
	
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
	 * @param 	storage
	 * 			The storage to transfer to.
	 * @param 	object
	 * 			The object to transfer.
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this storage.
	 * 			| !canTakeOutOfStorage(object)
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added to this storage.
	 * 			| !canAddToStorage(object)
	 */
	public void transferToStorage(Storage storage, Object object)
			throws IllegalArgumentException {
		this.takeOutOfStorage(object);
		storage.addToStorage(object);
	}
}

