package ru.jl1mbo.scoreboard;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;

public class ScoreboardListener implements Listener {

	@EventHandler()
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ScoreboardManager.getScoreboard(player).hideFor(player);
	}
}