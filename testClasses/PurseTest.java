package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class PurseTest {

	Purse purse;
	Ducat ducat;
	Backpack backpack;
	Hero hero;
	Armor armor;
	Weapon weapon;
	
	
	@Before
	public void setUpFixture(){
		purse = new Purse(1, Unit.KG, 23);
		ducat = new Ducat(79);
		backpack = new Backpack(132, 40, 1, Unit.KG);
		armor = new Armor(2477, 84, 752, 0, Unit.KG);
		hero = new Hero("Hero", 101, armor);
		weapon = new Weapon(38, Unit.KG, 21);
	}
	
	@Test
	public void testPurse_LegalCase(){
		assertTrue(Purse.canHaveAsIdentification(8));
		assertTrue(purse.getMaximumCapacity(Unit.KG) == 23);
		purse.addToStorage(ducat);
		assertTrue(purse.getUsedCapacity(Unit.KG) == (1 + 0.050*79));
		assertTrue(purse.getValue() == 79);
		purse.removeFromStorageAndTerminate(new Ducat(20));
		assertTrue(purse.getValue() == 59);
		assertTrue(purse.getTotalWeight(Unit.KG) == (1 + 0.050*59));
		assertFalse(backpack.canTakeOutOfStorage(new Ducat(26)));
		purse.addToStorage(new Ducat(2));
		purse.transferToStorage(backpack, new Ducat(26));
		assertTrue(purse.getValue() == 35);
		assertTrue(backpack.canTakeOutOfStorage(new Ducat(26)));
		backpack.addToStorage(purse);
		assertTrue(purse.getHolder() == backpack);
		purse.addToStorage(new Ducat(430));
		assertTrue(purse.getBroken());
		assertTrue(purse.getValue() == 0);
		assertTrue(backpack.canTakeOutOfStorage(new Ducat(463)));
		assertTrue(purse.getTotalWeight(Unit.KG) == 1);
	}
	
	@Test
	public void testIllegalIdentification(){
		assertFalse(Purse.canHaveAsIdentification(7));
	}
	
	@Test
	public void testCannotAddToStorage(){
		assertFalse(purse.canAddToStorage(backpack));
		backpack.addToStorage(purse);
		backpack.addToStorage(weapon);
		assertFalse(purse.canAddToStorage(new Ducat(100)));
		hero.addToAnchor(purse, "Belt");
		assertFalse(purse.canAddToStorage(new Ducat(2)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddToStorageException() throws IllegalArgumentException {
		purse.addToStorage(hero);
	}
	
	@Test
	public void testCannotTakeOutOfStorage(){
		purse.addToStorage(ducat);
		assertFalse(purse.canTakeOutOfStorage(new Ducat(80)));
		assertFalse(purse.canTakeOutOfStorage(purse));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveFromStorageException() throws IllegalArgumentException {
		purse.removeFromStorageAndTerminate(new Ducat(2));
	}
	
	@Test
	public void testTransferToOtherPurseAllContent1(){
		purse.addToStorage(ducat);
		backpack.addToStorage(purse);
		assertTrue(purse.getHolder() == backpack);
		Purse other = new Purse(1, Unit.KG, 23);
		purse.transferToStorage(other, ducat);
		assertFalse(purse.getHolder() == backpack);
	}
	
	@Test
	public void testTransferToOtherPurseAllContent2(){
		purse.addToStorage(ducat);
		hero.addToAnchor(purse, "Belt");
		assertTrue(purse.getHolder() == hero);
		Purse other = new Purse(1, Unit.KG, 23);
		purse.transferToStorage(other, ducat);
		assertFalse(purse.getHolder() == hero);
	}
}
