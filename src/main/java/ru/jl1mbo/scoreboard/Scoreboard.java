package ru.jl1mbo.scoreboard;

import cn.nukkit.plugin.PluginBase;

public class Scoreboard extends PluginBase {

	private static Scoreboard instance;

	@Override()
	public void onEnable() {
		instance = this;
		this.getLogger().info("§6Scoreboard §aАктивирован§7!");
	}

	@Override()
	public void onDisable() {
		this.getLogger().info("§6Scoreboard §cДеактивирован§7!");
	}

	public static Scoreboard getInstance() {
		return instance;
	}
}