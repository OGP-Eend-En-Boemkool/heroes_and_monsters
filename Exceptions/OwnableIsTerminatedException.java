package Exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import heroes_and_monsters.*;

/**
 * A class for signaling illegal attempts to let an ownable do something when it's terminated.
 */
public class OwnableIsTerminatedException extends RuntimeException {

	/**
	 * Variable referencing the ownable that is terminated.
	 */
	private final Ownable ownable;

	/**
	 * Initialize this new ownable is terminated exception involving the
	 * given ownable.
	 * 
	 * @param	ownable
	 * 			The ownable for the new ownable is terminated exception.
	 * @post	The ownable involved in the new ownable is terminated exception
	 * 			is set to the given ownable.
	 * 			| new.getOwnable() == ownable
	 */
	@Raw
	public OwnableIsTerminatedException(Ownable ownable) {
		this.ownable = ownable;
	}
	
	/**
	 * Return the ownable involved in this ownable is terminated exception.
	 */
	@Raw @Basic
	public Ownable getOwnable() {
		return ownable;
	}
}
