/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Passengers;


/**
 * Passenger is an abstract class specifying the basic state of an airline passenger,  
 * and providing methods to set and access that state. A passenger is created upon booking, 
 * at which point the reservation is confirmed, or the passenger goes on to a waiting list. 
 * If the waiting list is full, then the passenger is either bumped to the next available 
 * service or lost altogether from the system. A passenger lost from the system is recorded 
 * and their fare level used in the calculation of lost revenue.<br><br>
 * 
 * Passengers have the following states and permitted transitions:<br>
 * State: New - on creation; immediately transition to {Confirmed,Queued,Refused}
 * <ul>
 * <li>{@link #queuePassenger(int, int)}  | New -> Queued</li>
 * <li>{@link #confirmSeat(int,int)} | New -> Confirmed</li>
 * <li>{@link #refusePassenger(int)} | New -> Refused</li> 
 * </ul>
 * <br>
 * State: Queued - on central waiting list to see if seat available. 
 * Transitions to {Confirmed,Refused}
 * <ul>
 * <li>{@link #confirmSeat(int,int)} | Queued -> Confirmed; up until departureTime</li>
 * <li>{@link #refusePassenger(int)} | Queued -> Refused; finalised on departureTime</li> 
 * </ul>
 * <br>
 * State: Confirmed - seat confirmed on requested flight. Transitions to {New,Flown}
 * <ul>
 * <li>{@link #cancelSeat(int)}   | Confirmed -> New; up until departureTime</li>
 * <li>{@link #flyPassenger(int)} | Confirmed -> Flown; finalised on departureTime</li> 
 * </ul>
 * <br>
 * State: Refused - final state - no transitions permitted<br> 
 * State: Flown - final state - no transitions permitted<br>
 * <br>
 * {@link asgn2Passengers.PassengerException} is thrown if the state is inappropriate: see method javadoc for details. 
 * The method javadoc also indicates the constraints on the time and other parameters.<br><br>
 * 
 * @author hogan
 *
 */
public abstract class Passenger {

	private static int index = 0;
	protected String passID;
	protected boolean newState; 
	protected boolean confirmed;
	protected boolean inQueue;
	protected boolean flown;
	protected boolean refused;
	protected int bookingTime;
	protected int enterQueueTime;
	protected int exitQueueTime;
	protected int confirmationTime;
	protected int departureTime; 
	
	
	/**
	 * Passenger Constructor 
	 * On creation, Passenger is in a New state. Subsequent processing transitions to a 
	 * confirmed reservation, queued, or the booking is refused if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight. 
	 * @throws PassengerException if (bookingTime < 0) OR (departureTime <= 0) 
	 * OR (departureTime < bookingTime) 
	 */
	public Passenger(int bookingTime, int departureTime) throws PassengerException  {
		if (bookingTime < 0 || departureTime <= 0 || departureTime < bookingTime) {
			throw new PassengerException("Invalid booking");
		} else {
			this.bookingTime = bookingTime;
			this.departureTime = departureTime;
		}
		
		//Stuff here 
		this.passID = "" + Passenger.index; 
		Passenger.index++; 
		//Stuff here 
		
		newState = true;
	}
	
	/**
	 * Simple no-argument constructor to support {@link #upgrade()}
	 */
	protected Passenger() {

	}
	
	/**
	 * Transition passenger to New<br>
	 * PRE: isConfirmed(this)<br>
	 * POST: isNew(this) AND this.getBookingTime() == cancellationTime<br>
	 * <ul>
	 * <li>cancelSeat: Confirmed -> New; up until departureTime</li>
	 * </ul>
	 * 
	 * Cancellation returns booking to New state; Booking time is adjusted to this 
	 * cancellation time and Passenger is processed as a new booking. Cannot guarantee 
	 * either a new confirmed seat or a place in the queue. DepartureTime remains the same 
	 * initially, adjusted by subsequent method calls. 
	 *  
	 * @param cancellationTime <code>int</code> holding cancellation time 
	 * @throws PassengerException if isNew(this) OR isQueued(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (cancellationTime < 0) OR (departureTime < cancellationTime)
	 */
	public void cancelSeat(int cancellationTime) throws PassengerException {
		if (isNew() || isQueued() || isRefused() || isFlown()) {
			throw new PassengerException("Flight not confirmed and cannot be cancelled");
		} else if (cancellationTime < 0 || departureTime < cancellationTime) {
			throw new PassengerException("Invalid cancellation time");
		} else if (isConfirmed()){
			
			bookingTime = cancellationTime;
			newState = true;
			confirmed = false;
		}		
	}

	/**
	 * Transition passenger to Confirmed seat<br>
	 * PRE: isNew(this) OR inQueue(this)<br>
	 * POST: isConfirmed(this) AND this.getConfirmationTime() == confirmationTime AND <br>
	 * 	     this.getDepartureTime() == departureTime<br>
	 * <ul>
	 * <li>confirmSeat: New -> Confirmed</li>
	 * <li>confirmSeat: Queued -> Confirmed; up until departureTime</li> 
	 * <li>if isQueued(this), then POST: this.getExitQueueTime() == confirmationTime</li>
	 * </ul>
	 * 
	 * @param confirmationTime <code>int</code> day when seat is confirmed
	 * @param departureTime <code>int</code> day flight is scheduled to depart 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * 		   OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	public void confirmSeat(int confirmationTime, int departureTime) throws PassengerException {
		if (isConfirmed() || isRefused() || isFlown()) {
			throw new PassengerException("Not currently in queue or a new booking");
		} else if (confirmationTime < 0 || departureTime < confirmationTime) {
			throw new PassengerException("Invalid confirmation time");
		} else {
			if (isNew()) {
				newState = false;
			} else if (isQueued()) {
				inQueue = false;
				exitQueueTime = confirmationTime;
			}
			
			this.confirmationTime = confirmationTime;
			this.departureTime = departureTime;
			confirmed = true;
			
		}
	}

	/**
	 * Transition passenger to Flown<br>
	 * PRE: isConfirmed(this)<br>
	 * POST: isFlown(this) AND this.getDepartureTime() == departureTime<br> 
	 * <ul>
	 * <li>flyPassenger:Confirmed -> Flown; finalised on departureTime</li>
	 * </ul>
	 *  
	 * @param departureTime <code>int</code> holding actual departure time 
	 * @throws PassengerException if isNew(this) OR isQueued(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (departureTime <= 0)
	 */
	public void flyPassenger(int departureTime) throws PassengerException {
		if (isNew() || isQueued() || isRefused() || isFlown()) {
			throw new PassengerException("Passenger not yet confirmed");
		} else if (departureTime <= 0) {
			throw new PassengerException("Invalid departure time");
		} else if (isConfirmed()){
			
			flown = true;
			this.departureTime = departureTime;
			confirmed = false;
			
		}
	}

	/**
	 * Simple getter for the booking time 
	 * 
	 * @return the bookingTime
	 */
	public int getBookingTime() {
		return bookingTime;
	}

	/**
	 * Simple getter for the confirmation time
	 * Note: result may be 0 prior to confirmation 
	 * 
	 * @return the confirmationTime
	 */
	public int getConfirmationTime() {
		return confirmationTime;
	}

	/**
	 * Simple getter for the departureTime
	 * 
	 * @return the departureTime
	 */
	public int getDepartureTime() {
		return departureTime;
	}
	
	/**
	 * Simple getter for the queue entry time
	 * 
	 * @return the enterQueueTime
	 */
	public int getEnterQueueTime() {
		return enterQueueTime;
	}

	/**
	 * Simple getter for the queue exit time
	 * 
	 * @return the exitQueueTime
	 */
	public int getExitQueueTime() {
		return exitQueueTime;
	}

	/**
	 * Simple getter for the Passenger ID
	 * 
	 * @return the passID
	 */
	public String getPassID() {
		return passID;
	}

	/**
	 * Boolean status indicating whether Passenger has a Confirmed seat
	 * 
	 * @return <code>boolean</code> true if Confirmed state; false otherwise 
	 */
	public boolean isConfirmed() {
		return confirmed;
	}
		
	/**
	 * Boolean status indicating whether Passenger has flown
	 * 
	 * @return <code>boolean</code> true if Flown state; false otherwise 
	 */
	public boolean isFlown() {
		return flown;
	}
	
	/**
	 * Boolean status indicating whether Passenger is New
	 * 
	 * @return <code>boolean</code> true if New state; false otherwise 
	 */
	public boolean isNew() {
		return newState;
	}

	/**
	 * Boolean status indicating whether Passenger is currently Queued
	 * 
	 * @return <code>boolean</code> true if Queued state; false otherwise 
	 */
	public boolean isQueued() {
		return inQueue;
	}
	
	/**
	 * Boolean status indicating whether Passenger is Refused
	 * 
	 * @return <code>boolean</code> true if Refused state; false otherwise 
	 */
	public boolean isRefused() {
		return refused;
	}
	
	/**
	 * Abstract helper method to allow specialised "no seats" message (Supplied) 
	 * 
	 * @return <code>String</code> containing "no seats available" warning message
	 */
	public abstract String noSeatsMsg();
	
	/**
	 * Transition passenger to Queued<br>
	 * PRE: isNew(this)<br>
	 * POST: isQueued(this) AND this.getEnterQueueTime() == queueTime AND <br>
	 * this.getDepartureTime == departureTime<br>
	 * <ul>
	 * <li>queuePassenger: New -> Queued</li>
	 * </ul>
	 * 
	 * Queue on booking if seat is not available. Intended departureTime may be set here if 
	 * required. Queuing ceases on confirmation or when the departure time is reached. 
	 * 
	 * @param queueTime <code>int</code> holding queue entry time 
	 * @param departureTime <code>int</code> holding intended departure time 
	 * @throws PassengerException if isQueued(this) OR isConfirmed(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (queueTime < 0) OR (departureTime < queueTime)
	 */
	public void queuePassenger(int queueTime, int departureTime) throws PassengerException {
		if (isQueued() || isConfirmed() || isRefused() || isFlown()) {
			throw new PassengerException("Passenger is not new");
		} else if (queueTime < 0 || departureTime < queueTime) {
			throw new PassengerException("Invalid queue time");
		} else if (isNew()) {
			
			inQueue = true;
			newState = false;
			enterQueueTime = queueTime;
			this.departureTime = departureTime;
			
		}
	}
	
	/**
	 * Transition passenger to Refused<br>
	 * PRE: isNew(this) OR isQueued(this) <br>
	 * POST: isRefused(this)
	 * <ul>
	 * <li>refusePassenger:New -> Refused</li> 
     * <li>refusePassenger:Queued -> Refused; finalised on departureTime</li> 
     * <li>if isQueued(this), then POST: this.getExitQueueTime() == refusalTime</li>
     * </ul>
     * 
	 * @param refusalTime <code>int</code> holding refusal time  
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this) 
	 * 			OR (refusalTime < 0) OR (refusalTime < bookingTime)
	 */
	public void refusePassenger(int refusalTime) throws PassengerException {
		if (isConfirmed() || isRefused() || isFlown()) {
			throw new PassengerException("Passenger not new or in the queue");
		} else if (refusalTime < 0 || refusalTime < bookingTime) {
			throw new PassengerException("Invalid refusal time");
		} else {
			if (isNew()) {
				newState = false;
			} else if (isQueued()) {
				inQueue = false;
				exitQueueTime = refusalTime;
			}

			refused = true;		
			
		}
	}
	
	/* (non-Javadoc) (Supplied) 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "passID: " + passID + "\nBT: " + this.bookingTime; 
		//Queuing Information - duration may not be accurate if multiple queuing events 
		if (this.wasQueued()) {
			str += "\nExitQ:" + this.exitQueueTime; 
			int queueTime = (this.exitQueueTime - this.bookingTime);
			str += " QT: " + queueTime; 
		} else {
			str += "\nNotQ";
		}
		
		if (this.isFlown()) {
			str+= "\nConfT: " + this.confirmationTime; 
			str+= " Flew: " + this.departureTime; 
		} else if (this.wasConfirmed()) {
			str+= "\nConfT: " + this.confirmationTime;
			str+= " NotFlown";
		}	
		return str;
	}

	/**
	 * Method to create new Passenger instance based on upgrade to a higher fare class.
	 * Transition rules are specific to each fare class
	 * 
	 * @return <code>Passenger</code> of the upgraded fare class 
	 */
	public abstract Passenger upgrade();

	/**
	 * Boolean status indicating whether passenger was ever confirmed
	 * 
	 * @return <code>boolean</code> true if was Confirmed state; false otherwise
	 */
	public boolean wasConfirmed() {
		if (confirmationTime != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Boolean status indicating whether passenger was ever queued
	 * 
	 * @return <code>boolean</code> true if was Queued state; false otherwise
	 */
	public boolean wasQueued() {
		if (enterQueueTime != 0 || exitQueueTime != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Helper method to copy state to facilitate {@link #upgrade()}
	 * 
	 * @param <code>Passenger</code> state to transfer
	 */
	protected void copyPassengerState(Passenger p) {
		
	}
	
	//Various private helper methods to check arguments and throw exceptions

}
