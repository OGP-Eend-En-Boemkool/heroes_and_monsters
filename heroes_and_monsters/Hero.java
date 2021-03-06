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
	 * 			| if (anchorObjects.size() != anchorsStandard.size())
	 * 			|	throw new IllegalArgumentException()
	 * @throws	IllegalArgumentException
	 * 			The object on the fourth position must be effective and it must be
	 * 			an armor.
	 * 			| if (anchorObjects.get(3) == null || !(anchorObjects.get(3) instanceof Armor))
	 * 			|	throw new IllegalArgumentException()
	 * @throws	IllegalArgumentException
	 * 			The object on the fifth position must be a purse with a capacity of 100 ducats if it's effective.
	 * 			| if ((anchorObjects.get(4) != null) && ((anchorObjects.get(4)).getMaximumCapacity() != 5))
	 * 			|	throw new IllegalArgumentException()
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
	 * 			and maximum hitpoints. The strength is the default strength and only the
	 * 			given armor is added to the anchors.
	 * 			| this(name, getDefaultStrength(), maxHitpoints, new ArrayList<Object>(Arrays.asList(null, null, null, armor, null)))
	 */
	@Raw
	public Hero(String name, int maxHitpoints, Armor armor){
		this(name, new BigDecimal(2.00), maxHitpoints, new ArrayList<Object>(Arrays.asList(null, null, null, armor, null)));
	}
	
	/****************************************
	 * name
	 ****************************************
	
	/**
	 * Set the name of this hero to the given name.
	 * 
	 * @effect	The name of this hero is set to the given name.
	 * 			| super.setName(name)
	 */
	@Raw @Override
	protected void setName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name)){
			throw new IllegalArgumentException("This is not a valid name for a hero");
		}
		super.setName(name);
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
	 * 			The object on the fifth position is effective, but not a purse, or a purse that doesn't have a
	 * 			capacity of 5.
	 * 			| anchorObjects.get(4) != null && (!(anchorObjects.get(4) instanceof Purse) ||
	 * 			|	((Purse) anchorObjects.get(4)).getMaximumCapacity(Unit.KG) != 5)
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
		if (anchorObjects.get(4) != null && (!(anchorObjects.get(4) instanceof Purse) || ((Purse) anchorObjects.get(4)).getMaximumCapacity(Unit.KG) != 5)){
			throw new IllegalArgumentException("Fifth object must be a purse with capacity 5.");
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
			Iterator<Object> iterator = getAnchors().values().iterator();
			while (iterator.hasNext()){
				Object obj = iterator.next();
				if (obj instanceof Armor){
					armors.add(obj);
				}
				if (obj instanceof Backpack){
					Backpack backpack =  (Backpack) obj;
					Enumeration<Object> enumeration = backpack.getBackpackIterator();
					while (enumeration.hasMoreElements()){
						Object next = enumeration.nextElement();
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
	private final int standardProtection = 10;
	
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
		if (strength > 20){
			this.setStrength(getStrength().subtract(new BigDecimal(10)));
			double answer = 4 * getMaximumCapacity(unit);
			this.setStrength(getStrength().add(new BigDecimal(10)));
			return answer;
		}
		else if (strength < 1.00){
			capacity =  0;
		}
		else if (strength <= 10){
			capacity = (strength * 10);
		}
		else if (strength <= 20){
			int i = (int)Math.ceil(strength);
			capacity =  capacities.get(i-11);			
		}
		return ((double)Math.round(unit.convertFromKilogram(constant*capacity)* 1000d) / 1000d);
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

	/**
	 * This hero can have the advantages from killing another creature.
	 * 
	 * @param 	opponent
	 * 			The opponent that this creature has killed.
	 * @effect	The hero can heal a little and get more hitpoints.
	 * 			| heal()
	 * @effect	The hero can add some treasures from his opponents to its own possessions
	 * 			and he can also let behind the things he doesn't need anymore.
	 * 			| addTreasure(null, opponent)
	 */
	@Override
	protected void deathblow(Creature opponent) {
		heal();
		addTreasure(null, opponent);
	}
	
	/**
	 * Check whether this hero can hit the given other creature.
	 * 
	 * @param 	other
	 * 			The other creature to check.
	 * @return	True if and only if the other creature is a monster.
	 * 			| result == other instanceof Monster
	 */
	@Override
	public boolean canHitCreature(Creature other){
		return (other instanceof Monster);
	}

	
	/**********************************
	 * collect Treasures
	 **********************************/
	
	
	/**
	 * Adds a few of the possessions of the opponent to its own possessions.
	 * The hero will first of all choose the armor in all the possessions that he is capable of wearing with the highest protection and put it on his body.
	 * Secondly, the hero will choose the two weapons in all the possessions that he is capable of wearing with the highest damage and put it in his left and right hand.
	 * Furthermore, the hero will choose the backpack in all the possessions that he is capable of wearing with the highest capacity and put it on his back.
	 * Next, he will choose the purse in all the possessions that he is capable of wearing with the highest capacity and put it on his belt.
	 * Then he will add as much ducats as possible to his purse.
	 * After that he will add another armor out of all the possessions that he and his backpack are capable of wearing and has the highest protection to his backpack.
	 * Subsequently he will add as many weapons as possible out of all the possessions that he and his backpack are capable of wearing with damages that are as high as possible to his backpack.
	 * Finally he will add as many ducats as possible out of all the possessions that he and his backpack are capable of wearing with the highest value as possible to his backpack.
	 * 
	 * @param 	object
	 * 		  	This argument is not important for heroes.
	 * @param 	opponent
	 * 		  	The creature that was the opponent from which this creature steals.
	 * @post  	The opponents anchors will be emptied.
	 * 			| For all object in opponent.anchors.values():
	 * 			|	object == null
	 * @effect	addToAnchor, the given objects will be added to the given anchors.
	 * 			| this.addToAnchor(this.chooseArmor(allPossessions),"Body")
	 * 			| this.addToAnchor(this.chooseWeapon(allPossessions), "Right hand")
	 * 			| this.addToAnchor(this.chooseWeapon(allPossessions), "Left hand")
	 * 			| this.addToAnchor(this.chooseBackpack(allPossessions), "Back")
	 * 			| this.addToAnchor(this.choosePurse(allPossessions), "Belt")
	 * @effect	addToStorage, If there is a purse, as many ducats as possible will be added to the purse.
	 * 			| if purse != null
	 * 			| then	while (purse.getUsedCapacity(Unit.KG) + ducat.getWeight(Unit.KG) <= purse.getMaximumCapacity(Unit.KG)):
	 * 			|			purse.addToStorage(this.chooseDucat(allPossesions))
	 * @effect	addToStorage, If there is a backpack, a armor will be added to the backpack if the hero and the backpack have enough capacity.
	 * 			| if backpack != null
	 * 			| then	if (backpack.getUsedCapacity(Unit.KG) + armor.getOwnWeight(Unit.KG) <= backpack.getMaximumCapacity(Unit.KG))
	 * 			|		then	backpack.addToStorage(this.chooseArmor(allPossessions))
	 * @effect	addToStorage, If there is a backpack, as many weapons as possible will be added to the backpack if the hero and the backpack have enough capacity.
	 * 			| if backpack != null
	 * 			| then	while (backpack.getUsedCapacity(Unit.KG) + weapon.getOwnWeight(Unit.KG) <= backpack.getMaximumCapacity(Unit.KG)):
	 * 			|			backpack.addToStorage(this.chooseWeapon(allPossessions))
	 * @effect	addToStorage, If there is a backpack, as many ducats as possible will be added to the backpack if the hero and the backpack have enough capacity.
	 * 			| if backpack != null
	 * 			| then	while (backpack.getUsedCapacity(Unit.KG) + ducat.getWeight(Unit.KG) <= backpack.getMaximumCapacity(Unit.KG)):
	 * 			|			backpack.addToStorage(this.chooseDucat(allPossessions))
	 * @effect	terminateRemainingObjectsFromClass, The remaining armors and weapons that the hero doesn't take with him will be destroyed.
	 * 			| terminateRemainingObjectsFromClass("Armor", allPossessions);
	 * 			| terminateRemainingObjectsFromClass("Weapon", allPossessions);
	 * @post	If the hero had some items on him before he stole the treasures, he will have some items on him afterwards too.
	 * 			| if (!( for all anchor in this.getAnchors().keySet()
	 * 			| then			this.getAnchors().get(anchor) == null
	 * 			|		 )){
	 * 			| 				(!(for all anchorSub in new.getAnchors.keySet():
	 * 			|						new.getAnchors().get(anchorSub) == null
	 * 			|				   }))
	 */
	@Override
	protected void addTreasure(Object object, Creature opponent){
		HashMap<String, ArrayList<Object>> allPossessions = this.getAllPossessions(opponent);
		this.emptyAllAnchors();
		opponent.emptyAllAnchors();
		// Add the armor with the highest protection of all armors the hero is capable of wearing out of allPossessions.
		this.addToAnchor(this.chooseArmor(allPossessions), "Body");
		// Add the weapon with the highest damage of all weapons the hero is capable of wearing out of allPossessions.
		this.addToAnchor(this.chooseWeapon(allPossessions), "Right hand");
		// Add the next weapon with the highest damage of all weapons the hero is capable of wearing out of allPossessions.
		this.addToAnchor(this.chooseWeapon(allPossessions), "Left hand");
		// Add the backpack with the highest capacity of all backpacks the hero is capable of wearing out of allPossessions.
		this.addToAnchor(this.chooseBackpack(allPossessions), "Back");
		// Add the purse with the highest capacity of all purses the hero is capable of wearing out of allPossessions.
		this.addToAnchor(this.choosePurse(allPossessions), "Belt");
		// Add as many ducats (with the highest value) as possible to the purse.
		if (this.getAnchors().get("Belt") != null){
			Object objectForPurse = this.chooseDucat(allPossessions);
			Purse purse = (Purse) this.getAnchors().get("Belt");
			while (objectForPurse != null){
				Ducat ducat = (Ducat) objectForPurse;
				if (purse.getUsedCapacity(Unit.KG) + ducat.getWeight(Unit.KG) <= purse.getMaximumCapacity(Unit.KG)){
					((Purse) this.getAnchors().get("Belt")).addToStorage(ducat);
					purse = (Purse) this.getAnchors().get("Belt");
				}
				objectForPurse = this.chooseDucat(allPossessions);
			}
		}
		if (this.getAnchors().get("Back") != null){
			// Add the armor with the highest protection of all armors the hero is capable of wearing out of allPossessions.
			Backpack backpack = (Backpack) this.getAnchors().get("Back");
			Object armorForBackpack = this.chooseArmor(allPossessions);
			if (armorForBackpack != null){
				((Armor) armorForBackpack).setHolder();
				((Backpack) this.getAnchors().get("Back")).addToStorage(armorForBackpack);
				backpack = (Backpack) this.getAnchors().get("Back");
			}
			// Add as many weapons (with the highest damage) as possible to the backpack.
			Object weaponForBackpack = this.chooseWeapon(allPossessions);
			while (weaponForBackpack != null){
				((Weapon) weaponForBackpack).setHolder();
				Weapon weapon = (Weapon) weaponForBackpack;
				if (backpack.getUsedCapacity(Unit.KG) + weapon.getOwnWeight(Unit.KG) <= backpack.getMaximumCapacity(Unit.KG)){
					((Backpack) this.getAnchors().get("Back")).addToStorage(weapon);
					backpack = (Backpack) this.getAnchors().get("Back");
				}
				weaponForBackpack = this.chooseWeapon(allPossessions);
			}
			// Add as many ducats (with the highest value) as possible to the backpack.
			Object ducatForBackpack = this.chooseDucat(allPossessions);
			while (ducatForBackpack != null){
				Ducat ducat = (Ducat) ducatForBackpack;
				if (backpack.getUsedCapacity(Unit.KG) + ducat.getWeight(Unit.KG) <= backpack.getMaximumCapacity(Unit.KG)){
					((Backpack) this.getAnchors().get("Back")).addToStorage(ducat);
					backpack = (Backpack) this.getAnchors().get("Back");
				}
				ducatForBackpack = this.chooseDucat(allPossessions);
			}
		}
		terminateRemainingObjectsFromClass("Armor", allPossessions);
		terminateRemainingObjectsFromClass("Weapon", allPossessions);
	}
	
	/**
	 * Chooses the armor of all the armors between all the possessions of the monster and the hero that has the highest
	 * value for protection from all the armors that the hero has the capacity to wear.
	 * 
	 * @param  allPossessions
	 * 		   The hashmap that contains all the possessions of the hero and the monster
	 * @return The armor with the highest protection that the hero is capable of wearing. If there is no armor that meets
	 * 		   this requirements, null is returned.
	 * @post   If the hero isn't capable of wearing the armor with the highest protection, 
	 * 		   this armor is removed from the hashmap and it is terminated.
	 * 		   | if this.getUsedCapacity(unit) + armor.getOwnWeight(unit) > this.getMaximumCapacity(unit)
	 * 		   | then 	(armor.isTerminated)&& !(allPossessions.get("Armor").contains(armor))
	 * @post   The hashmap will no longer contain the armor that was returned.
	 * 		   | !(new.allPossessions.get("Armor").contains(armor))
	 */
	protected Object chooseArmor(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Armor")){
				ArrayList<Object> objectlist = (allPossessions.get("Armor"));
				ArrayList<Armor> armorlist = new ArrayList<Armor>();
				Iterator<Object> iterator = objectlist.iterator();
				while (iterator.hasNext()){
					armorlist.add((Armor) iterator.next());
				}
				while (!armorlist.isEmpty()){
					Collections.sort(armorlist);
					Armor armor = (Armor) armorlist.get(0);
					while (!(this.getUsedCapacity(Unit.KG) + armor.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (armorlist.size()>= 2)){
						((Armor)(allPossessions.get("Armor")).get(0)).terminate();
						allPossessions.get("Armor").remove(0);
						armorlist.remove(0);
						armor = (Armor) armorlist.get(0);
					}
					if (this.getUsedCapacity(Unit.KG) + armor.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
						allPossessions.get("Armor").remove(0);
						return armor;
					}
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
	 * @return The weapon with the highest damage that the hero is capable of wearing. If there is no weapon that meets
	 * 		   this requirements, null is returned.
	 * @post   If the hero isn't capable of wearing the weapon with the highest damage, 
	 * 		   this weapon is removed from the hashmap and it is terminated.
	 * 		   | if this.getUsedCapacity(unit) + weapon.getOwnWeight(unit) > this.getMaximumCapacity(unit)
	 * 		   | then	(weapon.terminate)&& !(allPossessions.get("Weapon").contains(weapon))
	 * @post   The hashmap will no longer contain the weapon that was returned.
	 * 		   | !(new.allPossessions.get("Weapon").contains(weapon))
	 */
	protected Object chooseWeapon(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Weapon")){
				ArrayList<Object> objectlist = (allPossessions.get("Weapon"));
				ArrayList<Weapon> weaponlist = new ArrayList<Weapon>();
				Iterator<Object> iterator = objectlist.iterator();
				while (iterator.hasNext()){
					weaponlist.add((Weapon) iterator.next());
				}
				Collections.sort(weaponlist);
				while (!weaponlist.isEmpty()){
					Weapon weapon = (Weapon) weaponlist.get(0);
					while (!(this.getUsedCapacity(Unit.KG) + weapon.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (weaponlist.size()>= 2)){
						((Weapon)allPossessions.get("Weapon").get(0)).terminate();
						allPossessions.get("Weapon").remove(0);
						weaponlist.remove(0);
						weapon = (Weapon) weaponlist.get(0);					
					}
					if (this.getUsedCapacity(Unit.KG) + weapon.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
						allPossessions.get("Weapon").remove(0);
						weaponlist.remove(0);
						return weapon;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Chooses the Backpack of all the backpacks between all the possessions of the monster and the hero that has the highest
	 * value for capacity from all the backpacks that the hero has the capacity to wear.
	 * 
	 * @param  allPossessions
	 * 		   The hashmap that contains all the possessions of the hero and the monster
	 * @return The backpack with the highest capacity that the hero is capable of wearing. If there is no backpack that meets
	 * 		   this requirements, null is returned.
	 * @post   If the hero isn't capable of wearing the backpack with the highest capacity, 
	 * 		   this backpack is removed from the hashmap.
	 * 		   | if this.getUsedCapacity(unit) + backpack.getOwnWeight(unit) > this.getMaximumCapacity(unit)
	 * 		   | then	!(allPossessions.get("Backpack").contains(backpack))
	 * @post   The hashmap will no longer contain the backpack that was returned.
	 * 		   | !(new.allPossessions.get("Backpack").contains(backpack))
	 * @post   The backpack that was returned will be empty.
	 * 		   | backpack.content.isEmpty()
	 */
	protected Object chooseBackpack(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Backpack")){
				ArrayList<Object> objectlist = (allPossessions.get("Backpack"));
				ArrayList<Backpack> backpacklist = new ArrayList<Backpack>();
				Iterator<Object> iterator = objectlist.iterator();
				while (iterator.hasNext()){
					backpacklist.add((Backpack) iterator.next());
				}
				Collections.sort(backpacklist);
				while (!backpacklist.isEmpty()){
					Backpack backpack = (Backpack) backpacklist.get(0);
					while (!(this.getUsedCapacity(Unit.KG) + backpack.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (backpacklist.size()>= 2)){
						allPossessions.get("Backpack").remove(0);
						backpacklist.remove(0);
						backpack = (Backpack) backpacklist.get(0);					
					}
					if (this.getUsedCapacity(Unit.KG) + backpack.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
						allPossessions.get("Backpack").remove(0);
						backpacklist.remove(0);
						backpack.emptyStorage();
						return backpack;
					}
				}
			}
		}
		return null;
	}
		
	/**
	 * Chooses the Purse of all the purses between all the possessions of the monster and the hero that has the highest
	 * value for capacity from all the purses that the hero has the capacity to wear.
	 * 
	 * @param  allPossessions
	 * 		   The hashmap that contains all the possessions of the hero and the monster
	 * @return The purse with the highest capacity that the hero is capable of wearing. If there is no purse that meets
	 * 		   this requirements, null is returned.
	 * @post   If the hero isn't capable of wearing the purse with the highest capacity, 
	 * 		   this purse is removed from the hashmap.
	 * 		   | if this.getUsedCapacity(unit) + purse.getOwnWeight(unit) > this.getMaximumCapacity(unit)
	 * 		   | then	!(allPossessions.get("Purse").contains(purse))
	 * @post   The hashmap will no longer contain the purse that was returned.
	 * 		   | !(new.allPossessions.get("Purse").contains(purse))
	 * @post   The purse that was returned will be empty.
	 * 		   | purse.content.isEmpty()
	 */
	protected Object choosePurse(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Purse")){
				ArrayList<Object> objectlist = (allPossessions.get("Purse"));
				ArrayList<Purse> purselist = new ArrayList<Purse>();
				Iterator<Object> iterator = objectlist.iterator();
				while (iterator.hasNext()){
					purselist.add((Purse) iterator.next());
				}
				Collections.sort(purselist);
				while (!purselist.isEmpty()){
					Purse purse = (Purse) purselist.get(0);
					while (!(this.getUsedCapacity(Unit.KG) + purse.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (purselist.size()>= 2)){
						allPossessions.get("Purse").remove(0);
						purselist.remove(0);
						purse = (Purse) purselist.get(0);					
					}
					if (this.getUsedCapacity(Unit.KG) + purse.getOwnWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
						allPossessions.get("Purse").remove(0);
						purselist.remove(0);
						purse.emptyStorage();
						return purse;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Chooses the Ducat of all the ducats between all the possessions of the monster and the hero that has the highest
	 * value for value from all the ducats that the hero has the capacity to wear.
	 * 
	 * @param  allPossessions
	 * 		   The hashmap that contains all the possessions of the hero and the monster
	 * @return The ducat with the highest value that the hero is capable of wearing. If there is no ducat that meets
	 * 		   this requirements, null is returned.
	 * @post   If the hero isn't capable of wearing the ducat with the highest value, 
	 * 		   this ducat is removed from the hashmap.
	 * 		   | if ((this.getUsedCapacity(unit) + ducat.getOwnWeight(unit) > this.getMaximumCapacity(unit))
	 * 		   | then	!(allPossessions.get("Ducat").contains(ducat))
	 * @post   The hashmap will no longer contain the ducat that was returned.
	 * 		   | !(new.allPossessions.get("Ducat").contains(ducat))
	 */
	protected Object chooseDucat(HashMap<String, ArrayList<Object>> allPossessions){
		if (this.getUsedCapacity(Unit.KG) < this.getMaximumCapacity(Unit.KG)){
			if (allPossessions.containsKey("Ducat")){
				ArrayList<Object> objectlist = (allPossessions.get("Ducat"));
				ArrayList<Ducat> ducatlist = new ArrayList<Ducat>();
				Iterator<Object> iterator = objectlist.iterator();
				while (iterator.hasNext()){
					ducatlist.add((Ducat) iterator.next());
				}
				while (!ducatlist.isEmpty()){
					Collections.sort(ducatlist);
					Ducat ducat = (Ducat) ducatlist.get(0);
					while (!(this.getUsedCapacity(Unit.KG) + ducat.getWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)) && (ducatlist.size()>= 2)){
						allPossessions.get("Ducat").remove(0);
						ducatlist.remove(0);
						ducat = (Ducat) ducatlist.get(0);					
					}
					if (this.getUsedCapacity(Unit.KG) + ducat.getWeight(Unit.KG) <= this.getMaximumCapacity(Unit.KG)){
						allPossessions.get("Ducat").remove(0);
						ducatlist.remove(0);
						return ducat;
					}
				}
			}
		}
		return null;
	}
}
