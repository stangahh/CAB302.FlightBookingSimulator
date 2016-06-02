package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * @author Megan Hunter
 *
 */
public class EconomyTests {

	Passenger person;

	@Before
	public  void setUpBeforeClass() throws PassengerException {
		person = new Economy(2, 10);
	}

	@Test
	public void testEconomyUpgrade() {
		person = person.upgrade();
		assertEquals("P(U)Y:0", person.getPassID());
		assertEquals(2, person.getBookingTime());
		assertEquals(10, person.getDepartureTime());
	}

}
