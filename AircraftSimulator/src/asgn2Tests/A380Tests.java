/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.Aircraft;
import asgn2Aircraft.AircraftException;

import asgn2Passengers.First;
import asgn2Passengers.PassengerException;

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
		assertTrue(/*contains passenger*/);
		plane.cancelBooking(passenger, 10);
		assertFalse(/*no longer contains passenger*/);
		
	}
	/*
	 * 
	 * */
	
	@Test
	public void testConfirmBooking() {
		fail("Not yet implemented");
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
