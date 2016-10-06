/**
 * A separate thread that is created by the server. This thread is used
 * to interact with the client.
 */

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionT2 extends Thread
{

	private static AtomicInteger count = new AtomicInteger(0); // track the connection
	//start key
	//how many iterations
	//key size
	//cipher text
	
	public ConnectionT2(Socket c ) {
		client = c;
		count.getAndIncrement();
		// init extra params
	} 


	/**
	 * this method is invoked as a separate thread
	 */
	public void run() {
		BufferedReader networkBin = null;
		OutputStreamWriter networkPout = null;
		
		try {
			/**
			 * get the input and output streams associated with the socket.
			 */
			networkBin = new BufferedReader(new InputStreamReader(client.getInputStream()));
			networkPout = new OutputStreamWriter(client.getOutputStream());

			/**
			 * the following successively reads from the input stream and returns
			 * what was read. The loop terminates with ^D or with the string "bye\r\n"
			 * from the  input stream.
			 */
			String response = null;
			while (true) {
				/**
				 * On receive "I'm ready" send params (lambda?) 
				 * Wait for either positive or negative results
				 * On positive print out result, close connection and set server flag on completion
				 * On negative just close connection
				 * 
				 */
			}
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			try {
				if (networkBin != null)
					networkBin.close();
				if (networkPout != null)
					networkPout.close();
				if (client != null)
					client.close();
			}
			catch (IOException ioee) {
				System.err.println(ioee);
			}
		}
	}

	private Socket client;
}

