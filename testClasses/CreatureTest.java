package testClasses;

import static org.junit.Assert.*;
import java.math.*;
import java.util.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class CreatureTest {

	Hero hero1, hero2;
	Monster monster1, monster2;
	Backpack backpack;
	Armor armor1, armor2;
	Weapon weapon1, weapon2, weapon3;
	Purse purse;
	
	@Before
	public void setUpFixture(){
		armor1 = new Armor(2477, 84, 752, 73, Unit.KG);
		armor2 = new Armor(13, 64, 654, 12, Unit.KG);
		weapon1 = new Weapon(20, Unit.KG, 42);
		weapon2 = new Weapon(12, Unit.KG, 56);
		weapon3 = new Weapon(15, Unit.KG, 21);
		purse = new Purse(1, Unit.KG, 5);
		backpack = new Backpack(132, 261, 1, Unit.KG);
		hero1 = new Hero("Superman", new BigDecimal(219.23), 127, new ArrayList<Object>(Arrays.asList(weapon1, null, null, armor1, purse)));
		hero2 = new Hero("Spiderman", new BigDecimal(156.49), 163, new ArrayList<Object>(Arrays.asList(null, weapon2, backpack, armor2, null)));
		monster1 = new Monster("Frankenstein", new BigDecimal(321.64), 97, new ArrayList<String>(Arrays.asList("Left hand", "Right hand", "Back", "Tail")), new ArrayList<Object>(Arrays.asList(weapon3)), 77, 52);
		monster2 = new Monster("Dracula", new BigDecimal(41.23), 73, new ArrayList<String>(Arrays.asList("Left hand", "Right hand")), new ArrayList<Object>(), 49, 46);
	}
	
	@Test
	public void testCreature_LegalCase(){
		assertTrue(hero1.getName() == "Superman");
		assertTrue(hero2.getName() == "Spiderman");
		assertTrue(monster1.getName() == "Frankenstein");
		assertTrue(monster2.getName() == "Dracula");
		assertTrue(hero1.canHaveAsName(hero1.getName()));
		assertTrue(hero2.canHaveAsName(hero2.getName()));
		assertTrue(monster1.canHaveAsName(monster1.getName()));
		assertTrue(monster2.canHaveAsName(monster2.getName()));
		assertTrue(hero1.getHitpoints() == hero1.getMaxHitpoints());
		assertTrue(hero2.getHitpoints() == 163);
		assertTrue(monster1.canHaveAsHitpointsNotFighting(monster1.getHitpoints()));
		assertTrue(monster2.canHaveAsHitpointsFighting(4));
		//assertTrue(Creature.getDefaultStrength().equals(0));
	}
}
