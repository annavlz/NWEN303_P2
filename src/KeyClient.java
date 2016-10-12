/**
 * An echo client. The client enters data to the server, and the
 * server echoes the data back to the client.\
 *
 * This has been simplified, what the client sends is now hardcoded.
 *
 */

import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class KeyClient
{

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.err.println("Usage: java EchoClient <IP address> <Port number>");
			System.exit(0);
		}

		BufferedReader in = null;
		PrintWriter out = null;
		Socket sock = null;
		
		while (true) {
			try {
				sock = new Socket(args[0], Integer.parseInt(args[1]));
	
				// set up the necessary communication channels
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(),true);
	
				// send a sequence of messages and print the replies
				/**
				 * Send to the server "I'm ready" and wait
				 * Receive start key, how many iterations, key size and ciphertext
				 * Run Search (lambda?)
				 * Return either positive or negative result
				 * On positive send result and close
				 * On negative send request for the next chunk and wait
				 */
				// send a sequence of messages and print the replies
		
				out.println("ready");
				String[] inArgs = in.readLine().split("\\s+");
				String result = search(inArgs);
				if (result == "not found"){
					out.println(result);
				}else{
					out.println(result);
					break;
				}
			}
			catch (IOException ioe) {
				System.err.println(ioe);
				break;
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			finally {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (sock != null)
					sock.close();
			}
		}
	}
	
	 public static String search(String[] args) throws InterruptedException{
       
		        // Extract the key, turn into an array (of right size) and 
		        //   convert the base64 ciphertext into an array
		        BigInteger bi = new BigInteger(args[0]);
		        int keysNumber = Integer.parseInt(args[1]);
		        int keySize = Integer.parseInt(args[2]);
		        byte[] key = Blowfish.asByteArray(bi, keySize);
		        byte[] ciphertext = Blowfish.fromBase64(args[3]);

		        // Go into a loop where we try a range of keys starting at the given one
		        String plaintext = null;
		        // Search from the key that will give us our desired ciphertext
		        for (int i=0; i<keysNumber; i++) {
		            // tell user which key is being checked
		            String keyStr = bi.toString();
		            System.out.print(keyStr);
		            Thread.sleep(100);
		            for (int j=0; j<keyStr.length();j++) {
		                System.out.print("\b");
		            }
		            // decrypt and compare to known plaintext
		            Blowfish.setKey(key);        
		            plaintext = Blowfish.decryptToString(ciphertext);
		            if (plaintext.equals("May good flourish; Kia hua ko te pai")) {
//		                System.out.println("Plaintext found!");
//		                System.out.println(plaintext);
//		                System.out.println("key is (hex) " + Blowfish.toHex(key) + " " + bi);
//		                System.exit(-1);
		                return "key is (hex) " + Blowfish.toHex(key) + " " + bi;
		            } 
		            
		            // try the next key
		            bi = bi.add(BigInteger.ONE);
		            key = Blowfish.asByteArray(bi,keySize);
		        }
//		        System.out.println("No key found!");
		        return "not found";
		    }
}
