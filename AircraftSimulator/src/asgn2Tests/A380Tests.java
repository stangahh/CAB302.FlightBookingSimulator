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

/**
 * @author Megan Hunter, Jesse Stanger
 *
 */
public class A380Tests extends A380 {
	
	Aircraft plane;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws Exception {
		this.plane = new A380("A380", 14, 25, 50, 100, 200);

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
	public void testAircraftEmptyFlightCodeDefault() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380("", 1);
	}
	
	@Test (expected = AircraftException.class)
	public void testAircraftNullFlightCodeDefault() throws AircraftException {
		@SuppressWarnings("unused")
		Aircraft nullPlane = new A380(null, 1);
	}

	
	@Test
	public void testAircraftnNegativeDepartureTime() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAircraftNegativeCapacityFirst() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAircraftNegativeCapacityBusiness() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAircraftNegativeCapacityPremium() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAircraftNegativeCapacityEconomy() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCancelBooking() {
		fail("Not yet implemented");
	}
	
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
