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

	First person;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws PassengerException {
		this.person = new First(2,10);
	}

	@Test (expected = PassengerException.class)
	public void testNewPassengerBookingTimeLessThenZero() throws PassengerException {
		First p = new First(-1, 10);
	}
	
	@Test (expected = PassengerException.class)
	public void testNewPassengerDepartureTimeLessThanOrEqualZero() throws PassengerException {
		First p = new First(0, 0);
	}
	
	@Test (expected = PassengerException.class)
	public void testNewPassengerDepartureTimeLessThanBookingTime() throws PassengerException {
		First p = new First(5, 2);
	}
	
	@Test
	public void testBookingTime() {
		assertEquals(2, person.getBookingTime());
	}
	
	@Test
	public void testDepartureTime() {
		assertEquals(10, person.getDepartureTime());
	}
	
	@Test
	public void testNewState() {
		assertTrue(person.isNew());
	}
	
	@Test 
	public void firstClassUpgrade() {
		Passenger upgraded = person.upgrade();
		assertEquals(2, upgraded.getBookingTime());
		assertEquals(10, upgraded.getDepartureTime());
	}
	
	@Test
	public void testCancelSeatValid() throws PassengerException {
		person.confirmSeat(3, person.getDepartureTime());
		person.cancelSeat(5);
		
		assertTrue(person.isNew());
		assertFalse(person.isConfirmed());
		assertEquals(5, person.getBookingTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatPassengerIsNew() throws PassengerException {
		person.cancelSeat(5);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatPassengerQueued() throws PassengerException {
		person.queuePassenger(3, 10);
		person.cancelSeat(5);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatPassengerRefused() throws PassengerException {
		person.refusePassenger(3);
		person.cancelSeat(5);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatPassengerFlown() throws PassengerException {
		person.flyPassenger(10);
		person.cancelSeat(5);
	} 
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatTimeLessThanZero() throws PassengerException {
		person.cancelSeat(-1);
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatTimeGreaterThanDeparture() throws PassengerException {
		person.cancelSeat(11);
	}
	
	//Cancellation time for boundaries? 0 and 10?
	
	@Test
	public void testConfirmSeatValid() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		assertEquals(5, person.getConfirmationTime());
		assertTrue(person.isConfirmed());
	}
	
	@Test
	public void testConfirmSeatPassengerFromQueue() throws PassengerException {
		person.queuePassenger(3, person.getDepartureTime());
		person.confirmSeat(5, person.getDepartureTime());
		assertEquals(5, person.getExitQueueTime());
		assertFalse(person.isQueued());
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
	
	@Test (expected = PassengerException.class)
	public void testConfirmSeatPassengerAlreadyConfirmed() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.confirmSeat(2, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmSeatPassengerRefused() throws PassengerException {
		person.refusePassenger(3);
		person.confirmSeat(5, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmSeatPassengerFlown() throws PassengerException {
		person.flyPassenger(4);
		person.confirmSeat(5, person.getDepartureTime());
	}
		
	@Test
	public void testFlyPassengerValid() throws PassengerException {
		person.confirmSeat(3, person.getDepartureTime());
		person.flyPassenger(person.getDepartureTime());
		assertTrue(person.isFlown());
		assertEquals(10, person.getDepartureTime());
		assertFalse(person.isConfirmed());
	}
	
	@Test
	public void testFlyPassengerValidEarlierDeparture() throws PassengerException {
		person.confirmSeat(3, person.getDepartureTime());
		person.flyPassenger(8);
		assertTrue(person.isFlown());
		assertEquals(8, person.getDepartureTime());
		assertFalse(person.isConfirmed());
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengerIsNew() throws PassengerException {
		person.flyPassenger(person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengerIsQueued() throws PassengerException {
		person.queuePassenger(3, person.getDepartureTime());
		person.flyPassenger(person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengerIsRefused() throws PassengerException {
		person.refusePassenger(3);
		person.flyPassenger(person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengerIsFlown() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.flyPassenger(person.getDepartureTime());
		person.flyPassenger(person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengerDepartureTimeLessThanZero() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.flyPassenger(-1);
	}
	
	//FlyPassenger departure time = 0?
	
	@Test
	public void testQueuePassengerValid() throws PassengerException {
		person.queuePassenger(3, person.getDepartureTime());
		assertEquals(3, person.getEnterQueueTime());
		assertTrue(person.isQueued());
		assertFalse(person.isNew());
	}
	
	@Test (expected = PassengerException.class)
	public void testQueuePassengerIsQueued() throws PassengerException {
		person.queuePassenger(3, person.getDepartureTime());
		person.queuePassenger(5, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testQueuePassengerIsConfirmed() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.queuePassenger(6, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testQueuePassengerIsRefused() throws PassengerException {
		person.refusePassenger(5);
		person.queuePassenger(6, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testQueuePassengerIsFlown() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.flyPassenger(6);
		person.queuePassenger(7, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testQueuePassengerQueueTimeLessThanZero() throws PassengerException {
		person.queuePassenger(-1, person.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testQueuePassengerQueueTimeGreaterThanDeparture() throws PassengerException {
		person.queuePassenger(11, person.getDepartureTime());
	}

	@Test
	public void testRefusePassengerValid() throws PassengerException {
		person.refusePassenger(3);
		assertTrue(person.isRefused());
		assertFalse(person.isNew());
	}
	
	@Test
	public void testRefusePassengerValidInQueue() throws PassengerException {
		person.queuePassenger(5, person.getDepartureTime());
		person.refusePassenger(6);
		assertEquals(6, person.getExitQueueTime());
		assertFalse(person.isQueued());
		assertTrue(person.isRefused());
	}
	
	@Test (expected = PassengerException.class)
	public void testRefusePassengerIsConfirmed() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.refusePassenger(6);
	}
	
	@Test (expected = PassengerException.class)
	public void testRefusePassengerIsRefused() throws PassengerException {
		person.refusePassenger(5);
		person.refusePassenger(6);
	}
	
	@Test (expected = PassengerException.class)
	public void testRefusePassengerIsFlown() throws PassengerException {
		person.confirmSeat(5, person.getDepartureTime());
		person.flyPassenger(6);
		person.refusePassenger(7);
	}
	
	@Test (expected = PassengerException.class)
	public void testRefusePassengerRefusalTimeLessThanZero() throws PassengerException {
		person.refusePassenger(-1);
	}
	
	@Test (expected = PassengerException.class)
	public void testRefusePassengerRefusalTimeLessThanBookingTime() throws PassengerException {
		person.refusePassenger(1);
	}
}
