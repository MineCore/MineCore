package net.minecore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Miner {
	
	private String playerName;
	private YamlConfiguration conf;

	public Miner(String playerName) {
		this.playerName = playerName;
		conf = new YamlConfiguration();
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public ConfigurationSection getConfigurationSection(String path){
		ConfigurationSection cs =  conf.getConfigurationSection(path);
		return cs == null ? conf.createSection(path) : cs;
	}
	
	public Object getValue(String path){
		return conf.get(path);
	}
	
	public void saveMinerToYamlFile(File f) throws FileNotFoundException, IOException, InvalidConfigurationException{
		conf.save(f);
	}
	
	public static Miner loadMinerFromYamlFile(String name, File f) throws IOException, InvalidConfigurationException{

		Miner m = new Miner(name);
		
		m.conf = new YamlConfiguration();
		m.conf.load(f);
		
		return m;
	}

}
