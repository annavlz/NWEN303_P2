/**
 * A separate thread that is created by the server. This thread is used
 * to interact with the client.
 */

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class KeyConnection extends Thread
{

	private static AtomicInteger count = new AtomicInteger(0); // track the connection
	BigInteger startKey;
	int chunk;
	int keySize;
	byte[] ciphertext;
	
	public KeyConnection(Socket c, BigInteger key, int chunk, int keySize, byte[] ciphertext ) {
		client = c;
		count.getAndIncrement();
		this.startKey = key;
		this.chunk = chunk;
		this.keySize = keySize;
		this.ciphertext = ciphertext;
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
				String line = networkBin.readLine();
				if ( (line == null) || line.equals("finished")) {
					break;
				}
				if (line.equals("ready")) {
					response = startKey.toString() + chunk + keySize + Blowfish.decryptToString(ciphertext);
				} else if (line.has("found")) {
					System.out.println(line);
				}
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

