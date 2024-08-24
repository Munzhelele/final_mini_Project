package acsse.csc03a3;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
 


/**
 * This class manages the functionality of our Blockchain jarFile and implement it in such a way that it withhold the functionality of a voting application
 * @author  Munzhelele Awelani
 * @version Computer Science 3A 
 * @param <T>
 */
public class BlockchainManager<T> { 
	
	
	/**
	 * Attributes 
	 */
    private Blockchain<T> bChain;
    private CustomBlockchain<T> cBchain;
    private List<Voter> registeredVoters;
    private Map<String, String> credentials;
    private Set<String> registeredUsernames;

    private Map<Candidate, Integer> castVotes; 
    
    /**
     * constructor 
     * @param blockchain
     */

    public BlockchainManager(Blockchain<T> blockchain) {
        this.bChain = blockchain;
        this.registeredVoters = new ArrayList<>();
        this.credentials = new HashMap<>();
        this.registeredUsernames = new HashSet<>();
        this.castVotes = new HashMap<>(); // Initialize castVotes
    }

    /**
     * Method to cast a vote for a candidate
     * @param candidate
     */
    public void castVote(Candidate candidate) {
        // Check if the candidate exists in the map, and increment their vote count
        castVotes.put(candidate, castVotes.getOrDefault(candidate, 0) + 1);
    }

    /**
     * Method to get the vote count for a specific candidate
     * @param candidate
     * @return
     */
    public int getVoteCountForCandidate(Candidate candidate) {
        return castVotes.getOrDefault(candidate, 0);
    }

    // Add a method to register a new voter
    public void registerVoter(Voter voter) {
        registeredVoters.add(voter);
    }

    // Add a method to create and store credentials for a voter
    public void createCredentials(Voter voter, String username, String password) {
        if (voter == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for creating credentials.");
        }

        if (registeredUsernames.contains(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Securely store credentials (ideally using password hashing)
        voter.setUsername(username);
        voter.setPassword(hashPassword(password)); // Replace hashPassword with a suitable hashing function

        registeredUsernames.add(username);
    }

    // Method to hash a password using SHA-256
    String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle the exception according to your application's logic
            return null;
        }
    }

    // Method to convert byte array to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // Method to handle user login
    public boolean login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password are required.");
        }

        if (!registeredUsernames.contains(username)) {
            throw new IllegalArgumentException("Username not found.");
        }

        // Retrieve stored password hash for the given username
        String storedPasswordHash = credentials.get(username);
        if (storedPasswordHash == null) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        // Hash the provided password and compare it with the stored hash
        String hashedPassword = hashPassword(password);
        if (hashedPassword.equals(storedPasswordHash)) {
            return true; // Login successful
        } else {
            throw new IllegalArgumentException("Invalid username or password.");
        }
    }

    // Method to add a new voter with hashed password
    public void addVoter(String fullName, String dateOfBirth, String partyAffiliation, String address, String voterId, String username, String hashedPassword) {
        // Create a new Voter instance
        Voter newVoter = new Voter(fullName, dateOfBirth, partyAffiliation, address, voterId);

        // Register the new voter
        registerVoter(newVoter);
        createCredentials(newVoter, username, hashedPassword);
    }
 // Method to calculate and return the vote counts for each candidate

 // ... (BlockchainManager class)
    public Map<String, Integer> getVoteCounts() {
       Map<String, Integer> voteCounts = new HashMap<>();
       for (Block<T> block : cBchain.getChain()) {
           for (Transaction<T> transaction : block.getTransactions()) {
               String candidate = transaction.getReceiver(); // Get candidate from transaction
               voteCounts.put(candidate, voteCounts.getOrDefault(candidate, 0) + 1);
           }
       }
       return voteCounts;
    }




    // Method to check if a username already exists
    public boolean usernameExists(String username) {
        return registeredUsernames.contains(username);
    }

}
