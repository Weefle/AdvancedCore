package com.Ben12345rocks.AdvancedCore.Objects;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

public class RewardBuilder {
	private FileConfiguration data;
	private String prefix = "";
	private String path;
	private Reward reward;
	private HashMap<String, String> placeholders;
	private boolean giveOffline;
	private boolean online;
	private boolean checkTimed = true;

	public RewardBuilder(FileConfiguration data, String path) {
		this.data = data;
		this.path = path;
		giveOffline = true;
		placeholders = new HashMap<String, String>();
		LocalDateTime ldt = LocalDateTime.now();
		Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		placeholders.put("Date", "" + new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(date));
	}

	public RewardBuilder(Reward reward) {
		this.reward = reward;
		giveOffline = true;
		placeholders = new HashMap<String, String>();
		LocalDateTime ldt = LocalDateTime.now();
		Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		placeholders.put("Date", "" + new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(date));
	}

	public RewardBuilder checkOffline(boolean giveOffline) {
		this.giveOffline = giveOffline;
		return this;
	}

	/**
	 * @return the placeholders
	 */
	public HashMap<String, String> getPlaceholders() {
		return placeholders;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return the reward
	 */
	public Reward getReward() {
		return reward;
	}

	/**
	 * @return the checkTimed
	 */
	public boolean isCheckTimed() {
		return checkTimed;
	}

	public boolean isGiveOffline() {
		return giveOffline;
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	@SuppressWarnings("deprecation")
	public void send(User user) {
		if (reward == null) {
			RewardHandler.getInstance().giveReward(user, prefix, data, path, online, giveOffline, checkTimed,
					placeholders);
		} else {
			RewardHandler.getInstance().giveReward(user, reward, isGiveOffline(), isGiveOffline(), checkTimed,
					placeholders);
		}
	}

	public void send(User... users) {
		for (User user : users) {
			send(user);
		}
	}

	public RewardBuilder setCheckTimed(boolean b) {
		checkTimed = b;
		return this;
	}

	public RewardBuilder setGiveOffline(boolean giveOffline) {
		this.giveOffline = giveOffline;
		return this;
	}

	public RewardBuilder setOnline(boolean online) {
		this.online = online;
		return this;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public RewardBuilder withPlaceHolder(HashMap<String, String> placeholders) {
		this.placeholders.putAll(placeholders);
		return this;
	}

	public RewardBuilder withPlaceHolder(String toReplace, String replaceWith) {
		placeholders.put(toReplace, replaceWith);
		return this;
	}

	public RewardBuilder withPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

}
