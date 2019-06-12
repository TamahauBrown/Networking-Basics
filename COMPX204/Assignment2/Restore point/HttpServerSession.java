//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServerSession extends Thread
{
    private Socket Client;
    private PrintWriter Write;

    public HttpServerSession(Socket s)
    {
	Client = s;
	Write = null;
    }
    
      public void run()
      {
	try
	{
	    Write = new PrintWriter(Client.getOutputStream(), true);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(Client.getInputStream()));
	    while(true)
		{
		    String r =reader.readLine();
		    if(r != null)
		     {
			 System.out.println("Connection is made");
		     }
		    else
		     {
			 break;
		     }
		}
	}
	catch(Exception e)
	{
	    System.err.println(e);
	}
      }
}
