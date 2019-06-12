import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.Array.array

class TftpClient extends TftpUtility
{
    //DECLARE VARIABLES
    static String filename;
    static InetAddress clientAddress;
    static int clientPort;

    //Make protocol variables
    private static final byte RRQ = 1;
    private static final byte DATA = 2;
    private static final byte ACK = 3;
    private static final byte ERROR = 4;

    //The stuff to create the DatagramPackets
    private static byte [] reqArray, buffArray;
    static DatagramPacket outBoundPacket;
    static DatagramPacket inBoundPacket; 
    static DatagramSocket clientSocket;
    static FileInputStream fis;
    static String type = "octet";
    //Used for incrementing
    static int a = 1;
    
    /*
    The main method which is the thing that creates the file transfer protocol with the packets and retrieves the data.
    */
    public static void main(String [] args)
    {
        //Try-catch method
        try
        {
        //Gets the arguments from the terminal
        clientAddress = InetAddress.getByName(args[0]);
        clientPort = Integer.parseInt(args[1]);
        filename = args[2];
        
        //Creates the DatagramSocket
	    clientSocket = new DatagramSocket();
        
/*        byte nullByte = 0;
        int rrqLength = 1 + filename.length();
        byte[] byteArray = new byte[rrqLength];
        
        int pos = 0;
        byteArray[pos] = RRQ;
*/
	
// byte[filename.getBytes().length + 1];
	 byte[] tempArray = filename.getBytes();

	//byteArray = RRQ + tempArray;
	Array<Byte> byteArray = array(RRQ).append(array(tempArray));
        for(int j = 0; j < filename.length(); j++)
        {
            byteArray[pos] = (byte) filename.charAt(j);
            pos++;
        }
        byteArray[pos] = nullByte;
        
        //Gets the RRQ, the name of the file and the type and stores it into the request array to receive data.
        reqArray = byteArray;
        //request(RRQ, filename, "octet");
        
        //Creates a datagrampacket to be able to get the data from the ServerSocket
        outBoundPacket = new DatagramPacket(reqArray, reqArray.length, clientAddress, clientPort);
        clientSocket.send(outBoundPacket);            

        //Calls the receiver method to get the ByteOutputStream
        ByteArrayOutputStream byteOS = receiver();    

        //Writes the file by getting the ByteOutStream and the filename
        writeFile(byteOS, filename);
        }
       //Catches the exception and states the line of exception
	   catch(Exception e)
	   {
            System.err.println("Error: " + e);
		    e.printStackTrace();
	   }
    }
    
    /*
    The receiver method is used to get the packets and return them into the ByteArrayOutputStream
    */
    static ByteArrayOutputStream receiver() throws IOException
    {
        //Declare a ByteArrayOutputStream
        ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
        
        do
        {
            //Prints out the count for the packets
            System.out.println("Packet count: " + a);
            
            //Increments the variable 'a'
            a++;
            
            //Declare the size of the byte
            buffArray = new byte[516];
  
            //Creates a new DatagramPacket which gets the buffer details and the address
            inBoundPacket = new DatagramPacket(buffArray, buffArray.length, clientAddress, clientSocket.getLocalPort());
            
            //Uses the receive method
            clientSocket.receive(inBoundPacket);
  
          
            //Gets the first two values stored in buffArray and stores the in checker
            byte [] checker = inBoundPacket.getData();
            
            //If the value of checker equals the error protocol write an error
            if(checker[0] == ERROR)
            {
                writeError();
            }
            //Otherwise if the values equals data it gets the new 2 values in BuffArray and stores them in the variable 'getter'
            else if(checker[0] == DATA)
            {
            
            DataOutputStream dataOutput = new DataOutputStream(byteOS);
            dataOutput.write(inBoundPacket.getData(), 2, inBoundPacket.getLength() - 2);
     
            //Calls the acknowledge method
            acknowledge(inBoundPacket.getData()[2]);
            }
        }
       //It keeps looping until all packets have been sent. 
	while(!isFinalPacket(inBoundPacket));
        return byteOS;
    }
    
    
    //Acknowledges the server if it has received the file.
    static void acknowledge(byte blockNum)
    {
        byte [] Acknowledge = 
        {
            0, ACK, blockNum
        };
        
        DatagramPacket ack = new DatagramPacket(Acknowledge, Acknowledge.length, clientAddress, inBoundPacket.getPort());
        
        //Sends a DatagramPacket showing it acknowledged the server
        try
        {
            clientSocket.send(ack);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }
    
    //Writes an error if it has not received the file
    static void writeError()
    {
        String err = new String(buffArray, 3, 1);
        String errTxt = new String(buffArray,4, inBoundPacket.getLength() - 4);
        System.err.println("ERROR: " + err + " " + errTxt);
    }
    
    //Writes to the file using the OutputStream
    static void writeFile(ByteArrayOutputStream byteAOS, String fileName)
    {
        try
        {
            OutputStream outStream = new FileOutputStream(fileName);
            byteAOS.writeTo(outStream);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }

    //Checks to see if it is the last packet
	static boolean isFinalPacket(DatagramPacket inPacket)
	{
		//If the packet size is less than 512 bytes it returns true
		if(inPacket.getLength() < 512)
		{
			return true;
		}
		//Otherwise it returns false
		else
		{
			return false;
		}
	}
}
