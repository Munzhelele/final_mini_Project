/**
 * 
 */
package acsse.csc03a3;

/**
 * @author Munzhelele Awelani 221016083
 * @version Computer Science 3A minProject
 * candidate class that creates all the candidate and party objects 
 */
public class Candidate {

    // Attributes
    private String candidateName;
    private String partyName;
    private int voteCount;
    private String candidateID;

    // Constructor
    public Candidate(String candidateName, String partyName, int voteCount, String candidateID) {
        this.candidateName = candidateName;
        this.partyName = partyName;
        this.voteCount = voteCount;
        this.candidateID = candidateID;
    }

    // Getters and setters
    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void incrementVoteCount() {
        voteCount++;
    }

    
}