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
	
	
	@Before
	public void setUpFixture(){
		purse = new Purse(1, Unit.KG, 23);
		ducat = new Ducat(79);
		backpack = new Backpack(132, 40, 1, Unit.KG);
		armor = new Armor(2477, 84, 752, 0, Unit.KG);
		hero = new Hero("Hero", 102, armor);
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
		assertFalse(purse.canAddToStorage(new Ducat(1000)));
	}
}
