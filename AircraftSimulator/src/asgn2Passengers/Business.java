/**
 *
 */
package asgn2Passengers;

/**
 * @author hogan
 *
 */
public class Business extends Passenger {

	/**
	 * Business Constructor (Partially Supplied)
	 * Passenger is created in New state, later given a Confirmed Business Class reservation,
	 * Queued, or Refused booking if waiting list is full.
	 *
	 * @param bookingTime <code>int</code> day of the original booking.
	 * @param departureTime <code>int</code> day of the intended flight.
	 * @throws PassengerException if invalid bookingTime or departureTime
	 * @see asgnPassengers.Passenger#Passenger(int,int)
	 */
	public Business(int bookingTime, int departureTime) throws PassengerException {
		super(bookingTime, departureTime);
		if (bookingTime < 0 || departureTime <= 0 || departureTime < bookingTime) {
			throw new PassengerException("Invalid booking");
		} else {
			this.passID = "J:" + this.passID;
		}
	}

	/**
	 * Simple constructor to support {@link asgn2Passengers.Passenger#upgrade()} in other subclasses
	 */
	protected Business() {}

	@Override
	public String noSeatsMsg() {
		return "No seats available in Business";
	}

	@Override
	public Passenger upgrade() {
		Passenger upgraded = new First();
		upgraded.copyPassengerState(this);
		upgraded.passID = "F(U)" + this.passID;

		return upgraded;
	}
}
