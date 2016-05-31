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
	public void testCancelBookingValid() throws AircraftException, PassengerException {
		plane.confirmBooking(passenger, 5);
		plane.cancelBooking(passenger, 10);
		assertFalse(passenger.isConfirmed());
		assertTrue(passenger.isNew());
		assertEquals(10, passenger.getBookingTime());
		assertEquals(0, plane.getNumFirst());
		assertEquals(0, plane.getPassengers().size());
	}
	
	@Test (expected = AircraftException.class)
	public void testCancelBookingDoesntContainPassenger() throws AircraftException, PassengerException {
		plane.cancelBooking(passenger, 5);
	}
	
	// DONT THINK THIS CAN BE TESTED?
//	@Test (expected = AircraftException.class)
//	public void testCancelBookingPassengerNotConfirmed() throws AircraftException, PassengerException {
//		plane.cancelBooking(passenger, 5);
//	}
	
	@Test (expected = PassengerException.class)
	public void testCancelBookingCancellationTimeLessThanZero() throws AircraftException, PassengerException {
		plane.confirmBooking(passenger, 5);
		plane.cancelBooking(passenger, -1);
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
		plane.confirmBooking(passenger, 1);
		plane.confirmBooking(passenger, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingExceptionRefusedPassenger() throws AircraftException, PassengerException {
		passenger.refusePassenger(1);
		plane.confirmBooking(passenger, 1);
	}
	
	@Test (expected = PassengerException.class)
	public void testConfirmBookingExceptionFlownPassenger() throws AircraftException, PassengerException {
		passenger.confirmSeat(1, passenger.getDepartureTime());
		passenger.flyPassenger(passenger.getDepartureTime());
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
	public void testFlightEmptyTrue() throws AircraftException {
		A380 emptyFlight = new A380("A380", 14, 10, 10, 10, 10);
		assertTrue(emptyFlight.flightEmpty());
	}
	
	@Test
	public void testFlightEmptyFalse() throws AircraftException, PassengerException {
		plane.confirmBooking(passenger, 1);
		assertFalse(this.plane.flightEmpty());
	}
	
	@Test
	public void testFlightFull() throws AircraftException, PassengerException {
		A380 fullFlight = new A380("A380", 14, 1, 0, 0, 0);
		fullFlight.confirmBooking(this.passenger, 1);
		assertTrue(fullFlight.flightFull());
	}
	
	@Test
	public void testFlyPassengers() throws PassengerException, AircraftException {
		plane.confirmBooking(passenger, 1);
		this.plane.flyPassengers(this.passenger.getDepartureTime());
		assertTrue(this.passenger.isFlown());
	}
	
	@Test (expected = PassengerException.class)
	public void testFlyPassengersWrongDepartureTime() throws PassengerException {
		plane.flyPassengers(3);
	}
	
	@Test
	public void testGetPassengersDeepCopy() throws PassengerException, AircraftException {
		plane.confirmBooking(passenger, 5);
		assertEquals(passenger, plane.getPassengers().get(0));
	}
	
	@Test
	public void testHasPassenger() throws AircraftException, PassengerException {
		plane.confirmBooking(passenger, 1);
		assertTrue(plane.hasPassenger(passenger));
	}
	
	@Test
	public void testSeatsAvailableTrue() {
		assertTrue(plane.seatsAvailable(passenger));
	}
	
	@Test
	public void testSeatsAvailableFalse() throws AircraftException {
		A380 noSeats = new A380("A380", 14, 0, 0, 0, 0);
		assertFalse(noSeats.seatsAvailable(passenger));
	}
	
	@Test
	public void testUpgradeBookingsEconomyToPremium() throws PassengerException, AircraftException {
		Economy e = new Economy(1, 14);
		plane.confirmBooking(e, 1);
		plane.upgradeBookings();
		assertEquals(1, plane.getNumPremium());
	}
	
	@Test
	public void testUpgradeBookingsPremiumToBusiness() throws PassengerException, AircraftException {
		Premium p = new Premium(1, 14);
		plane.confirmBooking(p, 1);
		plane.upgradeBookings();
		assertEquals(1, plane.getNumBusiness());
	}
	
	@Test
	public void testUpgradeBookingsBusinessToFirst() throws PassengerException, AircraftException {
		Business b = new Business(1, 14);
		plane.confirmBooking(b, 1);
		plane.upgradeBookings();
		assertEquals(1, plane.getNumFirst());
	}
}
