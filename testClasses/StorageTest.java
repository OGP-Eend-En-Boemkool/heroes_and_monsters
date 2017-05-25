package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class StorageTest {
	
	Backpack backpack1, backpack2;
	Purse purse;
	Ducat ducat;
	Weapon weapon;
	Armor armor;
	
	@Before
	public void setUpFixture(){
		backpack1 = new Backpack(132, 261, 1, Unit.KG);
		backpack2 = new Backpack(74, 194, 0.5, Unit.KG);
		purse = new Purse(1, Unit.KG, 5);
		ducat = new Ducat(61);
		weapon = new Weapon(20, Unit.KG, 42);
		armor = new Armor(2477, 84, 752, 73, Unit.KG);
	}
	
	@Test
	public void testStorage_LegalCase(){
		assertTrue(purse.canHaveAsTotalWeight(purse.getTotalWeight(Unit.KG)));
		assertTrue(backpack1.canHaveAsTotalWeight(backpack1.getTotalWeight(Unit.KG)));
	}
	
	@Test
	public void testTransferToStorage(){
		backpack1.addToStorage(armor);
		backpack1.transferToStorage(backpack2, armor);
		assertTrue(backpack2.OwnableInBackpack(armor));
		//backpack1.addToStorage(ducat);
		//backpack1.transferToStorage(backpack2, ducat);
		//backpack2.transferToStorage(purse, ducat);
		//assertTrue(purse.getValue() == ducat.getValue());
	}

}
