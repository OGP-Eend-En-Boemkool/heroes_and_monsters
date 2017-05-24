package Exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import heroes_and_monsters.*;

/**
 * A class for signaling illegal attempts to let a creature do something when it's dead.
 */
public class CreatureIsDeadException extends RuntimeException {

	/**
	 * Variable referencing the creature that is dead.
	 */
	private final Creature creature;

	/**
	 * Initialize this new creature is dead exception involving the
	 * given creature.
	 * 
	 * @param	creature
	 * 			The creature for the new creature is dead exception.
	 * @post	The creature involved in the new creature is dead exception
	 * 			is set to the given creature.
	 * 			| new.getCreature() == creature
	 */
	@Raw
	public CreatureIsDeadException(Creature creature) {
		this.creature = creature;
	}
	
	/**
	 * Return the creature involved in this creature is dead exception.
	 */
	@Raw @Basic
	public Creature getCreature() {
		return creature;
	}
}
