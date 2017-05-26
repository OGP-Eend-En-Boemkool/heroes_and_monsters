package testClasses;

import static org.junit.Assert.*;
import java.math.*;
import java.util.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class StorageTest {
	
	Backpack backpack1, backpack2;
	Purse purse;
	Ducat ducat;
	Weapon weapon;
	Armor armor, armor2;
	Hero hero;
	
	@Before
	public void setUpFixture(){
		backpack1 = new Backpack(132, 261, 1, Unit.KG);
		backpack2 = new Backpack(74, 194, 0.5, Unit.KG);
		purse = new Purse(1, Unit.KG, 5);
		ducat = new Ducat(61);
		weapon = new Weapon(20, Unit.KG, 42);
		armor = new Armor(2477, 84, 752, 73, Unit.KG);
		armor2 = new Armor(13, 64, 654, 12, Unit.KG);
		hero = new Hero("Hero", new BigDecimal(219.23), 123, new ArrayList<Object>(Arrays.asList(null, null, null, armor2, null)));
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
		backpack1.addToStorage(ducat);
		backpack1.transferToStorage(backpack2, ducat);
		assertTrue(backpack1.getValue() == backpack1.getStandardValue());
		backpack2.transferToStorage(purse, ducat);
		assertTrue(backpack2.getValue() == backpack2.getStandardValue() + armor.getValue());
		assertTrue(purse.getValue() == ducat.getValue());
	}
	
	@Test
	public void testTransferToCreature(){
		backpack1.addToStorage(weapon);
		backpack1.transferToCreature(weapon, hero, "Left hand");
		assertTrue(weapon.getHolder() == hero);
		backpack2.addToStorage(purse);
		backpack1.addToStorage(backpack2);
		assertTrue(purse.getHolder() == backpack2);
		assertTrue(purse.getUltimateHolder() == backpack1);
		hero.addToAnchor(backpack1, "Back");
		assertTrue(purse.getUltimateHolder() == hero);
		
	}

}
