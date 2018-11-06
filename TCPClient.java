// Name: SKG Software Development Team. Corey Everett, Michael Marino, Zach Rosa
// Date of Completion: November 5th, 2018
// Program: SKG TCP Client
// Purpose: This is the "end-user" program which the client uses to interact with the SKG Microtransaction Software server. The
// client can input several commands.

package skgLocal;
import java.net.*;
import java.io.*;
public class TCPClient { 
	public static void main(String args[]) throws IOException {
		System.out.println("client running");
		// Open your connection to a server, at port 6789
		Socket socket = new Socket("10.200.78.42",6789); 
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		// Get an input file handle from the socket and read the input
		OutputStream outStream = socket.getOutputStream();
		PrintWriter printWriter = new PrintWriter(outStream, true);
		InputStream inputStream = socket.getInputStream();
		BufferedReader received = new BufferedReader(new InputStreamReader(inputStream));
		
		System.out.println("Client:Type a word");
		String receivedMessage = "";  
		String sentMessage = ""; 
		
		while (true) {
			sentMessage = bufferedReader.readLine();
			printWriter.println(sentMessage); 
			printWriter.flush();
			
			// End the program when message input is "end"
			if (sentMessage.equals("end")) {socket.close(); System.exit(1);	}
			// When a message is received from the server, show to client.
			if ((receivedMessage = received.readLine())!=null) {System.out.println("Server says: " + receivedMessage);}	 
			
		}
	}
}
