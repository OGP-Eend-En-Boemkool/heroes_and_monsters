package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class WeaponTest {
	
	Weapon weapon;
	Backpack backpack;
	
	@Before
	public void setUpFixture(){
		weapon = new Weapon(20, Unit.KG, 42);
		backpack = new Backpack(new Ducat(132), 261, 1, Unit.KG);
		backpack.addToStorage(weapon);
	}
	
	@Test
	public void testWeapon_LegalCase(){
		assertTrue(weapon.getCurrentDamage() == 42);
		assertTrue(weapon.getMaximumDamage() == 100);
		assertTrue(weapon.canHaveAsDamage(weapon.getCurrentDamage()));
		assertTrue(weapon.isValidMaximumDamage(weapon.getMaximumDamage()));
		assertTrue(weapon.getValue().getValue() == 84);
		assertTrue(weapon.isValidValue(weapon.getValue()));
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testGetCurrentDamageTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.getCurrentDamage();
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testGetMaximumDamageTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.getMaximumDamage();
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testSetCurrentDamageTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.setCurrentDamage(20);
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testSetMaximumDamageTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(weapon);
		weapon.setMaximumDamage(98);
	}
	
	@Test
	public void testIllegalDamage(){
		assertFalse(weapon.canHaveAsDamage(0));
		assertFalse(weapon.canHaveAsDamage(-7));
		assertFalse(weapon.canHaveAsDamage(105));
		assertFalse(weapon.canHaveAsDamage(55));
	}
	
	@Test
	public void testIllegalMaximumDamage(){
		assertFalse(weapon.isValidMaximumDamage(0));
	}
	
	@Test
	public void testIllegalIdentification(){
		assertFalse(Weapon.canHaveAsIdentification(5));
	}
	
	@Test
	public void testInvalidValue(){
		assertFalse(weapon.isValidValue(new Ducat(201)));
	}
}
