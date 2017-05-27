package testClasses;

import static org.junit.Assert.*;
import java.math.*;
import java.util.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class MonsterTest {
	
	Hero hero1, hero2;
	Monster monster1, monster2;
	Backpack backpack;
	Armor armor1, armor2;
	Weapon weapon1, weapon2, weapon3;
	Purse purse;
	
	@Before
	public void setUpFixture(){
		armor1 = new Armor(2477, 84, new Ducat(752), 73, Unit.KG);
		armor2 = new Armor(13, 64, new Ducat(654), 12, Unit.KG);
		weapon1 = new Weapon(20, Unit.KG, 42);
		weapon2 = new Weapon(12, Unit.KG, 56);
		weapon3 = new Weapon(15, Unit.KG, 21);
		purse = new Purse(1, Unit.KG, 5);
		backpack = new Backpack(new Ducat(132), 261, 1, Unit.KG);
		hero1 = new Hero("Superman", new BigDecimal(219.23), 127, new ArrayList<Object>(Arrays.asList(weapon1, null, null, armor1, purse)));
		hero2 = new Hero("Spiderman", new BigDecimal(156.49), 163, new ArrayList<Object>(Arrays.asList(null, weapon2, backpack, armor2, null)));
		monster1 = new Monster("Frankenstein", new BigDecimal(321.64), 97, new ArrayList<String>(Arrays.asList("Left hand", "Right hand", "Back", "Tail")), new ArrayList<Object>(Arrays.asList(weapon3)), 77, 52);
		monster2 = new Monster("Dracula", new BigDecimal(41.23), 73, new ArrayList<String>(Arrays.asList("Left hand", "Right hand")), new ArrayList<Object>(), 49, 46);
	}
	
	@Test
	public void testMonster_LegalCase(){
		assertTrue(monster1.canHaveAsName(monster1.getName()));
		assertTrue(monster2.getCurrentDamage() == 49);
		assertTrue(monster1.getMaximumDamage() == 100);
		assertTrue(monster2.canHaveAsDamage(monster2.getCurrentDamage()));
		assertTrue(monster1.isValidMaximumDamage(monster2.getMaximumDamage()));
		assertTrue(monster2.getCurrentProtection() == 46);
		monster2.decreaseProtection(5);
		assertTrue(monster2.getCurrentProtection() == 41);
		monster2.increaseProtection(5);
		assertTrue(monster2.getCurrentProtection() == 46);
		assertTrue(monster1.getMaximumCapacity(Unit.KG) == 2894.76);
		assertTrue(monster1.canHaveAsProtection(monster1.getCurrentProtection()));
		assertTrue(monster2.canHitCreature(monster1));
	}
	
	@Test
	public void testIllegalName(){
		assertFalse(monster2.canHaveAsName("Monster_dracula"));
	}
	
	@Test
	public void testIllegalDamage(){
		assertFalse(monster1.canHaveAsDamage(0));
		assertFalse(monster2.canHaveAsDamage(101));
		assertFalse(monster1.canHaveAsDamage(50));
	}
	
	@Test
	public void testInvalidMaximumDamage(){
		assertFalse(monster2.isValidMaximumDamage(0));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDecreaseProtectionInvalidProtectionReached()
			throws IllegalArgumentException {
		monster1.decreaseProtection(60);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDecreaseProtectionNegativeNumberUsed()
			throws IllegalArgumentException {
		monster2.decreaseProtection(-30);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testIncreaseProtectionInvalidProtectionReached()
			throws IllegalArgumentException {
		monster2.increaseProtection(90);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testIncreaseProtectionNegativeNumberUsed()
			throws IllegalArgumentException {
		monster1.increaseProtection(-30);
	}
	
	@Test
	public void testIllegalProtection(){
		assertFalse(monster1.canHaveAsProtection(0));
		assertFalse(monster2.canHaveAsProtection(101));
	}
	
	@Test
	public void testCannotHitCreature(){
		assertFalse(monster1.canHitCreature(monster1));
	}

}
