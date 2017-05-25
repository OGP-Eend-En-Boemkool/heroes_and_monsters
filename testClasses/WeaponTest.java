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
		backpack = new Backpack(132, 261, 1, Unit.KG);
	}
	
	@Test
	public void testWeapon_LegalCase(){
		assertTrue(weapon.getCurrentDamage() == 42);
		assertTrue(weapon.getMaximumDamage() == 100);
		assertTrue(weapon.canHaveAsDamage(weapon.getCurrentDamage()));
		assertTrue(weapon.isValidMaximumDamage(weapon.getMaximumDamage()));
		assertTrue(weapon.getValue() == 84);
		assertTrue(weapon.isValidValue(weapon.getValue()));
	}
	
	
}
