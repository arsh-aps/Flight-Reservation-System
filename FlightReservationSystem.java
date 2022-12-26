import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
	Arshpreet Singh (501030338)
*	Main class of the system.
* 	Scanner to read user input
*	Call different functions according to input
*	5 main functions (LIST, RES, CANCEL, MYRES, PASMAN)
*/



public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		FlightManager manager = null;
		try {
			manager = new FlightManager();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


		Scanner scanner = new Scanner(System.in);
		System.out.print(">");


		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES"))
			{
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seat = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					seat = commandLine.next();
					try
					{
						Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);
						if (res != null)
						{
							myReservations.add(res);
							res.print();
						}
					}

					catch(FlightNotFoundException | WrongSeatException | EconomySeatsFullException | SeatOccupiedException e)
					{
						System.out.println(e.getMessage());
					}
				}
			}
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				Reservation res = null;
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seatType = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
					try
					{
						int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
						if (index >= 0)
						{
							manager.cancelReservation(myReservations.get(index));
							myReservations.remove(index);
						}
					}
					catch(FlightNotFoundException | PassengerNotInManifestException e)
					{
						System.out.println(e.getMessage());
					}

				}
			}

			else if(action.equalsIgnoreCase("SEATS"))
			{
				String  flightNum = "";
				if(commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				try
				{
					manager.printSeatLayout(flightNum);
				}
				catch(FlightNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)
					myres.print();
			}

			else if (action.equalsIgnoreCase("CRAFT"))
			{
				manager.printAllAircraft();
			}
			else if (action.equalsIgnoreCase("SORTCRAFT"))
			{
				manager.sortAircraft();
			}
			else if (action.equalsIgnoreCase("PASMAN"))
			{
				String flightNum = "";
				if(commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				try
				{
					manager.printManifest(flightNum);
				}
				catch(FlightNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			}
			System.out.print("\n>");
		}
	}


}

