/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */
// Arshpreet Singh (501030338)
public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
		
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{
		super();
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}
	
	public void assignSeat(Passenger p, String seat)
	{
		p.setSeat(seat);
	}

	/*
	*	Method to reserve first class seat
	* 	@param Passenger object, String seat
	*/
	public void reserveSeat(Passenger p, String seat) throws WrongSeatException, EconomySeatsFullException, SeatOccupiedException {
		//if (seatType.equalsIgnoreCase("FCL"))
		if(getSeatType(seat) == SeatType.FIRSTCLASS)
		{
			if (firstClassPassengers >= aircraft.getNumFirstClassSeats())
			{
				setErrorMessage("No First Class Seats Available");
				//return false;
			}
			//Passenger p = new Passenger(name, passport, "", seatType);
			
			if (manifest.indexOf(p) >=  0)
			{
				setErrorMessage("Duplicate Passenger " + p.getName() + " " + p.getPassport());
				//return false;
			}
			
			assignSeat(p,seat);
			manifest.add(p);
			seatMap.put(seat,p);
			firstClassPassengers++;
			//return true;
		}
		else // economy passenger
			//return super.reserveSeat(p, seat);
			super.reserveSeat(p, seat);

	}

	/*
	*	Method to cancel first class seat
	* 	@param Passenger  object
	*/
	public void cancelSeat(Passenger p) throws PassengerNotInManifestException {
		//if (seatType.equalsIgnoreCase("FCL"))
		if(getSeatType(p.getSeat()) == SeatType.FIRSTCLASS)
		{
			//Passenger p = new Passenger(name, passport);
			if (manifest.indexOf(p) == -1) 
			{
				setErrorMessage("Passenger " + p.getName() + " " + p.getPassport() + " Not Found");
				//return false;
			}
			manifest.remove(p);
			if (firstClassPassengers > 0)	firstClassPassengers--;
			//return true;
		}
		else
			//return super.cancelSeat(name, passport, seatType);
			super.cancelSeat(p);

	}
	
	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}
	
	
	public boolean seatsAvailable(SeatType seatType)
	{
		if (seatType == SeatType.FIRSTCLASS)
			return firstClassPassengers < aircraft.getNumFirstClassSeats();
		else
			return super.seatsAvailable(seatType);
	}

	/*
	*	Method to initialise 2D array seatLayout for long haul flights
	*/
	public String[][] getSeatLayout()
	{
		String[][] seatLayout =  new String[4][35];
		String alphabets = "ABCD";
		for(int i = 0; i < 4; i++)
		{
			int number = 1;
			for(int j = 0; j < 35; j++)
			{
				if(j < 4)
				{
					seatLayout[i][j] = "" + number + alphabets.charAt(i) + "+";
				}
				else
				{
					seatLayout[i][j] = "" + number + alphabets.charAt(i)+ "";
				}
				number++;
			}
		}
		return seatLayout;
	}

	/*
	*	Method to print seat layout of long haul flightss
	*/
	public void printSeats()
	{
		int rows = 4;
		int columns = 35;
		String[][] seatLayout = getSeatLayout();
		String alphabets = "ABCD";
		for (int i = 0; i < aircraft.getRows(); i++) {
			for (int j = 0; j < aircraft.getColumns(); j++) {
				//if(seatMap.containsKey(aircraft.getSeatLayout()[i][j]))
				if (seatMap.containsKey(seatLayout[i][j])) {
					System.out.print("XX ");
				} else {
					System.out.print(seatLayout[i][j] + " ");
				}
			}
			System.out.println("");
		}
	}


	public Type getFlightType()
	{
		return Type.LONGHAUL;
	}
	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}
}
