package ru.jl1mbo.scoreboard.manager;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;

public class ScoreboardManager {

	public static final Map<Player, ScoreboardBuilder> scoreboards = new HashMap<>();

	public static ScoreboardBuilder createScoreboard() {
		return new ScoreboardBuilder();
	}

	public static Map<Player, ScoreboardBuilder> getScoreboards() {
		return scoreboards;
	}

	public static ScoreboardBuilder getScoreboard(Player player) {
		return scoreboards.getOrDefault(player, null);
	}
}