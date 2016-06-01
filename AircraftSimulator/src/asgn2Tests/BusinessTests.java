/**
 *
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.Passenger;

/**
 * @author Megan
 *
 */
public class BusinessTests extends Business {

	Passenger person;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws Exception {
		person = new Business(2, 10);
	}

	@Test
	public void testBusinessUpgrade() {
		person = person.upgrade();
		assertEquals("F(U)J:0", person.getPassID());
		assertEquals(2, person.getBookingTime());
		assertEquals(10, person.getDepartureTime());
	}

}
