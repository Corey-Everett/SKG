package TCP;

import java.net.*;
import java.io.*;

public class TCPServer {
	public static void main(String args[]) throws IOException {
	
		System.out.println("server running");
			
		
		// Register service on port 6789
		ServerSocket serverSocket = new ServerSocket(12001);
		// Wait and accept a connection
		Socket socket = serverSocket.accept(); 
	
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		// Get a communication stream associated with the socket
		OutputStream outStream = socket.getOutputStream();
		
	//	DataOutputStream dataOutStream = new DataOutputStream(outStream);
		
		PrintWriter printWriter = new PrintWriter(outStream, true);
		InputStream inputStream = socket.getInputStream();
		BufferedReader received = new BufferedReader(new InputStreamReader(inputStream));
		
		String receivedMessage = "";
		String sentMessage = "";
		
		while (true) {
		//	receivedMessage = received.readLine();
			
			
			if ((receivedMessage = received.readLine())!=null) {
				
				System.out.println(receivedMessage);
				
				sentMessage = bufferedReader.readLine();
				printWriter.println(sentMessage);
				printWriter.flush();
				
				if (sentMessage.equals("end")) {
					
					socket.close();
					serverSocket.close();
					System.exit(1);
					
				}
			}
			
			
		}
//		// Send a string!
//		dataOutStream.writeUTF("Hi there");
//		// Close the connection, but not the server socket
//		
//		dataOutStream.close();
//		socket.close();
//		serverSocket.close();
		
	}
}