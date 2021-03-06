package com.Ben12345rocks.AdvancedCore.Util.Javascript;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.UserManager.UserManager;

public class JavascriptEngine {
	private HashMap<String, Object> engineAPI;

	public JavascriptEngine() {
		engineAPI = new HashMap<String, Object>();
	}

	public JavascriptEngine addPlayer(CommandSender player) {
		addToEngine("CommandSender", player);
		addToEngine("Player", player);
		return this;
	}

	public JavascriptEngine addPlayer(Player player) {
		addToEngine("Player", player);
		addToEngine("PlayerName", player.getName());
		addToEngine("PlayerUUID", player.getUniqueId().toString());
		addToEngine("AdvancedCoreUser", UserManager.getInstance().getUser(player));
		addToEngine("CommandSender", player);

		for (JavascriptPlaceholderRequest request : AdvancedCoreHook.getInstance().getJavascriptEngineRequests()) {
			addToEngine(request.getStr(), request.getObject(player));
		}
		return this;
	}

	public JavascriptEngine addToEngine(HashMap<String, Object> engineAPI) {
		if (engineAPI != null && !engineAPI.isEmpty()) {
			this.engineAPI.putAll(engineAPI);
		}
		return this;
	}

	public JavascriptEngine addToEngine(String text, Object ob) {
		engineAPI.put(text, ob);
		return this;
	}

	public void execute(String expression) {
		getResult(expression);
	}

	public boolean getBooleanValue(String expression) {
		Object result = getResult(expression);
		if (result != null) {
			try {
				return ((boolean) result);
			} catch (Exception e) {
				AdvancedCoreHook.getInstance().debug(e);
			}
		}
		return false;
	}

	public Object getResult(String expression) {
		if (!expression.equals("")) {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
			engine.put("Bukkit", Bukkit.getServer());
			engine.put("AdvancedCore", AdvancedCoreHook.getInstance());
			engine.put("Console", Bukkit.getConsoleSender());

			engineAPI.putAll(AdvancedCoreHook.getInstance().getJavascriptEngine());

			for (Entry<String, Object> entry : engineAPI.entrySet()) {
				engine.put(entry.getKey(), entry.getValue());
			}

			try {
				return engine.eval(expression);
			} catch (ScriptException e) {
				AdvancedCoreHook.getInstance().getPlugin().getLogger().warning(
						"Error occoured while evaluating javascript, turn debug on to see stacktrace: " + e.toString());
				AdvancedCoreHook.getInstance().debug(e);
			}
		}
		return null;
	}

	public String getStringValue(String expression) {
		Object result = getResult(expression);
		if (result != null) {
			try {
				return result.toString();
			} catch (Exception e) {
				AdvancedCoreHook.getInstance().debug(e);
			}
		}
		return "";
	}
}
