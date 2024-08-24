/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acsse.csc03a3.Block;
import acsse.csc03a3.Blockchain;
import acsse.csc03a3.CustomBlock;
import acsse.csc03a3.CustomBlockchain;
import acsse.csc03a3.Transaction;

/**
 * @author Munzhelele Awelani 221016083
 * @version Computer Science 3A miniProject
 * The Server class that establishes the Socket connection and also handles requests from the clients
 */
public class VotingServer { 
	
  /**
   * Attributes 
   */ 
	private static final int PORT = 8080;
    private Map<String, String> registeredUsers = new HashMap<>();
    private CustomBlockchain<String> blockchain = new CustomBlockchain<>(); // Shared blockchain
 
	
  /**
   * constructor 
   */ 
	public VotingServer() {
		this.registeredUsers = new HashMap<>();
	} 
	/**
	 * method to establish the connection 
	 * 
	 */ 
	public void startServer() {
		try(ServerSocket serverSocket = new ServerSocket(PORT)){ 
			System.out.println("Server running on port "+PORT);
			
			while(true) {
				Socket socketConnection =  serverSocket.accept();
				System.out.println("Connection established at "+socketConnection.getInetAddress());
				ClientHandler handler = new ClientHandler(socketConnection);
				new Thread(handler);
			}
			
			
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}  
private class ClientHandler implements Runnable {
		/**
		 * Attributes
		 */ 
		 private Socket clientSocket;
		 private BufferedReader rd;
		 private PrintWriter wr; 
		 private Blockchain<String> blockChain;
		 private Map<String,String> registeredUsers;
		 private CustomBlockchain<String> bchain;
		private Object voterId;
		 
		 public ClientHandler(Socket clientSocket) {
	         this.clientSocket = clientSocket; 
	         this.blockChain = new Blockchain<>();
	         this.bchain = blockchain; 
	 		this.registeredUsers = new HashMap<>();
	         try {
	             rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	             wr = new PrintWriter(clientSocket.getOutputStream(), true);
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	     }

	     @Override

	     public void run() {
	         try (
	             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
	         ) {
	             // Read client request
	             String request = reader.readLine();
	             System.out.println("Received request from client: " + request);
	             
	             // Process request
	             if (request.startsWith("REGISTER")) {
	                 String[] parts = request.split("\\s+");
	                 if (parts.length == 3) {
	                     String username = parts[1];
	                     String password = parts[2];
	                     // Call method to handle registration
	                     boolean success = registerUser(username, password);
	                     if (success) {
	                         writer.println("Registration successful");
	                     } else {
	                         writer.println("Registration failed");
	                     }
	                 } else {
	                     writer.println("Invalid registration request");
	                 }
	             } else if (request.startsWith("LOGIN")) {
	                 String[] parts = request.split("\\s+");
	                 if (parts.length == 3) {
	                     String username = parts[1];
	                     String password = parts[2];
	                     // Call method to handle login
	                     boolean success = loginUser(username, password);
	                     if (success) {
	                         writer.println("Login successful");
	                     } else {
	                         writer.println("Login failed");
	                     }
	                 } else {
	                     writer.println("Invalid login request");
	                 }
	             } else if (request.startsWith("VOTE")) {
	                 // Process vote request
	                 String[] parts = request.split("\\s+");
	                 if (parts.length == 3) {
	                     String candidate = parts[1];
	                     String voterId = parts[2];
	                     // Call method to cast vote
	                     castVote(candidate, voterId);
	                 } else {
	                     writer.println("Invalid vote request");
	                 }
	             } else if (request.startsWith("RESULTS")) {
	                 // Process results request
	                 sendResults();
	             } else {
	                 // Invalid request
	                 writer.println("Invalid request");
	             }
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	     }


	 

	     private synchronized boolean registerUser(String username, String password) {
	    	    if (!registeredUsers.containsKey(username)) {
	    	        registeredUsers.put(username, password);
	    	        List<Transaction<String>> transactions = new ArrayList<>();
	    	        transactions.add(new Transaction<>("System", "Registration", username)); // Changed voterId to username
	    	        CustomBlock<String> newBlock = new CustomBlock<>(bchain.getLatestBlock().getHash(), transactions);
	    	        bchain.addBlock(newBlock);
	    	        wr.println("REGISTER_SUCCESS");
	    	        return true;
	    	    } else {
	    	        wr.println("ERROR: Username already exists.");
	    	        return false;
	    	    }
	    	}



	     private boolean loginUser(String username, String password) {
	         if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password)) {
	             wr.println("LOGIN_SUCCESS");
	         } else {
	             wr.println("LOGIN_FAILED");
	         }
			return true;
	     }

	    

	     private void castVote(String candidate, String voterId) {
	            List<Transaction<String>> transactions = new ArrayList<>();
	            transactions.add(new Transaction<>("Voter", candidate, voterId));
	            CustomBlock<String> newBlock = new CustomBlock<>(bchain.getLatestBlock().getHash(), transactions);
	            bchain.addBlock(newBlock);  
	            wr.println("VOTE_SUCCESS");
	        }





	     private void sendResults() {
	         // Get the voting results from the blockchain
	         List<Block<String>> chain = bchain.getChain();
	         StringBuilder resultBuilder = new StringBuilder();
	         for (Block<String> block : chain) {
	             List<Transaction<String>> transactions = block.getTransactions();
	             for (Transaction<String> transaction : transactions) {
	                 resultBuilder.append("Candidate: ").append(transaction.getReceiver())
	                         .append(", Voter ID: ").append(transaction.getData())
	                         .append("\n");
	             }
	         }
	         // Send the results to the client
	         wr.println("RESULTS\n" + resultBuilder.toString());
	     }
	 }



		



    public static void main(String[] args) {
        VotingServer server = new VotingServer();
        server.startServer();
    }

}
