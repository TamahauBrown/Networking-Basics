import java.net.InetAddress;
import java.net.UnknownHostException;

class Reverse
{
    /*
    The main method  
    */
      public static void main(String [] args)
    {
	//Gets the number of inputs put in and then finds the IP address for every link in the list.
	for(int i = 0; i < args.length; i++)
	    {
		//Stores the argument in the IP address.
		String IP = args[i];
		
		//Checks if the website actually exists
		try
		    {
			//Gets the address for the IP address
			InetAddress address = InetAddress.getByName(IP);

			//Displays the IP address along with it's hosts name
			System.out.println(IP + " : " + address.getHostName()); 
		    } 
		//Catches any invalid addresses.
		catch(UnknownHostException e)
		    {
			//Prints an error message saying that the address has an unknown host.
			System.err.println(IP + " : unknown host"); 
		    }
	    }
    }
}
