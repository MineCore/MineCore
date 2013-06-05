package net.minecore;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

	private boolean useVaultEcon;
	private int currencyItemID;
	private MineCore mc;
	private Economy econ;

	public EconomyManager(boolean useVaultEcon, int currencyItem, MineCore mc) {
		this.useVaultEcon = useVaultEcon;
		this.currencyItemID = currencyItem;
		this.mc = mc;

		if (useVaultEcon) {
			if (setupEconomy())
				mc.log.info("Hooked into vault Economy!");
			else {
				mc.log.warning("Couldn't hook into Vault economy, defaulting to item currency");
				useVaultEcon = false;
			}
		}

	}

	/**
	 * Charges the player the given amount
	 * 
	 * @param p
	 *            Player to charge
	 * @param amt
	 *            Amount to charge them (converted to int if charged in items)
	 * @return true if it was successful
	 */
	public boolean charge(Player p, double amt) {

		if (useVaultEcon) {

			EconomyResponse resp = econ.withdrawPlayer(p.getName(), amt);

			return resp.transactionSuccess();
		} else {

			if (p.getInventory().contains(currencyItemID, (int) amt)) {
				p.getInventory().removeItem(new ItemStack(currencyItemID, (int) amt));
				return true;
			}

			return false;

		}
	}

	/**
	 * Gives the player the defined amount of money
	 * @param p  Player to give to
	 * @param amt Amount to give them (converted to int if items currency)
	 */
	public void give(Player p, double amt) {
		
		
		if (useVaultEcon) 
			econ.depositPlayer(p.getName(), amt);
		else
			for(ItemStack is : p.getInventory().addItem(new ItemStack(currencyItemID, (int) amt)).values())
				p.getLocation().getWorld().dropItem(p.getLocation(), is);

	}

	private boolean setupEconomy() {
		if (mc.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = mc.getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	/**
	 * @return Whether MineCore is using vault
	 */
	public boolean useVaultEcon() {
		return useVaultEcon;
	}

	/**
	 * @return the currencyItemID
	 */
	public int getCurrencyItemID() {
		return currencyItemID;
	}

	/**
	 * @param currencyItemID
	 *            the currencyItemID to set
	 */
	public void setCurrencyItemID(int currencyItemID) {
		this.currencyItemID = currencyItemID;
	}

}
