package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of creatures with a name.
 * 
 * @invar	Every creature must have a valid name
 * 			| canHaveAsName(getName())
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public abstract class Creature{

	/**
	 * Initialize this new Creature with the given name as name.
	 * 
	 * @param	name
	 * 			The name of this creature.
	 * @post	The name of this creature is set to the given name.
	 * 			| new.getName() == name
	 * @throws	IllegalArgumentException()
	 * 			This creature can't have this name.
	 * 			| !canHaveAsName(name)
	 */
	@Raw
	protected Creature(String name) throws IllegalArgumentException {
		setName(name);
	}
	
	/**
	 * Variable referencing the name of a creature.
	 */
	public String name;
	
	/**
	 * Return the name of this creature.
	 */
	@Raw @Basic
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the name of this creature to the given name.
	 * 
	 * @post	The name of this creature is equal to name.
	 * 			| new.getName() == name
	 * @throws	IllegalArgumentException()
	 * 			This creature can't have this name.
	 * 			| !canHaveAsName(name)
	 */
	@Raw
	protected abstract void setName(String name) throws IllegalArgumentException;
	
	/**
	 * Checks whether this creature can have the given name as name.
	 * 
	 * @param 	name
	 * 			The name to check.
	 * @return	True if and only if the given name is effective and the name starts with
	 * 			an uppercase letter.
	 * 			| result == ((name != null) && (Character.isUpperCase(name.charAt(0))))
	 */
	@Raw
	public boolean canHaveAsName(String name){
		if (name == null){
			return false;
		}
		else if (!Character.isUpperCase(name.charAt(0))){
			return false;
		}
		else {
			return true;
		}
	};
}
