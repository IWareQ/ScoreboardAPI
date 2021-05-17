package ru.jl1mbo.scoreboard;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;

public class Scoreboard extends PluginBase implements Listener {

	private static Scoreboard instance;

	@Override()
	public void onEnable() {
		instance = this;
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	public static Scoreboard getInstance() {
		return instance;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ScoreboardBuilder scoreboardBuilder = ScoreboardManager.getScoreboard(player);
		if (scoreboardBuilder != null) {
			scoreboardBuilder.hide();
		}
	}
}