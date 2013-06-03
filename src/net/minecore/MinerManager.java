package net.minecore;

import java.util.TreeMap;

import org.bukkit.entity.Player;

public class MinerManager {
	
	private MineCore mc;
	private TreeMap<String, Miner> miners;
	
	
	public MinerManager(MineCore m){
		mc = m;
		miners = new TreeMap<String, Miner>();
	}
	
	public Miner getMiner(String name){
		if(miners.get(name) == null)
			addMiner(new Miner(name));
		return miners.get(name);
	}
	
	public void addMiner(Miner miner) {
		miners.put(miner.getPlayerName(), miner);
	}

	public Miner getMiner(Player p){
		return getMiner(p.getName());
	}

}
