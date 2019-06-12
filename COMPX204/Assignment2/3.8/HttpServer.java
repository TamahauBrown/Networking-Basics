//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServer
{
	//Main method to run the server
	public static void main(String args[]) throws Exception
 	{               
	 //Try-catch method
         try
         {
            //Tells the console the web server is running
            System.out.println("Running the web server");
	    //Creates a new ServerSocket
            ServerSocket socket = new ServerSocket(8080);
	    //Loops until 'break;' is called
            while(true)
	    {		
		//Creates a socket which accepts the webserversocket
		Socket sock = socket.accept();
		//Creates an instance of the HttpServerSession class
		HttpServerSession listen = new HttpServerSession(sock);
		//Calls the run method from HttpServerSession
            	listen.run();
		//Gets the socket of the remote socket (the one running the web page)
		SocketAddress remote = sock.getRemoteSocketAddress();
		//Prints out who the server is connected to
		System.out.println("Connected to " + remote);
  	    }
         }
	 //Catches an unknown host and displays a message to the console telling it so
         catch(UnknownHostException e)
         {
                System.err.println("unknown host");
         }
	 //Catches the exception and tells the type of exception it is
	 catch(Exception e)
	 {
		System.err.println(e);
         }
	}
}
