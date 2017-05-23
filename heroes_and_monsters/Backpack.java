package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class of backpacks.
 * 
 * @invar	Every backpack must have the next correct identification number.
 * 			| getIdentification == calculateValidIdentification()
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Backpack extends Storage{

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new backpack with an identification.
	 * 
	 * @effect	This backpack is initialized as an ownable with a calculated identification.
	 * 			| super(calculateValidIdentification())
	 */
	public Backpack(int standardValue, double maxCapacity, Unit unit){
		super(calculateValidIdentification());
		setStandardValue(standardValue);
		setMaxCapacity(maxCapacity, unit);
	}
	
	/********************************
	 * identification
	 ********************************
	
	/**
	 * Set the identification of this backpack to the given identification.
	 * 
	 * @param 	identification
	 * 			The identification of this backpack.
	 * @effect	The identification is added to the list of identifications of backpacks.
	 * 			| idListBackpacks.add(identification)
	 * @post	The identification of this backpack is set to identification.
	 * 			| new.getIdentification() = identification
	 */
	@Raw @Override
	protected void setIdentification(long identification){
		this.identification = identification;
		idListBackpacks.add(identification);
	}
	
	/**
	 * Calculate a valid identification for a backpack. This is the sum of all the binomial
	 * coefficients with at the top the amount of backpacks that already exist plus one and
	 * at the bottom an increasing number from zero to that same number (increased with
	 * every new term of the sum).
	 */
	private static long calculateValidIdentification(){
		long n = idListBackpacks.size() + 1;
		long id = 0;
		for (int i = 0; i <= n; i++){
			id += calculateBinomial(n,i);
		}
		return id;
	}
	
	/**
	 * Calculate the binomial coefficient.
	 * 
	 * @param 	n
	 * 			The top of the binomial coefficient.
	 * @param 	i
	 * 			The bottom of the binomial coefficient.
	 * @return	The binomial coefficient of n and i.
	 * 			| if (i==0 | i==n){
	    		|		return 1 }  
	    		| else {
	    		|	return (calculateBinomial(n - 1, i) + calculateBinomial(n - 1, i - 1))
	 */
	private static long calculateBinomial(long n, long i){
		long coefficient;
	    if (i==0 | i==n){
	    	coefficient = 1;
	    }  
	    else{
	    	coefficient = calculateBinomial(n - 1, i) + calculateBinomial(n - 1, i - 1);
	    }
	    return coefficient;
	}

	/********************************
	 * Capacity - totaal
	 ********************************/
	
	/**
	 * Variable registering the maximum capacity of the backpack.
	 */
	private double maximumCapacity;
	
	/**
	 * Set the maximum capacity of the backpack to the given maximum capacity in the given weight unit.
	 * 
	 * @param maximumCapacity
	 * 		  The number to which the maximum capacity should be set.
	 * @param unit
	 * 		  The weight unit in which the capacity is set.
	 */
	private void setMaxCapacity(double maximumCapacity, Unit unit){
		this.maximumCapacity = maximumCapacity;
	}
	
	/**
	 * Return the maximum capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 */
	@Override
	public double getMaximumCapacity(Unit unit) {
		return unit.convertFromKilogram(maximumCapacity);
	}

	/**
	 * Return the used part of the total capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @return the resulting number cannot be larger than the maximum capacity of the object.
	 * 		   | result <= this.getMaximumCapacity()
	 */
	@Override
	public double getUsedCapacity(Unit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*********************************
	 * content
	 *********************************
	
	/**
	 * Variable referencing the content of a backpack.
	 */
	private ArrayList<Object> content = new ArrayList<Object>();
	
	/**
	 * Return the content of this backpack.
	 */
	public ArrayList<Object> getContent(){
		return new ArrayList<Object>(this.content);
	}
	
	/**
	 * Add the given object to this backpack.
	 * 
	 * @param 	object
	 * 			The object to add.
	 * @post	The object is added to this backpack.
	 * 			| if (object instanceof Ducat){
	 * 			|		if one of the objects in content already is a ducat {
	 * 			|			d.add(object) } }
	 * 			| else {
	 * 			| 		content.add(object) }
	 * @post	The size of content is increased by 1. Unless there is a ducat added and
	 * 			there already is a ducat in this backpack, then the value of that ducat is
	 * 			just increased.
	 * 			| if (object instanceof Ducat){
	 * 			|		if one of the objects in content already is a ducat {
	 * 			|				this.content.size() == new.content.size() } }
	 * 			| else {
	 * 			| 		this.content.size() + 1 == new.content.size() }
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be added to this backpack.
	 * 			| !canAddToStorage(object)
	 */
	@Override
	public void addToStorage(Object object) throws IllegalArgumentException {
		if (!canAddToStorage(object)){
			throw new IllegalArgumentException("The given object can't be added to this backpack.");
		}
		if (object instanceof Ducat){
			Ducat ducat = (Ducat) object;
			boolean alreadyDucat = false;
			for (Object obj: getContent()){
				if (obj instanceof Ducat){
					Ducat d = (Ducat) obj;
					d.add(ducat);
					alreadyDucat = true;
				}
			}
			if (!alreadyDucat){
				content.add(ducat);
			}
		}
		else {
			content.add(object);
		}
	}
	
	/**
	 * Check whether the given object can be added to this backpack.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @return	False if the object is not an ownable or a ducat. Also false if when the
	 * 			object is a ownable, it already has a holder. Also false if with this
	 * 			object the maximum capacity of this backpack would be exceeded. Also false
	 * 			if by adding an armor to a backpack, a hero would carry too many armors.
	 * 			Also false if this backpack is in 1 or more other backpacks and one of
	 * 			their capacities could be exceeded.
	 * 			| result == ( (object instanceof Ownable || object instanceof Ducat) &&
	 * 			|				ownable.getHolder() == null &&
	 * 			|				(this.getUsedCapacity(Unit.KG) + weight <=
	 * 			|				this.getMaximumCapacity(Unit.KG)) &&
	 * 			|				hero.canAddArmor(object) &&
	 * 			|				(backpack.getUsedCapacity(Unit.KG) + weight <=
	 * 			|				backpack.getMaximumCapacity(Unit.KG) )
	 */
	@Override
	public boolean canAddToStorage(Object object){
		double weight = 0;
		if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			if (ownable.getHolder() != null){
				return false;
			}
			weight = ownable.getWeight(Unit.KG);
		}
		else if (object instanceof Ducat){
			Ducat ducat = (Ducat) object;
			weight = ducat.getWeight(Unit.KG);
		}
		else {
			return false;
		}
		if (this.getUsedCapacity(Unit.KG) + weight > this.getMaximumCapacity(Unit.KG)){
			return false;
		}
		if (object instanceof Armor){
			if (this.getUltimateHolder() instanceof Hero){
				Hero hero = (Hero) this.getUltimateHolder();
				if (!hero.canAddArmor(object)){
					return false;
				}
			}
		}
		if (this.getHolder() instanceof Backpack){
			Object holder = this.getHolder();
			while (holder instanceof Backpack){
				Backpack backpack = (Backpack) holder;
				if (backpack.getUsedCapacity(Unit.KG) + weight > backpack.getMaximumCapacity(Unit.KG)){
					return false;
				}
				holder = backpack.getHolder();
			}
		}
		else {
			return true;
		}
	}
	
	/**
	 * Check whether the given object can be taken out of this backpack.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @return	True if the given object is direct or indirect content of this backpack.
	 * 			Also true if the given object is a ducat and the value of all the ducats
	 * 			in this backpack is greater than or equal to the given ducat.
	 * 			| result == (for one backpack in this backpack (including this backpack) {
	 * 			|					backpack.content.contains(object) }) ||
	 * 			|			(if (object instanceof Ducat) {
	 * 			|					ducat = total value of ducats in this backpack
	 * 			|					ducat.getValue() >= object.getValue() )
	 */
	@Override
	public boolean canTakeOutOfStorage(Object object){
		Ducat ducat = new Ducat(0);
		ArrayList<Backpack> backpacks = new ArrayList<Backpack>();
		backpacks.add(this);
		while (backpacks.size() > 0){
			Backpack currentBackpack = backpacks.get(0);
			while (currentBackpack.getBackpackIterator().hasMoreElements()){
				Object obj = currentBackpack.getBackpackIterator().nextElement();
				if (obj instanceof Ducat){
					Ducat d = (Ducat) obj;
					ducat.add(d);
				}
				else if (object == obj){
					return true;
				}
				else if (obj instanceof Backpack){
					Backpack b = (Backpack) obj;
					backpacks.add(b);
				}
				else if (obj instanceof Purse){
					Purse p = (Purse) obj;
					ducat.add(p.getContent());
				}
			}
			backpacks.remove(0);
		}
		if (object instanceof Ducat){
			Ducat ducatObject = (Ducat) object;
			return ducat.getValue() >= ducatObject.getValue();
		}
		else {
			return false;
		}
	}

	/**
	 * Take the given object out of this backpack.
	 * 
	 * @param 	object
	 * 			The object to take out.
	 * @post	If the given object is an amount of ducat, the same amount is taken out of
	 * 			this backpack or a backpack direct or indirect in this backpack or a purse
	 * 			direct or indirect in this backpack or a combination of these possibilities.
	 * 			If it's not, the given object is taken out of its holder.
	 * 			| if (object instanceof Ducat){
	 * 			|			for all ducats and purses {
	 * 			|				ducat.subtract(object) or purse.takeOutOfStorage(object)
	 * 			|				(until enough is subtracted) } }
	 * 			| else {
	 * 			|			object.getHolder().content.remove(object) }
	 * @post	If the given object is not a ducat, the size of the content of its holder
	 * 			is decreased by 1.
	 * 			| if (!(object instanceof Ducat)){
	 * 			|			new.getContent().size() == this.getContent().size() - 1
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this backpack.
	 * 			| !canTakeOutOfStorage(object)
	 */
	@Override
	public void takeOutOfStorage(Object object) throws IllegalArgumentException {
		if (!canTakeOutOfStorage(object)){
			throw new IllegalArgumentException("The given object can't be taken out of this backpack.");
		}
		else {
			Ducat restingDucat = new Ducat(0);
			boolean objectFound = false;
			if (object instanceof Ducat){
				restingDucat = (Ducat) object;
			}
			ArrayList<Backpack> backpacks = new ArrayList<Backpack>();
			backpacks.add(this);
			while (backpacks.size() > 0 && !objectFound){
				Backpack currentBackpack = backpacks.get(0);
				while (currentBackpack.getBackpackIterator().hasMoreElements() && !objectFound){
					Object obj = currentBackpack.getBackpackIterator().nextElement();
					if (obj instanceof Ducat && object instanceof Ducat){
						Ducat d = (Ducat) obj;
						if (d.getValue() >= restingDucat.getValue()){
							d.subtract(restingDucat);
							objectFound = true;
						}
						else {
							restingDucat.subtract(d);
							d.subtract(d);
						}
						
					}
					else if (object == obj){
						currentBackpack.content.remove(object);
						objectFound = true;
					}
					else if (obj instanceof Backpack){
						Backpack b = (Backpack) obj;
						backpacks.add(b);
					}
					else if (obj instanceof Purse && object instanceof Ducat){
						Purse p = (Purse) obj;
						if (p.getValue() >= restingDucat.getValue()){
							p.takeOutOfStorage(restingDucat);
							objectFound = true;
						}
						else {
							restingDucat.subtract(p);
							p.takeOutOfStorage(p.getContent());
						}
					}
				}
				backpacks.remove(0);
			}
		}
	}
	
	/***************************
	 * iterator
	 ***************************/
	
	/**
	 * Return an iterator that iterates over the content of this backpack.
	 */
	public Enumeration getBackpackIterator(){
		return new Enumeration(){

			/**
			 * Variable indexing the current element of this iterator.
			 */
			private int indexCurrent = 0;
			
			/**
			 * Check whether the iterator has more elements.
			 * 
			 * @return	True if and only if the size of the content of this backpack
			 * 			minus the current index is greater than zero.
			 * 			| result == content.size() - indexCurrent > 0
			 */
			@Override
			public boolean hasMoreElements() {
				return content.size() - indexCurrent > 0;
			}

			/**
			 * Return the next element of the content of this backpack.
			 */
			@Override
			public Object nextElement() {
				Object object = content.get(indexCurrent);
				indexCurrent++;
				return object;
			}
			
		};
	}

	/***************************
	 * value
	 ***************************/
	
	/**
	 * Variable registering the standard value of the backpack in itself.
	 */
	private int standardValue = 0;
	
	/**
	 * Sets the standard value of the backpack to the given integer 'standardValue'.
	 *
	 * @param standardValue
	 * 		  The integer to which the standardValue needs to be set.
	 * @post  if the given standard value is valid, the standardValue will be set to the given integer.
	 * 		  | if this.isValidStandardValue(standardValue)
	 * 		  | then new.getStandardValue().equals(standardValue)
	 * @post  If the given value isn't valid, standardValue is set to the default.
	 * 		  | if !this.isValidValue(standardValue) then this.setStandardValue(0)
	 */
	@Raw
	private void setStandardValue(int standardValue){
		if (this.isValidStandardValue(standardValue)){
			this.standardValue = standardValue;
		}
		else {
			this.setStandardValue(0);
		}
	}
	
	/**
	 * Returns the standard value in ducats of the backpack.
	 * 
	 * @return The resulting number must be a valid value.
	 * 		   | this.isValidValue(result)
	 */
	@Basic
	public int getStandardValue(){
		return this.standardValue;
	}
	
	/**
	 * Checks whether or not the given standard value is a valid value.
	 * 
	 * @param  standardValue
	 * 		   The value that needs to be checked
	 * @return True if the integer is not negative and smaller than or equal to 500.
	 * 		   | result == ((standardValue >= 0)&&(standardValue <= 500))
	 */
	@Raw
	protected boolean isValidStandardValue(int standardValue){
		return ((standardValue >= 0)&&(standardValue <= 500));
	}
	
	/**
	 * Change the standard value to a given value
	 * 
	 * @param standardValue
	 * 		  The new value for the standardValue
	 * @post  if the given standard value is valid, the standardValue will be set to the given integer.
	 * 		  | if this.isValidStandardValue(standardValue)
	 * 		  | then new.getStandardValue().equals(standardValue)
	 * @post  If the given value isn't valid, nothing happens.
	 * 		  | if !(this.isValidStandardValue(standardValue)) 
	 * 		  | then new.getStandardValue().equals(this.getStandardValue())
	 */
	protected void changeStandardValue(int standardValue){
		if (this.isValidStandardValue(standardValue)){
			this.setStandardValue(standardValue);
			this.setValue(this.calculateValue());
		}
	}
	
	/**
	 * Calculates the value in ducats of the ownable object.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	@Override
	protected int calculateValue() {
		int value = this.standardValue;
		while (this.getBackpackIterator().hasMoreElements()){
			Object object = this.getBackpackIterator().nextElement();
			if (object instanceof Ducat){
				Ducat ducat = (Ducat) object;
				value = value + ducat.getValue();
			}
			else if (object instanceof Ownable){
				Ownable ownable = (Ownable) object;
				value = value + ownable.getValue();
			}
		}
		return value;
	}
	
}
