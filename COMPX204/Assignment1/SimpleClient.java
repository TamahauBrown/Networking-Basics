import java.io.*;
import java.net.*;

public class SimpleClient
{
	/*
	* The main method, it runs the client, checks for the server by getting an argument
	* and then returns the message from the server.
	*/
	public static void main(String [] args) throws Exception
	{
		//Creates a new client and get the arguments of it's InetAddress
		SimpleClient client = new SimpleClient();
		InetAddress IP = InetAddress.getByName(args[0]);
		
		//Takes the argument for the socket to connect to 
		Socket socket = new Socket(IP, Integer.parseInt(args[1]));
		PrintStream PS = new PrintStream(socket.getOutputStream());
		
		//Prints the clients message
		PS.println("Hello to SERVER from CLIENT.");
		
		//Reads the message from the server
		InputStreamReader IR = new InputStreamReader(socket.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		
		//Reads each line of the servers message and then displays it on the clients message.
		for(int i = 0; i < 2; i++)
		{
		String message = BR.readLine();

		//Display message
		System.out.println(message);
		}
	}
}

