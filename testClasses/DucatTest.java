package testClasses;

import static org.junit.Assert.*;
import org.junit.*;
import heroes_and_monsters.*;

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
		ducat1.subtract(ducat2);
		assertTrue(ducat1.getValue() == 14);
		assertTrue(ducat1.getWeight(Unit.KG) == 0.7);
		ducat1.subtract(ducat1);
		assertTrue(ducat1.getValue() == 0);
		assertTrue(ducat1.getWeight(Unit.KG) == 0);
		ducat2.subtract(purse);
		assertTrue(ducat2.getValue() == 2);
		assertTrue(ducat2.getWeight(Unit.KG) == 0.1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorIllegalValue() throws IllegalArgumentException{
		Ducat ducat = new Ducat(-1);
		assertTrue(ducat.getValue() == -1);
	}
	
	@Test 
	public void testIsValidValueFalse(){
		assertTrue(Ducat.isValidValue(0));
		assertTrue(Ducat.isValidValue(-0));
		assertTrue(Ducat.isValidValue(1000000000));
		assertFalse(Ducat.isValidValue(-1));
		assertFalse(Ducat.isValidValue(-1000000000));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddDucatIsNull() throws IllegalArgumentException{
		Ducat ducat = null;
		ducat1.add(ducat);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddPurseIsNull() throws IllegalArgumentException{
		Purse purse = null;
		ducat1.add(purse);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSubtractDucatIsNull() throws IllegalArgumentException{
		Ducat ducat = null;
		ducat1.subtract(ducat);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSubtractDucatIsTooBig() throws IllegalArgumentException{
		ducat2.subtract(ducat1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSubtractPurseIsNull() throws IllegalArgumentException{
		Purse purse = null;
		ducat1.subtract(purse);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSubtractPurseIsTooBig() throws IllegalArgumentException{;
		ducat2.subtract(purse);
	}
}
