/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.Aircraft;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Megan Hunter, Jesse Stanger
 *
 */
public class A380Tests {
	
	A380 plane;
	First passenger;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws Exception {
		this.plane = new A380("A380", 14, 25, 50, 100, 200);
		this.passenger = new First(1, 14);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftEmptyFlightCode() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("", 1, 1, 1, 1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNullFlightCode() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380(null, 1, 1, 1, 1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNegativeDepartureTime() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("A380", -1, 1, 1, 1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftZeroDepartureTime() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("A380", 0, 1, 1, 1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNegativeCapacityFirst() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("A380", 1, -1, 1, 1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNegativeCapacityBusiness() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("A380", 1, 1, -1, 1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNegativeCapacityPremium() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("A380", 1, 1, 1, -1, 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNegativeCapacityEconomy() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("A380", 1, 1, 1, 1, -1);
	}
	
	@Test
	public void testCancelBooking() {
		fail("Not yet implemented");
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingNegativeConfirmationTime() throws AircraftException, PassengerException {
		plane.confirmBooking(passenger, -1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingConfirmationGreaterThanDepartureTime() throws AircraftException, PassengerException {
		plane.confirmBooking(passenger, passenger.getDepartureTime() + 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingExceptionConfirmedPassenger() throws AircraftException, PassengerException {
		fail("Not yet implemented");
		plane.confirmBooking(passenger, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingExceptionRefusedPassenger() throws AircraftException, PassengerException {
		fail("Not yet implemented");
		plane.confirmBooking(passenger, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingExceptionFlownPassenger() throws AircraftException, PassengerException {
		fail("Not yet implemented");
		plane.confirmBooking(passenger, 1);
	}
	
	@Test
	public void testConfirmBookingCorrectlyIncrementsFirst() throws PassengerException, AircraftException {
		int orig = plane.getNumFirst();
		Passenger test = new First(1, 14);
		plane.confirmBooking(test, 2);
		assertEquals(orig + 1, plane.getNumFirst());
	}
	
	@Test
	public void testConfirmBookingCorrectlyIncrementsBusiness() throws PassengerException, AircraftException {
		int orig = plane.getNumBusiness();
		Passenger test = new Business(1, 14);
		plane.confirmBooking(test, 2);
		assertEquals(orig + 1, plane.getNumBusiness());
	}
	
	@Test
	public void testConfirmBookingCorrectlyIncrementsPremium() throws PassengerException, AircraftException {
		int orig = plane.getNumPremium();
		Passenger test = new Premium(1, 14);
		plane.confirmBooking(test, 2);
		assertEquals(orig + 1, plane.getNumPremium());
	}
	
	@Test
	public void testConfirmBookingCorrectlyIncrementsEconomy() throws PassengerException, AircraftException {
		int orig = plane.getNumEconomy();
		Passenger test = new Economy(1, 14);
		plane.confirmBooking(test, 2);
		assertEquals(orig + 1, plane.getNumEconomy());
	}
	
	@Test
	public void testFinalState() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFlightEmpty() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFlightFull() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFlyPassengers() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetPassengersDeepCopy() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testHasPassenger() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSeatsAvailable() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpgradeBookings() {
		fail("Not yet implemented");
	}
	

}
