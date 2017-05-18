package heroes_and_monsters;

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
	
	protected abstract void setIdentification(long identification);
}

