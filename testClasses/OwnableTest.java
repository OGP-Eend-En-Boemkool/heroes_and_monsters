package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class OwnableTest {

	Weapon weapon;
	Backpack backpack;
	Hero hero;
	Armor armor;
	
	@Before
	public void setUpFixture(){
		weapon = new Weapon(2, Unit.KG, 42);
		backpack = new Backpack(new Ducat(132), 261, 0.1, Unit.KG);
		armor = new Armor(2477, 84, new Ducat(752), 0.1, Unit.KG);
		hero = new Hero("Hero", 89, armor);
		backpack.addToStorage(weapon);
	}
	
	@Test
	public void testOwnable_LegalCase(){
		assertTrue(weapon.getOwnWeight(Unit.KG) == 2);
		assertTrue(Ownable.isValidOwnWeight(weapon.getOwnWeight(Unit.KG)));
		assertTrue(weapon.getHolder() == backpack);
		assertTrue(weapon.getUltimateHolder() == backpack);
		assertTrue(armor.getHolder() == hero);
		assertTrue(armor.getUltimateHolder() == hero);
		assertTrue(weapon.canHaveAsHolder(hero));
		assertTrue(weapon.canHaveAsHolder(backpack));
		assertTrue(weapon.canHaveAsHolder(null));
		hero.addToAnchor(backpack, "Back");
		assertTrue(weapon.getUltimateHolder() == hero);
		assertFalse(armor.getTerminated());
	}
	
	@Test (expected =  OwnableIsTerminatedException.class)
	public void testGetIdentification_Terminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.getIdentification();
	}
	
	@Test
	public void testInvalidWeight(){
		assertFalse(Ownable.isValidOwnWeight(-23));
	}
	
	@Test (expected =  OwnableIsTerminatedException.class)
	public void testGetHolderTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.getHolder();
	}
	
	@Test (expected =  OwnableIsTerminatedException.class)
	public void testGetUltimateHolderTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.getUltimateHolder();
	}
	
	@Test
	public void testIllegalHolder(){
		assertFalse(weapon.canHaveAsHolder(armor));
	}
}
