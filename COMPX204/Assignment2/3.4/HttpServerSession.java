//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServerSession extends Thread
{
    private Socket Client;
    private PrintWriter Write;
	private HttpServer h = new HttpServer();

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
			String parts [] = r.split(" ");
			String filename = parts[1].substring(1);
			String get = parts[0];
		    if(r != null)
		     {
			 //System.out.println("Connection is made");
			 System.out.println(filename);
			 h.threads(this, r);			 
		     }
			 if(get.compareTo(" ") != 0)
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
	
	public void send(String message)
	{
		if(Write != null)
		{
			Write.println(message);
		}
	}
}
