package net.minecore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MineCore extends JavaPlugin {

	public Logger log;
	private MinerManager mm;
	private File playerFolder;
	private EconomyManager em;
	private FileConfiguration conf;
	private Metrics metrics;

	@Override
	public void onLoad() {

		log = this.getLogger();
		mm = new MinerManager(this);

		conf = this.getConfig();
		conf.options().copyDefaults(true);

		conf.addDefault("useVaultEcon", false);
		conf.addDefault("currency_item", 371);
		conf.addDefault("allowDataCollection", true);

		playerFolder = new File(this.getDataFolder().getPath() + File.separator + "players");
		playerFolder.mkdirs();

		for (File f : playerFolder.listFiles())
			if (f.getName().endsWith(".yml")) {
				try {
					mm.addMiner(Miner.loadMinerFromYamlFile(
							f.getName().substring(0, f.getName().indexOf('.')), f));
				} catch (IOException e) {
					log.warning("Encountered error while loading player file " + f.getPath() + ": "
							+ e.getMessage());
				} catch (InvalidConfigurationException e) {
					log.warning("Invalid player file " + f.getPath() + ": " + e.getMessage());
				}
			}

		saveConf();

	}

	public void saveConf() {
		try {
			conf.save(new File(this.getDataFolder().getPath() + File.separator + "config.yml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onEnable() {

		boolean useVaultEcon = conf.getBoolean("useVaultEcon");
		Material currencyItem = Material.matchMaterial(conf.getString("currency_item"));

		em = new EconomyManager(useVaultEcon, currencyItem, this);

		if (conf.getBoolean("allowDataCollection")) {
			try {
				metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void onDisable() {
		for (Miner m : mm.getMiners().values())
			try {
				log.info("Saving player " + m.getPlayerName());
				m.saveMinerToYamlFile(new File(playerFolder.getPath() + File.separator
						+ m.getPlayerName() + ".yml"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public MinerManager getMinerManager() {
		return mm;
	}

	public Miner getMiner(String name) {
		return mm.getMiner(name);
	}

	public EconomyManager getEconomyManager() {
		return em;
	}

}
