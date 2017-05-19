package Exceptions;

import be.kuleuven.cs.som.annotate.*;
import heroes_and_monsters.Ownable;

/**
 * A class for signaling illegal attempts for setting the holder of an ownable if that
 * ownable has already a holder.
 * 
 * @author Linde en Lotte
 * @ version 1.0
 */
public class HasAlreadyHolderException extends RuntimeException {

	/**
	 * Variable referencing the ownable that already has a holder, so that can't have a
	 * new one.
	 */
	private final Ownable ownable;
	
	/**
	 * Initialize this new has already holder exception involving the given ownable.
	 * 
	 * @param 	ownable
	 * 			The ownable for the new has already holder exception.
	 * @post	The ownable involved in the new has already holder exception is set to the
	 * 			given ownable.
	 * 			| new.getOwnable() == ownable
	 */
	@Raw
	public HasAlreadyHolderException(Ownable ownable){
		this.ownable = ownable;
	}
	
	/**
	 * Return the ownable involved in this has already holder exception.
	 */
	@Raw @Basic
	public Ownable getOwnable(){
		return this.ownable;
	}
}
