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
	}
}
