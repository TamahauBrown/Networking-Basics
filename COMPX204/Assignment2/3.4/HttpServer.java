//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServer
{
	ArrayList<HttpServerSession> serverSession = new ArrayList<>();
	
	public void threads(HttpServerSession ses, String line)
	{
		int len;
		synchronized(serverSession)
		{
			len = serverSession.size();
			for(int i = 0; i < len; i++)
			{
				HttpServerSession s = serverSession.get(i);
				if(ses != s)
				{
					s.send(line);
				}
			}
		}
	}
	
	public void run() throws Exception
	{
		    //write something to the console here
    System.out.println("Running the web server");
    HttpServer server = new HttpServer();
    ServerSocket socket = new ServerSocket(8080);

    //InputStreamReader IR = new InputStreamReader(sock.getInputStream());
    //BufferedReader BR = new BufferedReader(IR);
		
    //String MESSAGE = BR.readLine();
    //System.out.println(MESSAGE);
		
    //PrintStream PS = new PrintStream(sock.getOutputStream());
    //String address = ;
            
            try
            {
		Socket sock;
                //while(true)
		    //{		
			sock = socket.accept();	
			HttpServerSession listen = new HttpServerSession(sock);
			synchronized(serverSession)
			{
				serverSession.add(listen);
			}
			listen.start();
			SocketAddress remote = sock.getRemoteSocketAddress();
			//InetAddress IP = InetAddress.getByName(remote.to);
			System.out.println("Connected to " + remote);
			sock.close();
		    //}
            }
            catch(UnknownHostException e)
            {
                System.err.println("unknown host");
            }
	    catch(Exception e)
	    {
		System.err.println(e);
            }
      }
	  
	public static void main(String args[]) throws Exception
 	{
		HttpServer server = new HttpServer();
		server.run();
	}
}
