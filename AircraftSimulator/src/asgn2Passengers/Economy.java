/**
 * 
 */
package asgn2Passengers;

/**
 * @author hogan
 *
 */
public class Economy extends Passenger {

	/**
	 * Economy Constructor (Partially Supplied)
	 * Passenger is created in New state, later given a Confirmed Economy Class reservation, 
	 * Queued, or Refused booking if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight.  
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @see asgnPassengers.Passenger#Passenger(int,int)
	 */
	public Economy(int bookingTime,int departureTime) throws PassengerException {
		super(bookingTime, departureTime);
		if (bookingTime < 0 || departureTime <= 0 || departureTime < bookingTime) {
			throw new PassengerException("Invalid booking");
		} else {
			//Stuff here
			this.passID = "Y:" + this.passID;
		}
	}
	
//	/**
//	 * Simple constructor to support {@link asgn2Passengers.Passenger#upgrade()} in other subclasses
//	 */
//	protected Economy(){}
	//Shouldn't this have a no args constructor too? It didnt until now
	
	@Override
	public String noSeatsMsg() {
		return "No seats available in Economy";
	}

	@Override
	public Passenger upgrade() {
		//to premium
		Passenger upgraded = new Premium();
		upgraded.copyPassengerState(this);
		upgraded.passID = "P(U)" + upgraded.passID;
		
		return upgraded;
	}
}
