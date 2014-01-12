package net.minecore;

import java.util.TreeMap;

import org.bukkit.entity.Player;

/**
 * Stores and Manipulates Miners
 * @author The Numenorean
 *
 */
public class MinerManager {
	
	private TreeMap<String, Miner> miners;
	
	
	public MinerManager(){
		miners = new TreeMap<String, Miner>();
	}
	
	/**
	 * Gets a Miner by player name.
	 * @param name The player name
	 * @return A Miner
	 */
	public Miner getMiner(String name){
		if(miners.get(name) == null)
			addMiner(new Miner(name));
		return miners.get(name);
	}
	
	/**
	 * Adds the given Miner to the MinerManager
	 * @param miner
	 */
	public void addMiner(Miner miner) {
		miners.put(miner.getPlayerName(), miner);
	}

	/**
	 * Gets the Miner for a given player
	 * @param p a player
	 * @return a Miner instance
	 */
	public Miner getMiner(Player p){
		return getMiner(p.getName());
	}
	
	/**
	 * Gets all the Miners stored in this MinerManager
	 * @return
	 */
	public TreeMap<String, Miner> getMiners(){
		return miners;
	}

}
