package acsse.csc03a3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomBlock<T> extends Block<T> {

    private String previousHash;
    private String hash;
    private long nonce;  // For mining
    private int index; 
    private long timestamp;
    private int difficulty;

    // Constructor: Initializes the block with the given previous hash and transactions.
    public CustomBlock(String previousHash, List<Transaction<T>> transactions) {
        super(previousHash, transactions);
        this.hash = this.calculateHash();
        this.nonce = 0; 
    }

    // Sets the previous hash of this block and recalculates the hash if necessary.
    public void setPreviousHash(String previousHash) {
        if (!this.previousHash.equals(previousHash)) {
            this.previousHash = previousHash;
            this.hash = this.calculateHash();
        }
    }
    
    

    public void mineBlock(int difficulty) {
        this.difficulty = difficulty; 
        String target = new String(new char[difficulty]).replace('\0', '0'); 
        
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        timestamp = System.currentTimeMillis() / 1000L; // Set timestamp after mining
        System.out.println("Block Mined!!! : " + hash);
    }

    

    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dataToHash = previousHash 
                             + getTransactions().toString() // Assuming the hash needs transaction data
                             + Long.toString(nonce);
            byte[] hashBytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    // Delegation of List methods to the internal transactions list
    public int size() {
        return getTransactions().size();
    }

    public boolean isEmpty() {
        return getTransactions().isEmpty();
    }

    public boolean contains(Object o) {
        return getTransactions().contains(o);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getDifficulty() {
        return difficulty;
    }

    
}
