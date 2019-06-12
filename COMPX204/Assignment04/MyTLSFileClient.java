import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateFactory;
import javax.security.cert.Certificate;
import javax.net.ssl.SSLParameters;
import javax.naming.ldap.Rdn;
import javax.naming.ldap.LdapName;
import javax.net.ssl.SSLSession;

class MyTLSFileClient
{
    static FileOutputStream fis;
    public static void main(String [] args)
    {        
        try
        {
 	//SSLSocket socket = (SSLSocket) SSLSocket.createSocket(Integer.parseInt(args[0]));
	String localhost = args[0];
	int port = Integer.parseInt(args[1]);
    	String filename = args[2];
	
	InetAddress IP = InetAddress.getByName(localhost);
	
	SSLSocketFactory fact = (SSLSocketFactory)SSLSocketFactory.getDefault();
        
        //Need to get the host and the port
        SSLSocket sock = (SSLSocket)fact.createSocket(IP,port);
        
	//Creates a SSLParameter
        SSLParameters param = new SSLParameters();
        
	//Makes it a HTTPS protocol
        param.setEndpointIdentificationAlgorithm("HTTPS");

	//Uses the         
	sock.setSSLParameters(param);
            
	//Starts the handshake to allow it to start sending data across the server and the client 		with encryption
        sock.startHandshake();
        
	//Get the SSLSession to create with the server and the client
        SSLSession sesh = sock.getSession();
        
	//Creates the X509Certisifacate using the SSLSession
        X509Certificate cert = (X509Certificate) sesh.getPeerCertificates()[0];
        
	//Gets the common encryption used between the server and the client
        getCommonName(cert);
        
        //Can getInputStream and getOutputStream as you would regular socket

	//Creates a BufferedReader
	InputStream reader = sock.getInputStream();
	PrintWriter output = new PrintWriter(sock.getOutputStream(), true);
	output.println(filename);
	output.flush();
	//output.close();

	//Reads each line and splits whenever there is a space
	//Creates the byte size of the file
        byte [] buff = new byte[1000];
	//Gets the file for the fileinputstream
	fis = new FileOutputStream("_" + filename);
	//Makes the number equal to the size of the fileinputstream reading the byte size
	int num = reader.read(buff);
	
	while(num != -1)
	{
		fis.write(buff, 0, num);

		num = reader.read(buff);
		
	}
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    }
    
    private static String getCommonName(X509Certificate cert)
    {
        try
        {
	//Gets the name of the certificate
        String name = cert.getSubjectX500Principal().getName();

	//Gets the name of the certificate and puts it into the Ldap
        LdapName ln = new LdapName(name);
        String cn = null;
        
	//For each Ldap it loops this
        for(Rdn rdn : ln.getRdns())
        {
	    //Checks if it is the "IgnoreCase and converts it into a string called "cn" anc returns it
            if("CN".equalsIgnoreCase(rdn.getType()))
            {
                cn = rdn.getValue().toString();
            }
            return cn;
        }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
