
package android.button;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Requester {
		Socket requestSocket;
		ObjectOutputStream out;
	 	ObjectInputStream in;
	 	String message;
	 	
	   // empty constructor 
	   Requester(){ }
		
	   // kills the connection to the server
	   public void kill()
	   {
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
	   }
	   
	   // establishes connection to the server
	   public void connect()
	   {
			try {
				requestSocket = new Socket("lore.cs.purdue.edu", 4242);
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(requestSocket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	   }
	   
	   // send a message to the server
	   public void sendMessage(String msg) {
		   try {
			   out.writeObject(msg);
			   out.flush();
			   System.out.println("client> " + msg);
		   } catch(IOException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   // grabs the specified information from the server
	   public void grabInformation()
	   {
			do {
				try {
					message = (String)in.readObject();
				} catch (Exception e) { }
				
				System.out.println("server> " + message);
				
			} while(!message.equals("END"));

	   } 
   }