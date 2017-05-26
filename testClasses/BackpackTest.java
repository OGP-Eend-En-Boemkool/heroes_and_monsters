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
		backpack2 = new Backpack(200, 1000, 600, Unit.KG);
		backpack3 = new Backpack(10, 50, 0.7, Unit.KG);
		purse = new Purse(100, Unit.GRAM, 5000);
		ducat1 = new Ducat(20);
		ducat2 = new Ducat(200);
		ducat3 = new Ducat(50);
		armor = new Armor(13, 90, 830, 26, Unit.KG);
		weapon = new Weapon(5.67, Unit.KG, 84);
		ArrayList<Object> anchorList = new ArrayList<Object>((Arrays.asList(null, null, null, armor, null)));
		hero = new Hero("Special K", new BigDecimal(13.02), 2477, anchorList);
	}
	
	@Test
	public void testSetAndGetIdentificationLegal(){
		System.out.println(backpack1.getIdentification());
	}
	
	
}
