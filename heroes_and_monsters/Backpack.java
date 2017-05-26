package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;

import Exceptions.OwnableIsTerminatedException;

/**
 * A class of backpacks.
 * 
 * @invar	Every backpack must have the next correct identification number.
 * 			| getIdentification == calculateValidIdentification()
 * @invar	Every backpack must have a valid standard value.
 * 			| isValidStandardValue(getStandardValue())
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Backpack extends Storage{

	/******************************************
	 * Constructors
	 ******************************************
	
	/**
	 * Initialize this new backpack with an identification, own weight and unit.
	 * 
	 * @param	standardValue
	 * 			The standard value of this backpack.
	 * @param	maxCapacity
	 * 			The maximum capacity of this backpack
	 * @param	ownWeight
	 * 			The weight of this backpack.
	 * @param	unit
	 * 			The unit in which the weight is set.
	 * @effect	This backpack is initialized as a storage with a calculated identification
	 * 			and the given ownWeight and unit.
	 * 			| super(calculateValidIdentification(), ownWeight, unit)
	 * @post  	If the given standard value is valid, the standardValue of this backpack
	 * 			will be set to the given standardValue.
	 * 			| if this.isValidStandardValue(standardValue)
	 * 		  	| then new.getStandardValue().equals(standardValue)
	 * @post  	If the given value isn't valid, nothing happens.
	 * 		  	| if !(this.isValidStandardValue(standardValue)) 
	 * 		  	| then new.getStandardValue().equals(this.getStandardValue())
	 * @post	The maximum capacity of this backpack is set to maximumCapacity.
	 * 			| new.getMaximumCapacity(unit) == maximumCapacity
	 */
	@Raw
	public Backpack(int standardValue, double maxCapacity, double ownWeight, Unit unit){
		super(calculateValidIdentification(), ownWeight, unit);
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
	 * @effect	The identification is added to the list of identifications. The size of
	 * 			this list is increased by 1.
	 * 			| super.setIdentification(identification)
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
	 * @param 	maximumCapacity
	 * 		  	The number to which the maximum capacity should be set.
	 * @param 	unit
	 * 		  	The weight unit in which the capacity is set.
	 * @post	The maximum capacity of this backpack is set to maximumCapacity.
	 * 			| new.getMaximumCapacity(unit) == maximumCapacity
	 */
	private void setMaxCapacity(double maximumCapacity, Unit unit){
		this.maximumCapacity = unit.convertToKilogram(maximumCapacity);
	}
	
	/**
	 * Return the maximum capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public double getMaximumCapacity(Unit unit) throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		return unit.convertFromKilogram(maximumCapacity);
	}

	/**
	 * Return the used part of the total capacity of the object.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result > 0
	 * @return the resulting number cannot be larger than the maximum capacity of the object.
	 * 		   | result <= this.getMaximumCapacity()
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	@Override
	public double getUsedCapacity(Unit unit) throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
		double weight = this.getOwnWeight(unit);
		Enumeration<Object> iterator = this.getBackpackIterator();
		while (iterator.hasMoreElements()){
			Object object = iterator.nextElement();
			if (object instanceof Ownable){
				if (!(((Ownable) object).getTerminated())){
					if (object instanceof Storage){
						Storage storage = (Storage) object;
						weight = weight + storage.getOwnWeight(unit);
					}
					else {
						Ownable ownable = (Ownable) object;
						weight = weight + ownable.getOwnWeight(unit);
					}
				}
			}
			else if (object instanceof Ducat){
				Ducat ducat = (Ducat) object;
				weight = weight + ducat.getWeight(unit);
			}
		}
		return weight;
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
	 * 
	 * @throws	OwnableIsTerminatedException
	 * 			This ownable is terminated.
	 * 			| getTerminated()
	 */
	public ArrayList<Object> getContent() throws OwnableIsTerminatedException {
		if (getTerminated()){
			throw new OwnableIsTerminatedException(this);
		}
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
	 * 			|				this.getContent().size() == new.getContent().size() } }
	 * 			| else {
	 * 			| 		this.getContent().size() + 1 == new.getContent().size() }
	 * @effect	If the given object is an ownable, its holder is set to this.
	 * 			| if (object instanceof Ownable){
	 * 			| 		object.setHolder(this) }
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be added to this backpack.
	 * 			| !canAddToStorage(object)
	 */
	@Override
	public void addToStorage(Object object) throws IllegalArgumentException {
		if (!canAddToStorage(object)){
			throw new IllegalArgumentException("The given object can't be added to this backpack.");
		}
		else if (object instanceof Ducat){
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
				this.content.add(ducat);
			}
			ducat.subtract(ducat);
		}
		else if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			ownable.setHolder(this);
			ownable.addAllContainersToContainersSet(this);
			this.addToIdentificationNumbers(ownable);
			this.content.add(ownable);
		}
	}
	
	/**
	 * Check whether the given object can be added to this backpack.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @return	False if this object can't be added to any storage. Also false if the
	 * 			object is not an ownable or a ducat. Also false if when the object is an
	 * 			ownable, it already has a holder. Also false if with this object the
	 * 			maximum capacity of this backpack would be exceeded. Also false if by
	 * 			adding an armor to a backpack, a hero would carry too many armors. Also
	 * 			false if this backpack is in 1 or more other backpacks and one of their
	 * 			capacities could be exceeded.
	 * 			| result == ( super.canAddToStorage(object) &&
	 * 			|				(object instanceof Ownable || object instanceof Ducat) &&
	 * 			|				ownable.getHolder() == null &&
	 * 			|				(this.getUsedCapacity(Unit.KG) + weight <=
	 * 			|				this.getMaximumCapacity(Unit.KG)) &&
	 * 			|				hero.canAddArmor(object) &&
	 * 			|				(backpack.getUsedCapacity(Unit.KG) + weight <=
	 * 			|				backpack.getMaximumCapacity(Unit.KG) )
	 */
	@Override
	public boolean canAddToStorage(Object object){
		if (!super.canAddToStorage(object)){
			return false;
		}
		double weight = 0;
		if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			if (ownable.getHolder() != null){
				return false;
			}
			weight = ownable.getOwnWeight(Unit.KG);
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
		Iterator<Backpack> iterator = this.getContainersSet().iterator();
		while (iterator.hasNext()){
			Backpack backpack = iterator.next();
			if ((backpack.getUsedCapacity(Unit.KG) + weight) > backpack.getMaximumCapacity(Unit.KG)){
				return false;
			}
		}
		if (this.getUltimateHolder() instanceof Creature){
			Creature creature = (Creature) this.getUltimateHolder();
			return ((creature.getUsedCapacity(Unit.KG) + weight) <= creature.getMaximumCapacity(Unit.KG));
		}
		return true;
	}
	
	/**
	 * Check whether the given object can be taken out of this backpack.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @return	False if the given object can't be taken out of any storage. True if the
	 * 			given object ownable and it is direct or indirect content of this backpack.
	 * 			Also true if the given object is a ducat and the value of all the ducats
	 * 			in this backpack is greater than or equal to the given ducat. False
	 * 			otherwise.
	 * 			| if (!super.canTakeOutOfStorage(object)){
	 *			|		result == false
	 *			| }
	 * 			| if (object instanceof Ownable){
	 * 			|		result == this.OwnableInBackpack(object)
	 * 			| }
	 * 			| else if (object instanceof Ducat) {
	 * 			|		ducat = total value of ducats in this backpack
	 * 			|		result == ducat.getValue() >= object.getValue() )
	 * 			| }
	 * 			| else {
	 * 			| 		result == false
	 * 			| }
	 */
	@Override
	public boolean canTakeOutOfStorage(Object object){
		if (!super.canTakeOutOfStorage(object)){
			return false;
		}
		if (object instanceof Ownable){
			Ownable ownable = (Ownable) object;
			return this.OwnableInBackpack(ownable);
		}
		else if (object instanceof Ducat){
			Ducat ducat = (Ducat) object;
			Ducat newDucat = new Ducat(0);
			Enumeration<Object> iterator = this.getBackpackIterator();
			while (iterator.hasMoreElements()){
				Object objectInBackpack = iterator.nextElement();
				if (objectInBackpack instanceof Ducat){
					Ducat other = (Ducat) objectInBackpack;
					newDucat = newDucat.add(other);
				}
				if (objectInBackpack instanceof Purse){
					Ducat other = new Ducat(((Purse)objectInBackpack).getValue());
					newDucat = newDucat.add(other);
				}
			}
			return (ducat.getValue() <= newDucat.getValue());
		}
		return false;
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
	 * @post	If the given object is an ownable, the size of the content of its holder
	 * 			is decreased by 1.
	 * 			| if (object instanceof Ownable){
	 * 			|			(new object.getHolder()).getContent().size() == object.getHolder().getContent().size() - 1 }
	 * @effect	If the given object is an ownable, its holder is set to null.
	 * 			| if (object instanceof Ownable){
	 * 			| 			object.setHolder() }
	 * @throws 	IllegalArgumentException
	 * 			The given object can't be taken out of this backpack.
	 * 			| !canTakeOutOfStorage(object)
	 */
	@Override
	protected void takeOutOfStorage(Object object) throws IllegalArgumentException {
		if (!canTakeOutOfStorage(object)){
			throw new IllegalArgumentException("The given object can't be taken out of this backpack.");
		}
		else {
			if (object instanceof Ownable){
				((Backpack)(((Ownable) object).getHolder())).content.remove(object);
				((Ownable) object).removeAllContainers();
				this.removeFromIdentificationNumbers((Ownable) object);
				((Ownable) object).setHolder();
			}
			else if (object instanceof Ducat){
				Ducat ducat = (Ducat) object;
				Ducat newDucat = new Ducat(0);
				while (ducat.getValue() != newDucat.getValue()){
					Enumeration<Object> iterator = this.getBackpackIterator();
					while (iterator.hasMoreElements()){
						Object objectInBackpack = iterator.nextElement();
						if (objectInBackpack instanceof Purse){
							if ( ((Purse) objectInBackpack).getContent().getValue() >= (ducat.getValue()-newDucat.getValue())){
								 ((Purse) objectInBackpack).getContent().subtract(ducat).add(newDucat);
								 newDucat = newDucat.subtract(newDucat).add(ducat);
							}
							else {
								newDucat = newDucat.add( ((Purse) objectInBackpack).getContent());
								((Purse) objectInBackpack).getContent().subtract( ((Purse) objectInBackpack).getContent());
							}
						}
						else if (objectInBackpack instanceof Ducat){
							if (((Ducat) objectInBackpack).getValue() >= (ducat.getValue()-newDucat.getValue())){
								((Ducat) objectInBackpack).subtract(ducat).add(newDucat);
								newDucat = newDucat.subtract(newDucat).add(ducat);
							}
							else {
								newDucat = newDucat.add(((Ducat) objectInBackpack));
								((Ducat) objectInBackpack).subtract(((Ducat) objectInBackpack));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * The content of this backpack is emptied.
	 * 
	 * @effect	All objects in this backpack are taken out of this storage.
	 * 			| for all objects in getContent() {
	 * 			|		takeOutOfStorage(object) }
	 * @post	The size of content is 0.
	 * 			| new.getContent().getSize() == 0
	 */
	@Override
	protected void emptyStorage()throws IllegalArgumentException {
		Iterator<Object> iterator = getContent().iterator();
		while (iterator.hasNext()){
			this.takeOutOfStorage(iterator.next());
		}
	}
	
	/**
	 * A hashmap that contains all the different identificationnumbers in this backpack as keys
	 * and for every identificationnumber an arraylist of all the objects in this backpack with this identificationnumber.
	 */
	private HashMap<Long, ArrayList<Ownable>> identificationNumbers = new HashMap<Long, ArrayList<Ownable>>();
	
	/**
	 * Return a hashmap with all the different identificationnumbers in this backpack as keys and
	 * the associated objects in an arraylist as values.
	 */
	private HashMap<Long, ArrayList<Ownable>> getIdNumber(){
		return this.identificationNumbers;
	}
	
	/**
	 * Add an ownable and its content to the arraylists in identificationNumbers with their identificationnumbers as keys.
	 * 
	 * @param 	ownable
	 * 		  	the ownable object that needs to be added to the hashmap.
	 * @post 	the hashmap identificationNumbers of this backpack will contain the identificationnumber of the ownable as one of the keys.
	 * 		  	| this.identificationNumbers.containsKey(ownable.getIdentification())
	 * @post  	the hashmap identificationNumbers of this backpack will map the identificationnumber of the ownable to an arraylist that contains the ownabale.
	 * 		  	| this.getIdNumber().get(ownable.getIdentification()).contains(ownable)
	 * @post  	if the ownable object is a backpack, all the ownable objects in this backpack are also added to the hashmap of this backpack in the same way.
	 *        	| if (ownable instanceof Backpack){
	 *        	| 	for all object in ownable{
	 *        	|			if (object instanceof Ownable){
	 *        	|				this.getIdNumber().containsKey(object.getIdentification())
	 *        	|				this.getIdNumber().get(object.getIdentification()).contains(object)
	 *        	|			}
	 *        	|		}
	 *        	| }
	 * @post 	the hashmaps identificationNumbers of all the containers of this backpack will contain the identificationnumber of the ownable as one of the keys.
	 * 		  	| for all container in this.getContainersSet(){
	 * 			|	container.getIdNumber().containsKey(ownable.getIdentification())
	 * 			| }
	 * @post  	the hashmaps identificationNumbers of all the containers of this backpack will map the identificationnumber of the ownable to an arraylist that contains the ownabale.
	 * 		  	| for all container in this.getContainersSet(){
	 * 			| 	container.getIdNumber().get(ownable.getIdentification()).contains(ownable)
	 * 			| }
	 * @post  	if the ownable object is a backpack, all the ownable objects in this backpack are also added to the hashmaps of all the containers of this backpack in the same way.
	 *        	| for all container in this.getContainersSet(){
	 *        	| 	if (ownable instanceof Backpack){
	 *        	| 		for all object in ownable{
	 *        	|				if (object instanceof Ownable){
	 *        	|					container.getIdNumber().containsKey(object.getIdentification())
	 *        	|					container.getIdNumber().get(object.getIdentification()).contains(object)
	 *        	|				}
	 *        	|		}
	 *        	| 	}
	 *        	| }
	 */
	private void addToIdentificationNumbers(Ownable ownable){
		if (this.getIdNumber().containsKey(ownable.getIdentification())){
			this.getIdNumber().get(ownable.getIdentification()).add(ownable);
		}
		else {
			ArrayList<Ownable> arraylist = new ArrayList<Ownable>();
			arraylist.add(ownable);
			this.getIdNumber().put(ownable.getIdentification(), arraylist);
		}
		if (ownable instanceof Backpack){
			Backpack backpack = (Backpack) ownable;
			Enumeration<Object> enumeration = backpack.getBackpackIterator();
			while (enumeration.hasMoreElements()){
				Object object = enumeration.nextElement();
				if (object instanceof Ownable){
					ownable = (Ownable) object;
					this.addToIdentificationNumbers(ownable);
				}
			}
		}
		Iterator<Backpack> iterator = this.getContainersSet().iterator();
		while (iterator.hasNext()){
			Backpack container = iterator.next();
			container.addToIdentificationNumbers(ownable);
		}
	}
	
	/**
	 * Remove an ownable and its content from the arraylists in identificationNumbers with their identificationnumbers as keys.
	 * 
	 * @param ownable
	 * 		  the ownable object that needs to be removed from the hashmap.
	 * @post  the hashmap identificationNumbers of this backpack no longer map the identificationnumber of the ownable to an arraylist that contains the ownable.
	 * 		  If the identification number of the ownable is still used as key, the arraylist to which this key is mapped will not contain the ownable.
	 * 		  | if (this.getIdNumber().containsKey(ownable.getIdentification())){
	 * 		  |		!(this.getIdNumber().get(ownable.getIdentification()).contains(ownable))
	 * 		  | }
	 * @post  if the ownable object is a backpack, all the ownable objects in that backpack are also removed from the hashmap of this backpack in the same way.
	 *        | if (ownable instanceof Backpack){
	 *        | 	for all object in ownable{
	 *        |			if (object instanceof Ownable){
	 *        |				if (this.getIdNumber().containsKey(object.getIdentification())){
	 *        |					!(this.getIdNumber().get(object.getIdentification()).contains(object))
	 *        |				}
	 *        |			}
	 *        |		}
	 *        | }
	 * @post  the hashmaps identificationNumbers of all the containers of this backpack no longer map the identificationnumber of the ownable to an arraylist that contains the ownable.
	 * 		  If the identification number of the ownable is still used as key, the arraylist to which this key is mapped will not contain the ownable.
	 * 		  | for all container in this.getContainersSet(){
	 * 		  |		if (container.getIdNumber().containsKey(ownable.getIdentification())){
	 * 		  |			!(container.getIdNumber().get(ownable.getIdentification()).contains(ownable))
	 * 		  |		}
	 * 		  | }
	 * @post  if the ownable object is a backpack, all the ownable objects in that backpack are also removed from the hashmaps of all the containers of this backpack in the same way.
	 *  	  | for all container in this.getContainersSet(){
	 *        | 	if (ownable instanceof Backpack){
	 *        | 		for all object in ownable{
	 *        |				if (object instanceof Ownable){
	 *        |					if (container.getIdNumber().containsKey(object.getIdentification())){
	 *        |						!(container.getIdNumber().get(object.getIdentification()).contains(object))
	 *        |					}
	 *        |				}
	 *        |			}
	 *        |		}
	 *        | }
	 */
	private void removeFromIdentificationNumbers(Ownable ownable){
		(this.getIdNumber().get(ownable.getIdentification())).remove(ownable);
		ArrayList<Ownable> arraylist = this.getIdNumber().get(ownable.getIdentification());
		if (arraylist.isEmpty()){
			this.getIdNumber().remove(ownable.getIdentification());
		}
		if (ownable instanceof Backpack){
			Backpack backpack = (Backpack) ownable;
			Enumeration<Object> enumeration = backpack.getBackpackIterator();
			while (enumeration.hasMoreElements()){
				Object object = enumeration.nextElement();
				if (object instanceof Ownable){
					ownable = (Ownable) object;
					this.removeFromIdentificationNumbers(ownable);
				}
			}
		}
		Iterator<Backpack> iterator = this.getContainersSet().iterator();
		while (iterator.hasNext()){
			Backpack container = iterator.next();
			container.removeFromIdentificationNumbers(ownable);
		}
	}
	
	/**
	 * Checks whether or not the backpack contains a certain ownable object.
	 * 
	 * @param  ownable
	 * 		   The ownable from which it is tested if the backpack contains it.
	 * @return True if the backpack contains it, false otherwise.
	 * 		   | result == this.getContent().contains(ownable)
	 * @throws	OwnableIsTerminatedException
	 * 			The given ownable is terminated.
	 * 			| ownable.getTerminated()
	 */
	public boolean OwnableInBackpack(Ownable ownable) throws OwnableIsTerminatedException {
		if (ownable.getTerminated()){
			throw new OwnableIsTerminatedException(ownable);
		}
		long idNumber = ownable.getIdentification();
		if (this.getIdNumber().get(idNumber) != null){
			ArrayList<Ownable> arraylist = this.getIdNumber().get(idNumber);
			Iterator<Ownable> iterator = arraylist.iterator();
			while (iterator.hasNext()){
				Ownable ownableInList = iterator.next();
				if (ownable == ownableInList){
					return true;
				}
			}
		}
		return false;
	}
	
	/***************************
	 * iterator
	 ***************************/
	
	/**
	 * Return an iterator that iterates over the content of this backpack.
	 */
	public Enumeration<Object> getBackpackIterator(){
		return new Enumeration<Object>(){

			/**
			 * Variable indexing the current element of this iterator.
			 */
			private int indexCurrent = 0;
			
			/**
			 * Check whether the iterator has more elements.
			 * 
			 * @return	True if and only if the size of the content of this backpack
			 * 			minus the current index is greater than zero.
			 * 			| result == getContent().size() - indexCurrent > 0
			 */
			@Override
			public boolean hasMoreElements() {
				return getContent().size() - indexCurrent > 0;
			}

			/**
			 * Variable referencing an iterator to use for a backpack in a backpack.
			 */
			private Enumeration<Object> iterator = getBackpackIterator();
			
			/**
			 * Return the next element of the content of this backpack.
			 */
			@Override
			public Object nextElement() {
				Object object = getContent().get(indexCurrent);
				if (object instanceof Backpack){
					Backpack backpack = (Backpack) object;
					Enumeration<Object> iterator = backpack.getBackpackIterator();
					while (iterator.hasMoreElements()){
						return iterator.nextElement();
					}
				}
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
	 * @param 	standardValue
	 * 		  	The integer to which the standardValue needs to be set.
	 * @post  	If the given standard value is valid, the standardValue will be set to the
	 * 			given integer.
	 * 		  	| if this.isValidStandardValue(standardValue)
	 * 		  	| then new.getStandardValue().equals(standardValue)
	 * @post  	If the given value isn't valid, standardValue is set to the default.
	 * 		  	| if !this.isValidValue(standardValue) then this.setStandardValue(0)
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
		}
	}
	
	/**
	 * Calculates the value in ducats of the backpack.
	 * 
	 * @return The resulting number must be a valid value
	 * 		   | isValidValue(result)
	 */
	@Override
	public int getValue() {
		int value = this.standardValue;
		Enumeration<Object> iterator = this.getBackpackIterator();
		while (iterator.hasMoreElements()){
			Object object = iterator.nextElement();
			if (object instanceof Ducat){
				Ducat ducat = (Ducat) object;
				value = value + ducat.getValue();
			}
			else if (object instanceof Backpack){
				Backpack backpack = (Backpack) object;
				value = value + backpack.getStandardValue();
			}
			else if (object instanceof Ownable){
				Ownable ownable = (Ownable) object;
				value = value + ownable.getValue();
			}
		}
		return value;
	}

	/***************************
	 * weight - totaal
	 ***************************/
	
	/**
	 * Calculates the weight in the given weight unit of the backpack.
	 * 
	 * @param  unit
	 * 		   The weight unit in which the total weight should be returned.
	 * @return The resulting number must be a valid weight
	 * 		   | canHaveAsTotalWeight(result)
	 */
	@Override
	public double getTotalWeight(Unit unit) {
		double weight = unit.convertFromKilogram(this.ownWeight);
		Enumeration<Object> iterator = this.getBackpackIterator();
		while (iterator.hasMoreElements()){
			Object object = iterator.nextElement();
			System.out.println(object.toString());
			if (object instanceof Ducat){
				Ducat ducat = (Ducat) object;
				weight = weight + ducat.getWeight(unit);
			}
			else if (object instanceof Ownable){
				Ownable ownable = (Ownable) object;
				weight = weight + ownable.getOwnWeight(unit);
			}
		}
		System.out.println("total");
		return weight;
	}
	
	/********************************
	 * Containers
	 ********************************/
	
	/**
	 * Add all the backpacks that contain this new container and this new container to the set of containers of this ownable.
	 * (Used when this ownable is added to a backpack)
	 * 
	 * @param container
	 * 		  The backpack that contains the ownable.
	 * @post  The new getContainersSet() will contain the new container.
	 * 		  | new.getContainersSet().contains(container)
	 * @post  The new ContainersSet will contain all the containers of the container.
	 * 		  | new.getContainersSet().containsAll(container.getContainersSet())
	 * @post  The updated containersSet of all the ownable objects in this backpack will contain the container.
	 * 		  | For all ownable in this backpack{
	 * 		  | 	ownable.getContainersSet().contains(container)
	 * 		  |		}
	 * @post  The updated containersSet of all the ownable objects in this backpack will contain all the containers of the container.
	 * 		  | For all ownable in this backpack{
	 * 		  | 	ownable.getContainersSet().containsAll(container.getContainersSet())
	 * 		  |		}
	 */
	@Override
	protected void addAllContainersToContainersSet(Backpack container){
		super.addAllContainersToContainersSet(container);
		Enumeration<Object> iterator = this.getBackpackIterator();
		while (iterator.hasMoreElements()){
			Object object = iterator.nextElement();
			if (object instanceof Ownable){
				Ownable ownable = (Ownable) object;
				ownable.addAllContainersToContainersSet(container);
			}
		}
	}
	
	/**
	 * Removes all the containers out of the containersSet of this ownable.
	 * (Used when this ownable is removed from a backpack)
	 * 
	 * @post The new containersSet will be empty.
	 * 		 | new.getContainersSet().isEmpty()
	 * @post  The updated containersSet of all the ownable objects in this backpack won't contain all the old containers of the container any longer.
	 * 		  | For all ownable o in this backpack{
	 * 		  |		for all backpack b in this.getContainersSet(){
	 * 		  |			!(o.getContainersSet().contains(b))
	 * 		  |			}
	 * 		  |		}
	 */
	@Override
	protected void removeAllContainers(){
		Enumeration<Object> iterator = this.getBackpackIterator();
		while (iterator.hasMoreElements()){
			Object object = iterator.nextElement();
			if (object instanceof Ownable){
				Ownable ownable = (Ownable) object;
				ownable.getContainersSet().removeAll(this.getContainersSet());
			}
		}
		super.removeAllContainers();
	}
	
	/**********************************
	 * terminate
	 **********************************/
	
	/**
	 * Terminate all the items in the backpack that need to be terminated.
	 */
	protected void terminate(){
		Enumeration<Object> iterator = this.getBackpackIterator();
		while (iterator.hasMoreElements()){
			Object next = iterator.nextElement();
			if (next instanceof Weapon || next instanceof Armor){
				((Ownable) next).terminate();
			}
		}
	}
}
