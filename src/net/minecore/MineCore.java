package net.minecore;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class MineCore extends JavaPlugin {
	
	public Logger log;
	MinerManager mm;
	
	@Override
	public void onLoad(){
		log = this.getLogger();
		mm = new MinerManager(this);
		
	}
	
	@Override
	public void onEnable(){
		
	}
	
	@Override
	public void onDisable(){
		
	}

}
