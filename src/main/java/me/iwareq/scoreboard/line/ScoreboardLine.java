package me.iwareq.scoreboard.line;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.iwareq.scoreboard.Scoreboard;

@Getter
@AllArgsConstructor
public class ScoreboardLine {

	private final Scoreboard scoreboard;
	private final String text;
}
