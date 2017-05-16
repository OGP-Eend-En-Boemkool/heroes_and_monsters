package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * A class of monsters.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Monster extends Creature {

	/**
	 * Initialize this new monster with the given name as name and the given strength
	 * as strength.
	 * 
	 * @param 	name
	 * 			The name of this monster.
	 * @param	strength
	 * 			The strength of this monster.
	 * @effect	This monster is initialized as a creature with the given name and strength.		
	 */
	@Raw
	public Monster(String name, BigDecimal strength) throws IllegalArgumentException {
		super(name, strength);
	}
	
	/**
	 * Set the name of this monster to the given name.
	 * 
	 * @effect	The name of this monster is set to the given name.
	 */
	@Raw
	protected void setName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name)){
			throw new IllegalArgumentException("This is not a valid name for a monster");
		}
		this.name = name;
	}
	

	/**
	 * Checks whether this monster can have the given name as name.
	 * 
	 * @param	name
	 * 			The name to check
	 * @return	False if the given name can't be a name for any creature or if it has
	 * 			a character that is not valid for a name.
	 * 			True otherwise.
	 */
	@Override @Raw
	public boolean canHaveAsName(String name){
		if (!super.canHaveAsName(name)){
			return false;
		}
		else if (!name.matches("[a-zA-Z' ]+")){
			return false;
		}
		else {
			return true;
		}
	}
}
