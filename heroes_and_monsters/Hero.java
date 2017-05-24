package heroes_and_monsters;

import be.kuleuven.cs.som.annotate.*;

import java.math.*;
import java.util.*;

/**
 * A class of heroes.
 * 
 * @author Linde en Lotte
 * @version 1.0
 */
public class Hero extends Creature {
	
	/******************************************
	 * Constructors
	 ******************************************

	/**
	 * Initialize this new hero with a name, a strength, a maximum hitpoints, a
	 * current hitpoints, standard anchors and anchorObjects.
	 * 
	 * @param 	name
	 * 			The name of this hero.
	 * @param	strength
	 * 			The strength of this hero.
	 * @param	maxHitpoints
	 * 			The maximum and current hitpoints of this hero.
	 * @param	anchorObjects
	 * 			The objects that must go in the anchors.
	 * @effect	This hero is initialized as a creature with the given name, strength, 
	 * 			current hitpoints, maximum hitpoints and anchorObjects.
	 * 			| super(name, strength, maxHitpoints, anchorsStandard, anchorObjects)
	 * @throws 	IllegalArgumentException
	 * 			The list anchorObjects must have 5 objects.
	 * 			| anchorObjects.size() != anchorsStandard.size()
	 * @throws	IllegalArgumentException
	 * 			The object on the fourth position must be effective and it must be
	 * 			an armor.
	 * 			| anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor)
	 */
	@Raw
	public Hero(String name, BigDecimal strength, int maxHitpoints,
			ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		super(name, strength, maxHitpoints, anchorsStandard, anchorObjects);
	}
	
	/**
	 * Initialize this new hero with a name, the default strength, a maximum hitpoints, a
	 * current hitpoints, standard anchors and no anchorObjects.
	 * 
	 * @param 	name
	 * 			The name of this hero.
	 * @param	maxHitpoints
	 * 			The maximum and current hitpoints of this hero.
	 * @effect	This hero is initialized as a hero with the given name, maximum hitpoints
	 * 			and maximum hitpoints. The strength is the default strength and no objects
	 * 			are added to the anchors.
	 * 			| this(name, getDefaultStrength(), maxHitpoints, new ArrayList<Object>())
	 */
	@Raw
	public Hero(String name, int maxHitpoints){
		this(name, getDefaultStrength(), maxHitpoints, new ArrayList<Object>());
	}
	
	/****************************************
	 * name
	 ****************************************
	
	/**
	 * Set the name of this hero to the given name.
	 * 
	 * @effect	The name of this hero is set to the given name.
	 * 			| new.getName() == name
	 */
	@Raw @Override
	protected void setName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name)){
			throw new IllegalArgumentException("This is not a valid name for a hero");
		}
		this.name = name;
	}
	
	/**
	 * Checks whether this hero can have the given name as name.
	 * 
	 * @param	name
	 * 			The name to check
	 * @return	False if the given name can't be a name for any creature or if it has
	 * 			a character that is not valid for a name or if it has more than two
	 * 			apostrophes or if a colon is not followed by a space.
	 * 			True otherwise.
	 * 			| result == ((super.canHaveAsName(name)) &&
	 * 			|				( for all char in name:
	 * 			|						char in "a-zA-Z': " ) &&
	 * 			|				( name.length() - name.replaceAll("'", "").length() <= 2 ) &&
	 * 			|				( for char in name:
	 * 			|						if (char == ':'){
	 * 			|							next char == ' ' }) )
	 */
	@Override @Raw
	public boolean canHaveAsName(String name){
		if (!super.canHaveAsName(name)){
			return false;
		}
		else if (!name.matches("[a-zA-Z': ]+")){
			return false;
		}
		else if (name.length() - name.replaceAll("'", "").length() > 2){
			return false;
		}
		else {
			for (int i = 0; i < name.length(); i++){
				if (name.charAt(i) == ':'){
					if (i == name.length() - 1){
						return false;
					}
					else if (name.charAt(i+1) != ' '){
						return false;
					}
				}
			}
			return true;
		}
	}
	
	/****************************************
	 * anchors
	 ****************************************
	
	/**
	 * The anchors of a hero: "Left hand", "Right hand", "Back", "Body", "Belt".
	 */
	private static ArrayList<String> anchorsStandard = new ArrayList<String>(Arrays.asList("Left hand", "Right hand", "Back", "Body", "Belt")) ;
	
	/**
	 * The anchors of this hero are set to the objects in the given list anchorObjects.
	 * 
	 * @param 	anchorObjects
	 * 			The list with all the objects that must go in an anchor.
	 * @effect	The positions of the anchor are filled with the given objects in the list.
	 * 			| new.getAnchors() = super.setAnchorObjects(anchorObjects)
	 * @effect	The objects are added to their associated anchors.
	 * 			| for all objects in anchorObjects {
	 * 			|		addToAnchor(object, associated anchor)
	 * @throws 	IllegalArgumentException
	 * 			The list anchorObjects must have an equal amount of objects as anchors.
	 * 			| anchorObjects.size() != anchorsStandard.size()
	 * @throws	IllegalArgumentException
	 * 			The object on the fourth position must be effective and it must be
	 * 			an armor.
	 * 			| anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor
	 * @throws	IllegalArgumentException
	 * 			One of the objects can't be added to its associated anchor.
	 * 			| !canAddToAnchor(object, anchor)
	 */
	@Raw @Override
	protected void setAnchorObjects(ArrayList<Object> anchorObjects)
			throws IllegalArgumentException {
		if (anchorObjects.size() != anchorsStandard.size()){
			throw new IllegalArgumentException("Not the right amount of items");
		}
		if (anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor)){
			throw new IllegalArgumentException("At birth, the object on the fourth position "
					+ "must be effective and its class must be Armor.");
		}
		for (int i = 0; i < anchorObjects.size(); i++){
			if (anchorObjects.get(i) != null){
				this.addToAnchor(anchorObjects.get(i), anchorsStandard.get(i));
			}
		}
	}
	
	/**
	 * Check whether the given object can be added to the given anchor.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @param 	anchor
	 * 			The anchor to check.
	 * @return	True if and only if the given object can be added to the given anchor
	 * 			for any creature and if when the anchor is a belt, the object is a purse
	 * 			and if when the anchor is a body, the object is an armor and if the hero
	 * 			won't carry too many armors.
	 * 			| result == super.canAddToAnchor(object, anchor) &&
	 *			|				(anchor != "Belt" || (object instanceof Purse)) &&
	 *			|				(anchor != "Body" || (object instanceof Armor)) &&
	 *			|				canAddArmor(object)
	 */
	@Raw @Override
	public boolean canAddToAnchor(Object object, String anchor){
		return (super.canAddToAnchor(object, anchor) &&
				(anchor != "Belt" || (object instanceof Purse) || (object == null)) &&
				(anchor != "Body" || (object instanceof Armor) || (object == null)) &&
				canAddArmor(object));
	}
	
	/**
	 * Check whether a hero won't have too many armors if the given object is added.
	 * 
	 * @param 	object
	 * 			The object to check.
	 * @return	True if and only if the object is not an armor or the hero won't carry more
	 * 			than 2 armors when the object is added.
	 * 			| result == armors.size() < 2
	 */
	@Raw
	protected boolean canAddArmor(Object object){
		ArrayList<Object> armors = new ArrayList<Object>();
		if (object instanceof Armor){
			for (Object obj: getAnchors().values()){			
				if (obj instanceof Armor){
					armors.add(obj);
				}
				if (obj instanceof Backpack){
					Backpack backpack =  (Backpack) obj;
					while (backpack.getBackpackIterator().hasMoreElements()){
							Object next = backpack.getBackpackIterator().nextElement();
							if (next instanceof Armor){
								armors.add(next);
							}
						
					}
				}
			}
		}
		return (armors.size() < 2);
	}
	
	
	/**********************************
	 * Heal
	 **********************************/
	
	/**
	 * A hero can heal and get more hitpoints.
	 * 
	 * @post	The hitpoints are increased with a random percentage of the difference
	 * 			between the maximum hitpoints and the current hitpoints.
	 * 			| new.getHitpoints() == this.getHitpoints + 
	 * 			|						Math.round(randomNumber() * 0.01 *
	 * 			|						(this.getMaxHitpoints() - this.getHitpoints()))
	 */
	private void heal(){
		int difference = this.getMaxHitpoints() - this.getHitpoints();
		int extra = Math.round(randomNumber() * (float)0.01 * difference);
		if (this.getHitpoints() + extra > this.getMaxHitpoints()){
			this.setHitpoints(this.getMaxHitpoints());
		}
		else {
			this.setHitpoints(this.getHitpoints() + extra);
		}
		
	}
	
	/**********************************
	 * Protection 
	 **********************************/
	
	/**
	 * Variable registering the value of the standard protection of a hero.
	 */
	public final int standardProtection = 10;
	
	/**
	 * Returns the current value for the protection of a hero.
	 * 
	 * @return The resulting number must be bigger than or equal to 1 and smaller than the maximum protection.
	 * 		   | (result >= 1) && (result <= maxProtection)
	 * @return The resulting number must be the sum of the standardProtection and the currentProtection of the armor.
	 * 		   | result == (armor.getCurrentProtection() + this.standardProtection))
	 */
	public int getCurrentProtection() {
		Object object = getAnchors().get("Body");
		if ((object != null)&&(object instanceof Armor)){
			Armor armor = (Armor)object;
			return (armor.getCurrentProtection() + this.standardProtection);
		}
		else {
			return this.standardProtection;
		}
	}


	/**********************************
	 * Capacity -  totaal
	 **********************************/
	
	/**
	 * The list of capacities between 10 and 20 of a hero.
	 */
	private static ArrayList<Integer> capacities = new ArrayList<Integer>(Arrays.asList(115, 130, 150, 175, 200, 230, 260, 300, 350, 400));
	
	/**
	 * Return the maximum capacity of the hero.
	 * 
	 * @return the resulting number cannot be negative
	 * 		   | result >= 0
	 */
	@Override
	public double getMaximumCapacity(Unit unit) {
		float strength = this.getStrength().floatValue();
		int constant = 1;
		float capacity = 0;
		while (strength > 20){
			constant = constant * 4;
			strength = strength - 10;
		}
		if (strength < 1.00){
			capacity =  0;
		}
		else if (strength <= 10){
			capacity = (strength * 10);
		}
		else if (strength <= 20){
			int i = (int)Math.floor(strength);
			capacity =  capacities.get(i);			
		}
		return (unit.convertFromKilogram(constant*capacity));
	}

	/**********************************
	 * Hit
	 **********************************/
	
	/**
	 * Return the resulting damage of a certain hit of this creature
	 * 
	 * @return The resulting number cannot be negative.
	 * 		   | result >= 0
	 */
	@Override
	protected int getResultingDamage() {
		double attackForce = this.getStrength().floatValue();
		for (int i = 0; i < anchorsStandard.size(); i++){
			Object object = this.getAnchors().get(i);
			if ((object instanceof Weapon) && (object != null)){
				Weapon weapon = (Weapon)object;
				double damageWeapon = weapon.getCurrentDamage();
				attackForce = attackForce + damageWeapon;
			}
		}
		int attack = (int)Math.floor((attackForce -10)/2);
		if (attack < 0){
			return 0;
		}
		else{
			return attack;
		}
	}

	@Override
	protected int deathblow() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**********************************
	 * collect Treasures
	 **********************************/
	
	/**
	 * Return a hashmap with all the possessions of this hero and the monster that was its opponent, with the classname as keys
	 * and an arraylist of all the possessions of this class as a value.
	 * 
	 * @param  	opponent
	 * 			The monster that was beaten.
	 * @post   	If there was a mapping between a key and an arraylist in the hashmap with all the possessions of the monster, 
	 * 			this key will be a key in the new hashmap, and the associated new arraylist will have the associated old arraylist
	 * 			as subarraylist.
	 * 			| if this.getOpponentsPossessions(opponent).containsKey(key){
	 * 			|		this.getAllPossessions(opponent).containsKey(key)
	 * 			|		&& this.getAllPossessions(opponent).get(key).containsAll(this.getOpponentsPossessions(opponent).get(key))
	 * @post   	If there was a mapping between a key and an arraylist in the hashmap with all the possessions of this hero, 
	 * 			this key will be a key in the new hashmap, and the associated new arraylist will have the associated old arraylist
	 * 			as subarraylist.
	 * 			| if this.getOpponentsPossessions(this).containsKey(key){
	 * 			|		this.getAllPossessions(opponent).containsKey(key)
	 * 			|		&& this.getAllPossessions(opponent).get(key).containsAll(this.getOpponentsPossessions(this).get(key))
	 */
	private HashMap<String, ArrayList<Object>> getAllPossessions(Creature opponent){
		HashMap<String, ArrayList<Object>> allPossessions = this.getOpponentsPossessions(opponent);
		while (this.getOpponentsPossessions(this).keySet().iterator().hasNext()){
			String key = (this.getOpponentsPossessions(this)).keySet().iterator().next();
			if (allPossessions.containsKey(key)){
				allPossessions.get(key).addAll(this.getOpponentsPossessions(this).get(key));
			}
			else {
				allPossessions.put(key, this.getOpponentsPossessions(this).get(key));
			}
		}
		return allPossessions;
	}
	
	@Override
	protected void addTreasure(Object object, Creature opponent){
		HashMap<String, ArrayList<Object>> allPossessions = this.getAllPossessions(opponent);
		this.emptyAllAnchors();
		opponent.emptyAllAnchors();
		this.addToAnchor(this.chooseArmor(allPossessions), "Body");
	}
	
	/**
	 * Chooses the armor of all the armors between all the possessions of the monster and the hero that has the highest
	 * value for protection from all the armors that the hero has the capacity to wear.
	 * 
	 * @param  allPossessions
	 * 		   The hashmap that contains all the possessions of the hero and the monster
	 * @return The armor with the highest protection that the hero is capable of wearing. If there is no armor that meets
	 * 		   this requirements, null is returned.
	 * @
	 */
	protected Object chooseArmor(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Armor")){
				ArrayList<Object> armorlist = allPossessions.get("Armor");
				Collections.sort(armorlist, new Comparator<Object>() {
				    @Override
				    public int compare(Object o1, Object o2) {
				    	Armor a1 = (Armor) o1;
				    	Armor a2 = (Armor) o2;
				        return compare(a1.getCurrentProtection(), a2.getCurrentProtection());
				    }
				});
				Armor armor = (Armor) armorlist.get(0);
				while (!(this.getUsedCapacity(Unit.KG) + armor.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (armorlist.size()>= 2)){
					allPossessions.get("Armor").get(0).terminate();
					armor = (Armor) armorlist.get(0);					
				}
				if (this.getUsedCapacity(Unit.KG) + armor.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
					return armor;
				}
			}
		}
		return null;
	}
	
	/**
	 * Chooses the weapon of all the weapons between all the possessions of the monster and the hero that has the highest
	 * value for damage from all the weapons that the hero has the capacity to wear.
	 * 
	 * @param  allPossessions
	 * 		   The hashmap that contains all the possessions of the hero and the monster
	 * @return The weapon with the highest protection that the hero is capable of wearing. If there is no armor that meets
	 * 		   this requirements, null is returned.
	 */
	protected Object choosewEAPON(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Weapon")){
				ArrayList<Object> weaponlist = allPossessions.get("Weapon");
				Collections.sort(weaponlist, new Comparator<Object>() {
				    @Override
				    public int compare(Object o1, Object o2) {
				    	Weapon w1 = (Weapon) o1;
				    	Weapon w2 = (Weapon) o2;
				        return compare(w1.getCurrentDamage(), w2.getCurrentDamage());
				    }
				});
				Weapon weapon = (Weapon) weaponlist.get(0);
				while (!(this.getUsedCapacity(Unit.KG) + weapon.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (weaponlist.size()>= 2)){
					allPossessions.get("Weapon").remove(0);
					weapon = (Weapon) weaponlist.get(0);					
				}
				if (this.getUsedCapacity(Unit.KG) + weapon.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
					return weapon;
				}
			}
		}
		return null;
	}
		
}
