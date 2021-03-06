package com.Ben12345rocks.AdvancedCore.Util.Misc;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.Objects.User;
import com.Ben12345rocks.AdvancedCore.Thread.Thread;

public class PlayerUtils {
	/** The instance. */
	static PlayerUtils instance = new PlayerUtils();

	public static PlayerUtils getInstance() {
		return instance;
	}

	/** The plugin. */
	AdvancedCoreHook plugin = AdvancedCoreHook.getInstance();

	private PlayerUtils() {
	}

	/**
	 * Gets the player meta.
	 *
	 * @param player
	 *            the player
	 * @param str
	 *            the str
	 * @return the player meta
	 */
	public Object getPlayerMeta(Player player, String str) {
		for (MetadataValue meta : player.getMetadata(str)) {
			if (meta.getOwningPlugin().equals(plugin.getPlugin())) {
				return meta.value();
			}
		}
		return null;
	}

	public synchronized String getPlayerName(String uuid) {
		if ((uuid == null) || uuid.equalsIgnoreCase("null")) {
			plugin.debug("Null UUID");
			return null;
		}

		String name = "";

		java.util.UUID u = java.util.UUID.fromString(uuid);
		Player player = Bukkit.getPlayer(uuid);
		if (player == null) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(u);
			if (p.hasPlayedBefore() || p.isOnline()) {
				name = p.getName();
			} else {
				name = Thread.getInstance().getThread().getName(u);
			}
		} else {
			name = player.getName();
		}

		if (name.equals("")) {
			name = "Error getting name";
		}
		return name;

	}

	public synchronized String getPlayerName(User user, String uuid) {
		if ((uuid == null) || uuid.equalsIgnoreCase("null")) {
			plugin.debug("Null UUID");
			return null;
		}

		String name = "";
		String storedName = user.getData().getString("PlayerName");

		java.util.UUID u = java.util.UUID.fromString(uuid);
		Player player = Bukkit.getPlayer(uuid);
		if (player == null) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(u);
			if (p.getFirstPlayed() != 0) {
				name = p.getName();
			} else {
				name = storedName;
				if (name.equals("")) {
					name = Thread.getInstance().getThread().getName(u);
				}
			}
		} else {
			name = player.getName();
		}

		if (!name.equals("")) {
			if (!name.equals(storedName) && !storedName.equals("")) {
				user.getData().setString("PlayerName", name);
			}
		} else {
			name = "Error getting name";
		}
		return name;

	}

	/**
	 * Gets the uuid.
	 *
	 * @param playerName
	 *            the player name
	 * @return the uuid
	 */
	@SuppressWarnings("deprecation")
	public String getUUID(String playerName) {
		if (playerName == null) {
			return null;
		}
		Player player = Bukkit.getPlayer(playerName);
		if (player == null) {
			return Bukkit.getOfflinePlayer(playerName).getUniqueId().toString();
		} else {
			return player.getUniqueId().toString();
		}
	}

	/**
	 * Checks for permission.
	 *
	 * @param sender
	 *            the sender
	 * @param perm
	 *            the perm
	 * @return true, if successful
	 */
	public boolean hasPermission(CommandSender sender, String perm) {
		return sender.hasPermission(plugin.getPlugin().getName() + "." + perm);
	}

	/**
	 * Checks for permission.
	 *
	 * @param player
	 *            the player
	 * @param perm
	 *            the perm
	 * @return true, if successful
	 */
	public boolean hasPermission(Player player, String perm) {
		return player.hasPermission(plugin.getPlugin().getName() + "." + perm);
	}

	/**
	 * Checks for permission.
	 *
	 * @param playerName
	 *            the player name
	 * @param perm
	 *            the perm
	 * @return true, if successful
	 */
	public boolean hasPermission(String playerName, String perm) {
		if (playerName == null) {
			return false;
		}
		Player player = Bukkit.getPlayer(playerName);
		if (player != null) {
			return player.hasPermission(plugin.getPlugin().getName() + "." + perm);
		}
		return false;
	}

	/**
	 * Checks for server permission.
	 *
	 * @param playerName
	 *            the player name
	 * @param perm
	 *            the perm
	 * @return true, if successful
	 */
	public boolean hasServerPermission(String playerName, String perm) {
		if (playerName == null) {
			return false;
		}
		Player player = Bukkit.getPlayer(playerName);
		if (player != null) {
			return player.hasPermission(perm);
		}
		return false;
	}

	/**
	 * Checks if is player.
	 *
	 * @param sender
	 *            the sender
	 * @return true, if is player
	 */
	public boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is player online.
	 *
	 * @param playerName
	 *            the player name
	 * @return true, if is player online
	 */
	public boolean isPlayerOnline(String playerName) {
		if (playerName == null) {
			return false;
		}
		Player player = Bukkit.getPlayer(playerName);
		if (player != null) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the player meta.
	 *
	 * @param player
	 *            the player
	 * @param str
	 *            the str
	 * @param value
	 *            the value
	 */
	public void setPlayerMeta(Player player, String str, Object value) {
		player.removeMetadata(str, plugin.getPlugin());
		player.setMetadata(str, new MetadataValue() {

			@Override
			public boolean asBoolean() {

				return false;
			}

			@Override
			public byte asByte() {

				return 0;
			}

			@Override
			public double asDouble() {

				return 0;
			}

			@Override
			public float asFloat() {

				return 0;
			}

			@Override
			public int asInt() {

				return 0;
			}

			@Override
			public long asLong() {

				return 0;
			}

			@Override
			public short asShort() {

				return 0;
			}

			@Override
			public String asString() {

				return null;
			}

			@Override
			public Plugin getOwningPlugin() {
				return plugin.getPlugin();
			}

			@Override
			public void invalidate() {
			}

			@Override
			public Object value() {
				return value;
			}

		});
	}

}
