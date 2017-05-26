package testClasses;

import static org.junit.Assert.*;
import java.math.*;
import java.util.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class HeroTest {

	Hero hero1, hero2;
	Monster monster1;
	Backpack backpack;
	Armor armor1, armor2;
	Weapon weapon1, weapon2, weapon3;
	Purse purse;
	
	@Before
	public void setUpFixture(){
		armor1 = new Armor(2477, 84, 752, 73, Unit.KG);
		armor2 = new Armor(13, 64, 654, 0, Unit.KG);
		weapon1 = new Weapon(20, Unit.KG, 42);
		weapon2 = new Weapon(12, Unit.KG, 56);
		weapon3 = new Weapon(15, Unit.KG, 21);
		purse = new Purse(1, Unit.KG, 5);
		backpack = new Backpack(132, 261, 0, Unit.KG);
		hero1 = new Hero("Superman", new BigDecimal(219.23), 127, new ArrayList<Object>(Arrays.asList(weapon1, null, null, armor1, purse)));
		hero2 = new Hero("Spiderman", 163, armor2);
		monster1 = new Monster("Dracula", new BigDecimal(41.23), 73, new ArrayList<String>(Arrays.asList("Left hand", "Right hand")), new ArrayList<Object>(), 49, 46);
	}
	
	@Test
	public void testHero_LegalCase(){
		assertTrue(hero1.canHaveAsName(hero1.getName()));
		assertTrue(hero1.canAddToAnchor(weapon2, "Right hand"));
		Armor armor3 = new Armor(11, 20, 562, 48, Unit.KG);
		assertTrue(hero1.canAddToAnchor(armor3, "Back"));
		assertTrue(hero1.getCurrentProtection() == 11);
		assertTrue(hero1.canHitCreature(monster1));
	}
	
	@Test
	public void testGetMaximumCapacity(){
		Armor a1 = new Armor(17, 64, 654, 0, Unit.KG);
		Hero h1 = new Hero("Een", new BigDecimal(9.01), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a1, null)));
		assertTrue(h1.getMaximumCapacity(Unit.KG) == 90.1);
		Armor a2 = new Armor(19, 64, 654, 0, Unit.KG);
		Hero h2 = new Hero("Twee", new BigDecimal(0.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a2, null)));
		assertTrue(h2.getMaximumCapacity(Unit.KG) == 0);
		Armor a3 = new Armor(23, 64, 654, 0, Unit.KG);
		Hero h3 = new Hero("Drie", new BigDecimal(12.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a3, null)));
		assertTrue(h3.getMaximumCapacity(Unit.KG) == 130);
		Armor a4 = new Armor(29, 64, 654, 0, Unit.KG);
		Hero h4 = new Hero("Vier", new BigDecimal(12.03), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a4, null)));
		assertTrue(h4.getMaximumCapacity(Unit.KG) == 150);
		Armor a5 = new Armor(29, 64, 654, 0, Unit.KG);
		Hero h5 = new Hero("Vier", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a5, null)));
		assertTrue(h5.getMaximumCapacity(Unit.KG) == 800);
	}
	
	@Test
	public void testIllegalName(){
		assertFalse(hero1.canHaveAsName("Troy1"));
		// 2 apostrofs kunnen wel
		assertTrue(hero1.canHaveAsName("D'as P'it"));
		// 3 apostrofs kunnen niet
		assertFalse(hero1.canHaveAsName("Z'on Z'ee S'trand"));
		// spatie na dubbelpunt kan wel
		assertTrue(hero1.canHaveAsName("Hero: Awesome"));
		// dubbelpunt zonder spatie na kan niet
		assertFalse(hero1.canHaveAsName("Hero:Boring"));
		assertFalse(hero1.canHaveAsName("Dragon:"));
	}
	
	@Test
	public void testCannotAddToAnchor(){
		Purse p = new Purse(1, Unit.KG, 5);
		hero2.dropFromAnchorAndTerminate(armor2);
		assertFalse(hero2.canAddToAnchor(p, "Back"));
		Weapon w = new Weapon(0, Unit.KG, 42);
		assertFalse(hero2.canAddToAnchor(w, "Belt"));
		Armor a1 = new Armor(31, 64, 654, 0, Unit.KG);
		Armor a2 = new Armor(37, 64, 654, 0, Unit.KG);
		hero2.addToAnchor(a1, "Body");
		hero2.addToAnchor(backpack, "Back");
		backpack.addToStorage(a2);
		Armor a3 = new Armor(37, 64, 654, 0, Unit.KG);
		assertFalse(hero2.canAddToAnchor(a3, "Left hand"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorIllegalName() throws IllegalArgumentException {
		Armor a = new Armor(41, 64, 654, 0, Unit.KG);
		Hero h = new Hero("illegalname", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a, null)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorCannotAddObjectToAssociatedAnchor()
			throws IllegalArgumentException {
		Weapon w = new Weapon(12, Unit.KG, 42);
		Hero h = new Hero("Hero", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, w, null)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorAnchorObjectsNotRightSize()
			throws IllegalArgumentException {
		Armor a = new Armor(43, 64, 654, 0, Unit.KG);
		Hero h = new Hero("Hero", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorArmorAtBirthOnBody1() throws IllegalArgumentException {
		Hero h = new Hero("Hero", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, null, null)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorArmorAtBirthOnBody2() throws IllegalArgumentException {
		Weapon w = new Weapon(12, Unit.KG, 42);
		Hero h = new Hero("Hero", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, w, null)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorPurseOnBelt1() throws IllegalArgumentException {
		Armor a = new Armor(47, 64, 654, 0, Unit.KG);
		Weapon w = new Weapon(12, Unit.KG, 42);
		Hero h = new Hero("Hero", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a, w)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorPurseOnBelt2() throws IllegalArgumentException {
		Armor a = new Armor(51, 64, 654, 0, Unit.KG);
		Purse p = new Purse(1, Unit.KG, 6);
		Hero h = new Hero("Hero", new BigDecimal(25.00), 127, new ArrayList<Object>(Arrays.asList(null, null, null, a, p)));
	}
}
