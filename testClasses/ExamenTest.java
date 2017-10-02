package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class ExamenTest {

	Purse purse;
	Ducat ducat;
	Backpack backpack;
	Hero hero;
	Armor armor;
	Weapon weapon;
	
	
	@Before
	public void setUpFixture(){
		purse = new Purse(0, Unit.KG, 40);
		ducat = new Ducat(79);
		backpack = new Backpack(new Ducat(132), 40, 1, Unit.KG);
		armor = new Armor(2477, 84, new Ducat(752), 0, Unit.KG);
		hero = new Hero("Hero", 101, armor);
		weapon = new Weapon(38, Unit.KG, 21);
	}
	
	@Test
	public void testAddToStorageLegalCase(){
		assertTrue(purse.canAddToStorage(ducat));
		purse.addToStorage(ducat);
		assertTrue(purse.getContent().equals(new Ducat(79)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddToStorageNull(){
		assertFalse(purse.canAddToStorage(null));
		purse.addToStorage(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddToStorageNoOwnable(){
		assertFalse(purse.canAddToStorage(hero));
		purse.addToStorage(hero);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddToStoragePurseBroken(){
		purse.addToStorage(new Ducat(4000));
		assertFalse(purse.canAddToStorage(null));
		purse.addToStorage(null);
	}
}