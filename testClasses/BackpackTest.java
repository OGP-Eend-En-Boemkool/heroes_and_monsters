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
	Armor armor;
	Weapon weapon;
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
		armor = new Armor(13, 90, 830, 26, Unit.KG);
		weapon = new Weapon(5.67, Unit.KG, 84);
		ArrayList<Object> anchorList = new ArrayList<Object>((Arrays.asList(null, null, null, armor, null)));
		hero = new Hero("Special K", new BigDecimal(13.02), 2477, anchorList);
	}
	
	@Test
	public void testBackpack_CapacityLegalCase(){
		assertTrue(backpack1.getMaximumCapacity(Unit.GRAM) == 500000);
		assertTrue(backpack1.getMaximumCapacity(Unit.KG) == 500);
		assertTrue(backpack2.getMaximumCapacity(Unit.KG) == 1);
		backpack1.addToStorage(weapon);
		assertTrue(backpack1.getUsedCapacity(Unit.KG) == 5.67);
		backpack3.addToStorage(backpack1);
		assertTrue(backpack3.getUsedCapacity(Unit.KG) == 15.67);
		backpack2.addToStorage(ducat1);
		backpack3.addToStorage(backpack2);
		assertTrue(backpack3.getUsedCapacity(Unit.KG) == 16.37);
	}
	
	@Test
	public void testBackpack_contentLegalCase(){
		backpack3.addToStorage(ducat2);
		int sizeBefore = backpack3.getContent().size();
		assertTrue(backpack3.getContent().get(sizeBefore - 1) == ducat2);
		backpack3.addToStorage(ducat3);
		assertTrue(((Ducat)backpack3.getContent().get(backpack3.getContent().size() - 1)).equals(new Ducat(25)));
		
	}
	
	
}
