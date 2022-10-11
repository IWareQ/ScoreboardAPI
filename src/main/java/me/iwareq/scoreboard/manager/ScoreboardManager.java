package me.iwareq.scoreboard.manager;

import cn.nukkit.Player;
import lombok.Getter;
import me.iwareq.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

	@Getter
	private final Map<String, Scoreboard> scoreboards = new HashMap<>();

	public Scoreboard getScoreboard(Player player) {
		return this.scoreboards.get(player.getName());
	}

	public void addScoreboard(Player player, Scoreboard scoreboard) {
		this.scoreboards.put(player.getName(), scoreboard);
	}

	public void removeScoreboard(Player player) {
		this.scoreboards.remove(player.getName());
	}
}
