package skgLocal;

import java.net.*;
import java.io.*;
import java.lang.reflect.Array;


public class TCPServerLocal {
	public static void main(String args[]) throws IOException {
	
		String[] testArray = new String[5000];
		
		
		
		System.out.println("server running");
			
		
		// Register service on port 6789
		ServerSocket serverSocket = new ServerSocket(6789); 
		// Wait and accept a connection
		Socket socket = serverSocket.accept(); 
	
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		// Get a communication stream associated with the socket
		OutputStream outStream = socket.getOutputStream();
		
	
		
		PrintWriter printWriter = new PrintWriter(outStream, true);
		InputStream inputStream = socket.getInputStream();
		BufferedReader received = new BufferedReader(new InputStreamReader(inputStream));
		
		String receivedMessage = "";
		String sentMessage = "";
		
		int count = 0;
		
		while (true) {
		
			
			
			if ((receivedMessage = received.readLine())!=null) {
				
				System.out.println("Client says: " + receivedMessage);
				
				testArray[count] = receivedMessage;
				count = count + 1;
				
				
				
				sentMessage = bufferedReader.readLine();
				printWriter.println(sentMessage);
				printWriter.flush();
				
				if (sentMessage.equals("end")) {
					System.out.println(testArray[0]);
					System.out.println(testArray[1]);
					System.out.println(testArray[2]);
					System.out.println(testArray[3]);
					socket.close();
					serverSocket.close();
					System.exit(1);								
				}
				
			}		
		}	
	}
}