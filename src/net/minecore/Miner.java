package net.minecore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Stores data for a player.
 * @author The Numenorean
 *
 */
public class Miner {
	
	private String playerName;
	private YamlConfiguration conf;

	/**
	 * Creates a player with a blank configuration and the given player name
	 * @param playerName The Name of the player.
	 */
	public Miner(String playerName) {
		this.playerName = playerName;
		conf = new YamlConfiguration();
	}

	/**
	 * Gets the name of the player this represents.
	 * @return The string representation of a player
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Gets a configuration section from this miner's conf, or creates it if it doesnt exist.
	 * @param path The path to the section
	 * @return A ConfigurationSection
	 */
	public ConfigurationSection getConfigurationSection(String path){
		ConfigurationSection cs =  conf.getConfigurationSection(path);
		return cs == null ? conf.createSection(path) : cs;
	}
	
	/**
	 * Helper method. Gets a value from the conf for this miner.
	 * @param path The path to the value.
	 * @return An objectt, or null
	 */
	public Object getValue(String path){
		return conf.get(path);
	}
	
	/**
	 * Helper method. Sets a value.
	 * @param path Path to the value
	 * @param value The value to set.
	 */
	public void setValue(String path, Object value){
		conf.set(path, value);
	}
	
	/**
	 * Gets the base configuration that the data is stored in.
	 * @return A Configuration
	 */
	public Configuration getConfiguration(){
		return conf;
	}
	
	/**
	 * Saves all the player data to a yaml file at the given location.
	 * @param f File to save to.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidConfigurationException
	 */
	public void saveMinerToYamlFile(File f) throws FileNotFoundException, IOException, InvalidConfigurationException{
		conf.save(f);
	}
	
	/**
	 * Loads player data from the given yaml file and returns a new Miner
	 * @param name Name of the player
	 * @param f File to load from
	 * @return A new Miner
	 * @throws IOException
	 * @throws InvalidConfigurationException
	 */
	public static Miner loadMinerFromYamlFile(String name, File f) throws IOException, InvalidConfigurationException{

		Miner m = new Miner(name);
		
		m.conf = new YamlConfiguration();
		m.conf.load(f);
		
		return m;
	}

}
