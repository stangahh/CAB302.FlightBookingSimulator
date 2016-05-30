/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Megan
 *
 */
public class PremiumTests extends Premium {

	Passenger person;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws PassengerException {
		person = new Premium(2, 10);
	}

	@Test
	public void testPremiumUpgrade() {
		person = person.upgrade();
		assertEquals("J(U)P:0", person.getPassID());
		assertEquals(2, person.getBookingTime());
		assertEquals(10, person.getDepartureTime());
	}

}
