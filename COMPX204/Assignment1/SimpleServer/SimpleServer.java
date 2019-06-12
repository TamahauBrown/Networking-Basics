import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimpleServer
{
	public static void main(String [] args) throws Exception
	{
        System.out.println("Waiting for client to connect");
		SimpleServer server = new SimpleServer();
        ServerSocket socket = new ServerSocket(1234);
		Socket sock = socket.accept();
		InputStreamReader IR = new InputStreamReader(sock.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		
		String MESSAGE = BR.readLine();
		System.out.println(MESSAGE);
		
		if(MESSAGE != null)
		{
			PrintStream PS = new PrintStream(sock.getOutputStream());
            String address = args[0];
            
            try
            {
                InetAddress IP = InetAddress.getByName(address);
                PS.println("Hello " + args[0]);
                PS.println("Your IP address is " + IP.getHostAddress());
            }
            catch(UnknownHostException e)
            {
                System.err.println(address + " unknown host");
            }
		}
	}
}