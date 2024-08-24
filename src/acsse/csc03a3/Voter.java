/**
 * 
 */
package acsse.csc03a3;

/**
 * @author  Munzhelele Awelani 221016083 
 * @version Computer Science 3A miniProject
 * Voter class the create all the Voter objects such as Voter ID etc 
 */
public class Voter  {
  /**
   * Attributes 
   */
	    private String fullName;
	    private String dateOfBirth;
	    private String partyAffiliation;
	    private String voterID;
	    private String resAddress;
	    private String username;
	    private String password;
	    

	    public Voter(String fullName, String dateOfBirth, String partyAffiliation, String address, String voterId) {
	        this.fullName = fullName;
	        this.dateOfBirth = dateOfBirth;
	        this.partyAffiliation = partyAffiliation;
	        this.resAddress = address;
	        this.voterID = voterId;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getDateOfBirth() {
	        return dateOfBirth;
	    }

	    public void setDateOfBirth(String dateOfBirth) {
	        this.dateOfBirth = dateOfBirth;
	    }

	    public String getPartyAffiliation() {
	        return partyAffiliation;
	    }

	    public void setPartyAffiliation(String partyAffiliation) {
	        this.partyAffiliation = partyAffiliation;
	    }

	    public String getVoterID() {
	        return voterID;
	    }

	    public void setVoterID(String voterID) {
	        this.voterID = voterID;
	    }

	    public String getResAddress() {
	        return resAddress;
	    }

	    public void setResAddress(String resAddress) {
	        this.resAddress = resAddress;
	    }
	    public String getUsername() {
	        return username;
	    }
	    
	    public void setUsername(String username) {
	        this.username = username;
	    }
	    
	    public String getPassword() {
	        return password;
	    }
	    
	    public void setPassword(String password) {
	        this.password = password;
	    }

	    
		
}
