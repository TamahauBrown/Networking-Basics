import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocketFactory;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLSocket;

class MyTLSFileServer
{
	//private InetAddress address;
	//private static SSLServerSocket socket;
 
	public static void main(String [] args)
	{
		try
		{
			//Gets the SSLContext and communicates with TLS
			SSLContext context = SSLContext.getInstance("TLS");

			//initialize the servers private key with context and store it in 				//server.jks
			KeyStore keys = KeyStore.getInstance("JKS");
			
			//Gets the passphrase to get into the JKS file
			char[] passphrase = "Acer0610".toCharArray();

			//Loads the keys file
			keys.load(new FileInputStream("server.jks"), passphrase);
			
			//Manages the key from the KeyManager class
			KeyManagerFactory keyManageFac = KeyManagerFactory.getInstance("SUNX509");
			keyManageFac.init(keys, passphrase);

			//Use the SSLContext with the keys
			context.init(keyManageFac.getKeyManagers(), null, null);

			//Creates the ServerSocketFactory
			ServerSocketFactory ssf = context.getServerSocketFactory();
			
			//Creates a new ServerSocket
	        SSLServerSocket socket = (SSLServerSocket) ssf.createServerSocket(Integer.parseInt(args[0]));
            
            //Tells the user the ServerSocket is created
            System.out.println("SSLServerSocket created");
            System.out.println(socket.toString());

			//Creates an array of protocols to display the TLS
			String EnabledProtocols[] = { "TLSv1.2", "TLSv1.1" };

			//Uses the setEnabledProtocols to put the protocols into the 				//SSLServerSocket
			socket.setEnabledProtocols(EnabledProtocols);

			//Accepts the SSL Socket
			SSLSocket ssl = (SSLSocket)socket.accept();
            
            //Tells the user the ServerSocket is accepted.
            System.out.println("ServerSocket accepted");
            
            BufferedOutputStream output = new BufferedOutputStream(ssl.getOutputStream());

			//Creates a BufferedReader
			BufferedReader reader = new BufferedReader(new InputStreamReader(ssl.getInputStream()));

			//Reads each line and splits whenever there is a space
			String filename = reader.readLine();

			System.out.println(filename);

	File file = new File(filename);

	if(file.exists())
	{
	//Reads each line and splits whenever there is a space
	//Creates the byte size of the file
        byte [] buff = new byte[1000];
	//Gets the file for the fileinputstream
	FileInputStream fis = new FileInputStream(file);
	//Makes the number equal to the size of the fileinputstream reading the byte size
	int num = fis.read(buff);
			
			//Loops until the end of the BufferedReader.   
		    	while(num != -1)
			{
			   	output.write(buff, 0, num);
                    
                		//Reads each line and splits whenever there is a space
			   	num = fis.read(buff);
			}
			output.flush();
	}
	else
	{
		System.out.println("File doesn't exist");
	}
        System.out.println("Server closed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
