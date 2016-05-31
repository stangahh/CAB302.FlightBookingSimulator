/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Aircraft;


import java.util.ArrayList;
import java.util.List;

import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;
import asgn2Simulators.Log;

/**
 * The <code>Aircraft</code> class provides facilities for modelling a commercial jet 
 * aircraft with multiple travel classes. New aircraft types are created by explicitly 
 * extending this class and providing the necessary configuration information. 
 * 
 * In particular, <code>Aircraft</code> maintains a collection of currently booked passengers, 
 * those with a Confirmed seat on the flight. Queueing and Refused bookings are handled by the 
 * main {@link asgn2Simulators.Simulator} class. 
 *   
 * The class maintains a variety of constraints on passengers, bookings and movement 
 * between travel classes, and relies heavily on the asgn2Passengers hierarchy. Reports are 
 * also provided for logging and graphical display. 
 * 
 * @author hogan
 *
 */
public abstract class Aircraft {

	protected int firstCapacity;
	protected int businessCapacity;
	protected int premiumCapacity;
	protected int economyCapacity;
	protected int capacity;
		
	protected int numFirst;
	protected int numBusiness;
	protected int numPremium; 
	protected int numEconomy; 

	protected String flightCode;
	protected String type; 
	protected int departureTime; 
	protected String status;
	protected List<Passenger> seats;

	/**
	 * Constructor sets flight info and the basic size parameters. 
	 * 
	 * @param flightCode <code>String</code> containing flight ID 
	 * @param departureTime <code>int</code> scheduled departure time
	 * @param first <code>int</code> capacity of First Class 
	 * @param business <code>int</code> capacity of Business Class 
	 * @param premium <code>int</code> capacity of Premium Economy Class 
	 * @param economy <code>int</code> capacity of Economy Class 
	 * @throws AircraftException if isNull(flightCode) OR (departureTime <=0) OR ({first,business,premium,economy} <0)
	 */
	public Aircraft(String flightCode,int departureTime, int first, int business, int premium, int economy) throws AircraftException {
		if (isNull(flightCode) || departureTime <= 0 || capacityLessThanZero(first, business, premium, economy)) {
			throw new AircraftException("Cannot have Aircraft with null or negative items.");
		} else {
			this.flightCode = flightCode;
			this.departureTime = departureTime;
			this.firstCapacity = first;
			this.businessCapacity = business;
			this.premiumCapacity = premium;
			this.economyCapacity = economy;
			this.capacity = first + business + premium + economy;
			this.status = "";
			this.seats = new ArrayList<Passenger>();
		}
	}
	
	/**
	 * Method to remove passenger from the aircraft - passenger must have a confirmed 
	 * seat prior to entry to this method.   
	 *
	 * @param p <code>Passenger</code> to be removed from the aircraft 
	 * @param cancellationTime <code>int</code> time operation performed 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. See {@link asgn2Passengers.Passenger#cancelSeat(int)}
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	public void cancelBooking(Passenger p, int cancellationTime) throws AircraftException, PassengerException {
		if (!seats.contains(p)) {
			throw new AircraftException("Passenger not booked");
		} else if (!p.isConfirmed()) {
			throw new PassengerException("Passenger not confirmed");
		} else if (cancellationTime < 0) {
			throw new PassengerException("Cancellation time invalid");
		} else {
			//Update of status string for the aircraft (see below)
			this.status += Log.setPassengerMsg(p,"C","N");
			
		switch (getPassengerFlightClass(p)) {
			case "First":
				numFirst -= 1;
				break;
			case "Business":
				numBusiness -= 1;
				break;
			case "Premium":
				numPremium -= 1;
				break;
			case "Economy":
				numEconomy -= 1;
				break;
		}		
		//Remove passenger from the seat storage for the aircraft
		p.cancelSeat(cancellationTime);
		seats.remove(p);
		}
	}

	/**
	 * Method to add a Passenger to the aircraft seating. 
	 * Precondition is a test that a seat is available in the required fare class
	 * 
	 * @param p <code>Passenger</code> to be added to the aircraft 
	 * @param confirmationTime <code>int</code> time operation performed 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. See {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	public void confirmBooking(Passenger p, int confirmationTime) throws AircraftException, PassengerException { 
		//Change to just calling confirmSeat from the Passenger class???
//		if (confirmationTime < 0 ) { //|| confirmationTime > p.getDepartureTime()) {
//			throw new PassengerException("Invalid times");
//		} else if (p.isConfirmed() || p.isRefused() || p.isFlown()) {
//			throw new PassengerException("Not currently in queue or a new booking");
//		}
		p.confirmSeat(confirmationTime, p.getDepartureTime());
		
		//Somewhat of a clone of cancelBooking (inversed), relies on polymorphism
		switch (getPassengerFlightClass(p)) {
			case "First":
				if (numFirst == firstCapacity) {
					throw new AircraftException(noSeatsAvailableMsg(p));
				} else {
					numFirst += 1;
				}
				break;
			case "Business":
				if (numBusiness == businessCapacity) {
					throw new AircraftException(noSeatsAvailableMsg(p));
				} else {
					numBusiness += 1;
				}
				break;
			case "Premium":
				if (numPremium == premiumCapacity) {
					throw new AircraftException(noSeatsAvailableMsg(p));
				} else {
					numPremium += 1;
				}
				break;
			case "Economy":
				if (numEconomy == economyCapacity) {
					throw new AircraftException(noSeatsAvailableMsg(p));
				} else {
					numEconomy += 1;
				}
				break;
		}
		
		this.status += Log.setPassengerMsg(p,"N/Q","C");
		seats.add(p);
	}
	
	/**
	 * State dump intended for use in logging the final state of the aircraft. (Supplied) 
	 * 
	 * @return <code>String</code> containing dump of final aircraft state 
	 */
	public String finalState() {
		String str = aircraftIDString() + " Pass: " + this.seats.size() + "\n";
		
		for (Passenger p : this.seats) {
			str += p.toString() + "\n";
		}
		
		return str + "\n";
	}
	
	/**
	 * Simple status showing whether aircraft is empty
	 * 
	 * @return <code>boolean</code> true if aircraft empty; false otherwise 
	 */
	public boolean flightEmpty() {		
		return (getNumPassengers() == 0);
	}
	
	/**
	 * Simple status showing whether aircraft is full
	 * 
	 * @return <code>boolean</code> true if aircraft full; false otherwise 
	 */
	public boolean flightFull() {
		return (getNumPassengers() == capacity);
	}
	
	/**
	 * Method to finalise the aircraft seating on departure. 
	 * Effect is to change the state of each passenger to Flown. 
	 * departureTime parameter allows for rescheduling 
	 * 
	 * @param departureTime <code>int</code> actual departureTime from simulation  
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * See {@link asgn2Passengers.Passenger#flyPassenger(int)}. 
	 */
	public void flyPassengers(int departureTime) throws PassengerException { 
		if (departureTime == this.departureTime) {
			
			for (Passenger p : this.seats) {
				p.flyPassenger(departureTime);
			}
			
		} else {
			throw new PassengerException("Invalid Departure Time");
		}
		//going through and changing the state of each of the passengers.
		//call method on the passenger, and make sure the departure time is correct, then log the status again
	}
	
	/**
	 * Method to return an {@link asgn2Aircraft.Bookings} object containing the Confirmed 
	 * booking status for this aircraft. 
	 * 
	 * @return <code>Bookings</code> object containing the status.  
	 */
	public Bookings getBookings() {
		//grabs a single object containing the confirmed booking status for this aircraft
		//not a to string. purely here to enable the use of graphing later on so that we 
		//dont need to pass a string coming back.
		int totalAvailable = getAvailFirst() + getAvailBusiness() + getAvailPremium() + getAvailEconomy();
		return new Bookings(numFirst, numBusiness, numPremium, numEconomy, seats.size(), totalAvailable);
	}
	
	/**
	 * Simple getter for number of confirmed Business Class passengers
	 * 
	 * @return <code>int</code> number of Business Class passengers 
	 */
	public int getNumBusiness() {
		return numBusiness;
	}
	
	
	/**
	 * Simple getter for number of confirmed Economy passengers
	 * 
	 * @return <code>int</code> number of Economy Class passengers 
	 */
	public int getNumEconomy() {
		return numEconomy;
	}

	/**
	 * Simple getter for number of confirmed First Class passengers
	 * 
	 * @return <code>int</code> number of First Class passengers 
	 */
	public int getNumFirst() {
		return numFirst;
	}

	/**
	 * Simple getter for the total number of confirmed passengers 
	 * 
	 * @return <code>int</code> number of Confirmed passengers 
	 */
	public int getNumPassengers() {
		return getNumFirst() + getNumBusiness() + getNumPremium() + getNumEconomy();
	}
	
	/**
	 * Simple getter for number of confirmed Premium Economy passengers
	 * 
	 * @return <code>int</code> number of Premium Economy Class passengers
	 */
	public int getNumPremium() {
		return numPremium;
	}
	
	/**
	 * Method to return an {@link java.util.List} object containing a copy of 
	 * the list of passengers on this aircraft. 
	 * 
	 * @return <code>List<Passenger></code> object containing the passengers.  
	 */
	public List<Passenger> getPassengers() {
		return new ArrayList<Passenger>(seats);
	}
	
	/**
	 * Method used to provide the current status of the aircraft for logging. (Supplied) 
	 * Uses private status <code>String</code>, set whenever a transition occurs. 
	 *  
	 * @return <code>String</code> containing current aircraft state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ this.seats.size() + "::"
		+ "F:" + this.numFirst + "::J:" + this.numBusiness 
		+ "::P:" + this.numPremium + "::Y:" + this.numEconomy; 
		str += this.status;
		this.status="";
		return str+"\n";
	}
	
	/**
	 * Simple boolean to check whether a passenger is included on the aircraft 
	 * 
	 * @param p <code>Passenger</code> whose presence we are checking
	 * @return <code>boolean</code> true if isConfirmed(p); false otherwise 
	 */
	public boolean hasPassenger(Passenger p) {
		return (p.isConfirmed());
	}
	

	/**
	 * State dump intended for logging the aircraft parameters (Supplied) 
	 * 
	 * @return <code>String</code> containing dump of initial aircraft parameters 
	 */ 
	public String initialState() {
		return aircraftIDString() + " Capacity: " + this.capacity 
				+ " [F: " 
				+ this.firstCapacity + " J: " + this.businessCapacity 
				+ " P: " + this.premiumCapacity + " Y: " + this.economyCapacity
				+ "]";
	}
	
	/**
	 * Given a Passenger, method determines whether there are seats available in that 
	 * fare class. 
	 *   
	 * @param p <code>Passenger</code> to be Confirmed
	 * @return <code>boolean</code> true if seats in Class(p); false otherwise
	 */
	public boolean seatsAvailable(Passenger p) {
		switch (getPassengerFlightClass(p)) {
			case "First":
				return (numFirst != firstCapacity);
			case "Business":
				return (numBusiness != businessCapacity);
			case "Premium":
				return (numPremium != premiumCapacity);
			case "Economy":
				return (numEconomy != economyCapacity);
			default:
				return false;
		}
	}

	/* 
	 * (non-Javadoc) (Supplied) 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return aircraftIDString() + " Count: " + this.seats.size() 
				+ " [F: " + numFirst
				+ " J: " + numBusiness 
				+ " P: " + numPremium 
				+ " Y: " + numEconomy 
			    + "]";
	}

	/**
	 * Method to upgrade Passengers to try to fill the aircraft seating. 
	 * Called at departureTime. Works through the aircraft fare classes in 
	 * descending order of status. No upgrades are possible from First, so 
	 * we consider Business passengers (upgrading if there is space in First), 
	 * then Premium, upgrading to fill spaces already available and those created 
	 * by upgrades to First), and then finally, we do the same for Economy, upgrading 
	 * where possible to Premium.  
	 */
	public void upgradeBookings() { 
		//if I upgrade a passenger from business to first, i need to remember that
		//this will create an opening in business.
		
		for (Passenger p : seats) {
			if (canUpgradeToFirst(p)) {
				p.upgrade();
				numFirst += 1;
				numBusiness -= 1;
			} else if (canUpgradeToBusiness(p)) {
				p.upgrade();
				numBusiness += 1;
				numPremium -= 1;
			} else if (canUpgradeToPremium(p)) {
				p.upgrade();
				numPremium += 1;
				numEconomy -= 1;
			}
		}
	}

	
	/**
	 * Simple String method for the Aircraft ID 
	 * 
	 * @return <code>String</code> containing the Aircraft ID 
	 */
	private String aircraftIDString() {
		return this.type + ":" + this.flightCode + ":" + this.departureTime;
	}


	//Various private helper methods to check arguments and throw exceptions, to increment 
	//or decrement counts based on the class of the Passenger, and to get the number of seats 
	//available in a particular class


	//Used in the exception thrown when we can't confirm a passenger 
	/** 
	 * Helper method with error messages for failed bookings
	 * @param p Passenger seeking a confirmed seat
	 * @return msg string failure reason 
	 */
	private String noSeatsAvailableMsg(Passenger p) {
		String msg = "";
		return msg + p.noSeatsMsg(); 
	}
	
	private boolean capacityLessThanZero(int f, int j, int p, int y) {
		return (f < 0 || j < 0 || p < 0 || y < 0);
	}
	
	private boolean isNull(String flightCode) {
		return (flightCode == null || flightCode == "" || flightCode == " ");
	}
	
	private int getAvailFirst() {
		return firstCapacity - getNumFirst();
	}
	
	private int getAvailBusiness() {
		return businessCapacity - getNumBusiness();
	}
	
	private int getAvailPremium() {
		return premiumCapacity - getNumPremium();
	}
	
	private int getAvailEconomy() {
		return economyCapacity - getNumEconomy();
	}
	
	private String getPassengerFlightClass(Passenger p) {
		String pClass = p.getPassID();
		
		if (pClass.contains("F:")) {
			return "First";
		} else if (pClass.contains("J:")) {
			return "Business";
		} else if (pClass.contains("P:")) {
			return "Premium";
		} else if (pClass.contains("Y:")) {
			return "Economy";
		}
		
		return null;
	}
	
	private boolean canUpgradeToFirst(Passenger p) {
		return (getPassengerFlightClass(p) == "Business" && getAvailFirst() > 0);
	}
	
	private boolean canUpgradeToBusiness(Passenger p) {
		return (getPassengerFlightClass(p) == "Premium" && getAvailBusiness() > 0);
	}
	
	private boolean canUpgradeToPremium(Passenger p) {
		return (getPassengerFlightClass(p) == "Economy" && getAvailPremium() > 0);
	}
	
}
