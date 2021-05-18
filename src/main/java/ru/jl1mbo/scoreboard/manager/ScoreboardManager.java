package ru.jl1mbo.scoreboard.manager;

import cn.nukkit.Player;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

	private static final Map<String, ScoreboardBuilder> SCOREBOARDS = new HashMap<>();

	public static ScoreboardBuilder createScoreboard(Player player) {
		return new ScoreboardBuilder(player);
	}

	public static Map<String, ScoreboardBuilder> getScoreboards() {
		return SCOREBOARDS;
	}

	public static ScoreboardBuilder getScoreboard(Player player) {
		return SCOREBOARDS.getOrDefault(player.getName().toLowerCase(), null);
	}

	public static void putScoreboard(Player player, ScoreboardBuilder scoreboardBuilder) {
		SCOREBOARDS.put(player.getName().toLowerCase(), scoreboardBuilder);
	}

	public static void removeScoreboard(Player player) {
		SCOREBOARDS.remove(player.getName().toLowerCase());
	}
}