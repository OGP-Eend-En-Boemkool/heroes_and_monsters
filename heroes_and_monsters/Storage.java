package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.Raw;

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
	protected Storage(long identification){
		super(identification);
	}
	
	/**
	 * Set the identification to the given identification.
	 * 
	 * @effect	The identification is added to the list of identifications.
	 */
	protected abstract void setIdentification(long identification);
	
	@Raw
	protected abstract void setHolder(Object holder);
}

