package MicrotransactionsPackage;
// Java implementation of  Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
  
import java.io.*; 
import java.text.*; 
import java.util.*;

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
        while (true) {   
        
            try { 
            	// Ask user what he wants 
                dos.writeUTF("Enter a command.");
                  
                // receive the answer from client 
                received = dis.readUTF(); 
                      		                                                                                                           
                System.out.println("A client said: " + received); //prints out what each client types to the Server's console
                     
                    if(received.toLowerCase().equals("exit")) //if the client types in exit, their client will exit (not the server)
                    {  
                        System.out.println("Client " + this.s + " sends exit..."); 
                        System.out.println("Closing this connection."); 
                        this.s.close(); 
                        System.out.println("Connection closed"); 
                        break; 
                    } 
                    else if (received.length() < 4) {  //catches invalid commands
                    	dos.writeUTF("Please type \"help\" because you need help");
                    }
                    else if (received.length() == 5) {  //catches more invalid commands
                    	dos.writeUTF("Please type \"help\" because you need help");
                    }
                    else if (received.toLowerCase().substring(0,4).equals("help")) { //if the client types help, the server sends back valid commands
    					dos.writeUTF("Command \"help\" received. Valid commands are: create <Name of Game>|<Full Review - create a review. lookup <Name of Game>- lookup a review. exit- exit program");
    					
    				}
    				//if the client types create, the server sends back instructions on how to write a review
    				else if (received.toLowerCase().substring(0,6).equals("create")) { 
    					
    					// This section of code prints the gameName and review variables, and instantiates them.
    					   					   					
    					if (received.indexOf('|') != -1) {
    						
    					String gameName = received.substring(6, received.indexOf('|'));
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
    				else if (received.toLowerCase().equals("lookup")) { //if the client types lookup, the server sends back instructions on how to lookup a game
    					dos.writeUTF("Command \"lookup\" received. Tell me the game you want to look up");
    					
    				}
    				
    				else {dos.writeUTF("Please type \"help\" because you need help");} // Case for if the user doesn't type a valid command.
                                                              
                    // write on output stream based on the 
                    // answer from the client 
                               
            } catch (IOException e) {e.printStackTrace();} // Catch exception for file input/output.
        } 
          
        try { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e) {e.printStackTrace();} // Last-second error-catching when closing input/output streams.
            
    } 
} 