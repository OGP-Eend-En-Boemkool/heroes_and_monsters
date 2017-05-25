package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class ArmorTest {
	
	Armor armor;
	Backpack backpack;
	
	@Before
	public void setUpFixture(){
		armor = new Armor(2477, 84, 752, 73, Unit.KG);
		backpack = new Backpack(132, 261, 1, Unit.KG);
		backpack.addToStorage(armor);
	}
	
	@Test
	public void testArmor_LegalCase(){
		assertTrue(armor.getMaxProtection() == 84);
		assertTrue(armor.getCurrentProtection() == 1);
		armor.increaseProtection(23);
		assertTrue(armor.getCurrentProtection() == 24);
		armor.decreaseProtection(5);
		assertTrue(armor.getCurrentProtection() == 19);
		assertTrue(armor.getMaxValue() == 752);
		assertTrue(armor.isValidValue(armor.getValue()));
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testGetCurrentProtectionTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(armor);
		armor.getCurrentProtection();
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testGetMaxProtectionTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(armor);
		armor.getMaxProtection();
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testSetCurrentProtectionTerminated() throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(armor);
		armor.setCurrentProtection(20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDecreaseProtectionInvalidProtectionReached()
			throws IllegalArgumentException {
		armor.decreaseProtection(30);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDecreaseProtectionNegativeNumberUsed()
			throws IllegalArgumentException {
		armor.decreaseProtection(-30);
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testDecreaseProtectionTerminated()
			throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(armor);
		armor.increaseProtection(30);
		armor.decreaseProtection(20);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testIncreaseProtectionInvalidProtectionReached()
			throws IllegalArgumentException {
		armor.increaseProtection(90);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testIncreaseProtectionNegativeNumberUsed()
			throws IllegalArgumentException {
		armor.increaseProtection(-30);
	}
	
	@Test (expected = OwnableIsTerminatedException.class)
	public void testIncreaseProtectionTerminated()
			throws OwnableIsTerminatedException {
		backpack.removeFromStorageAndTerminate(armor);
		armor.increaseProtection(20);
	}
	
	@Test
	public void testIllegalProtection(){
		assertFalse(armor.canHaveAsProtection(0));
		assertFalse(armor.canHaveAsProtection(85));
	}
	
	@Test
	public void testIllegalMaxProtection(){
		assertFalse(armor.canHaveAsMaxProtection(0));
		assertFalse(armor.canHaveAsMaxProtection(101));
	}
	
	@Test
	public void testIllegalIdentification(){
		assertFalse(Armor.canHaveAsIdentification(0));
		assertFalse(Armor.canHaveAsIdentification(-3));
		assertFalse(Armor.canHaveAsIdentification(22));
		assertFalse(Armor.canHaveAsIdentification(2477));
	}
	
	@Test
	public void testInvalidValue(){
		assertFalse(armor.isValidValue(-2));
		assertFalse(armor.isValidValue(0));
		assertFalse(armor.isValidValue(-2));
		assertFalse(armor.isValidValue(1002));
		assertFalse(armor.isValidValue(501));
	}

}
