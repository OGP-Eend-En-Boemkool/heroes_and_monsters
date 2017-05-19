package Exceptions;

import be.kuleuven.cs.som.annotate.*;
import heroes_and_monsters.Ownable;

public class AnchorIsNotEmptyException extends RuntimeException {

	/**
	 * Variable referencing to the anchor that is not empty and where an ownable wants to
	 * be put in.
	 */
	private final String anchor;
	
	/**
	 * Initialize this new anchor is not empty exception involving the given anchor.
	 * 
	 * @param 	ownable
	 * 			The ownable involving this new anchor is not empty exception.
	 * @post	The anchor involved in this anchor is not empty exception is set to the
	 * 			given anchor.
	 * 			| new.getAnchor() == anchor
	 */
	@Raw
	public AnchorIsNotEmptyException(String anchor){
		this.anchor = anchor;
	}
	
	/**
	 * Return the anchor involving this anchor is not empty exception.
	 */
	@Raw @Basic
	public String getAnchor(){
		return this.anchor;
	}
}
