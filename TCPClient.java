package TCP;

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
			
			if (sentMessage.equals("end")) {
				
				socket.close();
				
				System.exit(1);
				
			}
			
			
			if ((receivedMessage = received.readLine())!=null) {
			
				System.out.println("Server says: " + receivedMessage);
				
			}
			
		}
//		DataInputStream dataInputStream = new DataInputStream(inputStream);
//		String sentence = new String (dataInputStream.readUTF());
//		System.out.println(sentence);
//		// When done, just close the connection and exit
		

	}
}
