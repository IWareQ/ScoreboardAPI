package ru.jl1mbo.scoreboard.manager;

import ru.jl1mbo.scoreboard.ScoreboardBuilder;

public class ScoreboardManager {

	public static ScoreboardBuilder createScoreboard() {
		return new ScoreboardBuilder();
	}
}