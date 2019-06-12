import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimpleServer
{
	/*
	*	Creates a server, that makes it open to the client.	
	*/
	public static void main(String [] args) throws Exception
	{
		//Try-catch method
		try
            	{
			//Displays a message saying it is waiting for a client to connect
        		System.out.println("Waiting for client to connect");
			
			//Creates a server and a socket and gets the first free address.
			SimpleServer server = new SimpleServer();
        		ServerSocket socket = new ServerSocket(0);
			System.out.println(socket.getLocalPort());
			Socket client = socket.accept();

			//Creates readers to read the clients message
			InputStreamReader IR = new InputStreamReader(client.getInputStream());
			BufferedReader BR = new BufferedReader(IR);
			
			//Reads the message from the client
			String MESSAGE = BR.readLine();
			System.out.println(MESSAGE);
		
			//If the message is not empty, it runs the process to run back to the client
			if(MESSAGE != null)
			{
				//Creates a prints stream
				PrintStream PS = new PrintStream(client.getOutputStream());

				//Gets the clients address details		    		
				InetAddress IP = client.getInetAddress();
				InetAddress LocalIP = InetAddress.getLocalHost();
				String clientName = LocalIP.getHostName();
	
				//Prints message to the client saying hello and telling it
				//the address details
		        	PS.println("Hello " + LocalIP + " \nYour IP address is " + IP);
		    	}
		}
		//Catches the exception and tells the exception caused.
    		catch(Exception e)
    		{
        		System.err.println(e);
    		}
	}
}
