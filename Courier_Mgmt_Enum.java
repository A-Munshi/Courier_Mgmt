import java.util.*;

// Enum for transport media
enum TransportMode 
{
	TRAIN, CAR, FLIGHT, SHIP
}
// Custom Exception for invalid transport
class InvalidTransExp extends Exception 
{
	public InvalidTransExp(String msg) 
	{
		super(msg);
	}
}

// Custom Exception for invalid weight
class InvalidWgtExp extends Exception 
{
	public InvalidWgtExp(String msg) 
	{
		super(msg);
	}
}

// Interface for courier operations
interface CourierService 
{
	double calculateCharges();
	void displayDetails();
}

// Courier class implementing interface
class Courier implements CourierService 
{
	private int courierId;
	private String sender;
	private String receiver;
	private double weight;
	private TransportMode mode;
	private double charges;
	
	public Courier(int courierId, String sender, String receiver, double weight, String modeStr) throws InvalidTransExp, InvalidWgtExp 
	{
		this.courierId = courierId;
		this.sender = sender;
		this.receiver = receiver;
		setWeight(weight);
		setTransportMode(modeStr);
		this.charges = calculateCharges();
	}
	private void setTransportMode(String modeStr) throws InvalidTransExp 
	{
		try 
		{
			this.mode = TransportMode.valueOf(modeStr.toUpperCase());
		} 
		catch (IllegalArgumentException e) 
		{
			throw new InvalidTransExp("Transport Mode " + modeStr + " is not available!\n");
		}
	}
	
	private void setWeight(double weight) throws InvalidWgtExp 
	{
		if (weight <= 0)
			throw new InvalidWgtExp("Weight must be greater than 0!\n");
		
		else if (weight > 100) 
			throw new InvalidWgtExp("Weight exceeds maximum limit (100kg)!\n");

		this.weight = weight;
	}
	
	// Charges depend on mode
	@Override
	public double calculateCharges() {
	double rate;
		switch (mode) 
		{
			case TRAIN: rate = 10; break;
			case CAR: rate = 15; break;
			case FLIGHT: rate = 30; break;
			case SHIP: rate = 8; break;
			default: rate = 0;
		}
		return weight * rate;
	}
	
	@Override
	public void displayDetails() 
	{
		System.out.println("Courier ID: " + courierId + "\nSender: " + sender + "\nReceiver: " + receiver);
		System.out.println("Weight: " + weight + "kg" + "\nTransport: " + mode + "\nCharges: Rs. " + charges);
		System.out.println("-----------------------------------");
	}
}
// Main Driver
public class Courier_Mgmt_Enum 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		Courier[] couriers = new Courier[5];
		int count = 0;
		System.out.println("\n====Courier Management System====");
		while(true) 
		{
			try 
			{
				System.out.print("\nEnter Courier ID: ");
				int id = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter Sender Name: ");
				String sender = sc.nextLine();
				System.out.print("Enter Receiver Name: ");
				String receiver = sc.nextLine();
				System.out.print("Enter Weight (kg): ");
				double weight = sc.nextDouble();
				sc.nextLine();
				System.out.print("Enter Transport Mode (TRAIN/CAR/FLIGHT/SHIP): ");
				String mode = sc.nextLine();
				couriers[count] = new Courier(id, sender, receiver, weight, mode);
				count++;
			} 
			catch (InvalidTransExp | InvalidWgtExp e) 
			{
				System.out.println("Error: " + e.getMessage() + "\n");
			} 
			catch (ArrayIndexOutOfBoundsException e) 
			{
				System.out.println("Courier storage is full! Cannot add more.\n");
				break;
			} 
			catch (Exception e) 
			{
				System.out.println("Unexpected Error: " + e + "\n");
			}
			//sc.nextLine();
			System.out.print("Do you want to add another courier? (yes/no): ");
			String choice = sc.nextLine();
			if (!choice.equalsIgnoreCase("yes")) 
				break;
		}
		System.out.println("\n--- Courier Details ---");
		for (int i=0; i<count; i++) 
			couriers[i].displayDetails();
		
		sc.close();
	}
}
