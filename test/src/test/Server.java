package test;
// Java implementation of  Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
  
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
public class Server  
{ 
    public static void main(String[] args) throws IOException  
    { 
    	
    	System.out.println("Server Running");
    	File file = new File("C:\\Users\\marinom1\\eclipse-workspace\\test\\src\\testFilez");
    	  
    	//Create the file
    	if (file.createNewFile())
    	{
    	    System.out.println("File is created!");
    	} else {
    	    System.out.println("File already exists.");
    	}
    	
    	//Write Testing Content
    	FileWriter writer = new FileWriter(file, true); //the true here makes it so the writer will not overwrite old data
    	writer.write("ahhhhhhh");
    	writer.write("222222");
    	writer.close();
        // server is listening on port 6789
        ServerSocket ss = new ServerSocket(6789); 
        
        
        
        
        // running infinite loop for getting client request         
        while (true)  
        { 
            Socket s = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        		// Get a communication stream associated with the socket
        		
                
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                
                
                
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread  
{ 
     
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run()  
    { 
    	
        String received; 
     //   String toreturn; 
        
        String gameName;
        
        while (true)  
        { 
            try { 
            	
            	OutputStream outStream = s.getOutputStream();
        		  		
        		PrintWriter printWriter = new PrintWriter(outStream, true);
        		InputStream inputStream = s.getInputStream();
        		BufferedReader buffr = new BufferedReader(new InputStreamReader(inputStream));
            	// Ask user what he wants 
                dos.writeUTF("Enter a command.");
                  
                // receive the answer from client 
                received = dis.readUTF(); 
                      		                                                                                                           
                System.out.println("A client said: " + received);
                     
                    if(received.toLowerCase().equals("Exit")) 
                    {  
                        System.out.println("Client " + this.s + " sends exit..."); 
                        System.out.println("Closing this connection."); 
                        this.s.close(); 
                        System.out.println("Connection closed"); 
                        break; 
                    } 
                    
                    else if (received.toLowerCase().equals("help")) { //if the client types help, the server sends back valid commands
    					dos.writeUTF("Command \"help\" received. Valid commands are: create - create a review. lookup- lookup a review. exit- exit program");
    					
    				}
    				
    				else if (received.toLowerCase().equals("create")) { //if the client types create, the server sends back instructions on how to write a review
    					
    					  					
    					dos.writeUTF("Command  \"create\" received. Tell me the game you want to review");
    					
    					gameName = dis.readUTF();
    					System.out.println("should see this");
    					File file = new File("C:\\Users\\marinom1\\eclipse-workspace\\test\\src\\testFilez");
    					FileWriter writer = new FileWriter(file, true); //the true here makes it so the writer will not overwrite old data
    					writer.write(gameName);
    			    	writer.close();
    			    	dos.writeUTF("Game name received. Write your review now: ");
    			    	received = dis.readUTF();
    			    	writer.write(received);
    			    	writer.close();
    			    	dos.writeUTF("Thank you. Your review was received.");
    				}
    				
    				else if (received.toLowerCase().equals("lookup")) { //if the client types lookup, the server sends back instructions on how to lookup a game
    					dos.writeUTF("Command \"lookup\" received. Tell me the game you want to look up");
    					
    				}
    				
    				else {
    					dos.writeUTF("Please type \"help\" because you need help");
    					
    				}
                      
                    
                      
                    // write on output stream based on the 
                    // answer from the client 
                    
            
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 

