/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

/**
 * @author Megan
 *
 */
public class FirstTests extends First {

	Passenger person;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws PassengerException {
		//CAN'T INSTANTIATE AN ABSTRACT CLASS
		//This before doesn't work with the test cases for some reason...
		this.person = new First(2,10);
	}

	@Test
	public void testBookingTime() throws PassengerException {
		assertEquals(2, person.getBookingTime());
	}
	
	@Test
	public void testDepartureTime() throws PassengerException {
		assertEquals(10, person.getDepartureTime());
	}
	
	@Test
	public void testNewState() throws PassengerException {
		assertTrue(person.isNew());
	}
	
	@Test
	public void testConfirmSeatValid() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmSeatLessThanZeroConfirmationTime() throws PassengerException {
		person.confirmSeat(-1, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmSeatConfirmationTimeAfterDepartureTime() throws PassengerException {
		person.confirmSeat(11, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmSeatConfirmationTimeBeforeBookingTime() throws PassengerException {
		person.confirmSeat(1, person.getDepartureTime());
	}
	
	@Test
	public void testCancelSeat() throws PassengerException {
		person.confirmSeat(3, person.getDepartureTime());
		person.cancelSeat(5);
		
		assertTrue(person.isNew());
		assertFalse(person.isConfirmed());
		assertEquals(5, person.getBookingTime());
	}

}
