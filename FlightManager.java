// Arshpreet Singh (501030338)
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;


public class FlightManager
{
  ArrayList<Flight> flights = new ArrayList<Flight>();
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();
  Random random = new Random();

  /*
  *	Default constructor
  * Adds aircrafts to airplanes ArrayList
  * Reads flights from "flights.txt" file and adds to flights ArrayList
  */
  public FlightManager() throws FileNotFoundException {

	  airplanes.add(new Aircraft(4, "Bombardier 5000"));
	  airplanes.add(new Aircraft(16, "Dash-8 100"));
	  airplanes.add(new Aircraft(40, "Boeing 737"));
	  airplanes.add(new Aircraft(80,"Airbus 320"));
	  airplanes.add(new Aircraft(124, 16, "Boeing 747"));

	  File file = new File("flights.txt");
	  Scanner fin = new Scanner(file);
	  while(fin.hasNextLine())
	  {
		String line = fin.nextLine();
		Scanner word = new Scanner(line);
		String flightName = word.next().replace("_"," ");
		String dest = word.next().replace("_"," ");
		String depart = word.next();
		int capacity = word.nextInt();
		String flightNum = generateFlightNumber(flightName);
		if(capacity < 100)
		{
			Flight flight = new Flight(flightNum, flightName, dest, depart, getFlightTime(dest), airplanes.get(getAircraft(capacity)));
			flights.add(flight);
		}
		else
		{
			Flight flight = new LongHaulFlight(flightNum, flightName, dest, depart, getFlightTime(dest), airplanes.get(getAircraft(capacity)));
			flights.add(flight);
		}
	  }
	  fin.close();

  }

  /*
  *	Method to return optimal aircraft according to number of seats required
  */
  public int getAircraft(int capacity)
  {
	if(capacity <= 4)
	return 0;
	else if(capacity > 4 && capacity <= 16)
	return 1;
	else if(capacity > 16 && capacity <= 40)
	return 2;
	else if(capacity > 40 && capacity <= 80)
	return 3;
	else return 4;
  }
  /*
  *	Method to return duration of flight based on destination
  * @param String dest
  */
  public int getFlightTime(String dest)
  {
	if(dest.equals("Dallas"))
		return 3;
	else if(dest.equals("New York"))
		return 1;
	else if(dest.equals("London"))
		return 7;
	else if(dest.equals("Paris"))
		return 8;
	else if(dest.equals("Tokyo"))
		return 16;
	return 0;
  }

  /*
  *	Method generating random flight number taking first letters of flight name and a random number from 101 - 300
  * @param String airline name
  * @return String flight number
  */
  private String generateFlightNumber(String airline)
  {
  	String word1, word2;
  	Scanner scanner = new Scanner(airline);
  	word1 = scanner.next();
  	word2 = scanner.next();
  	String letter1 = word1.substring(0, 1);
  	String letter2 = word2.substring(0, 1);
  	letter1.toUpperCase(); letter2.toUpperCase();
  	
  	// Generate random number between 101 and 300
  	boolean duplicate = false;
  	int flight = random.nextInt(200) + 101;
  	String flightNum = letter1 + letter2 + flight;
   	return flightNum;
  }

  public void printAllFlights()
  {
  	for (Flight f : flights)
  		System.out.println(f);
  }

  /*
  *	Method to make a reservation
  * @param String flight number, String passenger name, String passport number, String seat
  * @return Reservation object
  */
  public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) throws FlightNotFoundException, WrongSeatException, EconomySeatsFullException, SeatOccupiedException {
  	int index = flights.indexOf(new Flight(flightNum));
  	if (index == -1)
  	{

		throw new FlightNotFoundException("Flight " + flightNum + " not found");
  	}
  	Flight flight = flights.get(index);
  	Passenger p = new Passenger(name,passport);
	flight.reserveSeat(p,seat);
  	return new Reservation(flightNum, flight.toString(), name, passport, seat);

  }

  /*
  *	Method to cancel reservation
  * @param Reservation object
  */
  public void cancelReservation(Reservation res) throws FlightNotFoundException, PassengerNotInManifestException {
    int index = flights.indexOf(new Flight(res.getFlightNum()));
  	if (index == -1)
    {
		  throw new FlightNotFoundException("Flight "+ res.getFlightNum() + " not found");
	}
  	Flight flight = flights.get(index);
	Passenger p = new Passenger(res.passengerName, res.passengerPassport, res.seat);
	flight.cancelSeat(p);

  }

  private class DurationComparator implements Comparator<Flight>
  {
  	public int compare(Flight a, Flight b)
  	{
  	  return a.getFlightDuration() - b.getFlightDuration();
   	}
  }
  
  public void printAllAircraft()
  {
  	for (Aircraft craft : airplanes)
  		craft.print();
   }
  
  public void sortAircraft()
  {
  	Collections.sort(airplanes);
  }
  public void printSeatLayout(String flightNum) throws FlightNotFoundException {
	int index = flights.indexOf(new Flight(flightNum));
	if (index == -1)
	{
		//errMsg = "Flight " + flightNum + " Not Found";
		//return null;
		throw new FlightNotFoundException("Flight " + flightNum + " not found");
	}
	Flight flight = flights.get(index);
	flight.printSeats();
  }

  public void printManifest(String flightNum) throws FlightNotFoundException {
  	int index = flights.indexOf(new  Flight(flightNum));
  	if(index == -1)
	{
		throw new FlightNotFoundException("Flight " + flightNum + " not found");
	}
  	Flight flight = flights.get(index);
  	flight.printPassengerManifest();
  }
}
