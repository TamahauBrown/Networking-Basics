//Tamahau Brown's Assignment

import java.net.*;
import java.io.*;
import java.util.*;

class HttpServerSession extends Thread
{
    //Creates a socket variable, and a printwriter variable
    private Socket Client;
    private PrintWriter Write;

    //Creates an instance of the HttpServer class
    private HttpServer h = new HttpServer();

    //Declare the FileInputStream	
    FileInputStream fis;

    //The Constructor of the HttpServerSession
    public HttpServerSession(Socket s) throws Exception
    {
	Client = s;
	Write = null;
    }
    
    //Creates a run method
    public void run()
    {
      //Declare a String variable called filename
      String filename;
      File file;

      //Try-Catch method
      try
      {
	//Creates a BufferedOutputStream and a BufferedReader
        BufferedOutputStream bos = new BufferedOutputStream(Client.getOutputStream());
	BufferedReader reader = new BufferedReader(new InputStreamReader(Client.getInputStream()));
	//Loops until 'break;' is called.   
        while(true)
	{
	   //Reads each line and splits whenever there is a space
	   String r =reader.readLine();
	   String parts [] = r.split(" ");
	   
	   //If the length of the parts is not 3 and its not a "GET" then it breaks the loop.
           if(parts.length != 3 && parts[0].compareTo("GET") != 0)
           {
                break;
           }
	   //Otherwise it reads the file and continues
           else
           {
		//This gets the filename from parts[1] without the first letter, and it prints it 			//out 
                filename = parts[1].substring(1);
		if(filename.compareTo("") != 0)
		{
                	System.out.println(filename);
                }
		else
		{
			System.out.println("Lulu.txt (Default file when no file is put in)");
		}
 		//If there is nothing in the readline it prints out "EOF" in the terminal
            	if(r == null)
		{
			System.out.println("EOF");			 
		}
		//Otherwise if readline is "" it breaks from the loop
                if(r.compareTo("") == 0)
             	{
                   	break;
             	}
            }
	    if(filename.compareTo("") != 0)
	    {
	    	//Creates a new file   
            	file = new File(filename);
	    }
	    else
	    {
		file = new File("/home/tmmb1/COMPX204/Assignment2/3.9/Lulu.txt");
	    }
	    //If the file exists it prints it out that its okay and continues
	    if(file.exists())
	    {
		//Prints out the ok message to the bufferedoutputstream
		println(bos, "HTTP/1.1 200 OK");
		println(bos, "");

		//Creates the byte size of the file
                byte [] buff = new byte[1000];
		//Gets the file for the fileinputstream
                fis = new FileInputStream(file);
		//Makes the number equal to the size of the fileinputstream reading the byte size
		int num = fis.read(buff);
		//Loops as long as it is not an out of bounds exception it writes the file onto 		//the page.
		while(num != -1)
		{			          
			//Sleeps it for 1 second
			sleep(1000); 
			//Writes the  file contents
		       	bos.write(buff, 0, num);
			//Makes number equal to the fileinputstream reading the byte size.
		       	num = fis.read(buff);
		}
	   }
	   //If the file does not exist
	   else
	   {
		//Prints to the bos the 404 error message and that the file is not found
		println(bos, "HTTP/1.1 404 Not Found");
		println(bos, "");
		println(bos, "File not found");
	   }
	   //Closes the bufferedoutputstream and fileinputstream
	   bos.close();
           fis.close();
	}
    }
    //If an exception occurs it prints out the error message.
    catch(Exception e)
    {
	  System.err.println(e);
    }
    }

  //The println method which takes a bufferedoutputstream and a string   	
  public void println(BufferedOutputStream bos,String s) throws IOException
  {
     //Creates a news string which creates a "\r\n" message
     String news = s + "\r\n";
     //Gets the byte size of the array
     byte [] array = news.getBytes();
     //As long as i is less than the array length it runs and it increments i by 1.
     for(int i= 0; i < array.length; i++)
     {
	//Writes the position of the array
	bos.write(array[i]);
     }
     //Once its out of the array it returns out of the method.
     return;
  }
}
