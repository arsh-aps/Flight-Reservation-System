import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
//Arshpreet Singh (501030338)
/*
 *
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *
 */
public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap = new TreeMap<String, Passenger>();
	
	protected Random random = new Random();
	
	private String errorMessage = "";
	  
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
	}
	
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
	}
	
	public Type getFlightType()
	{
		return type;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	/*
	* This method assigns a seat to a passenger object
	* @param Passenger p, String seat
	 */
	public void assignSeat(Passenger p, String seat) throws SeatOccupiedException {
		if(seatMap == null)
		{
			p.setSeat(seat);
		}
		else if(seatMap.containsKey(seat))
		{
			throw new SeatOccupiedException("Selected seat is occupied");
		}
		else
		p.setSeat(seat);
		
	}
	public boolean seatsAvailable(SeatType seatType)
	{
		if (seatType != SeatType.ECONOMY) return false;
		return numPassengers < aircraft.getNumSeats();
	}

	/*
	*	Method to cancel seat, removing seat from seatMap and passenger from manifest
	* 	@param Passenger p
	*/
	public void cancelSeat(Passenger p) throws PassengerNotInManifestException {
		if(manifest.indexOf(p) == -1)
		{
			//errorMessage = "Passenger not found!";
			throw new PassengerNotInManifestException("Passenger not found");
		}
		else
		{
			manifest.remove(p);
			seatMap.remove(p.getSeat());
			if(numPassengers > 0)
			numPassengers--;
		}
	}

	/*
	*	Method to reserve seat for passenger, adding passenger to manifest and seat mapped to passenger in seatMap
	* 	@param Passenger p, String seat
	*/
	public void reserveSeat(Passenger p, String seat) throws WrongSeatException, EconomySeatsFullException, SeatOccupiedException {
		if(seat.charAt(seat.length()-1) == '+')
		{
			throw new WrongSeatException("Flight does not have first class seats");
		}
		else
		{
			if(seatsAvailable(SeatType.ECONOMY))
			{
				assignSeat(p,seat);
				manifest.add(p);
				seatMap.put(seat,p);
				numPassengers++;
			}
			else
			{
				//errorMessage = "No economy seats available!";
				throw new EconomySeatsFullException("No economy seats available");
			}
		}
	}
	
	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}
	public void printPassengerManifest()
	{
		for(int i = 0; i < manifest.size(); i++)
		{
			System.out.println(manifest.get(i).toString());
		}
	}

	/*
	*	Method to print seatLayout for a flight
	* 	XX --> seat occupied
	* 	+  --> first class seat
	*/
	public void printSeats()
	{
		if(seatMap == null)
		{
			aircraft.printSeatLayout();
		}
		else {
			String[][] sLayout = aircraft.getSeatLayout();
			for (int i = 0; i < aircraft.getRows(); i++) {
				for (int j = 0; j < aircraft.getColumns(); j++) {
					//if(seatMap.containsKey(aircraft.getSeatLayout()[i][j]))
					if (seatMap.containsKey(sLayout[i][j])) {
						System.out.print("XX ");
					} else {
						System.out.print(sLayout[i][j] + " ");
					}
				}
				System.out.println("");
			}
		}
	}
	public SeatType getSeatType(String seat)
	{
		if(seat.charAt(seat.length()-1) == '+')
		return SeatType.FIRSTCLASS;
		else
		return SeatType.ECONOMY;
	}
}



