package ru.jl1mbo.scoreboard;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;

public class Scoreboard extends PluginBase {

	@Getter
	private static Scoreboard instance;

	@Override()
	public void onEnable() {
		instance = this;
		this.getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
	}

	private static class ScoreboardListener implements Listener {

		@EventHandler
		public void onPlayerQuit(PlayerQuitEvent event) {
			ScoreboardBuilder scoreboardBuilder = ScoreboardManager.getScoreboard(event.getPlayer());
			if (scoreboardBuilder != null) {
				scoreboardBuilder.hide();
			}
		}
	}
}