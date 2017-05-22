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
	 * @effect	The identification is added to the list of identifications.
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
	 * @throws	IllegalArgumentException
	 * 			The object cannot be added.
	 * 			| !canAddToStorage(object)
	 */
	public abstract void addToStorage(Object object) throws IllegalArgumentException;
	
	/**
	 * Check whether the given object can be added to this storage.
	 */
	public abstract boolean canAddToStorage(Object object);
	
	public void takeOutOfStorage(Object object){
		
	}
	
	public abstract boolean canTakeOutOfStorage(Object object);
}

