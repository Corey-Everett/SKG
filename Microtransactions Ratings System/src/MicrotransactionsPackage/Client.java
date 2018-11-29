package MicrotransactionsPackage;

//Java implementation for a client 
//Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import javafx.application.Application; 
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;  // HBox class in here
import javafx.scene.image.ImageView; 
import javafx.geometry.Insets;

//Client class 
public class Client extends Application {  
	
	public void start(Stage primaryStage) 
	{
		// create images
		ImageView image1 = new ImageView("skglogo.png");
		

		// create pane
		HBox p = new HBox(50);
		
		// add spacing around images
		p.setPadding(new Insets(30));

		// place images on pane
		p.getChildren().addAll(image1);
		
		// create scene and place pane on it
		Scene s = new Scene(p);

		// place pane on stage
		primaryStage.setTitle("Welcome to SKG's Game Rating System!");
        primaryStage.setScene(s);
     	primaryStage.show();
	}

 public static void main(String[] args) throws IOException { 
	 launch(args);
	 
	 
     try {
      
         Scanner scn = new Scanner(System.in); 
           
         // getting localhost ip 
         InetAddress ip = InetAddress.getByName("localhost"); 
   
         // establish the connection with server port 5056 
         Socket s = new Socket(ip, 6789); 
   
         // obtaining input and out streams 
         DataInputStream dis = new DataInputStream(s.getInputStream()); 
         DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
   
         // the following loop performs the exchange of 
         // information between client and client handler 
         while (true) { 
          
             System.out.println(dis.readUTF()); 
             String tosend = scn.nextLine(); 
             dos.writeUTF(tosend); 
                
             // If client sends exit, this line of code closes the connection and then breaks from the while loop.
             if(tosend.toLowerCase().equals("exit")) {System.out.println("Closing this connection : " + s); s.close(); 
             System.out.println("Connection closed");
             System.exit(0);
             break;
             }    
             
             // printing 
             String received = dis.readUTF(); 
             System.out.println(received); 
         } 
           
         // closing resources 
         scn.close(); 
         dis.close(); 
         dos.close();
         
     }catch(Exception e) {e.printStackTrace();}  // Last-second error-catch.
          
 } 
} 
