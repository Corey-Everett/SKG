package MicrotransactionsPackage;

//Java implementation for a client 
//Save file as Client.java 

import java.util.concurrent.TimeUnit;
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;  // HBox class in here
import javafx.scene.text.Text;
import javafx.scene.image.ImageView; 
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

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
		primaryStage.setTitle("Welcome to SKG's Game Rating System! Please wait while this loads.");
        primaryStage.setScene(s);
     	primaryStage.show();
     	
    	
     		//Show the main page at this point
     		
     		TextField nameTextField = new TextField();
     		
			Button createButton = new Button("Create");
			Button lookupButton = new Button("Lookup");
			Button exitButton = new Button("Exit");
			
			GridPane g = new GridPane();
	    	g.setHgap(10);
	    	g.setVgap(10);
			
	    	g.add(new Label("Enter the game name"), 0, 0);
	    	g.add(nameTextField, 1,0);
	    	g.add(createButton, 0, 3);
	    	g.add(lookupButton, 0, 4);	    	
	    	g.add(exitButton, 0, 5);
	    	g.add(image1, 0, 6);
	    	g.setAlignment(Pos.CENTER);
	    	
			Scene scene = new Scene(g,1200,400);
			primaryStage.setTitle("SKG - Game Ratings System");
			primaryStage.setScene(scene);
			primaryStage.show();
			
				createButton.setOnAction(e ->{			
												
				String gameName = nameTextField.getText();
				
					
				//makes a screen, and makes it the size of user's screen, so i can make next scene size of screen
				Screen screen = Screen.getPrimary();
				Rectangle2D bounds = screen.getVisualBounds();
				
			//	g.add(info, 0, 0);
				primaryStage.setX(bounds.getMinX());
				primaryStage.setY(bounds.getMinY());
				primaryStage.setWidth(bounds.getWidth());
				primaryStage.setHeight(bounds.getHeight());
				primaryStage.setTitle("Create Command");				
				primaryStage.show();
				
			});
				
				lookupButton.setOnAction(e ->{			
					
					String gameName = nameTextField.getText();
					String infoString  = ("The game name is: " + gameName);
					Text info = new Text(infoString);
					
					g.add(info, 1, 3);
					//makes a screen, and makes it the size of user's screen, so i can make next scene size of screen
					Screen screen = Screen.getPrimary();
					Rectangle2D bounds = screen.getVisualBounds();
					
					
					primaryStage.setX(bounds.getMinX());
					primaryStage.setY(bounds.getMinY());
					primaryStage.setWidth(bounds.getWidth());
					primaryStage.setHeight(bounds.getHeight());
					primaryStage.setTitle("Lookup Command");				
					primaryStage.show();
					
				});
				
				exitButton.setOnAction(e ->{			
					
				});
     	
	}

 public static void main(String[] args) throws IOException { 
	 launch(args);
	 
	 
     try {
      
         Scanner scn = new Scanner(System.in); 
           
         // getting localhost ip 
         InetAddress ip = InetAddress.getByName("localhost"); //ip is ip address of server goes here
   
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
