package com.Ben12345rocks.AdvancedCore.Commands.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.Ben12345rocks.AdvancedCore.Main;
import com.Ben12345rocks.AdvancedCore.Util.Inventory.BInventory;
import com.Ben12345rocks.AdvancedCore.Util.Inventory.BInventory.ClickEvent;
import com.Ben12345rocks.AdvancedCore.Util.Inventory.BInventoryButton;
import com.Ben12345rocks.AdvancedCore.Util.Misc.ArrayUtils;
import com.Ben12345rocks.AdvancedCore.Util.Misc.PlayerUtils;
import com.Ben12345rocks.AdvancedCore.Util.ValueRequest.ValueRequest;
import com.Ben12345rocks.AdvancedCore.Util.ValueRequest.Listeners.StringListener;

/**
 * The Class UserGUI.
 */
public class UserGUI {

	/** The instance. */
	static UserGUI instance = new UserGUI();

	/**
	 * Gets the single instance of UserGUI.
	 *
	 * @return single instance of UserGUI
	 */
	public static UserGUI getInstance() {
		return instance;
	}

	/** The plugin. */
	Main plugin = Main.plugin;

	/** The plugin buttons. */
	private HashMap<Plugin, BInventory> pluginButtons = new HashMap<Plugin, BInventory>();

	/**
	 * Instantiates a new user GUI.
	 */
	private UserGUI() {
	}

	/**
	 * Adds the plugin button.
	 *
	 * @param plugin
	 *            the plugin
	 * @param inv
	 *            the inv
	 */
	public synchronized void addPluginButton(Plugin plugin, BInventory inv) {
		pluginButtons.put(plugin, inv);
	}

	/**
	 * Gets the current player.
	 *
	 * @param player
	 *            the player
	 * @return the current player
	 */
	public String getCurrentPlayer(Player player) {
		return (String) PlayerUtils.getInstance().getPlayerMeta(player, "UserGUI");
	}

	/**
	 * Open user GUI.
	 *
	 * @param player
	 *            the player
	 * @param playerName
	 *            the player name
	 */
	public void openUserGUI(Player player, String playerName) {
		if (!player.hasPermission("AdvancedCore.UserEdit")) {
			player.sendMessage("Not enough permissions");
			return;
		}
		BInventory inv = new BInventory("UserGUI: " + playerName);

		for (Entry<Plugin, BInventory> entry : pluginButtons.entrySet()) {
			inv.addButton(inv.getNextSlot(),
					new BInventoryButton(entry.getKey().getName(), new String[] {}, new ItemStack(Material.STONE)) {

						@Override
						public void onClick(ClickEvent clickEvent) {
							for (Plugin p : pluginButtons.keySet()) {
								if (p.getName().equals(clickEvent.getClickedItem().getItemMeta().getDisplayName())) {
									pluginButtons.get(p).openInventory(player);
									return;
								}
							}
						}
					});
		}

		inv.openInventory(player);
	}

	/**
	 * Open users GUI.
	 *
	 * @param player
	 *            the player
	 */
	public void openUsersGUI(Player player) {
		if (!player.hasPermission("AdvancedCore.UserEdit")) {
			player.sendMessage("Not enough permissions");
			return;
		}

		ArrayList<String> players = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			players.add(p.getName());
		}
		new ValueRequest().requestString(player, "", ArrayUtils.getInstance().convert(players), true,
				new StringListener() {

					@Override
					public void onInput(Player player, String value) {
						setCurrentPlayer(player, value);
						openUserGUI(player, value);
					}
				});
	}

	/**
	 * Sets the current player.
	 *
	 * @param player
	 *            the player
	 * @param playerName
	 *            the player name
	 */
	private void setCurrentPlayer(Player player, String playerName) {
		PlayerUtils.getInstance().setPlayerMeta(player, "UserGUI", playerName);
	}
}