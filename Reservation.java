//Arshpreet Singh (501030338)
public class Reservation
{
	String flightNum;
	String flightInfo;
	String seat;
	boolean firstClass;
	String passengerName;
	String passengerPassport;
	
	
	public Reservation(String flightNum, String info)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
	}
	
	public Reservation(String flightNum, String name, String passport)
	{
		this.flightNum = flightNum;
		this.passengerName = name;
		this.passengerPassport = passport;
	}

	public Reservation(String flightNum, String info, String name, String passport, String seat)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.passengerName = name;
		this.passengerPassport = passport;
		this.seat = seat;
		if(seat.charAt(seat.length()-1) == '+')
		{
			this.firstClass = true;
		}
		else
		{
			this.firstClass = false;
		}
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}
	
	public boolean equals(Object other)
	{
		Reservation otherRes = (Reservation) other;
		return flightNum.equals(otherRes.flightNum) &&  passengerName.equals(otherRes.passengerName) && passengerPassport.equals(otherRes.passengerPassport); 
	}

	public void print()
	{
		System.out.println(flightInfo + " " + passengerName + " " + seat);
	}
}
