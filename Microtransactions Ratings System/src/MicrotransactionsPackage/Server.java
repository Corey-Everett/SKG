package MicrotransactionsPackage;
// Java implementation of  Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
  
import java.io.*; 
import java.text.*; 
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import MicrotransactionsPackage.ClientHandler;

import java.net.*; 
  
// Server class 
public class Server {  

    public static void main(String[] args) throws IOException { 
     
    	// Server startup; Instantiate and declare file.
    	System.out.println("Server Running");
    	File file = new File("C:\\Users\\marinom1\\eclipse-workspace\\Microtransactions Ratings System\\src\\reviewedGames");
    	
    	//Create the file, or state file is already created if so.
    	if (file.createNewFile()) { System.out.println("File is created!");}
    	else {System.out.println("File already exists.");}
    	
    	//Write Testing Content
    	FileWriter writer = new FileWriter(file, true); //the true here makes it so the writer will not overwrite old data
       	
        // server is listening on port 6789
        ServerSocket ss = new ServerSocket(6789); 
        
        // running infinite loop for getting client request
          
        while (true) {  
         
            Socket s = null; 
              
            try {
             
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                  
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                                
                System.out.println("Assigning new thread for this client..."); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e) {s.close(); e.printStackTrace();}  // Error-catching.
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread { 
 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
        
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) { 
     
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run() {          	
        String received;       
        int connection = 1;
        while (connection == 1) {   
        
            try { 
            	// Ask user what he wants 
                dos.writeUTF("Enter a command. Type \"help\" to see valid commands.");
                 
                // receive the input from client 
                received = dis.readUTF(); 
                      		                                                                                                           
                System.out.println("A client said: " + received); //prints out what each client types to the Server's console
                    
                	if (received.length() == 0 || received.length() == 1 || received.length() == 2 || received.length() == 3) {
                		dos.writeUTF("Please type \"help\" because you need help");
                	}
                	else if (received.toLowerCase().substring(0,4).equals("help")) { //if the client types help, the server sends back valid commands
    					dos.writeUTF("Command \"help\" received. Valid commands are: create <Name of Game>|<Full Review - create a review. lookup <Name of Game>- lookup a review. lookall- view all reviews. exit- exit program");
    					
    				}               	
                    else if (received.length() == 5) {  //catches more invalid commands
                    	dos.writeUTF("Please type \"help\" because you need help");
                    }
                	else if(received.toLowerCase().equals("exit")) //if the client types in exit, their client will exit (not the server)
                    {  
                        System.out.println("Client " + this.s + " sends exit..."); 
                        System.out.println("Closing this connection."); 
                        this.s.close(); 
                        System.out.println("Connection closed"); 
                        break; 
                    } 
                	else if (received.length() == 4) {
                		dos.writeUTF("Please type \"help\" because you need help");
                	}
                	
                	//if the client types create, the server sends back instructions on how to write a review
                    else if (received.toLowerCase().substring(0,6).equals("create")) {  
    					
    					// This section of code prints the gameName and review variables, and instantiates them.
    					   					   					
    					if (received.indexOf('|') != -1) {
    						
    					String gameName = received.substring(7, received.indexOf('|'));
    					gameName = gameName.toLowerCase();
    					String review = received.substring(received.indexOf('|')+ 1, received.length());    					   			
    					File file = new File("C:\\Users\\marinom1\\eclipse-workspace\\Microtransactions Ratings System\\src\\reviewedGames");
    					FileWriter writer = new FileWriter(file, true); //the true here makes it so the writer will not overwrite old data
    					
    					String fileString = (gameName + ":" + review + "\n");
    			    	writer.write(fileString);    			       			    	
    			    	writer.close();    			
    			    	dos.writeUTF("Thank you. Your review was received.");   					
    				}
    					else {
    					dos.writeUTF("Improper syntax. create command syntax is create <Name of Game>|<Full Review>");
    					}
    				}
                    else if (received.toLowerCase().substring(0,6).equals("lookup")) { //if the client types lookup, the server sends back instructions on how to lookup a game
    					if (received.length() == 6) { //if the client types in lookup but without a game
    						dos.writeUTF("Improper syntax. lookup command syntax is lookup <gameName>");
    					}
    					else {
    						String gameName = received.substring(7, received.length());
        					gameName = gameName.toLowerCase();
        					String gameReview = "\n";
        					String x = "";       					
        					
        					File file = new File("C:\\Users\\marinom1\\eclipse-workspace\\Microtransactions Ratings System\\src\\reviewedGames");
        					Scanner fileScanner = new Scanner(file);
        					
        					while (fileScanner.hasNextLine()) {
        						x = fileScanner.nextLine();
        						
        						if (x.length() < gameName.length()) {
        							
        						}
        						
        						else if (x.substring(0, gameName.length()).equals(gameName) && x.charAt(gameName.length()) == (':')) { //if x is a review for the game
        							
        							gameReview = gameReview + "\n"+ x;
        						}        						       						        						       						
        					}
        				
        					if (gameReview.equals("\n")) {
        						dos.writeUTF("There are no reviews for this game. You can make the first by using the \"create\" command");
        					}
        					else {
        						dos.writeUTF("Command \"lookup\" received. Showing existing reviews for " + gameName + ": " +
        	        					gameReview + "\n");
        					}
        					
        					
        					
    					}
    					
    					
    				}
                    else if (received.length() == 6) {
                    	dos.writeUTF("Please type \"help\" because you need help");
                    }
                    else if (received.toLowerCase().substring(0,7).equals("lookall")) {
                    	String x = "";
                    	
                    	
                    	File file = new File("C:\\Users\\marinom1\\eclipse-workspace\\Microtransactions Ratings System\\src\\reviewedGames");
    					Scanner fileScanner = new Scanner(file);
    					while (fileScanner.hasNextLine()) {
    						x = x + fileScanner.nextLine();
    						x = x + "\n";
    					}
    					dos.writeUTF("All the game reviews: \n" + x);
                    }
                    
                    
    				
    				   				   				
    				else {dos.writeUTF("Please type \"help\" because you need help");} // Case for if the user doesn't type a valid command.
                                                              
                    // write on output stream based on the 
                    // answer from the client 
                               
            } catch (IOException e) {
            	connection = -1;
            	System.out.println("a client hit the red button");
            //	e.printStackTrace();
            } // Catch exception
        } 
          
        try { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e) {e.printStackTrace();} // Last-second error-catching when closing input/output streams.
            
    } 
} 