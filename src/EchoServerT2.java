/**
 * An echo server listening on port passed as an argument. This server reads 
 * from the client and echoes back the result. When the client enters the 
 * string "bye", the server closes the connection.
 *
 */

import java.net.*;
import java.io.*;

public class  EchoServerT2 
{
	//flag of completion

	public static void main(String[] args) throws IOException {
		//initial key, key size, cipher text
		ServerSocket sock = null;

		try {
			// establish the socket
			sock = new ServerSocket(0);
			System.out.println("Waiting for connections on " + sock.getLocalPort());

			/**
			 * listen for new connection requests.
			 * when a request arrives, pass the socket to
			 * a separate thread and resume listening for
			 * more requests. 
			 * creating a separate thread for each new request
			 * is known as the "thread-per-message" approach.
			 */
			while (true) { //flag on completion is false
				// now listen for connections
				Socket client = sock.accept();	

				// service the connection in a separate thread
				//create a queue of chunks 
				//set flag on completion true when the queue is empty
				Connection c = new Connection(client); //pass params with chunk
				c.start();
			}
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			if (sock != null)
				sock.close();
		}
	}
}