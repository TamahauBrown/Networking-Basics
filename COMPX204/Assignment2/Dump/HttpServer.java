//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServer
{
	ArrayList<HttpServerSession> serverSession = new ArrayList<>();
	
	//public void threads(HttpServerSession ses, String line) throws IOException
	//{
        //BufferedOutputStream stream;
		//int len;
		//synchronized(serverSession)
		//{
			//len = serverSession.size();
			//for(int i = 0; i < len; i++)
			//{
				//HttpServerSession s = serverSession.get(i);
				//if(ses != s)
				//{
					//s.println(stream, line);
				//}
			//}
		//}
	  
	public static void main(String args[]) throws Exception
 	{   
    //InputStreamReader IR = new InputStreamReader(sock.getInputStream());
    //BufferedReader BR = new BufferedReader(IR);
		
    //String MESSAGE = BR.readLine();
    //System.out.println(MESSAGE);
		
    //PrintStream PS = new PrintStream(sock.getOutputStream());
    //String address = ;
            
        try
        {
            //write something to the console here
            System.out.println("Running the web server");
            //HttpServer server = new HttpServer();
            ServerSocket socket = new ServerSocket(8080);
		    //Socket sock;
            //while(true)
		    //{		
			Socket sock = socket.accept();
			HttpServerSession listen = new HttpServerSession(sock);
			//synchronized(serverSession)
			//{
				//serverSession.add(listen);
			//}
            //Thread thread = new Thread(listen);
            //listen.start();
	    listen.run();
			SocketAddress remote = sock.getRemoteSocketAddress();
			//InetAddress IP = InetAddress.getByName(remote.to);
			System.out.println("Connected to " + remote);
            //listen.run();
			//sock.close();
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
}
