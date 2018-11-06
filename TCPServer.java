package skgLocal;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Array;


public class TCPServerLocal {
	public static void main(String args[]) throws IOException {
	
		String inputFileName;
		String outputFileName;
		
		
		
//		try (
//			
//			PrintWriter fout = new PrintWriter(new File(outputFileName));) {		
//			process(fin, fout);
//		}
//		catch (FileNotFoundException ex) {
//			System.out.printf("file not found");
//		}
		
		
		
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
				
				System.out.println("Client typed: " + receivedMessage);
				
				if (receivedMessage.toLowerCase().equals("help")) { //if the client types help, the server sends back valid commands
					sentMessage = "Command received. Valid commands are: help, create, lookup";
					printWriter.println(sentMessage);
					printWriter.flush();					
					
				}
				
				else if (receivedMessage.toLowerCase().equals("create")) { //if the client types create, the server sends back instructions on how to write a review
					sentMessage = "Command received. Tell me the game you want to review";
					printWriter.println(sentMessage);
					printWriter.flush();
					receivedMessage = received.readLine();
					System.out.println("Client typed: " + receivedMessage);
					sentMessage = "Command received. Write your review now";
					printWriter.println(sentMessage);
					printWriter.flush();
					receivedMessage = received.readLine();
					System.out.println("Client typed: " + receivedMessage);
					sentMessage = "Review Received. Thank you.";
					printWriter.println(sentMessage);
					printWriter.flush();
					
					
				}
				
				else if (receivedMessage.toLowerCase().equals("lookup")) { //if the client types lookup, the server sends back instructions on how to lookup a game
					sentMessage = "Command received. Tell me the game you want to look up";
					printWriter.println(sentMessage);
					printWriter.flush();
					receivedMessage = received.readLine();
					System.out.println("Client typed: " + receivedMessage);
					sentMessage = "Command received. Here are the results for " + receivedMessage;
					printWriter.println(sentMessage);
					printWriter.flush();
					sentMessage = "Results: (nothing so far)";
					printWriter.println(sentMessage);
					printWriter.flush();
					
									
				}
				
//				if (sentMessage.toLowerCase().equals("end")) {		
//					System.out.println("Ending server");
//							
//					socket.close();
//					serverSocket.close();
//					System.exit(1);								
//				}
				
				else {
					sentMessage = "Please type \"help\" because you need help";
					printWriter.println(sentMessage);
					printWriter.flush();
				}
				
				
			}		
		}	
	}
	
	public static void process(Scanner input, PrintWriter output) {
		// TODO: write your code here
		output.printf("test");
		
		
		
	}
}