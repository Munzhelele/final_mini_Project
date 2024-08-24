package acsse.csc03a3;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockchain<T> extends Blockchain<T> {
    private List<Block<T>> chain = new ArrayList<>();
    private static final int BASE_DIFFICULTY = 4; // Starting difficulty
    private static final int ADJUSTMENT_INTERVAL = 10; // Adjust difficulty every 10 blocks
    private static final int TARGET_BLOCK_TIME = 10; // Desired time per block (in seconds)


    public CustomBlockchain() {
        Block<T> genesisBlock = new Block<>("0", new ArrayList<>());
        chain.add(genesisBlock);

    public CustomBlock<T> getLatestBlock() {
        return (CustomBlock<T>) chain.get(chain.size() - 1); // Return the last block in the chain
    }
    public void addBlock(CustomBlock<T> newBlock) {
        newBlock.setPreviousHash(getLatestBlock().getHash()); 
        newBlock.mineBlock(this.getDifficulty());  
        chain.add(newBlock);                                
    }
    public int getDifficulty() {
        CustomBlock<T> latestBlock = getLatestBlock();
        if (latestBlock.getIndex() % ADJUSTMENT_INTERVAL == 0 && latestBlock.getIndex() != 0) {
            return getAdjustedDifficulty(latestBlock, chain);
        } else {
            return latestBlock.getDifficulty();
        }
    }

    private int getAdjustedDifficulty(CustomBlock<T> latestBlock, List<Block<T>> blockchain) {
        CustomBlock<T> prevAdjustmentBlock = (CustomBlock<T>) blockchain.get(blockchain.size() - ADJUSTMENT_INTERVAL);
        long timeExpected = ADJUSTMENT_INTERVAL * TARGET_BLOCK_TIME;
        long timeTaken = latestBlock.getTimestamp() - prevAdjustmentBlock.getTimestamp();

        if (timeTaken < timeExpected / 2) { // Blocks mined too fast
            return prevAdjustmentBlock.getDifficulty() + 1;
        } else if (timeTaken > timeExpected * 2) { // Blocks mined too slow
            return prevAdjustmentBlock.getDifficulty() - 1;
        } else {
            return prevAdjustmentBlock.getDifficulty();
        }
    }
}
