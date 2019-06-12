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
        
        SSLParameters param = new SSLParameters();
        
        param.setEndpointIdentificationAlgorithm("HTTPS");
        sock.setSSLParameters(param);
            
        sock.startHandshake();
        
        SSLSession sesh = sock.getSession();
        
        X509Certificate cert = (X509Certificate) sesh.getPeerCertificates()[0];
        
        getCommonName(cert);
        
        //Can getInputStream and getOutputStream as you would regular socket

	//Creates a BufferedReader
	BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	PrintWriter input = new PrintWriter(sock.getOutputStream(), true);
	
	//Reads each line and splits whenever there is a space
	String r = reader.readLine();
	while(r != null)
	{
		System.out.println(r);
		r = reader.readLine();
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
        String name = cert.getSubjectX500Principal().getName();
        LdapName ln = new LdapName(name);
        String cn = null;
        
        for(Rdn rdn : ln.getRdns())
        {
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
