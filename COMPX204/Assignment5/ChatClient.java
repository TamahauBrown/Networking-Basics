import java.io.*;
import java.net.*;
import java.util.*;

class ChatClient
{
	//Main method to run the server
	public static void main(String args[]) throws Exception
 	{               
	 	//Try-catch method
         	try
         	{

	    		//Creates a new MulticastSocket in port 40202 and setting up the address 				//for the group chat
            		MulticastSocket socket = new MulticastSocket(40202);
			int portNum = 40202;
			InetAddress groupAddress = InetAddress.getByName("239.0.202.1");
		
			//Joins the group using the InetAddress	
			socket.joinGroup(groupAddress);

			//Gets the input you type so it is ready to send in a BufferedReader
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			//Spawns a new thread of ChatClient and starts it
			ChatClientWorker ccw = new ChatClientWorker(socket);
			ccw.start();

			System.out.println("Welcome to the Multichat");
			//Keeps looking for other messages sent and displaying them to the user.
			while(true)
			{
				//Reads the line of code 
				String r = reader.readLine();

				//Gets the size of the messgae and stores it in a datagrampacket
				byte [] buff = r.getBytes();
				DatagramPacket dp = new DatagramPacket(buff, buff.length, groupAddress, portNum);

				//Sends the message
				socket.send(dp);
			}
		}
		//Prints the area of the exception
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

// Used to receive datagrampackets and put them in the console
class ChatClientWorker extends Thread
{
	//Creates a multicastsocket variable
	MulticastSocket sock;
	
	//Constructor of the class to get the socket
	public ChatClientWorker(MulticastSocket _sock)
	{
		sock = _sock;
	}
    
    //Receives the message
    public void run()
    {
    //Creates the size of the message
    byte[] byteSize = new byte[1000];
    
    while(true)
    {
        try
        {
	//Creates a datagram packet which gets the message
        DatagramPacket pack = new DatagramPacket(byteSize, byteSize.length);
        sock.receive(pack);
	
	//Creates a string to get the data of the packet and the address and displays it
        String message = new String(pack.getData(), pack.getOffset(), pack.getLength());
	String addressNum = pack.getAddress().getHostAddress();
        System.out.println(addressNum + ": " + message);
        }
	//Prints where the exception occurs
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    }
}
