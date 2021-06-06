package ru.jl1mbo.scoreboard.manager;

import cn.nukkit.Player;
import lombok.Getter;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

	@Getter
	private static final Map<String, ScoreboardBuilder> scoreboards = new HashMap<>();

	public static ScoreboardBuilder newBuilder(Player player) {
		return new ScoreboardBuilder(player);
	}

	public static ScoreboardBuilder getScoreboard(Player player) {
		return scoreboards.getOrDefault(player.getName().toLowerCase(), null);
	}

	public static void putScoreboard(Player player, ScoreboardBuilder scoreboardBuilder) {
		scoreboards.put(player.getName().toLowerCase(), scoreboardBuilder);
	}

	public static void removeScoreboard(Player player) {
		scoreboards.remove(player.getName().toLowerCase());
	}
}