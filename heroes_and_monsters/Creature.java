package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;

/**
 * A class of creatures with a name and a strength.
 * 
 * @invar	Every creature must have a valid name.
 * 			| canHaveAsName(getName())
 * @invar	Every creature must have a valid strength.
 * 			| isValidStrength(getStrength())
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public abstract class Creature{

	/**
	 * Initialize this new Creature with the given name as name and the
	 * given strength as strength.
	 * 
	 * @param	name
	 * 			The name of this creature.
	 * @post	The name of this creature is set to the given name.
	 * 			| new.getName() == name
	 * @post	The strength of this creature is set to the given strength.
	 * 			| new.getStrength() == strength
	 * @throws	IllegalArgumentException()
	 * 			This creature can't have this name.
	 * 			| !canHaveAsName(name)
	 */
	@Raw
	protected Creature(String name, BigDecimal strength) throws IllegalArgumentException {
		setName(name);
		setStrength(strength);
	}
	
	/**
	 * Variable referencing the name of a creature.
	 */
	protected String name;
	
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
	}
	
	/**
	 * Variable referencing the strength of a creature.
	 */
	protected BigDecimal strength;
	
	/**
	 * Constant with the default value of strength.
	 */
	private static final BigDecimal DEFAULT_STRENGTH = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	/**
	 * Return the default value of strength.
	 */
	@Raw @Basic @Immutable
	public final static BigDecimal getDefaultStrength(){
		return DEFAULT_STRENGTH;
	}
	
	/**
	 * Return the strength of this creature.
	 */
	@Raw @Basic
	public BigDecimal getStrength(){
		return this.strength;
	}
	
	/**
	 * Set strength of this creature to the given strength.
	 * 
	 * @param 	strength
	 * 			The strength of this creature.
	 * @post	If the given strength is not valid, the strength is set
	 * 			to the default value.
	 * 			| if (!isValidStrength(strength)){
	 * 			| 		strength = getDefaultStrength() }
	 * @post	The strength of this creature is equal to the given strength rounded
	 * 			(using HALF_DOWN) to a decimal number with 2 fractional digits.
	 * 			| if (numeral.scale() != 2){
	 * 			|		strength = strength.setScale(2, RoundingMode.HALF_UP) }
	 * @post	The strength for this creature is the same as strength.
	 * 			| new.getStrength() == strength
	 */
	@Raw
	protected void setStrength(BigDecimal strength){
		if (!isValidStrength(strength)){
			strength = getDefaultStrength();
		}
		if (strength.scale() != 2){
			strength = strength.setScale(2, RoundingMode.HALF_UP);
		}
		this.strength = strength;
	}
	
	/**
	 * Check whether the given strength is valid.
	 * 
	 * @param 	strength
	 * 			The strength to check.
	 * @return	True if and only if the given strength is effective and not negative.
	 * 			| result == (strength != null && strength.signum() != -1)
	 */
	@Raw
	public static boolean isValidStrength(BigDecimal strength){
		if (strength == null || strength.signum() == -1){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Multiply the strength with the given number.
	 * 
	 * @param 	number
	 * 			The number to multiply the strength from this creature with.
	 * @post	The strength of this creature is multiplied by the given number.
	 * 			| new.getStrength() = this.getStrength().multiply(new BigDecimal(number))
	 */
	protected void multiplyStrength(int number){
		setStrength(this.getStrength().multiply(new BigDecimal(number)));
	}
	
	/**
	 * Divide the strength with the given number.
	 * 
	 * @param 	number
	 * 			The number to divide the strength from this creature by.
	 * @post	If the given number is zero, it is changed to one, because division by zero
	 * 			is impossible.
	 * 			| if (number == 0){
	 * 			|		number = 1 }
	 * @post	The strength of this creature is divided by number.
	 * 			| new.getStrength() = this.getStrength().divide(new BigDecimal(number), RoundingMode.HALF_UP)
	 */
	protected void divideStrength(int number){
		if (number == 0){
			number = 1;
		}
		setStrength(this.getStrength().divide(new BigDecimal(number), RoundingMode.HALF_UP));
	}
	
}
