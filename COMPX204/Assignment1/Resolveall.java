import java.net.InetAddress;
import java.net.UnknownHostException;
/*
The resolveAll class gets the name of the website from the terminal
and then finds the IP address then displays it to the user.
*/
class Resolveall
{
    /*
    The main method  
    */
      public static void main(String [] args)
    {
	//Gets the number of inputs put in and then finds the IP address for every link in the list.
	for(int i = 0; i < args.length; i++)
	    {
		//Stores the argument in the string address.
		String address = args[i];
		
		//Checks if the website actually exists
		try
		    {
			//Gets the IP's for the name of the website
			InetAddress[] IPArray = InetAddress.getAllByName(address);
			for(int j = 0; j < IPArray.length; j++)
			    {
				
			//Displays the websites host address
			System.out.println(address + " : " + IPArray[j].getHostAddress());
			    }
		    } 
		//Catches any invalid addresses.
		catch(UnknownHostException e)
		    {
			//Prints an error message saying that the address has an unknown host.
			System.err.println(address + " : unknown host"); 
		    }
	    }
    }
}

