//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServerSession extends Thread
{
    private Socket Client;
    private PrintWriter Write;
	private HttpServer h = new HttpServer();
	FileInputStream fis;

    public HttpServerSession(Socket s) throws Exception
    {
	Client = s;
	Write = null;
    }
    
      public void run()
      {
      String filename;
	try
	{
	    BufferedOutputStream bos = new BufferedOutputStream(Client.getOutputStream());
	    //Write = new PrintWriter(Client.getOutputStream(), true);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(Client.getInputStream()));
	    while(true)
		{
		        String r =reader.readLine();
			String parts [] = r.split(" ");
			//String filename = parts[1].substring(1);
			//String get = parts[0];
		    if(parts.length != 3 && parts[0].compareTo("GET") != 0)
            {
                break;
            }
            else
            {
                filename = parts[1].substring(1);
                System.out.println(filename);
			 // h.threads(this, r);
            
            if(r == null)
		     {
			     System.out.println("EOF");			 
		     }
             if(r.compareTo("") == 0)
             {
                   break;
             }
            }
            
                //println(bos, "Hello World");    
                
                File file = new File(filename);
		if(file.exists())
{
println(bos, "HTTP/1.1 200 OK");
println(bos, "\r\n");

                byte [] buff = new byte[1000];
                fis = new FileInputStream(file);
                //String byt = fis.read(buff);
		while(true)
		{
		  int num = fis.read(buff);
		  if(num != -1)
		   { 
		       sleep(1000);          
		       bos.write(buff, 0, num);
		   }
		   else
		   {
			break;
		   }
		}
}
else
{
println(bos, "HTTP/1.1 404 Not Found");
println(bos, "");
println(bos, "File not found");
}
		bos.close();
                fis.close();
		}
	}
	catch(Exception e)
	{
	    System.err.println(e);
	}
    }
	
	public void println(BufferedOutputStream bos,String s) throws IOException
	{
        String news = s + "\r\n";
        byte [] array = news.getBytes();
        for(int i= 0; i < array.length; i++)
        {
		  //if(Write != null)
		    //{
			  bos.write(array[i]);
		    //}
         
        }
   	    bos.flush();
            return;
	}
}
