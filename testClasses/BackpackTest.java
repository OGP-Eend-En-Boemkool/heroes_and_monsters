package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;
import java.util.*;
import java.math.*;

public class BackpackTest {

	Backpack backpack1, backpack2, backpack3;
	Purse purse;
	Ducat ducat1, ducat2, ducat3;
	Armor armor1, armor2, armor3, armor4;
	Weapon weapon1, weapon2;
	Hero hero;
	Monster monster;
	
	@Before
	public void setUpFixture(){
		backpack1 = new Backpack(10, 500, 10, Unit.KG);
		backpack2 = new Backpack(200, 1000, 600, Unit.GRAM);
		backpack3 = new Backpack(15, 500, 0.7, Unit.KG);
		purse = new Purse(100, Unit.GRAM, 5000);
		ducat1 = new Ducat(2);
		ducat2 = new Ducat(20);
		ducat3 = new Ducat(5);
		armor1 = new Armor(13, 90, 830, 26, Unit.KG);
		armor2 = new Armor(7, 92, 850, 27, Unit.KG);
		armor3 = new Armor(17, 99, 710, 2, Unit.KG);
		armor4 = new Armor(19, 80, 615, 8, Unit.KG);
		weapon1 = new Weapon(5.67, Unit.KG, 84);
		weapon2 = new Weapon(300, Unit.KG, 84);
		ArrayList<Object> anchorList = new ArrayList<Object>((Arrays.asList(null, null, null, armor1, null)));
		hero = new Hero("Special K", new BigDecimal(13.02), 2477, anchorList);
	}
	
	@Test
	public void testBackpack_CapacityLegalCase(){
		assertTrue(backpack1.getMaximumCapacity(Unit.GRAM) == 500000);
		assertTrue(backpack1.getMaximumCapacity(Unit.KG) == 500);
		assertTrue(backpack2.getMaximumCapacity(Unit.KG) == 1);
		int sizeBefore1 = backpack1.getContent().size();
		backpack1.addToStorage(weapon1);
		int sizeAfter1 = backpack1.getContent().size();
		assertTrue(sizeBefore1 == sizeAfter1 -1);
		assertTrue(backpack1.getUsedCapacity(Unit.KG) == 5.67);
		int sizeBefore3 = backpack3.getContent().size();
		backpack3.addToStorage(backpack1);
		assertTrue(backpack3.getUsedCapacity(Unit.KG) == 15.67);
		backpack2.addToStorage(ducat1);
		backpack3.addToStorage(backpack2);
		int sizeAfter3 = backpack3.getContent().size();
		assertTrue(sizeAfter3 == sizeBefore3 + 2);
		assertTrue(backpack3.getUsedCapacity(Unit.KG) == 16.37);
	}
	
	@Test
	public void testBackpack_contentLegalCase(){
		backpack3.addToStorage(ducat2);
		int sizeBefore = backpack3.getContent().size();
		assertTrue((backpack3.getContent().get(sizeBefore - 1)).equals(new Ducat(20)));
		backpack3.addToStorage(ducat3);
		int sizeAfter = backpack3.getContent().size();
		assertTrue(((Ducat)backpack3.getContent().get(sizeAfter - 1)).equals(new Ducat(25)));
		assertTrue(sizeBefore == sizeAfter);	
		assertTrue(backpack3.canAddToStorage(purse));
		backpack3.addToStorage(armor3);
		assertTrue(backpack3.canTakeOutOfStorage(armor3));
		backpack3.removeFromStorageAndTerminate(armor3);
		assertTrue(armor3.getTerminated());
		backpack3.addToStorage(backpack1);
		assertTrue(backpack3.ownableInBackpack(backpack1));
		backpack1.addToStorage(armor4);
		assertTrue(backpack1.ownableInBackpack(armor4));
		assertTrue(backpack3.ownableInBackpack(armor4));
	}

	@Test
	public void testBackpack_valueLegalCase(){
		assertTrue(backpack3.getStandardValue() == 15);
		assertTrue(backpack2.getStandardValue() == 200);
		assertTrue(backpack1.getStandardValue() == 10);
		backpack3.addToStorage(ducat3);
		backpack1.addToStorage(armor4);
		backpack3.addToStorage(backpack1);
		assertTrue(backpack3.getValue() == 32);
	}
	
	@Test
	public void testBackpack_weightLegalCase(){
		assertTrue(backpack3.getTotalWeight(Unit.KG) == 0.7);
		backpack3.addToStorage(ducat3);
		assertTrue(backpack3.getTotalWeight(Unit.KG) == 0.95);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testBackpack_contentAddToStorageAlreadyHasAnOwner() throws IllegalArgumentException{
		backpack3.addToStorage(armor1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBackpack_contentAddToStorageTooHeavy() throws IllegalArgumentException{
		backpack2.addToStorage(armor2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBackpack_contentAddToStorageOwnerNotStrong() throws IllegalArgumentException{
		hero.addToAnchor(backpack3, "Back");
		backpack3.addToStorage(armor2);
		backpack3.addToStorage(weapon2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBackpack_contentAddToStorageHolderNotBig() throws IllegalArgumentException{
		backpack2.addToStorage(backpack3);
		backpack3.addToStorage(armor1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBackpack_contentAddToStorageThirdArmor() throws IllegalArgumentException{
		hero.addToAnchor(backpack3, "Back");
		backpack3.addToStorage(armor4);
		backpack3.addToStorage(armor3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBackpack_contentAddToStorageHeavyBackpackInBackpack() throws IllegalArgumentException{
		backpack3.addToStorage(weapon2);
		backpack2.addToStorage(backpack3);
	}
	
	@Test 
	public void testBackpack_contentCanAddToStorage(){
		assertTrue(backpack3.canAddToStorage(null));
		assertTrue(backpack3.canAddToStorage(weapon1));
		backpack3.addToStorage(weapon1);
		backpack3.removeFromStorageAndTerminate(weapon1);
		assertFalse(backpack3.canAddToStorage(weapon1));
		assertFalse(backpack3.canAddToStorage(armor1));
	}
	
	@Test 
	public void testBackpack_contentTakeOutOfStorage(){
		assertFalse(backpack3.canTakeOutOfStorage(armor2));
		assertFalse(backpack3.canTakeOutOfStorage(ducat2));
		backpack3.addToStorage(ducat1);
		//assertTrue(backpack3.)
	}

}
