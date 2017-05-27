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
		armor1 = new Armor(2477, 84, new Ducat(752), 73, Unit.KG);
		armor2 = new Armor(13, 64, new Ducat(654), 12, Unit.KG);
		weapon1 = new Weapon(20, Unit.KG, 42);
		weapon2 = new Weapon(12, Unit.KG, 84);
		weapon3 = new Weapon(15, Unit.KG, 21);
		purse = new Purse(1, Unit.KG, 5);
		backpack = new Backpack(new Ducat(132), 261, 1, Unit.KG);
		hero1 = new Hero("Superman", new BigDecimal(219.23), 127, new ArrayList<Object>(Arrays.asList(weapon1, null, null, armor1, purse)));
		hero2 = new Hero("Spiderman", new BigDecimal(256.49), 163, new ArrayList<Object>(Arrays.asList(null, weapon2, backpack, armor2, null)));
		monster1 = new Monster("Frankenstein", new BigDecimal(321.64), 7, new ArrayList<String>(Arrays.asList("Left hand", "Right hand", "Back", "Tail")), new ArrayList<Object>(Arrays.asList(weapon3)), 77, 52);
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
		assertTrue(Creature.getDefaultStrength().compareTo(new BigDecimal(0.00)) == 0);
		assertTrue(hero2.getStrength().floatValue() == 256.49F);
		assertTrue(Creature.isValidStrength(hero1.getStrength()));
		assertTrue(monster2.getUsedCapacity(Unit.KG) == 0);
		assertFalse(hero1.getKilled());
	}
	
	@Test
	public void testAnchors_LegalCase(){
		assertTrue(monster1.getAnchors().keySet().equals(new HashSet<Object>(Arrays.asList("Left hand", "Right hand", "Back", "Tail"))));
		assertTrue(hero1.getAnchors().values().containsAll(new HashSet<Object>(Arrays.asList(weapon1, armor1, purse))));
		Armor armor = new Armor(19, 54, new Ducat(546), 16, Unit.KG);
		monster2.addToAnchor(armor, "Left hand");
		assertTrue(armor.getHolder() == monster2);
		Weapon weapon = new Weapon(10, Unit.KG, 7);
		assertTrue(monster2.canAddToAnchor(weapon, "Right hand"));
		assertTrue(hero1.canEmptyAnchor("Left hand"));
		assertTrue(hero2.canDropFromAnchor(weapon2));
		hero1.passAlong(purse, hero2, "Belt");
		assertTrue(purse.getHolder() == hero2);
		monster1.passToStorage(weapon3, backpack);
		assertTrue(weapon3.getHolder() == backpack);
		hero1.emptyAnchorAndTerminate("Left hand");
		assertTrue(weapon1.getTerminated());
		hero2.dropFromAnchorAndTerminate(weapon2);
		assertTrue(weapon2.getTerminated());
	}
	
	@Test
	public void testHit_Hero1_Monster1_LegalCase(){
		hero1.hit(monster1);
		assertTrue(monster1.getHitpoints() <= monster1.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Hero1_Monster2_LegalCase(){
		hero1.hit(monster2);
		assertTrue(monster2.getHitpoints() <= monster2.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Hero2_Monster1_LegalCase(){
		hero2.hit(monster1);
		assertTrue(monster1.getHitpoints() <= monster1.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Hero2_Monster2_LegalCase(){
		Armor a = new Armor(83, 84, new Ducat(752), 41, Unit.KG);
		Purse p = new Purse(12, Unit.KG, 5);
		Ducat d = new Ducat(7);
		monster2.addToAnchor(p, "Left hand");
		monster2.addToAnchor(a, "Right hand");
		p.addToStorage(d);
		hero2.hit(monster2);
		assertTrue(monster2.getHitpoints() <= monster2.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Monster1_Monster2_LegalCase(){
		monster1.hit(monster2);
		assertTrue(monster2.getHitpoints() <= monster2.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Monster2_Monster1_LegalCase(){
		monster2.hit(monster1);
		assertTrue(monster1.getHitpoints() <= monster1.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Monster1_Hero1_LegalCase(){
		monster1.hit(hero1);
		assertTrue(hero1.getHitpoints() <= hero1.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Monster1_Hero2_LegalCase(){
		monster1.hit(hero2);
		assertTrue(hero2.getHitpoints() <= hero2.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Monster2_Hero1_LegalCase(){
		Weapon w = new Weapon(60, Unit.KG, 49);
		Armor a = new Armor(53, 84, new Ducat(752), 46, Unit.KG);
		Backpack b = new Backpack(new Ducat(132), 261, 1, Unit.KG);
		Ducat d1 = new Ducat(12);
		Ducat d2 = new Ducat(74);
		hero1.addToAnchor(b, "Back");
		purse.addToStorage(d1);
		b.addToStorage(d2);
		b.addToStorage(a);
		hero1.addToAnchor(w, "Right hand");
		monster2.hit(hero1);
		assertTrue(hero1.getHitpoints() <= hero1.getMaxHitpoints());
	}
	
	@Test
	public void testHit_Monster2_Hero2_LegalCase(){
		monster2.hit(hero2);
		assertTrue(hero2.getHitpoints() <= hero2.getMaxHitpoints());
	}
	
	@Test
	public void testIllegalName(){
		assertFalse(hero1.canHaveAsName(null));
		assertFalse(monster2.canHaveAsName("monster"));
	}
	
	@Test
	public void testIllegalHitpointsFighting(){
		assertFalse(hero1.canHaveAsHitpointsFighting(-5));
		assertFalse(hero2.canHaveAsHitpointsNotFighting(-2));
		assertFalse(monster1.canHaveAsHitpointsNotFighting(6));
	}
	
	@Test
	public void testInvalidStrength(){
		assertFalse(Creature.isValidStrength(new BigDecimal(-5.23)));
		assertFalse(Creature.isValidStrength(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddToAnchorException() throws IllegalArgumentException {
		hero1.addToAnchor(monster1, "Right hand");
	}
	
	@Test
	public void testCannotAddToAnchor(){
		Ducat ducat = new Ducat(3);
		assertFalse(monster2.canAddToAnchor(ducat, "Left hand"));
		hero1.passToStorage(weapon1, backpack);
		backpack.removeFromStorageAndTerminate(weapon1);
		assertFalse(hero1.canAddToAnchor(weapon1, "Left hand"));
		Backpack rugzak = new Backpack(new Ducat(100), 5000, 1, Unit.KG);
		Weapon wapen1 = new Weapon(4500, Unit.KG, 49);
		rugzak.addToStorage(wapen1);
		assertFalse(monster2.canAddToAnchor(rugzak, "Right hand"));
		Weapon wapen2 = new Weapon(800000000, Unit.KG, 77);
		assertFalse(monster2.canAddToAnchor(wapen2, "Right hand"));
		Weapon wapen3 = new Weapon(12, Unit.KG, 7);
		assertFalse(monster2.canAddToAnchor(wapen3, "Head"));
		assertFalse(hero2.canAddToAnchor(wapen3, "Right hand"));
		assertFalse(hero1.canAddToAnchor(monster2, "Right hand"));
	}
	
	@Test
	public void testCannotEmptyAnchor(){
		assertFalse(hero1.canEmptyAnchor("Tail"));
	}
	
	@Test
	public void testCannotDropFromAnchor(){
		assertFalse(hero1.canDropFromAnchor(weapon2));
		assertFalse(hero1.canDropFromAnchor(hero2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPassAlongException1() throws IllegalArgumentException {
		hero1.passAlong(weapon3, hero2, "Left hand");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPassAlongException2() throws IllegalArgumentException {
		hero1.passAlong(weapon1, hero2, "Right hand");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPassToStorageException1() throws IllegalArgumentException {
		hero1.passToStorage(weapon2, backpack);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPassToStorageException2() throws IllegalArgumentException {
		Purse extra = new Purse(1, Unit.KG, 5);
		hero1.passToStorage(weapon1, extra);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyAnchorAndTerminate() throws IllegalArgumentException {
		monster1.emptyAnchorAndTerminate("Body");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDropFromAnchorAndTerminate() throws IllegalArgumentException {
		monster1.dropFromAnchorAndTerminate(weapon1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testHitIllegalCreature() throws IllegalArgumentException {
		hero1.hit(hero2);
	}
}
