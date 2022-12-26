//Arshpreet Singh (501030338)
/*
 *
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of first class seats
 * as well as a 2D array seatLayout.
 */
public class Aircraft implements Comparable<Aircraft>
{
  	private int numEconomySeats;
  	private int numFirstClassSeats;
  	private String model;
  	private int rows;
  	private int columns;
  	private String[][] seatLayout;
  
  	public Aircraft(int seats, String model)
  	{
  		this.numEconomySeats = seats;
  		this.numFirstClassSeats = 0;
  		this.model = model;
		setSeatLayout();
  	}

  	public Aircraft(int economy, int firstClass, String model)
  	{
  		this.numEconomySeats = economy;
  		this.numFirstClassSeats = firstClass;
  		this.model = model;
		setSeatLayout();

 	}

	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

	public int getRows()
	{
		return rows;
	}

	public int getColumns()
	{
		return columns;
	}

	/*
	*	Method to initialise 2D array seatLayout
	*/
	public void setSeatLayout()
	{
		this.rows = 4;
		this.columns = (numEconomySeats + numFirstClassSeats)/4;
		this.seatLayout = new String[rows][columns];
		String alphabets = "ABCD";
		for(int i = 0; i < rows; i++)
		{
			int columnNumber = 1;
			for(int j = 0; j < columns; j++)
			{
				seatLayout[i][j] = "" + columnNumber + alphabets.charAt(i) + "";
				columnNumber++;
			}
		} 
	}
	public String[][] getSeatLayout()
	{
		return seatLayout;
	}

  	public int compareTo(Aircraft other)
  	{
  		if (this.numEconomySeats == other.numEconomySeats)
  			return this.numFirstClassSeats - other.numFirstClassSeats;
  	
  		return this.numEconomySeats - other.numEconomySeats; 
  	}

  	public void printSeatLayout()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
					System.out.print(seatLayout[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
