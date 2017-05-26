package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;
import Exceptions.*;

public class DucatTest {

	Ducat ducat1, ducat2, ducat3;
	Purse purse;
	
	@Before
	public void setUpFixture(){
		ducat1 = new Ducat(8);
		ducat2 = new Ducat(2);
		ducat3 = new Ducat(4);
		purse = new Purse(200, Unit.GRAM, 5000.00);
		purse.addToStorage(ducat3);
	}
	
	@Test
	public void testDucat_LegalCase(){
		assertTrue(ducat1.getValue() == 8);
		assertTrue(ducat2.getValue() == 2);
		assertTrue(ducat3.getValue() == 0);
		assertTrue(Ducat.ONE_DUCAT.getValue() == 1);
		assertTrue(Ducat.isValidValue(100));
		assertTrue(ducat1.getWeight(Unit.KG) == 0.4);
		assertTrue(ducat2.getWeight(Unit.KG) == 0.1);
		assertTrue(ducat3.getWeight(Unit.KG) == 0);
		ducat1.add(ducat2);
		assertTrue(ducat1.getValue() == 10);
		assertTrue(ducat2.getValue() == 2);
		ducat1.add(ducat1);
		assertTrue(ducat1.getValue() == 20);
		assertTrue(ducat1.getWeight(Unit.KG) == 1);
		ducat2.add(purse);
		assertTrue(ducat2.getValue() == 6);
		assertTrue(ducat2.getWeight(Unit.KG) == 0.3);
	}
}
