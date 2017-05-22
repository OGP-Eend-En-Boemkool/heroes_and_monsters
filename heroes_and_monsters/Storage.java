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
	 * Variable referencing the content of a storage.
	 */
	protected ArrayList<Object> content = new ArrayList<Object>();
	
	/**
	 * Add the given object to this storage.
	 * 
	 * @param 	object
	 * 			The object to add.
	 * @post	The object is added to this storage.
	 * 			| content.add(object)
	 * @post	The size of content is increased by 1.
	 * 			| this.content.size() + 1 == new.content.size()
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be added to this backpack.
	 * 			| !canAddToBackpack(object)
	 */
	public void addToStorage(Object object) throws IllegalArgumentException {
		if (!canAddToStorage(object)){
			throw new IllegalArgumentException("The given object can't be added to this storage.");
		}
		content.add(object);
	}
	
	public abstract boolean canAddToStorage(Object object);
}

